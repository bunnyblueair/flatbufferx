package io.flatbufferx.processor.type.field;

import com.squareup.javapoet.ClassName;
import io.flatbufferx.core.Constants;
import io.flatbufferx.processor.processor.ObjectMapperInjector;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;
import io.flatbufferx.processor.type.Type;

import java.util.List;
import java.util.Set;

import static io.flatbufferx.processor.processor.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static io.flatbufferx.processor.processor.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class DynamicFieldType extends FieldType {

    private TypeName mTypeName;
    private TypeName mTypeNameForBean;
    public DynamicFieldType(TypeName typeName) {

        mTypeName = ClassName.bestGuess(typeName.toString() + Constants.FLATBUFFER_INJECT_SUFFIX);
        mTypeNameForBean = ClassName.bestGuess(mTypeName.toString());
    }

    @Override
    public TypeName getTypeName() {
        return mTypeName;
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return mTypeName;
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        setter = replaceLastLiteral(setter, ObjectMapperInjector.getTypeConverterGetter(ClassName.bestGuess(mTypeName.toString())) + "().parse($L)");
        builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME));
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, List<String> processedFieldNames, String getter, boolean isObjectProperty, boolean checkIfNull, boolean writeIfNull, boolean writeCollectionElementIfNull) {
        if (!mTypeName.isPrimitive() && checkIfNull) {
            builder.beginControlFlow("if ($L != null)", getter);
        }
        if (mTypeName.toString().endsWith("FB")) {

        } else {
            //  Thread.dumpStack();
        }
        System.err.println("xxxx+++=" + ObjectMapperInjector.getTypeConverterGetter(ClassName.bestGuess(mTypeName.toString().toString())));
//        Type parameterTypeRaw=mTypeName;
//        if (mTypeName instanceof DynamicFieldType){
//            mTypeName= FieldType.fieldTypeFor(parameterType.getTypeName().toString()+ Constants.FLATBUFFER_INJECT_SUFFIX);
//            // ClassName.bestGuess(parameterType.getTypeName().toString()+ Constants.FLATBUFFER_INJECT_SUFFIX);
//        }
//fixed
        builder.addStatement(ObjectMapperInjector.getTypeConverterGetter(ClassName.bestGuess(mTypeName.toString())) + "().serialize($L, $S, $L, $L)", getter, isObjectProperty ? fieldName : null, isObjectProperty, JSON_GENERATOR_VARIABLE_NAME);
        if (!mTypeName.isPrimitive() && checkIfNull) {
            if (writeIfNull) {
                builder.nextControlFlow("else");

                if (isObjectProperty) {
                    builder.addStatement("$L.writeFieldName($S)", JSON_GENERATOR_VARIABLE_NAME, fieldName);
                }
                builder.addStatement("$L.writeNull()", JSON_GENERATOR_VARIABLE_NAME);
            }
            builder.endControlFlow();
        }
    }

    @Override
    public Set<TypeName> getUsedTypeConverters() {
        Set<TypeName> set = super.getUsedTypeConverters();
        // String fix

        set.add(ClassName.bestGuess(mTypeName.toString()));
        return set;
    }
}
