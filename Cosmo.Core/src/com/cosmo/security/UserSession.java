package com.cosmo.security;

import com.cosmo.Workspace;
import com.cosmo.security.providers.UserProvider;
import com.cosmo.security.providers.UserProviderException;
import java.util.Date;

/**
 * Representa una sessi贸n de usuario en el workspace.
 * 
 * @author Gerard Llort
 */
public class UserSession 
{
   private Workspace workspace;
   private User currentUser;
   private Date created;
   
   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.
    */
   public UserSession(Workspace workspace, String login, String pwd) throws UserNotFoundException, UserProviderException
   {
      initialize();
      
      this.workspace = workspace;
      
      // Instancia el proveedor de seguridad
      UserProvider provider = UserProvider.getInstance(workspace);
      
      // Autentica el usuario
      this.currentUser = provider.login(login, pwd);
   }
   
   //==============================================
   // Properties
   //==============================================

   /**
    * Devuelve una instancia de {@link User} que representa el usuario propietario de la sessi贸n.
    */
   public User getCurrentUser() 
   {
      return currentUser;
   }

   /**
    * Devuelve la fecha/hora de creaci贸n de la sesi贸n de usuario.
    */
   public Date getCreated() 
   {
      return created;
   }

   /**
    * Devuelve el workspace para el que es v醠ida la sesi髇 de usuario.
    */
   public Workspace getWorkspace() 
   {
      return workspace;
   }   
   
   //==============================================
   // Methods
   //==============================================

   /**
    * Obtiene el n煤mero de minutos desde que se cre贸 la sesi贸n de usuario.
    * 
    * @return Devuelve un entero que representa el n煤mero de minutos desde que se cre贸 la sesi贸n de usuario.
    */
   public long getSessionMinutes()
   {
      Date now = new Date();
      long diffInSeconds = (now.getTime() - this.created.getTime()) / 1000;
      
      return (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
   }

   //==============================================
   // Private members
   //==============================================
   
   /**
    * Inicializa la instancia.
    */
   private void initialize()
   {
      this.currentUser = null;
      this.created = new Date();
   }
}
