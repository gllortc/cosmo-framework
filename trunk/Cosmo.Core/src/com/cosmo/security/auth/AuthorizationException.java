package com.cosmo.security.auth;

/**
 * Se produce cuando hay un error con el proveedor de perfiles de usuario.
 * 
 * @author Gerard Llort
 */
public class AuthorizationException extends Exception
{
   /** Serial Version UID */
   private static final long serialVersionUID = 1837331881629720686L;

   // Declaraci�n de variables locales
   private Exception inner = null;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link AuthorizationException}.
    */
   public AuthorizationException()
   {
      super();
   }

   /**
    * Constructor de la clase {@link AuthorizationException}.
    * 
    * @param message Una cadena que contiene el mensaje descriptivo de la excepci�n.
    */
   public AuthorizationException(String message)
   {
      super(message);
   }

   /**
    * Constructor de la clase {@link AuthorizationException}.
    * 
    * @param message Una cadena que contiene el mensaje descriptivo de la excepci�n.
    * @param innerException Una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public AuthorizationException(String message, Exception innerException)
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
