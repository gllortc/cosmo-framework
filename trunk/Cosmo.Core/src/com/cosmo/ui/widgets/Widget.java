package com.cosmo.ui.widgets;

import com.cosmo.Workspace;
import com.cosmo.ui.templates.TemplateUnavailableException;
import java.text.DecimalFormat;

/**
 * Clase abstracta que deben implementar los widgets de p�gina.
 * 
 * @author Gerard Llort
 */
public abstract class Widget 
{
   private String id;
   private Workspace workspace;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link Widget}.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    */
   public Widget(Workspace workspace)
   {
      this.workspace = workspace;
   }


   //==============================================
   // Properties
   //==============================================

   /**
    * Devuelve un identificador �nico del tipo de widget.
    */
   public abstract String getWidgetTypeId();

   /**
    * Devuelve un identificador �nico de widget.
    * Cada widget de una p�gina tendr� un ID �nico.
    */
   public String getWidgetId() 
   {
      return this.id;
   }

   /**
    * Devuelve el workspace actual.
    */
   public Workspace getWorkspace() 
   {
      return this.workspace;
   }

   /**
    * Establece el identificador �nico del widget.
    */
   public void setWidgetId(int id) 
   {
      DecimalFormat df = new DecimalFormat("0000000");
      this.id = "CtrlId" + df.format(id);
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Renderiza el widget y genera el c�digo XHTML de representaci�n.
    *
    * @return Devuelve una cadena en formato XHTML que representa el widget.
    * 
    * @throws TemplateUnavailableException
    */
   public abstract String render() throws TemplateUnavailableException;


   //==============================================
   // Static members
   //==============================================

   public static String getTag(String name)
   {
      return "[@" + name.trim().toUpperCase() + "]";
   }

   public static void replaceTag(StringBuilder sb, String tag, String text)
   {
      int index = sb.indexOf(tag);
      sb.replace(index, index + tag.length(), text);
   }
}
