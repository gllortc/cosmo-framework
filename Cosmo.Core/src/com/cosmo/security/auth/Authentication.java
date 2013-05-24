package com.cosmo.security.auth;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.cosmo.security.User;
import com.cosmo.security.UserNotFoundException;

/**
 * Interface que deben implementar los agentes de autenticaci�n.
 * 
 * @author Gerard Llort
 */
public interface Authentication 
{
   public static String TOKEN_LOGIN_VALIDATED = "cosmo.authentication.logingateway.success";

   
   //------------------------------------------
   // Properties
   //------------------------------------------
   
   /**
    * Devuelve un {@code hash} que contiene los par�metros de configuraci�n del agente de seguridad.
    */
   public HashMap<String, String> getParameters();
   
   
   //------------------------------------------
   // Standard authentication methods
   //------------------------------------------
   
   /**
    * Verifica las credenciales de un usuario.
    * 
    * @param login Login del usuario.
    * @param password Contrase�a (sin encriptar) del usuario.
    * 
    * @return Una instancia de {@link User} que representa el usuario al que corresponden las credenciales proporcionadas.
    * 
    * @throws UserNotFoundException
    * @throws AuthenticationException 
    */
   public User login(String login, String password) throws UserNotFoundException, AuthenticationException;

   /**
    * Elimina la informaci�n de autenticaci�n actual.
    */
   public void logout();
   
   /**
    * Revalida la sesi�n de usuario.
    */
   public void validate();
   
   //------------------------------------------
   // Login Gateway authentication methods
   //------------------------------------------
   
   /**
    * Indica si el servicio usa un gateway para la autenticaci�n de usuarios.
    */
   public boolean isLoginGatewayRequired();
   
   /**
    * Devuelve la URL usada para la autenticaci�n de usuarios.
    */
   public String getLoginGatewayUrl();
   
   /**
    * Indica si una respuesta corresponde al retorno de la acci�n de login.
    * 
    * @param request Una instancia de {@link HttpServletRequest} que cotniene el contexto de la llamada.
    * 
    * @return {@code true} si la petici�n corresponde al retorno de la pantalla de login o {@coe false} en cualquier otro caso.
    */
   public boolean isLoginGatewayResponse(HttpServletRequest request);
   
   /**
    * Detecta si una autenticaci�n delegada (Login Gateway) ha sido exitosa.<br />
    * Las clases que extiendan a {@link AuthenticationFactory} ser�n responsables de obtener los datos del usuario 
    * autenticado en el sistema externo, ya sea mediante servicios REST u otros mecanismos.
    * 
    * @param request Una instancia de {@link HttpServletRequest} que cotniene el contexto de la llamada.
    * 
    * @return Una instancia de {@link User} que contiene las propiedades del usuario autenticado o {@code null} en cualquier otro caso. 
    */
   public User getLoginGatewayUser(HttpServletRequest request);
}
