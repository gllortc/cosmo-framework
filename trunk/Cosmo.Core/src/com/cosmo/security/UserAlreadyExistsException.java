package com.cosmo.security;

/**
 * Indica que el usuario ya existe (<em>login</em> o <em>mail</em> duplicados).
 *
 * @author Gerard Llort
 */
public class UserAlreadyExistsException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 2470642478480808946L;

   /**
    * Constructor de la clase.
    */
   public UserAlreadyExistsException()
   {
      super();
   }
   
   /**
    * Constructor de la clase.
    * 
    * @param message Una cadena que contiene el mensaje descriptivo de la excepci�n.
    */
   public UserAlreadyExistsException(String message)
   {
      super(message);
   }
}
