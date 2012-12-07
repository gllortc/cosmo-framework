package com.cosmo.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Anotaci�n que sirve para indicar a las p�ginas que se precisa ser usuario autenticado.
 */
@Target( value = ElementType.METHOD )
public @interface CosmoField 
{
   public enum FieldType
   {
      Text,
      Integer,
      Decimal
   }
   
   /**
    * Define el tipo de datos del campo.
    */
   FieldType fieldType() default FieldType.Text;

   /**
    * Nombre del campo en la BBDD.
    */
   String tableColumnName();
   
   /**
    * Nombre del campo (se usar� como nombre de campo en el formulario).
    */
   String name();
}
