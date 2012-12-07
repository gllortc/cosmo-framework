package com.cosmo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaci�n que sirve para indicar a las p�ginas que se precisa ser usuario autenticado.
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface CosmoTable 
{
   /**
    * Nombre del campo en la BBDD.
    */
   String tableName();
   
   /**
    * Nombre de la tabla (se usar� como nombre de formulario).
    */
   String name();
}
