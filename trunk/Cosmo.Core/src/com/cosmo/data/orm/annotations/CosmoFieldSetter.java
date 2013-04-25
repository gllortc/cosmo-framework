package com.cosmo.data.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Anotaci�n que sirve para indicar a las p�ginas que se precisa ser usuario autenticado.
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.METHOD )
public @interface CosmoFieldSetter 
{
   /**
    * Nombre del campo (se usar� como nombre de campo en el formulario).
    */
   String name();
}
