package com.cosmo.ui.controls;

import com.cosmo.Workspace;

/**
 * Implementa un control que permite incorporar c�digo XHTML directament a una p�gina de Cosmo.
 * 
 * @author Gerard Llort
 */
public class XhtmlControl extends Control
{
   private static final String CONTROL_ID = "CosmoUiCtrlXhtml";
   
   private StringBuilder xhtml;
   
   //==============================================
   // Contructors
   //==============================================
   
   /**
    * Contructor de la clase.
    */
   public XhtmlControl(Workspace workspace)
   {
      super(workspace);
      initialize("");
   }
   
   /**
    * Contructor de la clase.
    */
   public XhtmlControl(Workspace workspace, String id)
   {
      super(workspace, id);
      initialize("");
   }
   
   /**
    * Contructor de la clase.
    * 
    * @param xhtml Una cadena que contiene el c�digo XHTML con el que se rellenar� el control.
    */
   public XhtmlControl(Workspace workspace, String id, String xhtml)
   {
      super(workspace, id);
      initialize(xhtml);
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
      return XhtmlControl.CONTROL_ID;
   }

   /**
    * Devuelve la longitud del contenido del control.
    */
   public int length() 
   {
      return this.xhtml.length();
   }
   
   //==============================================
   // Methods
   //==============================================

   /**
    * Concatena una cadena de texto al contenido actual del control.
    * 
    * @param xhtml C�digo XHTML a concatenar.
    */
   public void append(String xhtml)
   {
      this.xhtml.append(xhtml);
   }
   
   /**
    * Concatena una cadena de texto al contenido actual del control.
    * 
    * @param obj C�digo XHTML a concatenar.
    */
   public void append(Object obj)
   {
      this.xhtml.append(obj.toString());
   }
   
   /**
    * Agrega un p�rrafo al contenido.
    * 
    * @param text Texto que contiene el p�rrafo.
    */
   public void appendParagraph(String text)
   {
      append("<p>" + text + "</p>\n");
   }
   
   /**
    * Agrega un t�tulo H1 al contenido.
    * 
    * @param title Texto que contiene el t�tulo.
    * @param headderLevel Nivel de importancia del t�tulo (1..5).
    */
   public void appendHeadder(String title, int headderLevel)
   {
      append("<h" + headderLevel + ">" + title + "</h" + headderLevel + ">\n");
   }
   
   /**
    * Renderiza el control y genera el c�digo XHTML de representaci�n.
    *
    * @return Devuelve una cadena en formato XHTML que representa el control. 
    */
   @Override
   public String render() 
   {
      return xhtml.toString();
   }
   
   
   //==============================================
   // Static members
   //==============================================
   
   public static String formatBold(String text)
   {
      return "<strong>" + text + "</strong>";
   }
   
   public static String formatEmphatized(String text)
   {
      return "<em>" + text + "</em>";
   }
   
   //==============================================
   // Private members
   //==============================================
   
   /**
    * Inicializa la instancia.
    */
   private void initialize(String text)
   {
      this.xhtml = new StringBuilder(text);
   }   
}
