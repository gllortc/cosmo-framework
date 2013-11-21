package com.cosmo.data;

public class DataException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = -2577150645305791318L;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Constructor de la clase {@link DataException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    */
   public DataException(String msg) 
   { 
      super(msg); 
   }
   
   /**
    * Constructor de la clase {@link DataException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    * @param throwable Una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public DataException(String msg, Throwable throwable) 
   { 
      super(msg, throwable); 
   }
}
