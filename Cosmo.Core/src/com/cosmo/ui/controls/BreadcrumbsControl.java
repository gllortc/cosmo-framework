package com.cosmo.ui.controls;

import java.util.ArrayList;

import com.cosmo.Workspace;
import com.cosmo.ui.templates.TemplateControl;

/**
 * Implementa una barra de navegaci�n.
 * 
 * @author Gerard Llort
 */
public class BreadcrumbsControl extends Control
{
   /** Control Type Unique ID */
   private static final String CTUID = "CosmoUiCtrlBreadcrumbs";

   private static final String CPART_HEADER = "navbar-top-header";
   private static final String CPART_FOOTER = "navbar-top-footer";

   // Declaraci�n de variables internas
   private ArrayList<BreadcrumbsItem> items;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Contructor de la clase {@link BreadcrumbsControl}.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el espacio de aplicaci�n actual.
    */
   public BreadcrumbsControl(Workspace workspace)
   {
      super(workspace);
      initialize();
   }

   /**
    * Contructor de la clase {@link BreadcrumbsControl}.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el espacio de aplicaci�n actual.
    * @param id Identificador �nico del control en la p�gina.
    */
   public BreadcrumbsControl(Workspace workspace, String id)
   {
      super(workspace, id);
      initialize();
   }


   //==============================================
   // Properties
   //==============================================

   /**
    * Devuelve un identificador �nico del tipo de control.
    */
   @Override
   public String getControlTypeId() 
   {
      return BreadcrumbsControl.CTUID;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Elimina todos los elementos de la barra de bavegaci�n.
    */
   public void clear()
   {
      this.items.clear();
   }

   /**
    * Agrega un nuevo elemento en la barra de navegaci�n.
    * 
    * @param item Una instancia de {@link BreadcrumbsItem} que representa el elemento a agregar.
    */
   public void addItem(BreadcrumbsItem item)
   {
      this.items.add(item);
   }

   /**
    * Renderiza el control y genera el c�digo XHTML de representaci�n.
    *
    * @return Devuelve una cadena en formato XHTML que representa el control. 
    */
   @Override
   public String render() 
   {
      TemplateControl ctrl;
      StringBuilder str = new StringBuilder();

      // Si no tiene elementos, no representa el control
      if (items.isEmpty())
      {
         return "<-- BradcrumbsControl placeholder (void) -->\n";
      }

      // Obtiene la plantilla y la parte del control
      ctrl = getWorkspace().getTemplate().getControl(BreadcrumbsControl.CTUID);

      // Genera la cabecera de la barra de navegaci�n
      str.append(ctrl.getElement(CPART_HEADER));

      for (BreadcrumbsItem item : this.items)
      {
         str.append(item.render(ctrl));
      }

      // Genera el pie de la barra de navegaci�n
      str.append(ctrl.getElement(CPART_FOOTER));

      return str.toString();
   }


   //==============================================
   // Private members
   //==============================================

   /**
    * Inicializa la instancia.
    */
   private void initialize()
   {
      items = new ArrayList<BreadcrumbsItem>();
   }
}
