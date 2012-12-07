package com.cosmo.data.adapter;

public class InvalidMappingException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 9134296343466070263L;

   /**
    * Constructor de la classe.
    */
   public InvalidMappingException() 
   { 
      super(); 
   }
   
   /**
    * Constructor de la classe.
    * 
    * @param msg Missatge que ha de mostrar l'excepci�n
    */
   public InvalidMappingException(String msg) 
   { 
      super(msg); 
   }
   
   /**
    * Constructor de la classe.
    * 
    * @param msg Missatge que ha de mostrar l'excepci�n
    * @param throwable Excepci�n que l'ha provocat.
    */
   public InvalidMappingException(String msg, Throwable throwable) 
   { 
      super(msg, throwable); 
   }
}
