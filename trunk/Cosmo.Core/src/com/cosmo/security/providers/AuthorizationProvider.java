package com.cosmo.security.providers;

import com.cosmo.Cosmo;
import com.cosmo.Workspace;
import com.cosmo.security.User;
import com.cosmo.security.User.UserStates;
import com.cosmo.security.UserAlreadyExistsException;
import com.cosmo.security.UserNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Interface para los proveedores de seguridad de Cosmo.
 * 
 * @author Gerard Llort
 */
public abstract class AuthorizationProvider 
{
   private static AuthorizationProvider instance = null;
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Verifica las credenciales de un usuario.
    * 
    * @param login Login del usuario.
    * @param password Contrase�a (sin encriptar) del usuario.
    * @return Una instancia de {@link User} que representa el usuario al que corresponden las credenciales proporcionadas.
    * 
    * @throws UserNotFoundException
    * @throws AuthenticationProviderException 
    */
   public abstract User login(String login, String password) throws UserNotFoundException, AuthenticationProviderException;

   /**
    * Crea una nueva cuenta de usuario.
    * 
    * @param user Una instancia de {@link User} que representa el nuevo usuario.
    *     
    * @throws UserAlreadyExistsException
    * @throws AuthenticationProviderException
    */
   public abstract void add(User user) throws UserAlreadyExistsException, AuthenticationProviderException;
   
   //==============================================
   // Static members
   //==============================================
   
   /**
    * Devuelve una instancia de {@link AuthorizationProvider} convenientemente instanciada y con
    * el proveedor de autenticaci�n de usuarios cargado.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * @return Una instancia única de {@link AuthorizationProvider} (sigleton).
    * 
    * @throws AuthenticationProviderException 
    */
   public static AuthorizationProvider getInstance(Workspace workspace) throws AuthenticationProviderException 
   {
      if (instance == null) 
      {
         instance = loadProvider(workspace);
      }

      return instance;
   }

   //==============================================
   // Static members
   //==============================================
   
   /**
    * Convierte el estado de un usuario a un valor num�rico usable en soportes como BBDD, archivos, etc.
    * 
    * @param state Un elemento de la enumeraci�n {@link UserStates}.
    * @return El valor num�rico equivalente al estado proporcionado.
    */
   public static int statusToNumber(UserStates state)
   {
      switch (state)
      {
         case Active:
            return 1;
         case NotConfirmed:
            return 2;
         default:
            return 0;
      }
   }
   
   //==============================================
   // Private members
   //==============================================
   
   /**
    * Carga el controlador de usuarios.
    * 
    * @throws AuthenticationProviderException 
    */
   private static AuthorizationProvider loadProvider(Workspace workspace) throws AuthenticationProviderException
   {
      String className = "-- no user provider defined in proprties --";
      AuthorizationProvider provider;
      
      try 
		{
         className = workspace.getProperties().getString(Cosmo.PROPERTY_WORKSPACE_SECURITY_PROVIDER);
         
         Class<?> cls = Class.forName(className);
         Constructor<?> cons = cls.getConstructor(Workspace.class);
         provider = (AuthorizationProvider) cons.newInstance(workspace);
         
         return provider;
		}
      catch (NoSuchMethodException ex) 
		{
         throw new AuthenticationProviderException("NoSuchMethodException: " + className, ex);
      }
      catch (InvocationTargetException ex) 
		{
         throw new AuthenticationProviderException("InvocationTargetException: " + className, ex);
      }
		catch (ClassNotFoundException ex) 
		{
         throw new AuthenticationProviderException("ClassNotFoundException: " + className, ex);
		}
      catch (InstantiationException ex)
      {
         throw new AuthenticationProviderException("InstantiationException: " + className, ex);
      }
      catch (IllegalAccessException ex)
      {
         throw new AuthenticationProviderException("IllegalAccessException: " + className, ex);
      }
   }
}
