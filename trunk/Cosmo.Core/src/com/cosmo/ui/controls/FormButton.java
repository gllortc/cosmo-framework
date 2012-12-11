package com.cosmo.ui.controls;

import javax.servlet.http.HttpSession;

/**
 * Implementa un bot�n de formulario.
 * 
 * @author Gerard Llort
 */
public class FormButton  extends FormField
{
   private String name;
   private String label;
   private ButtonType type;
   
   /**
    * Enumera los distintos tipos de bot�n de formulario soportados.
    */
   public enum ButtonType
   {
      /** Envia la informaci�n */ 
      Submit,
      /** Borra todos los datos del formulario en el cliente */ 
      Reset,
      /** NO SOPORTADO: Ejecuta una acci�n JavaScript */ 
      JsAction
   }
   
   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.
    * 
    * @param label Cadena de texto que ser� visible en el bot�n.
    * @param type Tipo de bot�n.
    */
   public FormButton(String name, String label, ButtonType type)
   {
      this.name = name;
      this.label = label;
      this.type = type;
   }
   
   //==============================================
   // Properties
   //==============================================

   @Override
   public String getName() 
   {
      return name;
   }

   public void setName(String name) 
   {
      this.name = name;
   }
   
   public String getLabel() 
   {
      return label;
   }

   public void setLabel(String label) 
   {
      this.label = label;
   }

   public ButtonType getType() 
   {
      return type;
   }

   public void setType(ButtonType type) 
   {
      this.type = type;
   }
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Establece el valor del campo.
    */
   @Override
   public void setValue(String value)
   {
      // No aplica en este tipo de control
      // Se omite la llamada a este m�todo
   }
   
   /**
    * Convierte la instancia en una cadena XHTML que representa el elemento en una p�gina web.
    */
   @Override
   public String render(HttpSession session) 
   {
      return toString();
   }
   
   @Override
   public String toString()
   {
      String btnType = "";
      
      switch (this.type)
      {
         case Submit:   btnType = "submit";  break;
         case Reset:    btnType = "reset";   break;
         case JsAction: btnType = "button";  break;
      }
      
      StringBuilder sb = new StringBuilder();
      sb.append("<input type=\"").append(btnType).append("\" id=\"").append(this.name).append("\" name=\"").append(this.name).append("\" value=\"").append(this.label).append("\" />");
      
      return sb.toString();
   }
}
