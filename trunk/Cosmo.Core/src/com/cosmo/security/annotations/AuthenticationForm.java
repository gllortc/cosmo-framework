package com.cosmo.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaci�n que sirve para indicar si una p�gina contiene el formulario de autenticaci�n (login).
 * 
 * @author Gerard Llort 
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface AuthenticationForm 
{
    // String message() default "";
}
