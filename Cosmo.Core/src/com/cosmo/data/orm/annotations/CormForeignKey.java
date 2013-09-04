package com.cosmo.data.orm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Anotaci�n que sirve para indicar a las p�ginas que se precisa ser usuario autenticado.
 * 
 * @author Gerard Llort
 */
@Documented
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.METHOD )
public @interface CormForeignKey 
{
   /**
    * Nombre de la tabla en la BBDD.
    */
   String dbTableName();
   
   /**
    * Nombre del campo en la BBDD.
    */
   String dbTableField();
}
