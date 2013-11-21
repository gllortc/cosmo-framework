package com.cosmo.orm;

/**
 * Excepci�n que indica que existe alg�n problema con el mapeado ORM en algun objeto.
 * 
 * @author Gerard Llort
 */
public class InvalidMappingException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 9134296343466070263L;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Constructor de la clase {@link InvalidMappingException}.
    */
   public InvalidMappingException() 
   { 
      super(); 
   }

   /**
    * Constructor de la clase {@link InvalidMappingException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    */
   public InvalidMappingException(String msg) 
   { 
      super(msg); 
   }

   /**
    * Constructor de la clase {@link InvalidMappingException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    * @param throwable Una instancia de {@link Throwable} que contiene la informaci�n del or�gen del problema.
    */
   public InvalidMappingException(String msg, Throwable throwable) 
   { 
      super(msg, throwable); 
   }
}
