package com.cosmo.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaci�n que se usa para indicar qu� agentes deben usar <em>Login Gateway</em>. Todo agente que 
 * contenga esta anotaci�n usar� el mecanismo de logn externo (<em>Login Gateway</em>).
 * 
 * @author Gerard Llort 
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface LoginGatewayAgent { }
