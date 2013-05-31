package com.cosmo.ui;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosmo.Cosmo;
import com.cosmo.Workspace;
import com.cosmo.net.URL;
import com.cosmo.security.NotAuthorizedException;
import com.cosmo.security.User;
import com.cosmo.security.UserNotFoundException;
import com.cosmo.security.annotations.ActivitiesAllowed;
import com.cosmo.security.annotations.AuthenticationForm;
import com.cosmo.security.annotations.AuthenticationRequired;
import com.cosmo.security.annotations.RolesAllowed;
import com.cosmo.security.auth.Authentication;
import com.cosmo.security.auth.AuthenticationException;
import com.cosmo.security.auth.AuthenticationFactory;
import com.cosmo.security.auth.AuthorizationException;

/**
 * Implementa los mecanismos de seguridad de las p�ginas de Cosmo.
 * 
 * @author Gerard Llort
  */
public class PageSecurity 
{
   /**
    * Constructor de la clase.
    */
   public PageSecurity() { }
   
   /**
    * Chequea la seguridad de una p�gina y activa los mecanismos necesarios.
    * 
    * @param page Una instancia de {@link Page} que representa la p�gina a comprobar.
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual. 
    * 
    * @throws IOException 
    * @throws NotAuthorizedException 
    * @throws AuthenticationException 
    * @throws AuthorizationException 
    * @throws UserNotFoundException 
    */
   public void checkPageSecurity(Page page, Workspace workspace, HttpServletRequest request, HttpServletResponse response) throws IOException, NotAuthorizedException, AuthenticationException, UserNotFoundException, AuthorizationException
   {
      // Obtiene el agente de autenticaci�n
      Authentication auth = AuthenticationFactory.getInstance(workspace);
      
      //----------------------
      // Sesi�n de usuario
      //----------------------
      
      // Realiza el chequeo particular para cada tipo de mecanismo de login.
      if (AuthenticationFactory.isLoginGatewayAgent(auth))
      {
         checkLoginGatewaySession(request, response, workspace, auth, page);
      }
      else
      {
         checkSession(request, response, workspace, auth, page);
      }
      
      //----------------------
      // Anotaciones
      //----------------------
      
      // NOTA: No se comprueba la anotaci�n AuthenticationRequired debido a que ya va impl�cita 
      // a la llamada al m�todo isSessionRequired(page)-
      
      // Comprueba si existe la anotaci�n RolesAllowed y efect�a las comporbaciones de seguridad
      if (isRoleAllowed(page))
      {
         if (!workspace.getUserSession().isInRole(getRolesAllowed(page)))
         {
            throw new NotAuthorizedException("El usuario " + workspace.getUserSession().getCurrentUser().getLogin() + 
                                             " no puede ejecutar la acci�n solicitada debido a que no tiene ning�n rol con permiso.");
         }
      }
      
      // Comprueba si existe la anotaci�n ActivitiesAllowed y efect�a las comporbaciones de seguridad
      if (isActivityAllowed(page))
      {
         if (!workspace.getUserSession().isActivityAllowed(getActivitiesAllowed(page)))
         {
            throw new NotAuthorizedException("El usuario " + workspace.getUserSession().getCurrentUser().getLogin() + 
                                             " no puede ejecutar la acci�n solicitada debido a que no tiene permisos sobre la(s) actividad(es) solicitada(s).");
         }
      }
      
      /*
      // Comprueba si la llamada es una respuesta de autenticaci�n (retorno de login)
      if (auth.isLoginGatewayResponse(request))
      {
         // Obtiene el usuario y genera una nueva sesi�n
         User user = auth.getLoginGatewayUser(request);
         
         if (user == null)
         {
            throw new AuthenticationException("El proveedor de seguridad no pudo obtener las credenciales del usuario autenticado. La autenticaci�n ha fallado.");
         }
         
         workspace.createSession(user);
      }
      
      // Si no requiere sesi�n de usuario termina la comprobaci�n si hacer nada
      if (!isSessionRequired(page) && !isAuthenticationForm(page))
      {
         return;
      }
      
      // Si es la p�gina de login termina la comprobaci�n
      if (isAuthenticationForm(page) && !AuthenticationFactory.isLoginGatewayAgent(auth))
      {
         return;
      }
      
      // Comprueba si existe autenticaci�n (obligatorio)
      if (!workspace.isValidUserSession())
      {
         if (AuthenticationFactory.isLoginGatewayAgent(auth))
         {
            // Comprueba si la llamada es una respuesta de autenticaci�n (retorno de login)
            if (auth.isLoginGatewayResponse(request))
            {
               // Obtiene el usuario y genera una nueva sesi�n
               User user = auth.getLoginGatewayUser(request);
               
               if (user == null)
               {
                  throw new AuthenticationException("El proveedor de seguridad no pudo obtener las credenciales del usuario autenticado. La autenticaci�n ha fallado.");
               }
               
               workspace.createSession(user);
            }
            else
            {
               // Redirige hacia el mecanismo de login
               sendLoginGatewayRedirect(workspace, auth, response);
            }
         }
         else
         {
            // Redirige hacia la pantalla de login
            sendLoginRedirect(workspace, response);
         }
         
         return;
      }
      */
   }

   /**
    * Comprueba el acceso a la p�gina usando el mecanismo de login integrado.
    * El gui�n del chequeo es el siguiente:
    * <ul>
    * <li>Si el usuario tiene sesi�n v�lida, termina.</li>
    * <li>Si la p�gina requiere sesi�n de usuario, redirige al mecanismo de login interno.</li>
    * </ul>
    * 
    * @throws AuthenticationException 
    * @throws IOException 
    */
   private void checkSession(HttpServletRequest request, HttpServletResponse response, Workspace workspace, Authentication auth, Page page) throws AuthenticationException, IOException
   {
      if (workspace.isValidUserSession())
      {
         // Si la sesi�n de usuario es correcta, termina el chequeo de sesi�n
         return;
      }
      else if (isSessionRequired(page))
      {
         // Redirige hacia la pantalla de login
         sendLoginRedirect(workspace, response);
      }
   }
   
   /**
    * Comprueba la autenticaci�n (no autorizaci�n) para una determinada p�gina usando el mecanismo de <em>Login Gateway</em>.
    * El gui�n del chequeo es el siguiente:
    * <ul>
    * <li>Si el usuario tiene sesi�n v�lida, termina.</li>
    * <li>Si es una respuesta del servicio de autenticaci�n externo, recupera el usuario, genera la nueva sesi�n y termina.</li>
    * <li>Si la p�gina requiere sesi�n de usuario, redirige al mecanismo de login externo.</li>
    * </ul>
    * 
    * @throws AuthenticationException 
    * @throws AuthorizationException 
    * @throws UserNotFoundException 
    * @throws IOException 
    */
   private void checkLoginGatewaySession(HttpServletRequest request, HttpServletResponse response, Workspace workspace, Authentication auth, Page page) throws AuthenticationException, UserNotFoundException, AuthorizationException, IOException
   {
      // Si la sesi�n de usuario es correcta, termina el chequeo de sesi�n
      if (workspace.isValidUserSession())
      {
         return;
      }
      // Comprueba si la llamada es una respuesta de autenticaci�n (retorno de login)
      else if (auth.isLoginGatewayResponse(request))
      {
         // Obtiene el usuario y genera una nueva sesi�n
         User user = auth.getLoginGatewayUser(request);
         
         if (user == null)
         {
            throw new AuthenticationException("El proveedor de seguridad no pudo obtener las credenciales del usuario autenticado. La autenticaci�n ha fallado.");
         }
         
         workspace.createSession(user);
      }
      else if (isSessionRequired(page))
      {
         // Redirige hacia el mecanismo de login
         sendLoginGatewayRedirect(workspace, auth, response);
      }
   }
   
   /**
    * Redirecciona el flujo hacia la pantalla (o mecanismo) de login mediante <em>Login Gateway</em>.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual. 
    * @param auth Una instancia de {@link Authentication} que proporciona la funcionalidad espec�fica del agente para redireccionar.
    * @param response Una instancia de {@link HttpServletResponse} que representa la respuesta en el contexto.
    * 
    * @throws IOException
    */
   private void sendLoginGatewayRedirect(Workspace workspace, Authentication auth, HttpServletResponse response) throws IOException
   {
      response.sendRedirect(auth.getLoginGatewayUrl());
   }
   
   /**
    * Envia una redirecci�n hacia el mecanismo de authenticaci�n (login).
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * 
    * @throws IOException 
    */
   private void sendLoginRedirect(Workspace workspace, HttpServletResponse response) throws IOException
   {
      URL url;
      
      url = new URL(workspace.getProperties().getLoginPage());
      url.addParameter(Cosmo.URL_PARAM_TOURL, workspace.getRequestedUrl());
      
      // Redirecciona la p�gina al servlet de LOGIN.
      response.sendRedirect(url.build(workspace.getCharset()));
   }
   
   /**
    * Indica si la p�gina requiere sesi�n de usuario (contiene alguna anotaci�n de seguridad) para ser accedida.
    * 
    *  @param page Una instancia de {@link Page} que representa la p�gina a comprobar.
    *  
    *  @return {@code true} si la p�gina requiere autenticaci�n para ser accedida o {@code false} en cualquier otro caso.
    */
   private boolean isSessionRequired(Page page)
   {
      boolean sessionRequired = false;
      
      sessionRequired = sessionRequired || (page.getClass().isAnnotationPresent(AuthenticationRequired.class));
      sessionRequired = sessionRequired || (page.getClass().isAnnotationPresent(RolesAllowed.class));
      sessionRequired = sessionRequired || (page.getClass().isAnnotationPresent(ActivitiesAllowed.class));
      
      return sessionRequired;
   }
   
   /**
    * Indica si la p�gina contiene un formulario de autenticaci�n.
    * 
    *  @param page Una instancia de {@link Page} que representa la p�gina a comprobar.
    *  
    *  @return {@code true} si la p�gina contiene un formulario de autenticaci�n o {@code false} en cualquier otro caso.
    */
   private boolean isAuthenticationForm(Page page)
   {
      return page.getClass().isAnnotationPresent(AuthenticationForm.class);
   }
   
   /**
    * Indica si la p�gina requiere uno de los roles especificados para ser accedida. 
    */
   private boolean isRoleAllowed(Page page)
   {
      return page.getClass().isAnnotationPresent(RolesAllowed.class);
   }
   
   /**
    * Obtiene la lista de roles admitidos para ejecutar determinada p�gina.<br />
    * El usuario debe disponer de una de ellas para ejecutar la p�gina.
    * 
    * @return Un array de identificadores de rol.
    */
   private ArrayList<String> getRolesAllowed(Page page)
   {
      RolesAllowed ra;
      ArrayList<String> list;
      
      list = new ArrayList<String>();
      ra = page.getClass().getAnnotation(RolesAllowed.class);
      
      for (String str : ra.value())
      {
         list.add(str);
      }
      
      return list;
   }
   
   /**
    * Indica si la p�gina requiere permisos sobre una o varias actividades para ser accedida. 
    */
   private boolean isActivityAllowed(Page page)
   {
      return page.getClass().isAnnotationPresent(ActivitiesAllowed.class);
   }
   
   /**
    * Obtiene una lista de las actividades requeridas para ejecutar determinada p�gina.<br />
    * El usuario debe disponer de permiso sobre una de ellas para ejecutar la p�gina.
    * 
    * @return Un array de identificadores de actividad.
    */
   private ArrayList<String> getActivitiesAllowed(Page page)
   {
      ActivitiesAllowed aa;
      ArrayList<String> list;
      
      list = new ArrayList<String>();
      aa = page.getClass().getAnnotation(ActivitiesAllowed.class);
      
      for (String str : aa.value())
      {
         list.add(str);
      }
      
      return list;
   }
}
