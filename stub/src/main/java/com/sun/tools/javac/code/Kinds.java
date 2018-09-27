package com.sun.tools.javac.code;

/**
 * Internal symbol kinds, which distinguish between elements of
 * different subclasses of Symbol. Symbol kinds are organized so they can be
 * or'ed to sets.
 *
 * <p><b>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
public class Kinds {

    private Kinds() {
    } // uninstantiable

    /**
     * Kind of symbols.
     * <p>
     * IMPORTANT: This is an ordered type.  The ordering of
     * declarations in this enum matters.  Be careful when changing
     * it.
     */
    public enum Kind {
    }
}