package com.cosmo.annotations;

/**
 * Anotaci�n que sirve para indicar a las p�ginas que se precisa ser usuario autenticado.
 */
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
