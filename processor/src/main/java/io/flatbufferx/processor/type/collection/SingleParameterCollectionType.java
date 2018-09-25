package io.flatbufferx.processor.type.collection;

import com.squareup.javapoet.ClassName;
import io.flatbufferx.core.Constants;
import io.flatbufferx.processor.processor.TextUtils;
import io.flatbufferx.processor.type.Type;
import com.fasterxml.jackson.core.JsonToken;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import io.flatbufferx.processor.type.field.DynamicFieldType;
import io.flatbufferx.processor.type.field.FieldType;

import java.util.List;

import static io.flatbufferx.processor.processor.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static io.flatbufferx.processor.processor.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public abstract class SingleParameterCollectionType extends CollectionType {

    public abstract Class getGenericClass();

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        Type parameterType = parameterTypes.get(0);
        Type parameterTypeRaw = parameterType;
//        if (parameterType instanceof DynamicFieldType){
//            parameterType= FieldType.fieldTypeFor(parameterType.getTypeName().toString());
//       // ClassName.bestGuess(parameterType.getTypeName().toString()+ Constants.FLATBUFFER_INJECT_SUFFIX);
//        }

        final String collectionVarName = "collection" + depth;
        final String valueVarName = "value" + depth;

        final String instanceCreator = String.format("$T<%s> $L = new $T<%s>()", parameterType.getParameterizedTypeString(), parameterType.getParameterizedTypeString());
        final Object[] instanceCreatorArgs = expandStringArgs(getTypeName(), parameterType.getParameterizedTypeStringArgs(), collectionVarName, getTypeName(), parameterType.getParameterizedTypeStringArgs());

        builder.beginControlFlow("if ($L.getCurrentToken() == $T.START_ARRAY)", JSON_PARSER_VARIABLE_NAME, JsonToken.class)
                .addStatement(instanceCreator, instanceCreatorArgs)
                .beginControlFlow("while ($L.nextToken() != $T.END_ARRAY)", JSON_PARSER_VARIABLE_NAME, JsonToken.class)
                .addStatement("$T $L", parameterType.getTypeName(), valueVarName);

        parameterType.parse(builder, depth + 1, "$L = $L", valueVarName);

        builder
                .addStatement("$L.add($L)", collectionVarName, valueVarName)
                .endControlFlow();

        builder
                .addStatement(setter, expandStringArgs(setterFormatArgs, collectionVarName))
                .nextControlFlow("else")
                .addStatement(setter, expandStringArgs(setterFormatArgs, "null"))
                .endControlFlow();
    }

    @Override
    public void serialize(MethodSpec.Builder builder, int depth, String fieldName, List<String> processedFieldNames, String getter, boolean isObjectProperty, boolean checkIfNull, boolean writeIfNull, boolean writeCollectionElementIfNull) {
        Type parameterType = parameterTypes.get(0);
//        if (parameterType instanceof DynamicFieldType){
//            parameterType= FieldType.fieldTypeFor(parameterType.getTypeName().toString());
//            // ClassName.bestGuess(parameterType.getTypeName().toString()+ Constants.FLATBUFFER_INJECT_SUFFIX);
//        }
        final String cleanFieldName = TextUtils.toUniqueFieldNameVariable(fieldName, processedFieldNames);
        final String collectionVariableName = "lslocal" + cleanFieldName;
        final String elementVarName = "element" + depth;

        final String instanceCreator = String.format("final $T<%s> $L = $L", parameterType.getParameterizedTypeString());
        final Object[] instanceCreatorArgs = expandStringArgs(getGenericClass(), parameterType.getParameterizedTypeStringArgs(), collectionVariableName, getter);

        final String forLine = String.format("for (%s $L : $L)", parameterType.getParameterizedTypeString());
        final Object[] forLineArgs = expandStringArgs(parameterType.getParameterizedTypeStringArgs(), elementVarName, collectionVariableName);

        builder
                .addStatement(instanceCreator, instanceCreatorArgs)
                .beginControlFlow("if ($L != null)", collectionVariableName);

        if (isObjectProperty) {
            builder.addStatement("$L.writeFieldName($S)", JSON_GENERATOR_VARIABLE_NAME, fieldName);
        }

        builder
                .addStatement("$L.writeStartArray()", JSON_GENERATOR_VARIABLE_NAME)
                .beginControlFlow(forLine, forLineArgs)
                .beginControlFlow("if ($L != null)", elementVarName);

        parameterType.serialize(builder, depth + 1, collectionVariableName + "Element", processedFieldNames, elementVarName, false, false, false, writeCollectionElementIfNull);

        if (writeCollectionElementIfNull) {
            builder
                    .nextControlFlow("else")
                    .addStatement("$L.writeNull()", JSON_GENERATOR_VARIABLE_NAME);
        }

            builder
                .endControlFlow()
                .endControlFlow()
                .addStatement("$L.writeEndArray()", JSON_GENERATOR_VARIABLE_NAME)
                .endControlFlow();
    }

}
