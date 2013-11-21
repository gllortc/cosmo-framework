package com.cosmo;

/**
 * Indica que se ha producido un error durante el cargado o la recuperaci�n del Workspace.
 * 
 * @author Gerard Llort
 */
public class WorkspaceLoadException extends Exception 
{
   /** Serial Version UID */
   private static final long serialVersionUID = 4236007126103262540L;

   // Declaraci�n de variables
   private Exception innerException;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Constructor de la clase {@link WorkspaceLoadException}.
    */
   public WorkspaceLoadException()
   {
      super();
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link WorkspaceLoadException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    */
   public WorkspaceLoadException(String message)
   {
      super(message);
      this.innerException = null;
   }

   /**
    * Constructor de la clase {@link WorkspaceLoadException}.
    * 
    * @param msg Mensaje descriptivo de la excepci�n.
    * @param innerException Una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public WorkspaceLoadException(String message, Exception innerException)
   {
      super(message);
      this.innerException = innerException;
   }


   //==============================================
   // Contructors
   //==============================================

   /**
    * Devuelve una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public Exception getInnerException() 
   {
      return innerException;
   }

   /**
    * Establece una instancia de {@link Exception} que contiene la informaci�n del or�gen del problema.
    */
   public void setInnerException(Exception innerException) 
   {
      this.innerException = innerException;
   }
}
