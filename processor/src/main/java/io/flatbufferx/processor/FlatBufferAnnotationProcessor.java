package io.flatbufferx.processor;

import io.flatbufferx.processor.processor.FlatBuffersInjector;
import io.flatbufferx.processor.processor.JsonObjectHolder;
import io.flatbufferx.processor.processor.ObjectMapperInjector;
import io.flatbufferx.core.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import static javax.tools.Diagnostic.Kind.ERROR;

public class FlatBufferAnnotationProcessor extends AbstractProcessor {
    private Elements mElementUtils;
    private Types mTypeUtils;
    private Filer mFiler;
    private List<Processor> mProcessors;
    private Map<String, JsonObjectHolder> mJsonObjectMap;
    private boolean mLoaderWritten;
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        mElementUtils = env.getElementUtils();
        mTypeUtils = env.getTypeUtils();
        mFiler = env.getFiler();
        mJsonObjectMap = new HashMap<>();
        mProcessors = Processor.allProcessors(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportTypes = new LinkedHashSet<>();
        for (Processor processor : mProcessors) {
            supportTypes.add(processor.getAnnotation().getCanonicalName());
        }
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {
        try {
            for (Processor processor : mProcessors) {
                processor.findAndParseObjects(env, mJsonObjectMap, mElementUtils, mTypeUtils);
            }

            if (!mLoaderWritten) {
                mLoaderWritten = true;

                final FlatBuffersInjector loaderInjector = new FlatBuffersInjector(mJsonObjectMap.values());
                try {
                    JavaFileObject jfo = mFiler.createSourceFile(Constants.LOADER_PACKAGE_NAME + "." + Constants.LOADER_CLASS_NAME);
                    Writer writer = jfo.openWriter();
                    writer.write(loaderInjector.getJavaClassFile());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    error(Constants.LOADER_CLASS_NAME, "Exception occurred while attempting to write injector for the mapper loader. Exception message: %s", e.getMessage());
                }
            }


            for (Map.Entry<String, JsonObjectHolder> entry : mJsonObjectMap.entrySet()) {
                String fqcn = entry.getKey();
                JsonObjectHolder jsonObjectHolder = entry.getValue();

                if (!jsonObjectHolder.fileCreated) {
                    jsonObjectHolder.fileCreated = true;

                    try {
                        JavaFileObject jfo = mFiler.createSourceFile(fqcn);
                        Writer writer = jfo.openWriter();
                        writer.write(new ObjectMapperInjector(jsonObjectHolder).getJavaClassFile());
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        error(fqcn, "Exception occurred while attempting to write injector for type %s. Exception message: %s", fqcn, e.getMessage());
                    }
                }
            }

            return true;
        } catch (Throwable e) {
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            error("Exception while processing Json classes. Stack trace incoming:\n%s", stackTrace.toString());
            return false;
        }
    }

    private void error(String message, Object... args) {
        processingEnv.getMessager().printMessage(ERROR, String.format(message, args));
    }
}
