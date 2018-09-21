package io.flatbufferx.processor.type.field;

import com.sun.tools.javac.code.Symbol;
import io.flatbufferx.core.annotation.JsonObject;
import io.flatbufferx.processor.type.Type;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.lang.annotation.Annotation;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class FieldType extends Type {

    public abstract TypeName getNonPrimitiveTypeName();

    @Override
    public String getParameterizedTypeString() {
        return "$T";
    }

    @Override
    public Object[] getParameterizedTypeStringArgs() {
        return new Object[] { getNonPrimitiveTypeName() };
    }

    public static FieldType fieldTypeFor(TypeMirror typeMirror, TypeMirror typeConverterType, Elements elements, Types types) {
        if (typeMirror != null) {
            if (typeConverterType != null && !"void".equals(typeConverterType.toString())) {
                return new TypeConverterFieldType(TypeName.get(typeMirror), ClassName.bestGuess(typeConverterType.toString()));
            } else if (typeMirror.getKind() == TypeKind.BOOLEAN) {
                return new BooleanFieldType(true);
            } else if (Boolean.class.getCanonicalName().equals(typeMirror.toString())) {
                return new BooleanFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.BYTE) {
                return new ByteFieldType(true);
            } else if (Byte.class.getCanonicalName().equals(typeMirror.toString())) {
                return new ByteFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.INT) {
                return new IntegerFieldType(true);
            } else if (Integer.class.getCanonicalName().equals(typeMirror.toString())) {
                return new IntegerFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.LONG) {
                return new LongFieldType(true);
            } else if (Long.class.getCanonicalName().equals(typeMirror.toString())) {
                return new LongFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.FLOAT) {
                return new FloatFieldType(true);
            } else if (Float.class.getCanonicalName().equals(typeMirror.toString())) {
                return new FloatFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.DOUBLE) {
                return new DoubleFieldType(true);
            } else if (Double.class.getCanonicalName().equals(typeMirror.toString())) {
                return new DoubleFieldType(false);
            } else if (String.class.getCanonicalName().equals(typeMirror.toString())) {
                return new StringFieldType();
            } else if (Object.class.getCanonicalName().equals(typeMirror.toString())) {
                return new UnknownFieldType();
            } else if (typeMirror instanceof DeclaredType) {
                Annotation annotation = ((DeclaredType) typeMirror).asElement().getAnnotation(JsonObject.class);
                if (annotation != null) {
                    return new JsonFieldType(ClassName.bestGuess(typeMirror.toString()));
                }
            }

            return new DynamicFieldType(TypeName.get(typeMirror));
        } else {
            return null;
        }
    }
    public static FieldType fieldTypeForMethod( Elements elements, Types types, Symbol.MethodSymbol enclosedElement) {
      String type=  enclosedElement.getReturnType().toString();

           if (Boolean.class.getCanonicalName().equals(type)||boolean.class.getCanonicalName().equals(type)) {
                return new BooleanFieldType(false);
            }  if (Byte.class.getCanonicalName().equals(type)) {
                return new ByteFieldType(false);
            } else  if (Integer.class.getCanonicalName().equals(type)||int.class.getCanonicalName().equals(type)) {
                return new IntegerFieldType(false);
            } else  if (Long.class.getCanonicalName().equals(type) ||long.class.getCanonicalName().equals(type)){
                return new LongFieldType(false);
            } else if (Float.class.getCanonicalName().equals(type)) {
                return new FloatFieldType(false);
            } else if (Double.class.getCanonicalName().equals(type)) {
                return new DoubleFieldType(false);
            } else if (String.class.getCanonicalName().equals(type)) {
                return new StringFieldType();
            } else if (Object.class.getCanonicalName().equals(type)) {
                return new UnknownFieldType();
            }
//            else if (typeMirror instanceof DeclaredType) {
//                Annotation annotation = ((DeclaredType) typeMirror).asElement().getAnnotation(JsonObject.class);
//                if (annotation != null) {
//                    return new JsonFieldType(ClassName.bestGuess(typeMirror.toString()));
//                }
//            }

            return new DynamicFieldType(TypeName.get(enclosedElement.getReturnType()));

    }

    public static FieldType fieldTypeFor(String  typeMirror) {
        if (typeMirror != null) {
         if (typeMirror.equals(Boolean.class.getCanonicalName())) {
                return new BooleanFieldType(true);
            } else if (Boolean.class.getCanonicalName().equals(typeMirror)) {
                return new BooleanFieldType(false);
            } else if (typeMirror.contentEquals(byte.class.getCanonicalName())) {
                return new ByteFieldType(true);
            } else if (typeMirror.contentEquals(Byte.class.getCanonicalName())) {
                return new ByteFieldType(false);
            } else if (typeMirror.contentEquals(int.class.getCanonicalName())) {
                return new IntegerFieldType(true);
            } else if (Integer.class.getCanonicalName().equals(typeMirror)) {
                return new IntegerFieldType(false);
            } else if (typeMirror.contentEquals(long.class.getCanonicalName())) {
                return new LongFieldType(true);
            } else if (typeMirror.contentEquals(Long.class.getCanonicalName())) {
                return new LongFieldType(false);
            } else if (typeMirror.contentEquals(float.class.getCanonicalName())) {
                return new FloatFieldType(true);
            } else if (typeMirror.contentEquals(Float.class.getCanonicalName())) {
                return new FloatFieldType(false);
            } else if (typeMirror.contentEquals(double.class.getCanonicalName())) {
                return new DoubleFieldType(true);
            } else if (typeMirror.contentEquals(Double.class.getCanonicalName())) {
                return new DoubleFieldType(false);
            } else if (String.class.getCanonicalName().equals(typeMirror)) {
                return new StringFieldType();
            } else if (Object.class.getCanonicalName().equals(typeMirror)) {
                return new UnknownFieldType();
            }
//            else if (typeMirror instanceof DeclaredType) {
//                Annotation annotation = ((DeclaredType) typeMirror).asElement().getAnnotation(JsonObject.class);
//                if (annotation != null) {
//                    return new JsonFieldType(ClassName.bestGuess(typeMirror.toString()));
//                }
//            }

            return new DynamicFieldType(ClassName.bestGuess(typeMirror));
        } else {
            return null;
        }
    }
    protected static String replaceLastLiteral(String string, String replacement) {
        int pos = string.lastIndexOf("$L");
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + 2, string.length());
        } else {
            return string;
        }
    }
}
