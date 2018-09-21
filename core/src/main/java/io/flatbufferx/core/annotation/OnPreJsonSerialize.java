package io.flatbufferx.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Declare that a method should be called before a class is serialized to JSON.
 * <pre><code>
 * {@literal @}OnPreJsonSerialize
 * public void preSerializeMethod() {
 *     ...
 * }
 * </code></pre>
 */
@Target(METHOD)
@Retention(CLASS)
public @interface OnPreJsonSerialize { }