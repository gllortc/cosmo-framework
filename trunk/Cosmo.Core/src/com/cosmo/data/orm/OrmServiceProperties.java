package com.cosmo.data.orm;

import java.util.Collection;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cosmo.data.orm.apps.OrmApplication;

/**
 * Contenedor encargado de leer y almacenar toda la configuraci�n de UI Services.
 * 
 * @author Gerard Llort
 */
public class OrmServiceProperties
{
   // Definici�n de tags y atributos para ORM Services
   private static final String XML_TAG_CORM_APPS = "corm-apps";
   private static final String XML_TAG_CORM_APP = "corm-app";
   private static final String XML_TAG_APPACTION = "app-action";

   private static final String XML_ATT_CLASS = "class";
   private static final String XML_ATT_CONNECTION = "connection";
   private static final String XML_ATT_TYPE = "type";
   private static final String XML_ATT_TITLE = "title";
   private static final String XML_ATT_DESCRIPTION = "description";
   private static final String XML_ATT_ID = "id";
   
   // Declaraci�n de variables locales para UI Services
   private HashMap<String, OrmApplication> ormApps;


   //==============================================
   // Constructors
   //==============================================
   
   public OrmServiceProperties(Document xmlDocument)
   {
      loadProperties(xmlDocument);
   }


   //==============================================
   // Properties
   //==============================================


   //==============================================
   // Methods
   //==============================================

   /**
    * Obtiene todas las aplicaciones CORM registradas.
    *
    * @return Una colecci�n de instancias de {@link OrmApplication}.
    */
   public Collection<OrmApplication> getOrmApplications()
   {
      return this.ormApps.values();
   }

   /**
    * Obtiene la definici�n de una aplicaci�n CORM.
    *
    * @param appId Identificador de la aplicaci�n.
    *
    * @return Una instancia de {@link OrmApplication} que representa la aplicaci�n.
    */
   public OrmApplication getOrmApplication(String appId)
   {
      return this.ormApps.get(appId);
   }

   /**
    * Lee y almacena las propiedades de configuraci�n de UI Services.
    * 
    * @param doc Una instancia de {@link Document} que contiene la configuraci�n (XML) de la aplicaci�n.
    */
   public void loadProperties(Document doc)
   {
      Node actionNode;
      NodeList appsList;
      NodeList attribList;
      Element appElement;
      Element actionElement;
      OrmApplication oa;

      this.ormApps = new HashMap<String, OrmApplication>();

      // Comprueba si existe la definici�n
      attribList = doc.getElementsByTagName(OrmServiceProperties.XML_TAG_CORM_APPS);
      if (attribList.getLength() < 1)
      {
         return;
      }

      appsList = doc.getElementsByTagName(OrmServiceProperties.XML_TAG_CORM_APP);
      for (int pidx = 0; pidx < appsList.getLength(); pidx++)
      {
         Node appNode = appsList.item(pidx);
         if (appNode.getNodeType() == Node.ELEMENT_NODE)
         {
            appElement = (Element) appNode;

            oa = new OrmApplication();
            oa.setId(appElement.getAttribute(OrmServiceProperties.XML_ATT_ID));
            oa.setClassName(appElement.getAttribute(OrmServiceProperties.XML_ATT_CLASS));
            oa.setConnectionId(appElement.getAttribute(OrmServiceProperties.XML_ATT_CONNECTION));
            oa.setTitle(appElement.getAttribute(OrmServiceProperties.XML_ATT_TITLE));
            oa.setDescription(appElement.getAttribute(OrmServiceProperties.XML_ATT_DESCRIPTION));

            attribList = appElement.getElementsByTagName(OrmServiceProperties.XML_TAG_APPACTION);
            for (int aidx = 0; aidx < attribList.getLength(); aidx++) 
            {
               actionNode = attribList.item(aidx);
               if (actionNode.getNodeType() == Node.ELEMENT_NODE)
               {
                  actionElement = (Element) actionNode;

                  if (actionElement.getAttribute(OrmServiceProperties.XML_ATT_TYPE).toLowerCase().trim().equals("delete"))
                  {
                     oa.setDeleteEnabled(true);
                  }
                  else if (actionElement.getAttribute(OrmServiceProperties.XML_ATT_TYPE).toLowerCase().trim().equals("create"))
                  {
                     oa.setCreateEnabled(true);
                  }
                  else if (actionElement.getAttribute(OrmServiceProperties.XML_ATT_TYPE).toLowerCase().trim().equals("edit"))
                  {
                     oa.setEditEnabled(true);
                  }
               }
            }

            this.ormApps.put(oa.getId(), oa);
         }
      }
   }
}
