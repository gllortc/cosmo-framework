package com.cosmo.ui.controls;

/**
 * Excepci�n que indica que un formulario no est� correctamente compuesto.
 * 
 * @author Gerard Llort
 */
public class MalformedFormException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 4985425210084943743L;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Constructor de la clase {@link MalformedFormException}.
    */
   public MalformedFormException()
   {
      super();
   }

   /**
    * Constructor de la clase {@link MalformedFormException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    */
   public MalformedFormException(String msg)
   {
      super(msg);
   }
}
