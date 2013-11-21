package com.cosmo.orm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaci�n que sirve para indicar a las p�ginas que se precisa ser usuario autenticado.
 * 
 * @author Gerard Llort
 */
@Documented
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface CormObject 
{
   /**
    * Nombre de la tabla en la base de datos.
    */
   String dbTable();
   
   /**
    * Nombre del formulario.
    */
   String formName();
   
   /**
    * T�tulo del formulario.
    */
   String title() default "";
   
   /**
    * Descripci�n del formulario.
    */
   String description() default "";
}
