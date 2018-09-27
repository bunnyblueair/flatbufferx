package com.sun.tools.javac.code;

import com.sun.tools.javac.code.Kinds.Kind;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Set;

//import javax.lang.model.element.ModuleElement;
//import static com.sun.tools.javac.code.Flags.*;

public class Symbol extends AnnoConstruct implements Element {
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 1 << 1;
    public static final int PROTECTED = 1 << 2;
    public static final int STATIC = 1 << 3;
    public static final int FINAL = 1 << 4;
    public static final int SYNCHRONIZED = 1 << 5;
    public static final int VOLATILE = 1 << 6;
    public static final int TRANSIENT = 1 << 7;
    public static final int NATIVE = 1 << 8;
    public static final int INTERFACE = 1 << 9;
    public static final int ABSTRACT = 1 << 10;
    public static final int STRICTFP = 1 << 11;
    /**
     * The kind of this symbol.
     *
     * @see Kinds
     */
    public Kind kind;

    /**
     * The flags of this symbol.
     */
    public long flags_field;
    /**
     * The name of this symbol in Utf8 representation.
     */
    public Name name;
    /**
     * The type of this symbol.
     */
    public Type type;
    /**
     * The owner of this symbol.
     */
    public Symbol owner;
    /**
     * A cache for the type erasure of this symbol.
     */
    public Type erasure_field;

    public Symbol(Kind kind, long flags, Name name, Type type, Symbol owner) {
        this.kind = kind;
        this.flags_field = flags;
        this.type = type;
        this.owner = owner;

        this.name = name;
    }

//    /** The completer of this symbol.
//     * This should never equal null (NULL_COMPLETER should be used instead).
//     */
//    public Completer completer;

    /**
     * An accessor method for the flags of this symbol.
     * Flags of class symbols should be accessed through the accessor
     * method to make sure that the class symbol is loaded.
     */
    public long flags() {
        return flags_field;
    }

    public Name getQualifiedName() {
        return name;
    }

    public Name getSimpleName() {
        return name;
    }

    @Override
    public List<? extends Attribute.Compound> getAnnotationMirrors() {
        return null;
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annoType) {
        return null;
    }

    public Element getEnclosingElement() {
        return null;
    }

    public Set<Modifier> getModifiers() {
        return null;
    }

    public TypeMirror asType() {
        return null;
    }

    public ElementKind getKind() {
        return null;
    }

//    public List<TypeVariableSymbol> getTypeParameters() {
//        return null;
//    }

    public <R, P> R accept(Symbol.Visitor<R, P> v, P p) {
        return null;
    }

    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
        return null;
    }

    public java.util.List<Symbol> getEnclosedElements() {

        return null;
    }

    /**
     * A visitor for symbols.  A visitor is used to implement operations
     * (or relations) on symbols.  Most common operations on types are
     * binary relations and this interface is designed for binary
     * relations, that is, operations on the form
     * Symbol&nbsp;&times;&nbsp;P&nbsp;&rarr;&nbsp;R.
     * <!-- In plain text: Type x P -> R -->
     *
     * @param <R> the return type of the operation implemented by this
     *            visitor; use Void if no return type is needed.
     * @param <P> the type of the second argument (the first being the
     *            symbol itself) of the operation implemented by this visitor; use
     *            Void if a second argument is not needed.
     */
    public interface Visitor<R, P> {
        R visitClassSymbol(ClassSymbol s, P arg);

        R visitMethodSymbol(MethodSymbol s, P arg);

        R visitPackageSymbol(PackageSymbol s, P arg);

        R visitOperatorSymbol(OperatorSymbol s, P arg);

        R visitVarSymbol(VarSymbol s, P arg);

        R visitTypeSymbol(TypeSymbol s, P arg);

        R visitSymbol(Symbol s, P arg);
    }

    public static class TypeVariableSymbol
            extends TypeSymbol implements TypeParameterElement {
        public TypeVariableSymbol(Kind kind, long flags, Name name, Type type, com.sun.tools.javac.code.Symbol owner) {
            super(kind, flags, name, type, owner);
        }
//        public TypeVariableSymbol(long flags, Name name, Type type, Symbol owner) {
//            super(TYP, flags, name, type, owner);
//        }

        public List<Type> getBounds() {
            return null;
        }

        public Symbol getGenericElement() {
            return owner;
        }

        public <R, P> R accept(ElementVisitor<R, P> v, P p) {
            return null;
        }

    }

    public static class MethodSymbol extends Symbol {
        public MethodSymbol(Kind kind, long flags, Name name, Type type, Symbol owner) {
            super(kind, flags, name, type, owner);
        }

        public Type getReturnType() {
            return null;
        }

        public com.sun.tools.javac.util.List<VarSymbol> getParameters() {
            return null;
        }
    }

    public static class VarSymbol extends Symbol {
        public VarSymbol(Kind kind, long flags, Name name, Type type, Symbol owner) {
            super(kind, flags, name, type, owner);
        }

    }

    /**
     * A base class for Symbols representing types.
     */
    public static abstract class TypeSymbol extends Symbol {
        public TypeSymbol(Kind kind, long flags, Name name, Type type, Symbol owner) {
            super(kind, flags, name, type, owner);
        }

        /**
         * form a fully qualified name from a name and an owner
         */
        static public Name formFullName(Name name, Symbol owner) {
            return null;
        }

        /**
         * form a fully qualified name from a name and an owner, after
         * converting to flat representation
         */
        static public Name formFlatName(Name name, Symbol owner) {
            return null;
        }

        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return null;
        }

        public List<Attribute.Compound> getAnnotationMirrors() {
            return null;
        }

        public Type asType() {
            return null;
        }

        /**
         * Returns the {@code kind} of this element.
         *
         * @return the kind of this element
         */
        public ElementKind getKind() {
            return null;
        }

        public List<? extends TypeMirror> getInterfaces() {
            return null;
        }

        /**
         * Returns the formal type parameters of this type element
         * in declaration order.
         *
         * @return the formal type parameters, or an empty list
         * if there are none
         */
        //   @Override
        public List<TypeVariableSymbol> getTypeParameters() {
            return null;
        }

        public TypeMirror getSuperclass() {
            return null;
        }

        /**
         * A partial ordering between type symbols that refines the
         * class inheritance graph.
         * <p>
         * Type variables always precede other kinds of symbols.
         */
        public final boolean precedes(TypeSymbol that, Types types) {
            return false;
        }


        public java.util.List<Symbol> getEnclosedElements() {

            return null;
        }


        public boolean isAnnotationType() {
            return false;
        }

    }

    public static class ClassSymbol extends TypeSymbol implements TypeElement {
        public ClassSymbol(Kind kind, long flags, Name name, Type type, com.sun.tools.javac.code.Symbol owner) {
            super(kind, flags, name, type, owner);
        }

        public Symbol getEnclosingElement() {
            return null;
        }

        public NestingKind getNestingKind() {
            return null;
        }

        public List<Type> getInterfaces() {
            return null;
        }

        public Type getSuperclass() {
            return null;
        }
//        @Override
//        public java.util.List<? extends TypeMirror> getInterfaces() {
//            return null;
//        }

        public <R, P> R accept(ElementVisitor<R, P> v, P p) {
            return null;
        }
    }

    public static class PackageSymbol extends TypeSymbol
            implements PackageElement {
        public PackageSymbol(Kind kind, long flags, Name name, Type type, com.sun.tools.javac.code.Symbol owner) {
            super(kind, flags, name, type, owner);
        }

        @Override
        public java.util.List<Symbol> getEnclosedElements() {
            return super.getEnclosedElements();
        }



        public boolean isUnnamed() {
            return false;
        }

        public <R, P> R accept(ElementVisitor<R, P> v, P p) {
            return null;
        }

        public <A extends Annotation> A[] getAnnotationsByType(Class<A> annoType) {
            return null;
        }
    }

    public static class OperatorSymbol extends MethodSymbol {
        public OperatorSymbol(Kind kind, long flags, Name name, Type type, com.sun.tools.javac.code.Symbol owner) {
            super(kind, flags, name, type, owner);
        }


//        public OperatorSymbol(Name name, Type type, int opcode, Symbol owner) {
//           / super(null, name, type, owner);
//        }
    }
}