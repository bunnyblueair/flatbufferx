/*
 * Copyright (c) 2005, 2015, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.tools.javac.code;

import com.sun.tools.javac.util.List;

import javax.lang.model.AnnotatedConstruct;
import java.lang.annotation.Annotation;

/**
 * Common super type for annotated constructs such as Types and Symbols.
 * <p>
 * This class should *not* contain any fields since it would have a significant
 * impact on the javac memory footprint.
 *
 * <p><b>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 */
public abstract class AnnoConstruct implements AnnotatedConstruct {
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annoType) {
        return null;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annoType) {
        return null;
    }

    public List<? extends Attribute.Compound> getAnnotationMirrors() {
        return null;
    }
//    public ElementKind getKind() {
//        return null;       // most unkind
//    }
}
