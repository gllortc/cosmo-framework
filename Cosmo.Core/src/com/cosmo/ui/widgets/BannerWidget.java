package com.cosmo.ui.widgets;

import com.cosmo.Workspace;
import com.cosmo.ui.templates.TemplateUnavailableException;

/**
 * Implementa un widget de banner.
 * 
 * @author Gerard Llort
 */
public class BannerWidget extends Widget
{
   /** Identificador �nico del tipo de widget */
   private static final String WIDGET_ID = "CosmoUiWdgtBannerArea";


   //==============================================
   // Constructor
   //==============================================

   /**
    * Constructor de la clase {@link BannerWidget}.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace usado por la aplicaci�n actual.
    */
   public BannerWidget(Workspace workspace)
   {
      super(workspace);
   }

   /**
    * Devuelve un identificador �nico del tipo de widget.
    */
   @Override
   public String getWidgetTypeId() 
   {
      return BannerWidget.WIDGET_ID;
   }

   /**
    * Renderiza el widget y genera el c�digo XHTML de representaci�n.
    *
    * @return Devuelve una cadena en formato XHTML que representa el widget.
    * 
    * @throws TemplateUnavailableException
    */
   @Override
   public String render() throws TemplateUnavailableException 
   {
      // NO IMPLEMENTADO

      throw new UnsupportedOperationException("Not supported yet.");
   }
   
}
