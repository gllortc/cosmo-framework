package com.cosmo.ui.templates;

/**
 * Excepci�n que indica que se han producido errores durante la carga de las reglas de presentaci�n.
 * 
 * @author Gerard Llort
 */
public class RulesLoadException extends Exception 
{
   /** Serial Version UID */
   private static final long serialVersionUID = -8343012851203730237L;

   // Declaraci�n de variables locales
   private Exception innerException;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link RulesLoadException}.
    */
   public RulesLoadException()
   {
      super();
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link RulesLoadException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    */
   public RulesLoadException(String message)
   {
      super(message);
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link RulesLoadException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    * @param innerException Una instancia de {@link Exception} que contiene la excepci�n que ha ocasionado el error.
    */
   public RulesLoadException(String message, Exception innerException)
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
