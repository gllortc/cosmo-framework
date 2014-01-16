package com.cosmo.ui.controls;

import java.util.ArrayList;

import com.cosmo.Workspace;
import com.cosmo.util.StringUtils;

/**
 * Implementa un control que permite incorporar c�digo XHTML directament a una p�gina de Cosmo.
 * 
 * @author Gerard Llort
 */
public class XhtmlControl extends Control
{
   /** CTUID - Control Type Unique ID */
   private static final String CONTROL_ID = "CosmoUiCtrlXhtml";

   private final String WIKI_REGEX_BOLD = "([']{3})(.*?)([']{3})";
   private final String WIKI_REGEX_ITALIC = "([']{2})(.*?)([']{2})";
   private final String WIKI_REGEX_STRIKE = "([-]{2})(.*?)([-]{2})";
   private final String WIKI_REGEX_CODE = "([/]{2})(.*?)([/]{2})";

   // Declaraci�n de variables internas
   private StringBuilder xhtml;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Contructor de la clase {@link XhtmlControl}.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    */
   public XhtmlControl(Workspace workspace)
   {
      super(workspace);
      initialize(StringUtils.EMPTY);
   }

   /**
    * Contructor de la clase {@link XhtmlControl}.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * @param id Una cadena que contiene el identificador �nico del control.
    */
   public XhtmlControl(Workspace workspace, String id)
   {
      super(workspace, id);
      initialize(StringUtils.EMPTY);
   }

   /**
    * Contructor de la clase {@link XhtmlControl}.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * @param id Una cadena que contiene el identificador �nico del control.
    * @param xhtml C�digo XHTML a mostrar en el control.
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
    * Concatena una cadena de texto al contenido actual del control.<br />
    * El texto admite los formatos inline soportados por Cosmo:
    * <ul>
    * <li>'''bold'''</li>
    * <li>''emphatized''</li>
    * <li>--strike--</li>
    * <li>//code//</li>
    * </ul>
    * 
    * @param xhtml C�digo XHTML a concatenar.
    */
   public XhtmlControl append(String xhtml)
   {
      // Convierte los estilos inline mediante regular expressions
      xhtml = xhtml.replaceAll(WIKI_REGEX_BOLD, "<strong>$2</strong>");
      xhtml = xhtml.replaceAll(WIKI_REGEX_ITALIC, "<em>$2</em>");
      xhtml = xhtml.replaceAll(WIKI_REGEX_STRIKE, "<strike>$2</strike>");
      xhtml = xhtml.replaceAll(WIKI_REGEX_CODE, "<code>$2</code>");

      // A�ade el fragmento al c�digo principal
      this.xhtml.append(xhtml);

      return this;
   }

   /**
    * Concatena una cadena de texto al contenido actual del control.
    * 
    * @param obj C�digo XHTML a concatenar.
    */
   public XhtmlControl append(Object obj)
   {
      this.xhtml.append(obj.toString());

      return this;
   }

   /**
    * Agrega una l�nea separadora.
    */
   public XhtmlControl appendHorizontalLine()
   {
      this.xhtml.append("<hr />");

      return this;
   }

   /**
    * Agrega un p�rrafo al contenido.
    * 
    * @param text Texto que contiene el p�rrafo.
    */
   public XhtmlControl appendParagraph(String text)
   {
      return append("<p>" + text + "</p>\n");
   }

   /**
    * Agrega un t�tulo H1 al contenido.
    * 
    * @param title Texto que contiene el t�tulo.
    * @param headderLevel Nivel de importancia del t�tulo (1..5).
    */
   public XhtmlControl appendHeadder(String title, int headderLevel)
   {
      append("<h" + headderLevel + ">" + title + "</h" + headderLevel + ">\n");

      return this;
   }

   /**
    * Agrega una lista no ordenada (UL).
    * 
    * @param list Lista de objetos que forman la lista.
    */
   public XhtmlControl appendUnorderedList(ArrayList<?> list)
   {
      appendUnorderedList(list, null);

      return this;
   }

   /**
    * Agrega una lista no ordenada (UL).
    * 
    * @param list Lista de objetos que forman la lista.
    * @param cssClass Clase CSS a aplicar a la lista (se desaconseja su uso).
    */
   public XhtmlControl appendUnorderedList(ArrayList<?> list, String cssClass)
   {
      append("<ul" + (cssClass != null && !cssClass.isEmpty() ? " class=\"" + cssClass + "\"" : StringUtils.EMPTY) + ">\n");
      for (Object item : list)
      {
         append("  <li>" + item.toString() + "</li>\n");
      }
      append("</ul>\n");

      return this;
   }

   /**
    * Agrega un texto formateado en negrita.
    * 
    * @param text Una cadena que contiene el texto a representar en negrita.
    */
   public XhtmlControl appendBold(String text)
   {
      append(XhtmlControl.formatBold(text));

      return this;
   }

   /**
    * Agrega un texto formateado en it�lica.
    * 
    * @param text Una cadena que contiene el texto a representar en it�lica.
    */
   public XhtmlControl appendEmphatized(String text)
   {
      append(XhtmlControl.formatEmphatized(text));

      return this;
   }

   /**
    * Limpia el contenido del control.
    */
   public void clear()
   {
      this.xhtml = new StringBuilder();
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

   /**
    * Aplica negrita a una cadena de texto.
    * 
    * @param text Texto a formatear.
    * 
    * @return Un c�digo XHTML que representa el texto proporcionado con el formato solicitado.
    */
   public static String formatBold(String text)
   {
      return "<strong>" + text + "</strong>";
   }

   /**
    * Aplica it�lica a una cadena de texto.
    * 
    * @param text Texto a formatear.
    * 
    * @return Un c�digo XHTML que representa el texto proporcionado con el formato solicitado.
    */
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
