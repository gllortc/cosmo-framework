package com.cosmo.security;

import java.util.ArrayList;
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
   private SecurityInfo securityInfo;
   
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
            // Obtiene la informaci�n de seguridad para el usuario autenticado
            this.securityInfo = new SecurityInfo();
            authorizationProvider.loadAuthorizationData(login, this.securityInfo);
         }
      }
   }
   
   /**
    * Constructor de la clase.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * @param user Una instancia de {@link User} que representa el usuario para el que se desea crear la sesi�n.
    * 
    * @throws UserNotFoundException
    * @throws AuthenticationProviderException
    * @throws AuthorizationProviderException
    */
   public UserSession(Workspace workspace, User user) throws UserNotFoundException, AuthenticationProviderException, AuthorizationProviderException
   {
      initialize();
      
      this.workspace = workspace;
      
      // Autenticaci�n
      this.currentUser = user;
         
      // Instancia el proveedor de seguridad
      AuthorizationProvider authorizationProvider = AuthorizationProvider.getInstance(workspace);
         
      if (authorizationProvider != null)
      {
         // Obtiene la informaci�n de seguridad para el usuario autenticado
         this.securityInfo = new SecurityInfo();
         authorizationProvider.loadAuthorizationData(user.getLogin(), this.securityInfo);
      }
   }
   
   /**
    * Constructor de la clase.<br />
    * Este contructor crea una sesi�n de usuario sin autenticar y sirve exclusivamente cuando se delega la p�gina de login
    * a otro servicio externo mediante LoginGateway (por ejemplo, Jasig CAS).
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * @param login Una cadena que contiene el login del usuario.
    * 
    * @throws AuthorizationProviderException
    */
   /*public UserSession(Workspace workspace, String login) throws AuthorizationProviderException
   {
      this.currentUser = new User();
      this.currentUser.setLogin(login);
      this.currentUser.setName(login);
      
      // Instancia el proveedor de seguridad
      AuthorizationProvider authorizationProvider = AuthorizationProvider.getInstance(workspace);
      
      if (authorizationProvider != null)
      {
         // Obtiene la informaci�n de seguridad para el usuario autenticado
         this.securityInfo = new SecurityInfo();
         authorizationProvider.loadAuthorizationData(login, this.securityInfo);
      }
   }*/
   
   //==============================================
   // Properties
   //==============================================

   /**
    * Indica si la sesi�n es v�lida.
    * 
    * @return {@code true} si la sesi�n es v�lida o {@code false} en cualquier otro caso.
    */
   public boolean isValidSession()
   {
      if (this.currentUser == null)
      {
         return false;
      }
      else if (this.securityInfo == null)
      {
         return false;
      }
      
      return true;
   }
   
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
    * Destruye la sesi�n de usuario, eliminando cualquir dato que contenga.<br />
    * Este m�todo equivale a realizar la acci�n de <em>logout</em> en el agente activo.
    */
   public void destroy()
   {
      try
      {
         // Invoca el m�todo logout() en el agente de autenticaci�n
         AuthenticationProvider authenticationProvider = AuthenticationProvider.getInstance(workspace);
         authenticationProvider.logout();
      }
      catch (Exception ex)
      {
         // No se tiene en cuenta
      }
      finally
      {
         // Elimina los datos de la inst�ncia
         initialize();
      }
   }
   
   /**
    * Obtiene el n�mero de minutos desde que se cre� la sesi�n de usuario.
    * 
    * @return Devuelve un entero que representa el n�mero de minutos desde que se cre� la sesi�n de usuario.
    */
   public long getSessionMinutes()
   {
      long diffInSeconds;
      Date now;
      
      now = new Date();
      diffInSeconds = (now.getTime() - this.created.getTime()) / 1000;

      return (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
   }
   
   /**
    * Determina si el usuario propietario de la sesi�n tiene asignado un determinado rol.
    * 
    * @param roleId Una cadena que contiene el identificador (nombre) del rol.
    * 
    * @return {@code true} si el usuario pertenece al rol especificado o {@code false} en cualquier otro caso.
    */
   public boolean isInRole(String roleId)
   {
      return this.securityInfo.isInRole(roleId);
   }

   /**
    * Determina si el usuario propietario de la sesi�n tiene asignado como m�nimo un rol de entre la lista 
    * de roles proporcionada.
    * 
    * @param roleList Un array que contiene los identificadores (nombre) de los roles.
    * 
    * @return {@code true} si el usuario pertenece a como m�nimo uno de los roles de la lista o {@code false} en cualquier otro caso.
    */
   public boolean isInRole(ArrayList<String> roleList)
   {
      return this.securityInfo.isInRole(roleList);
   }
   
   /**
    * Determina si el usuario propietario de la sesi�n tiene permiso para ejecutar determinada actividad.
    * 
    * @param activityId Una cadena que contiene el identificador (nombre) de la actividad.
    * 
    * @return {@code true} si el usuario puede ejecutar la actividad o {@code false} en cualquier otro caso.
    */
   public boolean isActivityAllowed(String activityId)
   {
      return this.securityInfo.isActivityGranted(activityId);
   }
   
   /**
    * Determina si el usuario propietario de la sesi�n tiene permiso para ejecutar como m�nimo una determinada actividad
    * de las contenidas en una lista.
    * 
    * @param activityList Array de cadenas que contiene los identificadores (nombre) de las actividades.
    * 
    * @return {@code true} si el usuario puede ejecutar como m�nimo una actividad de las contenidas en la lista o {@code false} en cualquier otro caso.
    */
   public boolean isActivityAllowed(ArrayList<String> activityList)
   {
      return this.securityInfo.isActivityGranted(activityList);
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
      this.securityInfo = null;
   }
   
   
   //==============================================
   // Internal classes
   //==============================================
   
   /**
    * Contiene la informaci�n de autorizaci�n referente a la sesi�n actual.
    */
   public class SecurityInfo
   {
      private HashMap<String, Role> roles;
      private HashMap<String, Permission> permissions;
      
      /**
       * Constructor de la clase.
       */
      public SecurityInfo()
      {
         this.roles = new HashMap<String, Role>();
         this.permissions = new HashMap<String, Permission>();
      }
      
      /**
       * Agrega un rol a la informaci�n de seguridad de una sesi�n de usuario.
       * 
       * @param role Una instancia de {@link Role} que representa el rol a agregar.
       */
      public void addRole(Role role)
      {
         this.roles.put(role.getId(), role);
      }

      /**
       * Agrega un conjunto de roles a la informaci�n de seguridad de una sesi�n de usuario.
       * 
       * @param roles Un array de instancias de {@link Role} que representan los roles a agregar.
       */
      public void addRoles(ArrayList<Role> roles)
      {
         this.roles = new HashMap<String, Role>();
         
         for (Role role : roles)
         {
            addRole(role);
         }
      }
      
      /**
       * Agrega un permiso sobre una actividad a la informaci�n de seguridad de una sesi�n de usuario.
       * <br /><br />
       * La pol�tica de permisos indica que si el usuario tiene varios permisos sobre una actividad y al menos
       * una de ellas es afirmativa, el usuario tendr� permiso de ejecuci�n de dicha actividad.
       * 
       * @param permission Una instancia de {@link Permission} que representa el permiso a agregar.
       */
      public void addPermission(Permission permission)
      {
         Permission perm = this.permissions.get(permission.getId());
         
         if (perm == null)
         {
            this.permissions.put(permission.getId(), permission);
         }
         else if (!perm.isGranted() && permission.isGranted())
         {
            this.permissions.put(permission.getId(), permission);
         }
      }
      
      /**
       * Agrega un conjunto de permisos a la informaci�n de seguridad de una sesi�n de usuario.
       * 
       * @param permissions Un array de instancias de {@link Permission} que representan los permisos a agregar.
       */
      public void addPermissions(ArrayList<Permission> permissions)
      {
         this.permissions = new HashMap<String, Permission>();
         
         for (Permission permission : permissions)
         {
            addPermission(permission);
         }
      }
      
      /**
       * Determina si el usuario propietario de la sesi�n tiene asignado un determinado rol.
       * 
       * @param roleId Una cadena que contiene el identificador (nombre) del rol.
       * 
       * @return {@code true} si el usuario pertenece al rol especificado o {@code false} en cualquier otro caso.
       */
      public boolean isInRole(String roleId)
      {
         Role role = this.roles.get(roleId);

         return (role != null);
      }
      
      /**
       * Determina si el usuario propietario de la sesi�n tiene asignado como m�nimo un rol de entre la lista 
       * de roles proporcionada.
       * 
       * @param roleList Un array que contiene los identificadores (nombre) de los roles.
       * 
       * @return {@code true} si el usuario pertenece a como m�nimo uno de los roles de la lista o {@code false} en cualquier otro caso.
       */
      public boolean isInRole(ArrayList<String> roleList)
      {
         Role role;
         
         for (String roleId : roleList)
         {
            role = this.roles.get(roleId);
            
            if (role != null)
            {
               return true;
            }
         }
         
         return false;
      }
      
      /**
       * Determina si el usuario propietario de la sesi�n tiene permiso para ejecutar determinada actividad.
       * 
       * @param activityId Una cadena que contiene el identificador (nombre) de la actividad.
       * 
       * @return {@code true} si el usuario puede ejecutar la actividad o {@code false} en cualquier otro caso.
       */
      public boolean isActivityGranted(String activityId)
      {
         Permission permission = this.permissions.get(activityId);

         return permission.isGranted();
      }
      
      /**
       * Determina si el usuario propietario de la sesi�n tiene permiso para ejecutar como m�nimo una determinada actividad
       * de las contenidas en una lista.
       * 
       * @param activityList Array de cadenas que contiene los identificadores (nombre) de las actividades.
       * 
       * @return {@code true} si el usuario puede ejecutar como m�nimo una actividad de las contenidas en la lista o {@code false} en cualquier otro caso.
       */
      public boolean isActivityGranted(ArrayList<String> activityList)
      {
         Permission permission;
         
         for (String activityId : activityList)
         {
            permission = this.permissions.get(activityId);
            
            if (permission != null)
            {
               return true;
            }
         }
         
         return false;
      }
   }
   
}
