package com.cosmo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cosmo.data.DataSource;
import com.cosmo.security.Agent;
import com.cosmo.util.IOUtils;
import com.cosmo.util.StringUtils;

/**
 * Representa la configuraci�n del workspace.
 */
public class WorkspaceProperties 
{
   public static final String PROPERTIES_FILENAME = "cosmo.config.xml";

   // Literales para los nodos del archivo de configuraci�n
   private static final String XML_TAG_CONNECTIONS = "connections";
   private static final String XML_ATT_COSMOSERVER = "cosmo-server";
   private static final String XML_TAG_CONNECTION = "connection";
   private static final String XML_ATT_ID = "id";
   private static final String XML_ATT_JDBC_DRIVER = "jdbc.driver";
   private static final String XML_ATT_CORM_DRIVER = "corm.driver";
   private static final String XML_ATT_SERVER = "server";
   private static final String XML_ATT_PORT = "port";
   private static final String XML_ATT_SCHEMA = "schema";
   private static final String XML_ATT_USER = "user";
   private static final String XML_ATT_PASSWORD = "pwd";
   private static final String XML_ATT_KEY = "key";
   private static final String XML_ATT_VALUE = "value";
   private static final String XML_TAG_AUTHENTICATION = "authentication-agent";
   private static final String XML_TAG_AUTHORIZATION = "authorization-agent";
   private static final String XML_ATT_DRIVER = "driver";
   private static final String XML_TAG_PARAMETER = "param";
   private static final String XML_TAG_SECURITY = "security";
   private static final String XML_ATT_AUTHENTICATIONAGENT = "authentication-agent";
   private static final String XML_ATT_AUTHORIZATIONAGENT = "authorization-agent";
   private static final String XML_ATT_LOGINPAGE = "login-page";
   
   // Par�metros de configuraci�n
   private HashMap<String, String> properties;
   
   // Datasources (conexiones a datos)
   private HashMap<String, DataSource> dataSources;
   private String serverDatasource;
   
   // Seguridad
   private String loginPage;
   private String authenticationAgent;
   private String authorizationAgent;
   private HashMap<String, Agent> authenticationAgents;
   private HashMap<String, Agent> authorizationAgents;
      

   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.
    * 
    * @param context Contexto del servidor web.
    * 
    * @throws WorkspaceLoadException
    */
   public WorkspaceProperties(ServletContext context) throws WorkspaceLoadException
   {
      initialize();
      loadConfig(context);
   }
   
   
   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve el n�mero de propiedades de configuraci�n le�das.
    */
   public int getNumProperties()
   {
      return this.properties.size();
   }
   
   /**
    * Devuelve el n�mero de DataSources le�dos.
    */
   public int getNumDatasources()
   {
      return this.dataSources.size();
   }
   
   /**
    * Obtiene el nombre de la conexi�n al servidor Cosmo.
    * 
    * @return Una cadena que contiene el nombre del datasource definido como servidor Cosmo.
    */
   public String getServerDataSourceName()
   {
      return this.serverDatasource;
   }
   
   /**
    * Obtiene la p�gina de login.
    * 
    * @return Una cadena que contiene el nombre del servlet que act�a de p�gina de login.
    */
   public String getLoginPage()
   {
      return this.loginPage;
   }
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Obtiene el valor de configuraci�n asociado a una clave.
    * 
    * @param key Clave asociada al valor deseado.
    * @return Devuelve una cadena de texto que corresponde al valor asociado a la clave especificada.
    */
   public String getString(String key) 
   {
      return properties.get(key);
   }
   
   /**
    * Obtiene el valor de configuraci�n asociado a una clave.
    * 
    * @param key Clave asociada al valor deseado.
    * @return Devuelve una cadena de texto que corresponde al valor asociado a la clave especificada.
    */
   public Integer getInteger(String key, Integer defaultNum) 
   {
      String snum = properties.get(key);
      
      try
      {
         return Integer.valueOf(snum);
      }
      catch (NumberFormatException ex)
      {      
         return defaultNum;
      }
   }
   
   /**
    * Obtiene el valor de configuraci�n asociado a una clave.
    * 
    * @param key Clave asociada al valor deseado.
    * @return Devuelve una cadena de texto que corresponde al valor asociado a la clave especificada.
    */
   public Boolean getBoolean(String key) 
   {
      String snum = properties.get(key);
      return Boolean.valueOf(snum);
   }
   
   /**
    * Obtiene el valor de configuraci�n asociado a una clave.
    * 
    * @param key Clave asociada al valor deseado.
    * @return Devuelve una cadena de texto que corresponde al valor asociado a la clave especificada.
    */
   @Deprecated
   public String getWorkspaceProperty(String key) 
   {
      return properties.get(key);
   }
   
   /**
    * Obtiene la consexi�n al servidor Cosmo.
    * 
    * @return Una instancia de {@link DataSource} que contiene los par�metros de conexi�n a la base de datos.
    */
   public DataSource getServerDataSource()
   {
      if (StringUtils.isNullOrEmptyTrim(this.serverDatasource))
      {
         return null;
      }
      
      return getDataSource(this.serverDatasource);
   }
   
   /**
    * Obtiene una conxei�n a un or�gen de datos.
    * 
    * @param key Clave identificativa de la conexi�n.
    * @return Una instancia de {@link DataSource} que contiene los par�metros de conexi�n a la base de datos.
    */
   public DataSource getDataSource(String key)
   {
      return this.dataSources.get(key);
   }
   
   /**
    * Obtiene el agente de autenticaci�n.
    * 
    * @return Una instancia de {@link Agent} que contiene la informaci�n del agente de autenticaci�n a usar o {@code null} si no se ha configurado.
    */
   public Agent getAuthenticationAgent()
   {
      if (StringUtils.isNullOrEmptyTrim(this.authenticationAgent))
      {
         return null;
      }
      
      return this.authenticationAgents.get(this.authenticationAgent);
   }
   
   /**
    * Obtiene el agente de autorizaci�n.
    * 
    * @return Una instancia de {@link Agent} que contiene la informaci�n del agente de autorizaci�n a usar o {@code null} si no se ha configurado.
    */
   public Agent getAuthorizationAgent()
   {
      if (StringUtils.isNullOrEmptyTrim(this.authorizationAgent))
      {
         return null;
      }
      
      return this.authorizationAgents.get(this.authorizationAgent);
   }
   
   //==============================================
   // Private members
   //==============================================
   
   /**
    * Carga la configuraci�n del workspace. 
    * 
    * @param context Contexto del servidor web.
    * 
    * @throws ParserConfigurationException
    * @throws SAXException
    * @throws IOException
    */
   private void loadConfig(ServletContext context) throws WorkspaceLoadException
   {
      Node nNode;
      Node pNode;
      Element eElement;
      Element pElement;
      NodeList nList;
      NodeList pList;
      DataSource ds;
      Agent agent;
      InputStream iStream = null;
      
      try
      {
         iStream = new FileInputStream(context.getRealPath("/" + WorkspaceProperties.PROPERTIES_FILENAME));

         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(iStream);
         doc.getDocumentElement().normalize();

         // Obtiene los valores de configuraci�n clave/valor
         nList = doc.getElementsByTagName(WorkspaceProperties.XML_TAG_PARAMETER);
         for (int temp = 0; temp < nList.getLength(); temp++) 
         {
            nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
               eElement = (Element) nNode;
               String name = eElement.getAttribute(WorkspaceProperties.XML_ATT_KEY);
               String val = eElement.getAttribute(WorkspaceProperties.XML_ATT_VALUE); 

               this.properties.put(name, val);
            }
         }

         // Obtiene la informaci�n de conexiones a base de datos
         nList = doc.getElementsByTagName(WorkspaceProperties.XML_TAG_CONNECTIONS);
         if (nList.getLength() >= 1)
         {
            // Obtiene la configuraci�n
            nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
               eElement = (Element) nNode;
               this.serverDatasource = eElement.getAttribute(WorkspaceProperties.XML_ATT_COSMOSERVER);
            }
            
            nList = doc.getElementsByTagName(WorkspaceProperties.XML_TAG_CONNECTION);
            for (int temp = 0; temp < nList.getLength(); temp++) 
            {
               nNode = nList.item(temp);
               if (nNode.getNodeType() == Node.ELEMENT_NODE)
               {
                  eElement = (Element) nNode;

                  ds = new DataSource();
                  ds.setId(eElement.getAttribute(WorkspaceProperties.XML_ATT_ID));
                  ds.setJdbcDriver(eElement.getAttribute(WorkspaceProperties.XML_ATT_JDBC_DRIVER));
                  ds.setCormDriver(eElement.getAttribute(WorkspaceProperties.XML_ATT_CORM_DRIVER));
                  ds.setHost(eElement.getAttribute(WorkspaceProperties.XML_ATT_SERVER));
                  ds.setPort(eElement.getAttribute(WorkspaceProperties.XML_ATT_PORT));
                  ds.setSchema(eElement.getAttribute(WorkspaceProperties.XML_ATT_SCHEMA));
                  ds.setLogin(eElement.getAttribute(WorkspaceProperties.XML_ATT_USER));
                  ds.setPassword(eElement.getAttribute(WorkspaceProperties.XML_ATT_PASSWORD));

                  this.dataSources.put(ds.getId(), ds);
               }
            }
         }
         
         // Obtiene la informaci�n de seguridad
         nList = doc.getElementsByTagName(WorkspaceProperties.XML_TAG_SECURITY);
         if (nList.getLength() >= 1)
         {
            // Obtiene la configuraci�n
            nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
               eElement = (Element) nNode;

               this.authenticationAgent = eElement.getAttribute(WorkspaceProperties.XML_ATT_AUTHENTICATIONAGENT);
               this.authorizationAgent = eElement.getAttribute(WorkspaceProperties.XML_ATT_AUTHORIZATIONAGENT);
               this.loginPage = eElement.getAttribute(WorkspaceProperties.XML_ATT_LOGINPAGE);
            }
            
            // Obtiene todos los agentes de autenticaci�n
            nList = doc.getElementsByTagName(WorkspaceProperties.XML_TAG_AUTHENTICATION);
            for (int temp = 0; temp < nList.getLength(); temp++) 
            {
               nNode = nList.item(temp);
               if (nNode.getNodeType() == Node.ELEMENT_NODE)
               {
                  eElement = (Element) nNode;

                  agent = new Agent();
                  agent.setId(eElement.getAttribute(WorkspaceProperties.XML_ATT_ID));
                  agent.setModuleClass(eElement.getAttribute(WorkspaceProperties.XML_ATT_DRIVER));
                  
                  pList = eElement.getElementsByTagName(WorkspaceProperties.XML_TAG_PARAMETER);
                  for (int pNum = 0; pNum < pList.getLength(); pNum++) 
                  {
                     pNode = pList.item(pNum);
                     if (pNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        pElement = (Element) pNode;
                        agent.setParam(pElement.getAttribute(WorkspaceProperties.XML_ATT_KEY), 
                                       pElement.getAttribute(WorkspaceProperties.XML_ATT_VALUE));
                     }
                  }
                  
                  this.authenticationAgents.put(agent.getId(), agent);
               }
            }
            
            // Obtiene todos los agentes de autorizaci�n
            nList = doc.getElementsByTagName(WorkspaceProperties.XML_TAG_AUTHORIZATION);
            for (int temp = 0; temp < nList.getLength(); temp++) 
            {
               nNode = nList.item(temp);
               if (nNode.getNodeType() == Node.ELEMENT_NODE)
               {
                  eElement = (Element) nNode;

                  agent = new Agent();
                  agent.setId(eElement.getAttribute(WorkspaceProperties.XML_ATT_ID));
                  agent.setModuleClass(eElement.getAttribute(WorkspaceProperties.XML_ATT_DRIVER));
                  
                  pList = eElement.getElementsByTagName(WorkspaceProperties.XML_TAG_PARAMETER);
                  for (int pNum = 0; pNum < pList.getLength(); pNum++) 
                  {
                     pNode = pList.item(pNum);
                     if (pNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        pElement = (Element) pNode;
                        agent.setParam(pElement.getAttribute(WorkspaceProperties.XML_ATT_KEY), 
                                       pElement.getAttribute(WorkspaceProperties.XML_ATT_VALUE));
                     }
                  }
                  
                  this.authorizationAgents.put(agent.getId(), agent);
               }
            }
         }
         
         iStream.close();
      }
      catch (ParserConfigurationException ex)
      {
         throw new WorkspaceLoadException(ex.getMessage(), ex);
      }
      catch (SAXException ex)
      {
         throw new WorkspaceLoadException(ex.getMessage(), ex);
      }
      catch (IOException ex)
      {
         throw new WorkspaceLoadException(ex.getMessage(), ex);
      }
      finally
      {
         IOUtils.closeStream(iStream);
      }
   }
   
   /**
    * Inicializa la instancia.
    */
   private void initialize()
   {
      properties = new HashMap<String, String>();
      dataSources = new HashMap<String, DataSource>();
      serverDatasource = "";
      authenticationAgents = new HashMap<String, Agent>();
      authorizationAgents = new HashMap<String, Agent>();
      loginPage = "";
      authenticationAgent = "";
      authorizationAgent = "";
   }
}
