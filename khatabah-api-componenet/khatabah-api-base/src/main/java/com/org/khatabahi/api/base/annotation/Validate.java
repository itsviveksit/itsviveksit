/**
 * 
 */
package com.org.khatabahi.api.base.annotation;

import javax.validation.groups.Default;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 * @author Vivek
 *
 */
public @interface Validate {
	/**
	 * Specify one or more {@link "ValidationConstraint} types. The execution order
	 * of the {@link "ValidationConstraint} is in the same order as specified in the
	 * declaration
	 * 
	 * @return the list of subclass type of {@link "ValidationConstraint}
	 */
	String[] constraints() default {};

	Class[] groups() default Default.class;
}
