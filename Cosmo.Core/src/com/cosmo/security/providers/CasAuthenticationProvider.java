package com.cosmo.security.providers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cosmo.Workspace;
import com.cosmo.data.DataSource;
import com.cosmo.net.HttpRequestUtils;
import com.cosmo.security.Agent;
import com.cosmo.security.User;
import com.cosmo.security.UserNotFoundException;
import com.cosmo.util.StringUtils;

/**
 * Proveedor de seguridad nativo de Cosmo.<br />
 * Este proveedor requiere conexi�n a BBDD y tener las tablas de usuarios de Cosmo.
 * 
 * @author Gerard Llort
 */
public class CasAuthenticationProvider extends AuthenticationProvider 
{
   private Workspace workspace;
   private Agent agent;

   private static String PARAM_CASSERVICE = "cas-service";
   private static String PARAM_SERVICEURL = "service-url";
   
   private static String URL_PARAM_TICKET = "ticket";
   
   private String loginTicket;
   private String serviceTicket;
   
   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase. 
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace al que est� conectado actualmente.
    */
   public CasAuthenticationProvider(Workspace workspace)
   {
      serviceTicket = "";
      loginTicket = "";
      
      fClient = new HttpClient();
      
      this.workspace = workspace;
      this.agent = this.workspace.getProperties().getAuthenticationAgent();
   }
   
   
   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve el Ticket Granting Ticket de CAS.
    * 
    * @return Una cadena que contiene el Ticket Granting Ticket de CAS.
    */
   public String getGrantingTicket()
   {
      return this.serviceTicket;
   }
   
   
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
   @Override
   public User login(String login, String password) throws UserNotFoundException, AuthenticationProviderException
   {
      User user = null;
      
      try
      {
         fCasUrl = agent.getParamString(PARAM_CASSERVICE);
         
         authenticate(workspace.getRequestedUrl(), login, password);
      }
      catch (Exception ex)
      {
         throw new AuthenticationProviderException(ex.getMessage(), ex);
      }
      
      return user;
   }
   
   /**
    * Elimina la informaci�n de autenticaci�n actual.
    */
   @Override
   public void logout()
   {
      return;
   }
   
   /**
    * Revalida la sesi�n de usuario.
    */
   @Override
   public void validate() 
   {
      return;   
   };
   
   /**
    * Indica si el servicio usa un gateway para la autenticaci�n de usuarios.
    */
   public boolean isLoginGatewayRequired()
   {
      return true;
   }
   
   /**
    * Devuelve la URL usada para la autenticaci�n de usuarios.
    */
   public String getLoginGatewayUrl()
   {
      String host = agent.getParamString(PARAM_CASSERVICE).trim();
      host += (host.endsWith("/") ? "" : "/") + "login";
      
      com.cosmo.util.URL url = new com.cosmo.util.URL(host);
      url.addParameter("service", agent.getParamString(PARAM_SERVICEURL)); // workspace.getRequestedUrl()); 
      
      return url.toString();
   }
   
   /**
    * Detecta si una autenticaci�n delegada (Login Gateway) ha sido exitosa.<br />
    * Las clases que extiendan a {@link AuthenticationProvider} ser�n responsables de obtener los datos del usuario autenticado
    * en el sistema externo, ya sea mediante servicios REST u otros mecanismos.
    * 
    * @see Detalls del protocol: https://wiki.jasig.org/display/CAS/CAS+Functional+Tests
    * @see Detalls del protocol amb proxy: https://wiki.jasig.org/display/CAS/Proxy+CAS+Walkthrough
    * 
    * @param request Una instancia de {@link HttpServletRequest} que cotniene el contexto de la llamada.
    * 
    * @return {@code true} si detecta que la autenticaci�n ha tenido �xito o {@code false} en cualquier otro caso. 
    */
   @Override
   public boolean isLoginGatewayValidated(HttpServletRequest request)
   {
      // Obtiene el ServiceTicket
      serviceTicket = HttpRequestUtils.getValue(request, URL_PARAM_TICKET);

      if (!StringUtils.isNullOrEmpty(serviceTicket))
      {
         // Valida el ticket para obtener el LOGIN de usuario
         String login = validate(agent.getParamString(PARAM_SERVICEURL), serviceTicket);
      }
      
      return (!StringUtils.isNullOrEmpty(serviceTicket));
   }
   
   //==============================================
   // Private members
   //==============================================
   
   // public static Logger LOG = Logger.getLogger( CasClient.class  );

   public static final String LOGIN_URL_PART = "login";
   public static final String SERVICE_VALIDATE_URL_PART = "serviceValidate";
   public static final String TICKET_BEGIN = "ticket=";
   private static final String LT_BEGIN = "name=\"lt\" value=\"";
   public static final String CAS_USER_BEGIN = "<cas:user>";
   public static final String CAS_USER_END = "</cas:user>";
  
   private HttpClient fClient;
   private String fCasUrl;
  
   // public String 
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   /**
    * Construct a new CasClient.
    *
    * @param casUrl The base URL of the CAS service to be used.
    */
   /*public CasClient( String casBaseUrl )
   {
       this( new HttpClient(), casBaseUrl );
   }*/
  
   /**
    * Construct a new CasClient which uses the specified HttpClient
    * for its HTTP calls.
    *
    * @param client
    * @param casBaseUrl
    */
   /*public CasClient( HttpClient client, String casBaseUrl )
   {
       fClient = client;
       fCasUrl = casBaseUrl;
   }*/
  
   /**
    * Authenticate the specified username with the specified password.
    * This will not yield any ticket, as no service is authenticated
    * against. This wil just set the CAS cookie in this client upon
    * successful authentication.
    *
    * @param username
    * @param password
    */
   public void authenticate(String username, String password)
   {
      authenticate(null, username, password);
   }
  
   /**
    * Authenticate the specified user with the specified password against the specified service.
    *
    * @param serviceUrl May be null. If a url is specified, the authentication will happen against this service, yielding a service ticket which can be validated.
    * @param username
    * @param password
    * @return A valid service ticket, if and only if the specified service URL is not null.
    */
   public String authenticate(String serviceUrl, String username, String password)
   {
      String lt = getLt(serviceUrl);
      String result = null;
      PostMethod method = null;
       
      if (lt == null)
      {
         // LOG.error( "Cannot retrieve LT from CAS. Aborting authentication for '" + username + "'" );
         return null;
      }
       
      method = new PostMethod(fCasUrl + LOGIN_URL_PART);

      /*if (serviceUrl != null) // optional
      {
         method.setParameter("service", serviceUrl);
      }
      method.setParameter("_eventId", "submit");
      method.setParameter("username", username);
      method.setParameter("password", password);
      method.setParameter("lt", lt);
      method.setParameter("gateway", "true");*/
      
      method.setRequestBody(new NameValuePair[] 
      {
         new NameValuePair("service", serviceUrl),
         new NameValuePair("_eventId", "submit"),
         new NameValuePair("username", username),
         new NameValuePair("password", password),
         new NameValuePair("lt", lt),
         new NameValuePair("gateway", "true")
      });
      
      try
      {
         fClient.executeMethod(method);
           
         if (serviceUrl == null)
         {
            // if CAS does not return a login page with an LT authentication was successful
            if (extractLt(new String(method.getResponseBody())) != null)
            {
               // LOG.error("Authentication for '" +  username + "' unsuccessful");
               // if (LOG.isDebugEnabled())
               // {
                  // LOG.debug( "Authentication for '" + username + "' unsuccessful." );
               // }
            } 
            else
            {
               // if (LOG.isDebugEnabled())
               // {
               //    LOG.debug( "Authentication for '" + username + "' unsuccessful." );
               // }
            }
         } 
         else
         {
            Header h = method.getResponseHeader("Location");
               
            if (h != null)
            {
               result = extractServiceTicket(h.getValue());
            }
               
            if (result == null)
            {
               // LOG.error( "Authentication for '" + username + "' unsuccessful." );
            }
         }
      } 
      catch (Exception x)
      {
         // LOG.error( "Could not authenticate'" + username + "':" + x.toString () );
      }
      finally
      {
         method.releaseConnection();
      }
       
      return result;
   }
   
   /**
    * Validate the specified service ticket against the specified service.
    * If the ticket is valid, this will yield the clear text user name
    * of the autenticated user.<br>
    * Note that each service ticket issued by CAS can be used exactly once
    * to validate.
    *
    * @param serviceUrl
    * @param serviceTicket
    *
    * @return Clear text username of the authenticated user.
    */
   public String validate(String serviceUrl, String serviceTicket)
   {
      String result = null;
      PostMethod method = new PostMethod(agent.getParamString(PARAM_CASSERVICE) + SERVICE_VALIDATE_URL_PART);
      
      method.setParameter("service", serviceUrl);
      method.setParameter("ticket", serviceTicket);

      String url = method.getQueryString();
      
      try
      {
         int statusCode = fClient.executeMethod(method);
         
         if (statusCode != HttpStatus.SC_OK)
         {
            // LOG.error("Could not validate: " + method.getStatusLine());
            method.releaseConnection();
         } 
         else
         {   
            result = extractUser(new String(method.getResponseBody()));
         }
      } 
      catch (Exception x)
      {
         // LOG.error("Could not validate: " + x.toString ());
         x.printStackTrace();
      }
      finally
      {
         method.releaseConnection();
      }
      
      System.out.print("CAS USER: " + result);
      
      return result;
   }
  
   /**
    * Helper method to extract the user name from a "service validate" call to CAS.
    *
    * @param data Response data.
    * @return The clear text username, if it could be extracted, null otherwise.
    */
   protected String extractUser(String data)
   {
      int start;
      int end;
      String user = null;
      
      start = data.indexOf(CAS_USER_BEGIN);
       
      if (start >= 0)
      {
         start += CAS_USER_BEGIN.length();
         end = data.indexOf(CAS_USER_END);
           
         if (end > start)
         {
            user = data.substring(start, end);
         }
         else
         {
            // LOG.warn("Could not extract username from CAS validation response. Raw data is: '" + data + "'" );
         }
      } 
      else
      {
         // LOG.warn("Could not extract username from CAS validation response. Raw data is: '" + data + "'" );
      }

      return user;
   }
  
   /**
    * Helper method to extract the service ticket from a login call to CAS.
    *
    * @param data Response data.
    * @return The service ticket, if it could be extracted, null otherwise.
    */
   protected String extractServiceTicket(String data)
   {
      int start;
      String serviceTicket = null;
      
      start = data.indexOf(TICKET_BEGIN);

      if (start > 0)
      {
         start += TICKET_BEGIN.length();
         serviceTicket = data.substring(start);
      }

      return serviceTicket;
   }
  
   /**
    * Helper method to extract the LT from a login form from CAS.
    *
    * @param data Response data.
    * @return The LT, if it could be extracted, null otherwise.
    */
   protected String extractLt(String data)
   {
      int start;
      int end;
      String token = null;
      
      start = data.indexOf(LT_BEGIN);
       
      if ( start < 0 )
      {
         // LOG.error( "Could not obtain LT token from CAS: LT Token not found in response." );
      } 
      else
      {
         start += LT_BEGIN.length();
         end = data.indexOf("\"", start);
         token = data.substring(start, end);
      }

      return token;
   }
  
   /**
    * This method requests the original login form from CAS.
    * This form contains an LT, an initial token that must be
    * presented to CAS upon sending it an authentication request
    * with credentials.<br>
    * If a service URL is provided (which is optional), this method
    * will post the URL such that CAS authenticates against the
    * specified service when a subsequent authentication request is
    * sent.
    *
    * @param serviceUrl
    * @return The LT token if it could be extracted from the CAS response.
    */
   protected String getLt(String serviceUrl)
   {
       String lt = null;
       HttpMethod method = null;
       
       if (serviceUrl == null)
       {
           method = new GetMethod(fCasUrl + LOGIN_URL_PART);
       }
       else
       {
           method = new PostMethod(fCasUrl + LOGIN_URL_PART);
           ((PostMethod) method).setParameter("service", serviceUrl);
       }

       try
       {
           int statusCode = fClient.executeMethod(method);
           
           if (statusCode != HttpStatus.SC_OK)
           {
               // LOG.error( "Could not obtain LT token from CAS: " + method.getStatusLine() );
               method.releaseConnection();
           } 
           else
           {
               // Object o = method.getResponseHeaders();
               method.getResponseHeaders();
               return extractLt(new String(method.getResponseBody()));
           }
       } 
       catch (Exception x)
       {
           // LOG.error( "Could not obtain LT token from CAS: " + x.toString ());
       }
       finally
       {
          method.releaseConnection();
       }
       
       return lt;
   }
   
   // Tags XML para el parseo de las respuestas de CAS
   private static String TAG_CAS_USER = "<cas:user>";
   private static String TAG_CAS_PROPERTY = "<cas:attribute>";
   
   // Constantes para el mapeo de propiedades del usuario
   private static String CAS_ATTRIB_MAIL = "cas-attrib-mail";
   private static String CAS_ATTRIB_COMPLETNAME = "cas-attrib-cname";
   private static String CAS_ATTRIB_NAME = "cas-attrib-name";
   private static String CAS_ATTRIB_SURNAME = "cas-attrib-surname";
   
   /**
    * Parsea la respuesta de la validaci�n de ticket de CAS y extrae la informaci�n del usuario.
    * 
    * @param responseData Una cadena que contiene la respuesta del servidor CAS (en formato XML).
    * 
    * @return Una instancia de {@link User} que contiene los datos del usuario.
    * 
    * @throws Exception 
    */
   public static User getUserDataFromValidation(String responseData) throws Exception
   {
      Node nNode;
      Node pNode;
      Element eElement;
      Element pElement;
      NodeList nList;
      NodeList pList;
      
      User user = null;
      
      try
      {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setNamespaceAware(true);

         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(new InputSource(new StringReader(responseData)));
         doc.getDocumentElement().normalize();

         // Obtiene el LOGIN del usuario
         nList = doc.getElementsByTagName(CasAuthenticationProvider.TAG_CAS_USER);  // solo devolver� 1 elemento
         if (nList.getLength() > 0)
         {
            nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
               eElement = (Element) nNode;
               user.setLogin(eElement.getNodeValue());
            }
         }
         
         // Obtiene las PROPIEDADES del usuario
         nList = doc.getElementsByTagName(CasAuthenticationProvider.TAG_CAS_PROPERTY);
         for (int temp = 0; temp < nList.getLength(); temp++)
         {
            nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
               eElement = (Element) nNode;
               user.setLogin(eElement.getNodeValue());
            }
         }
         
         return user;
      }
      catch (ParserConfigurationException ex)
      {
         throw new Exception(ex.getMessage(), ex);
      }
      catch (SAXException ex)
      {
         throw new Exception(ex.getMessage(), ex);
      }
      catch (IOException ex)
      {
         throw new Exception(ex.getMessage(), ex);
      }
   }
   
   public static void main(String[] args)
   {
      String str = "<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'>\n" +
                   "<cas:authenticationSuccess>\n" +
                   "  <cas:user>gllort</cas:user>\n" +
                   "  <!-- Begin Ldap Attributes -->\n" +
                   "    <cas:attributes>\n" +
                   "      <cas:attribute>\n" +
                   "        <cas:name>mail</cas:name>\n" +
                   "        <cas:value>gllort@altanet.org</cas:value>\n" +
                   "      </cas:attribute>\n" +
                   "      <cas:attribute>\n" +
                   "        <cas:name>nif</cas:name>\n" +
                   "        <cas:value>46725607A</cas:value>\n" +
                   "      </cas:attribute>\n" +
                   "      <cas:attribute>\n" +
                   "        <cas:name>nomComplet</cas:name>\n" +
                   "        <cas:value>Gerard Llort Casanova</cas:value>\n" +
                   "      </cas:attribute>\n" +
                   "    </cas:attributes>\n" +
                   "  </cas:authenticationSuccess>\n" +
                   "</cas:serviceResponse>";
      
      try 
      {
         System.out.print(getUserDataFromValidation(str));
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
      }
   }
}
