package com.cosmo.ui.render;

import com.cosmo.Workspace;
import com.cosmo.ui.Page;
import com.cosmo.ui.PageContext;
import com.cosmo.ui.templates.TemplateUnavailableException;

/**
 * Interface que deben implementar todas las implementaciones de <em>renderer</em> para p�ginas de Cosmo.
 * 
 * @author Gerard Llort
 */
public interface PageRender
{
   public static final String TAG_WIDGET_LOGIN = "[@LOGIN]";
   public static final String TAG_WIDGET_MENU = "[@MENU]";
   public static final String TAG_WIDGET_NAVIGATIONBAR = "[@NAVBAR]";
   public static final String TAG_WIDGET_CONTENT = "[@CONTENT]";
   public static final String TAG_WIDGET_CONTENT_RIGHT = "[@CONTENT-RIGHT]";
   public static final String TAG_WIDGET_BANNERS_LEFT = "[@BANNERS-LEFT]";
   public static final String TAG_WIDGET_BANNERS_RIGHT = "[@BANNERS-RIGHT]";
   public static final String TAG_WIDGET_BANNERS_CENTER_TOP = "[@BANNERS-CENTER-TOP]";
   public static final String TAG_WIDGET_BANNERS_CENTER_BOTTOM = "[@BANNERS-CENTER-BOTTOM]";
   public static final String TAG_WIDGET_STATS = "[@STATS]";
   
   /**
    * Renderiza la p�gina convirtiendo la l�gica de clases en c�digo XHTML.
    * 
    * @param page Una instancia de {@link Page} que representa la p�gina a renderizar.
    * @return Una cadena que contiene el c�digo XHTML.
    * 
    * @throws TemplateUnavailableException
    * @throws PageRenderException 
    */
   public abstract String render(Workspace workspace, PageContext page) throws TemplateUnavailableException, PageRenderException;
}
