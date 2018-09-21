package io.flatbufferx.processor;

import io.flatbufferx.core.annotation.OnJsonParseComplete;
import io.flatbufferx.processor.processor.JsonObjectHolder;
import io.flatbufferx.processor.processor.TypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class OnJsonParseCompleteProcessor extends MethodProcessor {

    public OnJsonParseCompleteProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public Class getAnnotation() {
        return OnJsonParseComplete.class;
    }

    @Override
    public void findAndParseObjects(RoundEnvironment env, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements, Types types) {
        for (Element element : env.getElementsAnnotatedWith(OnJsonParseComplete.class)) {
            try {
                processOnCompleteMethodAnnotation(element, jsonObjectMap, elements);
            } catch (Exception e) {
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));

                error(element, "Unable to generate injector for %s. Stack trace incoming:\n%s", OnJsonParseComplete.class, stackTrace.toString());
            }
        }
    }

    private void processOnCompleteMethodAnnotation(Element element, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements) throws Exception {
        if (!isCallbackMethodAnnotationValid(element, OnJsonParseComplete.class.getSimpleName())) {
            return;
        }

        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        ExecutableElement executableElement = (ExecutableElement)element;
        JsonObjectHolder objectHolder = jsonObjectMap.get(TypeUtils.getInjectedFQCN(enclosingElement, elements));
        objectHolder.onCompleteCallback = executableElement.getSimpleName().toString();
    }
}
