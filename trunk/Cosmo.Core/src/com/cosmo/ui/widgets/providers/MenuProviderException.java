package com.cosmo.ui.widgets.providers;

/**
 * Indica que se ha producido un error en el proveedor del men�.
 * 
 * @author Gerard Llort
 */
public class MenuProviderException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 5399108489302105210L;

   // Declaraci�n de variables locales
   private Exception inner = null;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link MenuProviderException}.
    */
   public MenuProviderException()
   {
      super();
   }

   /**
    * Constructor de la clase {@link MenuProviderException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    */
   public MenuProviderException(String message)
   {
      super(message);
   }

   /**
    * Constructor de la clase {@link MenuProviderException}.
    * 
    * @param message Una cadena que contiene un mensaje descriptivo referente a la excepci�n ocurrida.
    * @param innerException Una instancia de {@link Exception} que contiene la excepci�n que ha ocasionado el error.
    */
   public MenuProviderException(String message, Exception innerException)
   {
      super(message);
      this.inner = innerException;
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
      return this.inner;
   }
}
