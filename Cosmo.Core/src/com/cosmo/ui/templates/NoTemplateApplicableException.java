package com.cosmo.ui.templates;

/**
 * Excepci�n que indica que no se ha podido aplicar un tema.
 * 
 * @author Gerard Llort
 */
public class NoTemplateApplicableException extends Exception 
{
   /** Serial Version UID */
   private static final long serialVersionUID = 5924404941548886648L;

   // Declaraci�n de variables locales
   private Exception innerException;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link NoTemplateApplicableException}.
    */
   public NoTemplateApplicableException()
   {
      super();
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link NoTemplateApplicableException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    */
   public NoTemplateApplicableException(String message)
   {
      super(message);
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link NoTemplateApplicableException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    * @param innerException Una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public NoTemplateApplicableException(String message, Exception innerException)
   {
      super(message);
      this.innerException = innerException;
   }

   
   //==============================================
   // Methods
   //==============================================

   /**
    * Devuelve una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public Exception getInnerException() 
   {
      return innerException;
   }
}
