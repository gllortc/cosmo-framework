package com.cosmo.ui.templates;

/**
 * Indica que se ha producido un error en la lectura o manejo de un control de plantilla.
 * 
 * @author Gerard Llort
 */
public class TemplateControlException extends Exception 
{
   /** Serial Version UID */
   private static final long serialVersionUID = 4282790500754859617L;

   // Declaraci�n de variables locales de la clase
   private Exception innerException;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link TemplateControlException}.
    */
   public TemplateControlException()
   {
      super();
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link TemplateControlException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    */
   public TemplateControlException(String message)
   {
      super(message);
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link TemplateControlException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    * @param innerException Una instancia de {@link Exception} que contiene la excepci�n que ha ocasionado el error.
    */
   public TemplateControlException(String message, Exception innerException)
   {
      super(message);
      this.innerException = innerException;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Devuelve la excepci�n original que ocasion� el lanzamiento de la excepci�n actual.
    * 
    * @return Una instancia de {@link Exception}.
    */
   public Exception getInnerException() 
   {
      return innerException;
   }
}
