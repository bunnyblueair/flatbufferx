package com.sun.tools.javac.code;

import com.sun.tools.javac.code.Symbol.TypeSymbol;

import javax.lang.model.type.TypeMirror;

public abstract class Type extends AnnoConstruct implements TypeMirror {

    public static boolean moreInfo = false;
    public TypeSymbol tsym;

    public Type getReturnType() {
        return null;
    }

    public boolean hasTag(TypeTag var1) {
        return var1 == this.getTag();
    }

    public abstract TypeTag getTag();

    public boolean isNumeric() {
        return false;
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean isPrimitiveOrVoid() {
        return false;
    }

    public boolean isReference() {
        return false;
    }

    public boolean isNullOrReference() {
        return false;
    }

    public boolean isPartial() {
        return false;
    }

    public Object constValue() {
        return null;
    }

    public boolean isFalse() {
        return false;
    }

    public boolean isTrue() {
        return false;
    }

    public Type getModelType() {
        return this;
    }
}