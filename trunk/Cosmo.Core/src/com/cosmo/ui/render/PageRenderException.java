package com.cosmo.ui.render;

/**
 *
 * @author Gerard Llort
 */
public class PageRenderException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 7710741671392850771L;

   // Declaraci�n de variables locales
   private Exception inner = null;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link PageRenderException}.
    */
   public PageRenderException()
   {
      super();
   }

   /**
    * Constructor de la clase {@link PageRenderException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    */
   public PageRenderException(String message)
   {
      super(message);
   }

   /**
    * Constructor de la clase {@link PageRenderException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    * @param innerException Una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public PageRenderException(String message, Exception innerException)
   {
      super(message);
      this.inner = innerException;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Devuelve una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public Exception getInnerException()
   {
      return this.inner;
   }
}
