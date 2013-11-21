package com.cosmo.ui.controls;

import com.cosmo.Workspace;

/**
 * Implementa un cuadro de texto representable dentro de un formulario Cosmo.
 * 
 * @author Gerard Llort
 */
public class FormFieldTextArea extends FormField
{
   private String name;
   private String value;
   private String label;
   private String description;
   private int maxLength;
   private boolean password;


   //==============================================
   // Contructors
   //==============================================

   /**
    * Contructor de la clase.
    * 
    * @param name Nombre identificativo del elemento dentro de la p�gina.
    * @param label Etiqueta que se mostrar� junto el control.
    */
   public FormFieldTextArea(String name, String label) 
   {
      this.name = name;
      this.label = label;
      this.description = "";
      this.value = "";
      this.maxLength = -1;
      this.password = false;
   }

   /**
    * Contructor de la clase.
    * 
    * @param name Nombre identificativo del elemento dentro de la p�gina.
    * @param label Etiqueta que se mostrar� junto el control.
    */
   public FormFieldTextArea(String name, String label, boolean isPassword) 
   {
      this.name = name;
      this.label = label;
      this.description = "";
      this.value = "";
      this.maxLength = -1;
      this.password = isPassword; 
   }

   /**
    * Contructor de la clase.
    * 
    * @param name Nombre identificativo del elemento dentro de la p�gina.
    * @param label Etiqueta que se mostrar� junto el control.
    * @param maxLength N�mero m�ximo de car�cteres que admite el control.
    */
   public FormFieldTextArea(String name, String label, int maxLength) 
   {
      this.name = name;
      this.label = label;
      this.description = "";
      this.value = "";
      this.maxLength = maxLength;
      this.password = false;
   }

   /**
    * Contructor de la clase.
    * 
    * @param name Nombre identificativo del elemento dentro de la p�gina.
    * @param label Etiqueta que se mostrar� junto el control.
    * @param maxLength N�mero m�ximo de car�cteres que admite el control.
    * @param isPassword Indica si el campo ser� de texto oculto.
    */
   public FormFieldTextArea(String name, String label, int maxLength, boolean isPassword) 
   {
      this.name = name;
      this.label = label;
      this.description = "";
      this.value = "";
      this.maxLength = maxLength;
      this.password = isPassword;
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

   public String getValue() 
   {
      return value;
   }

   @Override
   public void setValue(Object value) 
   {
      this.value = (String) value;
   }

   public String getLabel() 
   {
      return label;
   }

   public void setLabel(String label) 
   {
      this.label = label;
   }

   public String getDescription() 
   {
      return description;
   }

   public void setDescription(String description) 
   {
      this.description = description;
   }
   
   public int getMaxLength() 
   {
      return maxLength;
   }

   public void setMaxLength(int maxLength) 
   {
      this.maxLength = maxLength;
   }

   public boolean isPassword() 
   {
      return password;
   }

   public void setPassword(boolean password) 
   {
      this.password = password;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Convierte el campo en un TAG XHTML.
    */
   @Override
   public String render(Workspace workspace)
   {
      StringBuilder sb = new StringBuilder();

      sb.append("<textarea ").
         append("id=\"").append(this.name).append("\" ").
         append("name=\"").append(this.name).append("\" ").
         append("style=\"width:100%;height:200px;\" ").
         append("class=\"textarea\">").append(this.value).append("</textarea>");

      return sb.toString();
   }

   /**
    * Convierte la instancia en una cadena de texto.
    */
   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder();

      sb.append("    <div>").append("\n");
      sb.append("      <label for=\"").append(this.name).append("\">"); 
      sb.append(this.label).append(" ");
      sb.append("<input type=\"").append(password ? "password" : "text").append("\" id=\"").append(this.name).append("\" name=\"").append(this.name).append("\" value=\"").append(this.value).append("\" />");
      sb.append("</label>").append("\n");
      sb.append("    </div>");

      return sb.toString();
   }
}
