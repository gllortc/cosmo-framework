package com.cosmo.ui.render;

import com.cosmo.Cosmo;
import com.cosmo.Workspace;
import com.cosmo.security.auth.AuthenticationException;
import com.cosmo.ui.Page;
import com.cosmo.ui.templates.TemplateUnavailableException;

/**
 * Singleton que proporciona la instancia del renderizador de paginas Cosmo.
 * 
 * @author Gerard Llort
 */
public abstract class PageRenderFactory 
{
   // Instancia singleton del renderizador
   private static PageRenderFactory instance = null;
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Renderiza la p�gina convirtiendo la l�gica de clases en c�digo XHTML.
    * 
    * @param page Una instancia de {@link Page} que representa la p�gina a renderizar.
    * @return Una cadena que contiene el c�digo XHTML.
    * 
    * @throws TemplateUnavailableException
    * @throws PageRenderException 
    */
   public abstract String render(Page page) throws TemplateUnavailableException, PageRenderException;
   
   //==============================================
   // Static members
   //==============================================
   
   /**
    * Devuelve una instancia de {@link PageRenderFactory} convenientemente instanciada y con
    * el proveedor de renderizado cargado.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * 
    * @return Una instancia �nica de {@link PageRenderFactory} (sigleton).
    * 
    * @throws LoadPageRenderException 
    */
   public static PageRenderFactory getInstance(Workspace workspace) throws LoadPageRenderException 
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
    * @throws AuthenticationException 
    */
   private static PageRenderFactory loadProvider(Workspace workspace) throws LoadPageRenderException
   {
      String className = "-- no render provider defined in proprties --";
      PageRenderFactory provider;
      
      try 
		{
         // Recupera el nombre de la clase
         className = workspace.getProperties().getString(Cosmo.PROPERTY_WORKSPACE_UI_RENDER_PROVIDER);
         
         // Genera una instancia de la clase
         Class<?> cls = Class.forName(className);
         provider = (PageRenderFactory) cls.newInstance();
         
         return provider;
		} 
		catch (ClassNotFoundException ex) 
		{
         throw new LoadPageRenderException("ClassNotFoundException: " + className, ex);
		}
      catch (InstantiationException ex)
      {
         throw new LoadPageRenderException("InstantiationException: " + className, ex);
      }
      catch (IllegalAccessException ex)
      {
         throw new LoadPageRenderException("IllegalAccessException: " + className, ex);
      }
   }
}
