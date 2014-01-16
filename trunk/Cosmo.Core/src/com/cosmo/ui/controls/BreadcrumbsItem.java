package com.cosmo.ui.controls;

import com.cosmo.ui.templates.TemplateControl;
import com.cosmo.util.StringUtils;

/**
 * Implementa un elemento de la barra de navegaci�n (breadcrumbs).
 * 
 * @author Gerard Llort
 */
public class BreadcrumbsItem 
{
   private static final String CPART_ITEM = "navbar-top-item";

   private static final String TAG_TITLE = "FTITLE";
   private static final String TAG_HREF = "HREF";
   private static final String TAG_ICON = "ICON";

   // Declaraci�n de variables internas
   private String title;
   private String href;
   private String icon;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase.
    */
   public BreadcrumbsItem()
   {
      this.title = StringUtils.EMPTY;
      this.href = StringUtils.EMPTY;
      this.icon = StringUtils.EMPTY;
   }

   /**
    * Constructor de la clase.
    * 
    * @param title T�tulo del elemento.
    */
   public BreadcrumbsItem(String title)
   {
      this.title = title;
      this.href = StringUtils.EMPTY;
      this.icon = StringUtils.EMPTY;
   }

   /**
    * Constructor de la clase.
    * 
    * @param title T�tulo del elemento.
    * @param href URL del enlace.
    */
   public BreadcrumbsItem(String title, String href)
   {
      this.title = title;
      this.href = href;
      this.icon = StringUtils.EMPTY;
   }

   /**
    * Constructor de la clase.
    * 
    * @param title T�tulo del elemento.
    * @param href URL del enlace.
    */
   public BreadcrumbsItem(String title, String href, String icon)
   {
      this.title = title;
      this.href = href;
      this.icon = icon;
   }


   //==============================================
   // Properties
   //==============================================

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

   public String getIcon() 
   {
      return icon;
   }

   public void setIcon(String icon) 
   {
      this.icon = icon;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Convierte la instancia en una cadena en formato XHTML a partir de la estructura de la plantilla.
    */
   public String render(TemplateControl tc)
   {
      String xhtml;

      xhtml = tc.getElement(CPART_ITEM);
      xhtml = Control.replaceTag(xhtml, TAG_ICON, Icon.render(this.icon, Icon.ICON_SIZE_DEFAULT));
      xhtml = Control.replaceTag(xhtml, TAG_TITLE, this.title);
      xhtml = Control.replaceTag(xhtml, TAG_HREF, this.href);

      return xhtml;
   }
}
