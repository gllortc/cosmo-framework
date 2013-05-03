package com.cosmo.security;

import java.util.Date;
import java.util.HashMap;

import com.cosmo.Workspace;
import com.cosmo.security.providers.AuthenticationProvider;
import com.cosmo.security.providers.AuthenticationProviderException;
import com.cosmo.security.providers.AuthorizationProvider;
import com.cosmo.security.providers.AuthorizationProviderException;

/**
 * Representa una sesi�n de usuario en el workspace.
 * 
 * @author Gerard Llort
 */
public class UserSession 
{
   private Workspace workspace;
   private User currentUser;
   private Date created;
   private HashMap<String, ActivityPermission> activityPermissions;

   
   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * @param login Una cadena que contiene el login del usuario.
    * @param pwd Una cadena que contiene la contrase�a del usuario.
    * 
    * @throws UserNotFoundException
    * @throws AuthenticationProviderException
    * @throws AuthorizationProviderException
    */
   public UserSession(Workspace workspace, String login, String pwd) throws UserNotFoundException, AuthenticationProviderException, AuthorizationProviderException
   {
      initialize();
      
      this.workspace = workspace;
      
      // Instancia el proveedor de autenticaci�n
      AuthenticationProvider authenticationProvider = AuthenticationProvider.getInstance(workspace);
      
      if (authenticationProvider != null)
      {
         // Autenticaci�n
         this.currentUser = authenticationProvider.login(login, pwd);
         
         // Instancia el proveedor de seguridad
         AuthorizationProvider authorizationProvider = AuthorizationProvider.getInstance(workspace);
         
         if (authorizationProvider != null)
         {
            // Obtiene la informaci�n de seguridad
            authorizationProvider.loadAuthorizationData(login);
         }
      }
   }
   
   //==============================================
   // Properties
   //==============================================

   /**
    * Devuelve una instancia de {@link User} que representa el usuario propietario de la sesi�n.
    */
   public User getCurrentUser() 
   {
      return currentUser;
   }

   /**
    * Devuelve la fecha/hora de creaci�n de la sesi�n de usuario.
    */
   public Date getCreated() 
   {
      return created;
   }

   /**
    * Devuelve el workspace para el que es v�lida la sesi�n de usuario.
    */
   public Workspace getWorkspace() 
   {
      return workspace;
   }   
   
   
   //==============================================
   // Methods
   //==============================================

   /**
    * Obtiene el n�mero de minutos desde que se cre� la sesi�n de usuario.
    * 
    * @return Devuelve un entero que representa el n�mero de minutos desde que se cre� la sesi�n de usuario.
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
      this.workspace = null;
      this.currentUser = null;
      this.created = new Date();
      this.activityPermissions = new HashMap<String, ActivityPermission>();
   }
}
