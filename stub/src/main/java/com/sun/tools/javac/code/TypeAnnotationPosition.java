package com.sun.tools.javac.code;

/**
 * A type annotation position.
 *
 * <p><b>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
// Code duplicated in com.sun.tools.classfile.TypeAnnotation.Position
public class TypeAnnotationPosition {

    public enum TypePathEntryKind {
        ARRAY(0),
        INNER_TYPE(1),
        WILDCARD(2),
        TYPE_ARGUMENT(3);

        public final int tag;

        private TypePathEntryKind(int tag) {
            this.tag = tag;
        }
    }
}