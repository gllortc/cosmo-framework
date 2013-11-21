package com.cosmo.ui.controls;

/**
 * Excepci�n que indica que un formulario no est� correctamente compuesto.
 * 
 * @author Gerard Llort
 */
public class FieldNotFoundException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 4985425210084943743L;

   public FieldNotFoundException()
   {
      super();
   }

   public FieldNotFoundException(String msg)
   {
      super(msg);
   }
}
