/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.tools.javac.util;

import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;


/**
 * A class for generic linked lists. Links are supposed to be
 * immutable, the only exception being the incremental construction of
 * lists via ListBuffers.  List is the main container class in
 * GJC. Most data structures and algorithms in GJC use lists rather
 * than arrays.
 *
 * <p>Lists are always trailed by a sentinel element, whose head and tail
 * are both null.
 *
 * <p><b>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
public class List<A> extends AbstractCollection<A> implements java.util.List<A> {

    private static final List<?> EMPTY_LIST = null;
    /**
     * The first element of the list, supposed to be immutable.
     */
    public A head;
    /**
     * The remainder of the list except for its first element, supposed
     * to be immutable.
     */
    //@Deprecated
    public List<A> tail;

    /**
     * Construct a list given its head and tail.
     */
    List(A head, List<A> tail) {
        this.tail = tail;
        this.head = head;
    }

    /**
     * Construct an empty list.
     */
    @SuppressWarnings("unchecked")
    public static <A> List<A> nil() {
        return null;
    }

    /**
     * Returns the list obtained from 'l' after removing all elements 'elem'
     */
    public static <A> List<A> filter(List<A> l, A elem) {
        return null;
    }

    /**
     * Construct a list consisting of given element.
     */
    public static <A> List<A> of(A x1) {
        return null;

    }

    /**
     * Construct a list consisting of given elements.
     */
    public static <A> List<A> of(A x1, A x2) {
        return null;

    }

    /**
     * Construct a list consisting of given elements.
     */
    public static <A> List<A> of(A x1, A x2, A x3) {
        return null;

    }

    /**
     * Construct a list consisting of given elements.
     */
    @SuppressWarnings({"varargs", "unchecked"})
    public static <A> List<A> of(A x1, A x2, A x3, A... rest) {
        return null;
    }

    /**
     * Construct a list consisting all elements of given array.
     *
     * @param array an array; if {@code null} return an empty list
     */
    public static <A> List<A> from(A[] array) {
        return null;
    }

    public static <A> List<A> from(Iterable<? extends A> coll) {
        return null;
    }

    /**
     * Construct a list consisting of a given number of identical elements.
     *
     * @param len  The number of elements in the list.
     * @param init The value of each element.
     */
    @Deprecated
    public static <A> List<A> fill(int len, A init) {
        List<A> l = nil();
        for (int i = 0; i < len; i++) l = new List<>(init, l);
        return l;
    }

    @SuppressWarnings("unchecked")
    private static <A> Iterator<A> emptyIterator() {
        return null;
    }

    public List<A> intersect(List<A> that) {
        return null;
    }

    public List<A> diff(List<A> that) {
        return null;
    }

    /**
     * Create a new list from the first {@code n} elements of this list
     */
    public List<A> take(int n) {
        return null;
    }

    /**
     * Does list have no elements?
     */
    @Override
    public boolean isEmpty() {
        return tail == null;
    }

    /**
     * Does list have elements?
     */
    //@Deprecated
    public boolean nonEmpty() {
        return tail != null;
    }

    /**
     * Return the number of elements in this list.
     */
    //@Deprecated
    public int length() {
        List<A> l = this;
        int len = 0;
        while (l.tail != null) {
            l = l.tail;
            len++;
        }
        return len;
    }

    @Override
    public int size() {
        return length();
    }

    public List<A> setTail(List<A> tail) {
        this.tail = tail;
        return tail;
    }

    /**
     * Prepend given element to front of list, forming and returning
     * a new list.
     */
    public List<A> prepend(A x) {
        return new List<>(x, this);
    }

    /**
     * Prepend given list of elements to front of list, forming and returning
     * a new list.
     */
    public List<A> prependList(List<A> xs) {
        return null;
    }

    /**
     * Reverse list.
     * If the list is empty or a singleton, then the same list is returned.
     * Otherwise a new list is formed.
     */
    public List<A> reverse() {
        // if it is empty or a singleton, return itself
        if (isEmpty() || tail.isEmpty())
            return this;

        List<A> rev = nil();
        for (List<A> l = this; l.nonEmpty(); l = l.tail)
            rev = new List<>(l.head, rev);
        return rev;
    }

    /**
     * Append given element at length, forming and returning
     * a new list.
     */
    public List<A> append(A x) {
        return of(x).prependList(this);
    }

    /**
     * Append given list at length, forming and returning
     * a new list.
     */
    public List<A> appendList(List<A> x) {
        return x.prependList(this);
    }

    /**
     * Copy successive elements of this list into given vector until
     * list is exhausted or end of vector is reached.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] vec) {
        int i = 0;
        List<A> l = this;
        Object[] dest = vec;
        while (l.nonEmpty() && i < vec.length) {
            dest[i] = l.head;
            l = l.tail;
            i++;
        }
        if (l.isEmpty()) {
            if (i < vec.length)
                vec[i] = null;
            return vec;
        }

        vec = (T[]) Array.newInstance(vec.getClass().getComponentType(), size());
        return toArray(vec);
    }

    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    /**
     * Form a string listing all elements with given separator character.
     */
    public String toString(String sep) {
        if (isEmpty()) {
            return "";
        } else {
            StringBuilder buf = new StringBuilder();
            buf.append(head);
            for (List<A> l = tail; l.nonEmpty(); l = l.tail) {
                buf.append(sep);
                buf.append(l.head);
            }
            return buf.toString();
        }
    }

    /**
     * Form a string listing all elements with comma as the separator character.
     */
    @Override
    public String toString() {
        return toString(",");
    }

    /**
     * Compute a hash code, overrides Object
     *
     * @see java.util.List#hashCode
     */
    @Override
    public int hashCode() {
        List<A> l = this;
        int h = 1;
        while (l.tail != null) {
            h = h * 31 + (l.head == null ? 0 : l.head.hashCode());
            l = l.tail;
        }
        return h;
    }

    /**
     * Does the list contain the specified element?
     */
    @Override
    public boolean contains(Object x) {
        List<A> l = this;
        while (l.tail != null) {
            if (x == null) {
                if (l.head == null) return true;
            } else {
                if (l.head.equals(x)) return true;
            }
            l = l.tail;
        }
        return false;
    }

    /**
     * The last element in the list, if any, or null.
     */
    public A last() {
        A last = null;
        List<A> t = this;
        while (t.tail != null) {
            last = t.head;
            t = t.tail;
        }
        return last;
    }

    @Override
    public Iterator<A> iterator() {

        return emptyIterator();

    }


    public A get(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));

        List<A> l = this;
        for (int i = index; i-- > 0 && !l.isEmpty(); l = l.tail)
            ;

        if (l.isEmpty())
            throw new IndexOutOfBoundsException("Index: " + index + ", " +
                    "Size: " + size());
        return l.head;
    }

    public boolean addAll(int index, Collection<? extends A> c) {
        if (c.isEmpty())
            return false;
        throw new UnsupportedOperationException();
    }

    public A set(int index, A element) {
        throw new UnsupportedOperationException();
    }

    public void add(int index, A element) {
        throw new UnsupportedOperationException();
    }

    public A remove(int index) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        int i = 0;
        for (List<A> l = this; l.tail != null; l = l.tail, i++) {
            if (l.head == null ? o == null : l.head.equals(o))
                return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int last = -1;
        int i = 0;
        for (List<A> l = this; l.tail != null; l = l.tail, i++) {
            if (l.head == null ? o == null : l.head.equals(o))
                last = i;
        }
        return last;
    }

    public ListIterator<A> listIterator() {
        return null;
    }

    public ListIterator<A> listIterator(int index) {
        return null;
    }

    public java.util.List<A> subList(int fromIndex, int toIndex) {

        return null;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
//    @Override
//    void clear() {
//    }
//    @Override
//    boolean retainAll(Collection<?> c){
//        return false;
//    }

}
