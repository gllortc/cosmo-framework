package com.cosmo.ui.controls;

import com.cosmo.Workspace;
import com.cosmo.security.NotAuthorizedException;

/**
 * Representa un men� de aplicaci�n Cosmo.
 * 
 * @author Gerard Llort
 */
public class ErrorMessageWidget extends Control
{
   private static final String WIDGET_ID = "CosmoUiWdgtErrMsg";
   
   private Exception exception;
   
   //==============================================
   // Constructor
   //==============================================
   
   /**
    * Constructor de la clase.
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el espacio de aplicaci�n actual.
    * @param exception Una instancia de {@link Exception} que contiene los detalles del error.
    */
   public ErrorMessageWidget(Workspace workspace, Exception exception)
   {
      super(workspace);
      
      this.exception = exception;
   }
   
   //==============================================
   // properties
   //==============================================

   @Override
   public String getControlTypeId() 
   {
      return ErrorMessageWidget.WIDGET_ID;
   }

   public Exception getException() 
   {
      return exception;
   }

   public void setException(Exception exception) 
   {
      this.exception = exception;
   }
   
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Renderiza el widget y genera el c�digo XHTML de representaci�n.
    *
    * @return Devuelve una cadena en formato XHTML que representa el widget. 
    */
   @Override
   public String render()
   {
      String xhtml;
      String icon = " icon-remove-circle";
      String title = "Error de servidor";
      
      if (this.getException() instanceof NotAuthorizedException)
      {
         title = "Av�s de seguretat (NotAuthorizedException)";
         icon = "icon-lock";
      }
      
      xhtml = "";
      xhtml += "<div class=\"col_12\">\n";
      xhtml += "  <ul class=\"tabs\">\n" +
               "    <li class=\"current first last\"><a href=\"#instructions\">ERROR</a></li>\n" +
               "  </ul>";
      xhtml += "  <div id=\"instructions\" class=\"tab-content clearfix\" style=\"\">\n" +
               "    <h4><i class=\"" + icon + "\"></i> " + title + "</h4>\n" +
               "    <p>El servidor ha detectat un problema i no ha generat la p�gina sol�licitada. Els detalls d'aquest problema es mostren a continuaci�.</p>\n" +
               "    <hr />\n" +
               "    <h6>" + this.getException().getMessage() + "</h6>\n";
      if (this.getException().getStackTrace().length <= 0)
      {
         xhtml += "    <p>La tra�a que ha produ�t l'error �s la que es mostra a continuaci�:</p>\n";
         xhtml += "    <pre class=\"prettyprint\">" + this.getException().getStackTrace().toString() + "</pre>\n";
      }
      xhtml += "  </div>\n";
      xhtml += "</div>\n";

      return xhtml;
   }
}
