package com.cosmo.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaci�n que sirve para indicar a las p�ginas que se precisa ser usuario autenticado.
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface SessionRequired 
{
    // String message() default "";
}
