package com.cosmo.ui.widgets.providers;

import com.cosmo.Workspace;
import com.cosmo.WorkspaceProperties;
import com.cosmo.ui.widgets.MenuWidget;
import com.cosmo.ui.widgets.MenuWidget.MenuTypes;
import com.cosmo.ui.widgets.MenuItem;
import com.cosmo.util.DataTypeException;
import com.cosmo.util.IOUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Implementa un proveedor de men� que coge las opciones de men� del archivo de configuraci�n de Cosmo.
 * 
 * @author Gerard Llort
 */
public class CosmoStaticMenuProvider extends MenuProvider
{
   private static final String XML_NODE_MENU = "menu";
   private static final String XML_NODE_MENUITEM = "menuitem";
   private static final String XML_ATT_ID = "id";
   private static final String XML_ATT_NAME = "name";
   private static final String XML_ATT_HREF = "href";
   private static final String XML_ATT_PARENT = "parent";
   private static final String XML_ATT_TYPE = "type";
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Obtiene los elementos de men� del workspace.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace para el que se desea obtener el men�.
    * @param type Un elemento de {@link MenuTypes} que indica qu� tipo de men� se desea obtener.
    * @return Una lista de instancias de {@link MenuItem} que representan los elementos de men�.
    * 
    * @throws MenuProviderException 
    */
   @Override
   public ArrayList<MenuItem> loadMenu(Workspace workspace, MenuTypes type) throws MenuProviderException
   {
      InputStream is = null;
      MenuTypes mtype; 
      Node nNode;
      Node menuNode;
      Element eElement;
      Element menuElement;
      NodeList menuList;
      NodeList nList;
      MenuItem menuitem;
      ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
      
      try
      {
         is = new FileInputStream(workspace.getServerContext().getRealPath("/" + WorkspaceProperties.PROPERTIES_FILENAME));

         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(is);
         doc.getDocumentElement().normalize();

         // Obtiene los elementos de men�
         menuList = doc.getElementsByTagName(CosmoStaticMenuProvider.XML_NODE_MENU);
         for (int menuId = 0; menuId < menuList.getLength(); menuId++) 
         {
            menuNode = menuList.item(menuId);
            if (menuNode.getNodeType() == Node.ELEMENT_NODE)
            {
               menuElement = (Element) menuNode;
               if (MenuWidget.convertToMenuType(menuElement.getAttribute(CosmoStaticMenuProvider.XML_ATT_TYPE)) == type)
               {
                  mtype = MenuWidget.convertToMenuType(menuElement.getAttribute(CosmoStaticMenuProvider.XML_ATT_TYPE));
                  if (mtype == type)
                  {
                     nList = menuElement.getElementsByTagName(CosmoStaticMenuProvider.XML_NODE_MENUITEM);
                     for (int temp = 0; temp < nList.getLength(); temp++) 
                     {
                        nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                           eElement = (Element) nNode;
                           menuitem = new MenuItem(eElement.getAttribute(CosmoStaticMenuProvider.XML_ATT_ID),
                                                   eElement.getAttribute(CosmoStaticMenuProvider.XML_ATT_NAME), 
                                                   eElement.getAttribute(CosmoStaticMenuProvider.XML_ATT_HREF),
                                                   eElement.getAttribute(CosmoStaticMenuProvider.XML_ATT_PARENT));
                           menuItems.add(menuitem);
                        }
                     }
                  }
               }
            }
         }
         
         return menuItems;
      }
      catch (FileNotFoundException ex)
      {
         throw new MenuProviderException(ex.getMessage(), ex);
      }
      catch (IOException ex)
      {
         throw new MenuProviderException(ex.getMessage(), ex);
      }
      catch (ParserConfigurationException ex)
      {
         throw new MenuProviderException(ex.getMessage(), ex);
      }
      catch (SAXException ex)
      {
         throw new MenuProviderException(ex.getMessage(), ex);
      }
      catch (DataTypeException ex)
      {
         throw new MenuProviderException(ex.getMessage(), ex);
      }
      finally
      {
         IOUtils.closeStream(is);
      }
   }
}
