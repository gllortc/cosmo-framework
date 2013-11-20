package com.cosmo.ui.templates;

/**
 * Indica que se ha producido una excepci�n durante la carga de una plantilla.
 * 
 * @author Gerard Llort
 */
public class TemplateLoadException extends Exception 
{
   /** Serial Version UID */
   private static final long serialVersionUID = 6717283397611708242L;

   // Declaraci�n de variables locales de la clase
   private Exception innerException;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link TemplateLoadException}.
    */
   public TemplateLoadException()
   {
      super();
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link TemplateLoadException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    */
   public TemplateLoadException(String message)
   {
      super(message);
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link TemplateLoadException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    * @param innerException Una instancia de {@link Exception} que contiene la excepci�n que ha ocasionado el error.
    */
   public TemplateLoadException(String message, Exception innerException)
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
