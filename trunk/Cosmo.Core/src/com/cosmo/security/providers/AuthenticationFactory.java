package com.cosmo.security.providers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.cosmo.Workspace;
import com.cosmo.structures.PluginProperties;
import com.cosmo.util.StringUtils;

/**
 * Implementa una clase que permite obtener la instancia del agente de autenticaci�n configurado en el workspace.
 * <br /><br />
 * Esta clase est� dise�ada seg�n el patr�n <em>Singleton</em> para asegurar que s�lo se instancia una vez
 * la implementaci�n del agente de autenticaci�n.
 * 
 * @author Gerard Llort
 */
public abstract class AuthenticationFactory 
{
   // Instancia �nica del agente de autenticaci�n
   private static Authentication instance = null;
   
   
   //==============================================
   // Static members
   //==============================================
   
   /**
    * Devuelve una instancia de {@link Authentication} convenientemente instanciada y con
    * el proveedor de autenticaci�n de usuarios cargado.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * 
    * @return Una instancia �nica de {@link Authentication} (sigleton).
    * 
    * @throws AuthenticationProviderException 
    */
   public static Authentication getInstance(Workspace workspace) throws AuthenticationProviderException 
   {
      if (instance == null) 
      {
         instance = loadProvider(workspace);
      }

      return instance;
   }

   
   //==============================================
   // Private members
   //==============================================
   
   /**
    * Carga el controlador de usuarios.
    * 
    * @throws AuthenticationProviderException 
    */
   private static Authentication loadProvider(Workspace workspace) throws AuthenticationProviderException
   {
      String className = "-- no authentication provider defined in proprties --";
      PluginProperties agent;
      Authentication provider;
      
      // Obtiene el agente de autenticaci�n
      agent = workspace.getProperties().getAuthenticationAgent();
      if (agent == null)
      {
         throw new AuthenticationProviderException("Security Configuration Exception: No authentication agent found");
      }
      
      // Obtiene el driver de autenticaci�n
      className = agent.getModuleClass();
      if (StringUtils.isNullOrEmptyTrim(className))
      {
         throw new AuthenticationProviderException("Security Configuration Exception: No authentication driver found");
      }
      
      try 
		{
         Class<?> cls = Class.forName(className);
         Constructor<?> cons = cls.getConstructor(Workspace.class);
         provider = (Authentication) cons.newInstance(workspace);
         
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
