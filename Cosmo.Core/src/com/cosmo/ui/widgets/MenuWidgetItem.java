package com.cosmo.ui.widgets;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implementa un elemento de men�.
 * 
 * @author Gerard Llort
 */
public class MenuWidgetItem 
{
   private String id;
   private String title;
   private String href;
   private String parentId;
   private ArrayList<MenuWidgetItem> subitems;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link MenuWidgetItem}.
    * 
    * @param id Identificador �nico del elemento.
    * @param title T�tulo visible del elemento de men�.
    * @param href URL de acceso a la p�gina.
    */
   public MenuWidgetItem(String id, String title, String href, String parentId)
   {
      this.id = id;
      this.title = title;
      this.href = href;
      this.parentId = parentId;
      this.subitems = new ArrayList<MenuWidgetItem>();
   }

   
   //==============================================
   // Properties
   //==============================================

   public String getId() 
   {
      return id;
   }

   public void setId(String id) 
   {
      this.id = id;
   }
   
   public String getTitle() 
   {
      return title;
   }

   public void setTitle(String title) 
   {
      this.title = title;
   }

   public String getHref() 
   {
      return href;
   }

   public void setHref(String href) 
   {
      this.href = href;
   }

   public String getParentId() 
   {
      return parentId;
   }

   public void setParentId(String parentId) 
   {
      this.parentId = parentId;
   }

   public Iterator<MenuWidgetItem> getSubItems()
   {
      return this.subitems.iterator();
   }

   public boolean hasSubItems()
   {
      return !this.subitems.isEmpty();
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Agrega un sub-elemento al elemento actual.
    * 
    * @param item Una instancia de {@link MenuWidgetItem} que representa el elemento de men� a insertar.
    */
   public void addSubItem(MenuWidgetItem item)
   {
      this.subitems.add(item);
   }
}
