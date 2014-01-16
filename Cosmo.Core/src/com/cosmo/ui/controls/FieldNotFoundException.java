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


   //==============================================
   // Constructor
   //==============================================

   /**
    * Constructor de la clase {@link FieldNotFoundException}.
    */
   public FieldNotFoundException()
   {
      super();
   }

   /**
    * Constructor de la clase {@link FieldNotFoundException}.
    * 
    * @param msg Una cadena que contiene un mensaje descritivo de la excepci�n.
    */
   public FieldNotFoundException(String msg)
   {
      super(msg);
   }
}
