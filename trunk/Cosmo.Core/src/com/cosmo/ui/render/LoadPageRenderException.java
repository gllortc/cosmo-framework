package com.cosmo.ui.render;

/**
 * Exceptci�n que indica que no se ha podido cargar el renderizador de p�ginas.
 * 
 * @author Gerard Llort
 */
public class LoadPageRenderException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 7538912157946062566L;

   // Declaraci�n de variables locales.
   private Exception inner = null;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Constructor de la clase {@link LoadPageRenderException}.
    */
   public LoadPageRenderException()
   {
      super();
   }

   /**
    * Constructor de la clase {@link LoadPageRenderException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    */
   public LoadPageRenderException(String message)
   {
      super(message);
   }

   /**
    * Constructor de la clase {@link LoadPageRenderException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    * @param innerException Una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public LoadPageRenderException(String message, Exception innerException)
   {
      super(message);
      this.inner = innerException;
   }


   //==============================================
   // Contructors
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
