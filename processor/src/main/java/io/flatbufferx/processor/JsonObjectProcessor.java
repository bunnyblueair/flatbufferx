package io.flatbufferx.processor;

import com.sun.tools.javac.code.Symbol;
import io.flatbufferx.core.Constants;
import io.flatbufferx.core.FlatBufferSrc;

import io.flatbufferx.processor.processor.JsonFieldHolder;
import io.flatbufferx.processor.processor.JsonObjectHolder;
import io.flatbufferx.processor.processor.JsonObjectHolder.JsonObjectHolderBuilder;
import io.flatbufferx.processor.processor.TextUtils;
import io.flatbufferx.processor.processor.TypeUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import io.flatbufferx.core.annotation.JsonIgnore;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PRIVATE;

public class JsonObjectProcessor extends Processor {

    public JsonObjectProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public Class getAnnotation() {
        return FlatBufferSrc.class;
    }

    @Override
    public void findAndParseObjects(RoundEnvironment env, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements, Types types) {
        for (Element element : env.getElementsAnnotatedWith(FlatBufferSrc.class)) {
            try {
                processJsonObjectAnnotation(element, jsonObjectMap, elements, types);
            } catch (Exception e) {
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));

                error(element, "Unable to generate injector for %s. Stack trace incoming:\n%s", FlatBufferSrc.class, stackTrace.toString());
            }
        }
    }

    private void processJsonObjectAnnotation(Element element, Map<String, JsonObjectHolder> jsonObjectMap, Elements elements, Types types) {
        TypeElement typeElement = (TypeElement)element;

        if (element.getModifiers().contains(PRIVATE)) {
            error(element, "%s: %s annotation can't be used on private classes.", typeElement.getQualifiedName(), FlatBufferSrc.class.getSimpleName());
        }

        JsonObjectHolder holder = jsonObjectMap.get(TypeUtils.getInjectedFQCN(typeElement, elements));
        if (holder == null) {
            String packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
            String objectClassName = TypeUtils.getSimpleClassName(typeElement, packageName);
            String injectedSimpleClassName = objectClassName + Constants.FLATBUFFER_INJECT_SUFFIX;
            boolean abstractClass = element.getModifiers().contains(ABSTRACT);
            List<? extends TypeParameterElement> parentTypeParameters = new ArrayList<>();
            List<String> parentUsedTypeParameters = new ArrayList<>();
            TypeName parentClassName = null;

            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass.getKind() != TypeKind.NONE) {
                TypeElement superclassElement = (TypeElement)types.asElement(superclass);
                if (superclassElement.getAnnotation(FlatBufferSrc.class) != null) {
                    if (superclassElement.getTypeParameters() != null) {
                        parentTypeParameters = superclassElement.getTypeParameters();
                    }

                    String superclassName = superclass.toString();
                    int indexOfTypeParamStart = superclassName.indexOf("<");
                    if (indexOfTypeParamStart > 0) {
                        String typeParams = superclassName.substring(indexOfTypeParamStart + 1, superclassName.length() - 1);
                        parentUsedTypeParameters = Arrays.asList(typeParams.split("\\s*,\\s*"));
                    }
                }
            }
            while (superclass.getKind() != TypeKind.NONE) {
                TypeElement superclassElement = (TypeElement)types.asElement(superclass);

                if (superclassElement.getAnnotation(FlatBufferSrc.class) != null) {
                    String superclassPackageName = elements.getPackageOf(superclassElement).getQualifiedName().toString();
                    parentClassName = ClassName.get(superclassPackageName, TypeUtils.getSimpleClassName(superclassElement, superclassPackageName));
                    break;
                }

                superclass = superclassElement.getSuperclass();
            }

            FlatBufferSrc annotation = element.getAnnotation(FlatBufferSrc.class);

            holder = new JsonObjectHolderBuilder()
                    .setPackageName(packageName)
                    .setInjectedClassName(injectedSimpleClassName)
                    .setObjectTypeName(TypeName.get(typeElement.asType()))
                    .setIsAbstractClass(abstractClass)
                    .setParentTypeName(parentClassName)
                    .setParentTypeParameters(parentTypeParameters)
                    .setParentUsedTypeParameters(parentUsedTypeParameters)
                    .setFieldDetectionPolicy(annotation.fieldDetectionPolicy())
                    .setFieldNamingPolicy(annotation.fieldNamingPolicy())
                    .setSerializeNullObjects(annotation.serializeNullObjects())
                    .setSerializeNullCollectionElements(annotation.serializeNullCollectionElements())
                    .setTypeParameters(typeElement.getTypeParameters())
                    .build();

            FlatBufferSrc.FieldDetectionPolicy fieldDetectionPolicy = annotation.fieldDetectionPolicy();
            if (fieldDetectionPolicy == FlatBufferSrc.FieldDetectionPolicy.NONPRIVATE_FIELDS || fieldDetectionPolicy == FlatBufferSrc.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS) {
                addAllNonPrivateFields(element, elements, types, holder);
            }
            if (fieldDetectionPolicy == FlatBufferSrc.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS) {
                addAllNonPrivateAccessors(element, elements, types, holder);
            }
            filterAllFlatBuffer(element, elements, types, holder);
            jsonObjectMap.put(TypeUtils.getInjectedFQCN(typeElement, elements), holder);
        }
    }

    private void addAllNonPrivateFields(Element element, Elements elements, Types types, JsonObjectHolder objectHolder) {
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            ElementKind enclosedElementKind = enclosedElement.getKind();
            if (enclosedElementKind == ElementKind.FIELD) {
                Set<Modifier> modifiers = enclosedElement.getModifiers();
                if (!modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.PROTECTED) && !modifiers.contains(Modifier.TRANSIENT) && !modifiers.contains(Modifier.STATIC)) {
                    createOrUpdateFieldHolder(enclosedElement, elements, types, objectHolder);
                }
            }
        }
    }

    private void addAllNonPrivateAccessors(Element element, Elements elements, Types types, JsonObjectHolder objectHolder) {
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            ElementKind enclosedElementKind = enclosedElement.getKind();
            if (enclosedElementKind == ElementKind.FIELD) {
                Set<Modifier> modifiers = enclosedElement.getModifiers();

                if (modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.TRANSIENT) && !modifiers.contains(Modifier.STATIC)) {

                    String getter = JsonFieldHolder.getGetter(enclosedElement, elements);
                    String setter = JsonFieldHolder.getSetter(enclosedElement, elements);

                    if (!TextUtils.isEmpty(getter) && !TextUtils.isEmpty(setter)) {
                        createOrUpdateFieldHolder(enclosedElement, elements, types, objectHolder);
                    }
                }
            }
        }
    }
    private void filterAllFlatBuffer(Element element, Elements elements, Types types, JsonObjectHolder objectHolder) {
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        String targetMethod="create"+element.getSimpleName();
        for (Element enclosedElement : enclosedElements) {
            if ("__init".contentEquals(enclosedElement.getSimpleName())||"__assign".contentEquals(enclosedElement.getSimpleName())){
                continue;
            }
          if (enclosedElement instanceof Symbol.MethodSymbol ){
              if (enclosedElement.getKind()== ElementKind.CONSTRUCTOR){
                  continue;
              }
           //   if (enclosedElement.g)
              Symbol.MethodSymbol methodSymbol= (Symbol.MethodSymbol) enclosedElement;
              if (!methodSymbol.getModifiers().contains(Modifier.STATIC)){
                  if (methodSymbol.getReturnType().toString().equals(ByteBuffer.class.getName())){
                      continue;
                  }
                  System.err.println(methodSymbol.getSimpleName());
                  createOrUpdateFieldHolder2( elements, types, objectHolder,methodSymbol);
              }
          }
//            if (enclosedElement.getSimpleName().equals(targetMethod)){
//                Symbol.MethodSymbol createFlatBuffer= (Symbol.MethodSymbol) enclosedElement;
//                for (int i = 0; i < createFlatBuffer.getParameters().length() ; i++) {
//                Symbol.VarSymbol varSymbol= createFlatBuffer.getParameters().get(i);
//                }
//            }
//            ElementKind enclosedElementKind = enclosedElement.getKind();
//            if (enclosedElementKind == ElementKind.FIELD) {
//                Set<Modifier> modifiers = enclosedElement.getModifiers();
//
//                if (modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.TRANSIENT) && !modifiers.contains(Modifier.STATIC)) {
//
//                    String getter = JsonFieldHolder.getGetter(enclosedElement, elements);
//                    String setter = JsonFieldHolder.getSetter(enclosedElement, elements);
//
//                    if (!TextUtils.isEmpty(getter) && !TextUtils.isEmpty(setter)) {
//                        createOrUpdateFieldHolder(enclosedElement, elements, types, objectHolder);
//                    }
//                }
//            }
        }
    }
    private void createOrUpdateFieldHolder(Element element, Elements elements, Types types, JsonObjectHolder objectHolder) {
        JsonIgnore ignoreAnnotation = element.getAnnotation(JsonIgnore.class);
        boolean shouldParse = ignoreAnnotation == null || ignoreAnnotation.ignorePolicy() == JsonIgnore.IgnorePolicy.SERIALIZE_ONLY;
        boolean shouldSerialize = ignoreAnnotation == null || ignoreAnnotation.ignorePolicy() == JsonIgnore.IgnorePolicy.PARSE_ONLY;

        if (shouldParse || shouldSerialize) {
            JsonFieldHolder fieldHolder = objectHolder.fieldMap.get(element.getSimpleName().toString());
            if (fieldHolder == null) {
                fieldHolder = new JsonFieldHolder();
                objectHolder.fieldMap.put(element.getSimpleName().toString(), fieldHolder);
            }

            String error = fieldHolder.fill(element, elements, types, null, null, objectHolder, shouldParse, shouldSerialize);
            if (!TextUtils.isEmpty(error)) {
                error(element, error);
            }
        }
    }
    private void createOrUpdateFieldHolder2( Elements elements, Types types, JsonObjectHolder objectHolder, Symbol.MethodSymbol enclosedElement) {
      //  JsonIgnore ignoreAnnotation = element.getAnnotation(JsonIgnore.class);


            JsonFieldHolder fieldHolder = objectHolder.fieldMap.get(enclosedElement.getSimpleName().toString());
            if (fieldHolder == null) {
                fieldHolder = new JsonFieldHolder();
                objectHolder.fieldMap.put(enclosedElement.getSimpleName().toString(), fieldHolder);
            }

            String error = fieldHolder.fillWithMethod( elements, types, enclosedElement,null, objectHolder, true, true);
            if (!TextUtils.isEmpty(error)) {
                error(enclosedElement, error);
            }

    }
}
