package com.cosmo.ui.controls;

import com.cosmo.Workspace;
import com.cosmo.util.StringUtils;

/**
 * Implementa un campo de formulario que est� oculto al usuario pero que contiene un valor.
 * 
 * @author Gerard Llort
 */
public class FormFieldHidden extends FormField
{
   // Declaraci�n de variables internas
   private String name;
   private String value;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link FormFieldHidden}.
    * 
    * @param name Nombre identificativo del control.
    */
   public FormFieldHidden(String name) 
   {
      this.name = name;
      this.value = StringUtils.EMPTY;
   }

   /**
    * Constructor de la clase {@link FormFieldHidden}.
    * 
    * @param name Nombre identificativo del control.
    * @param value Valor asociado al campo.
    */
   public FormFieldHidden(String name, String value) 
   {
      this.name = name;
      this.value = value;
   }


   //==============================================
   // Properties
   //==============================================

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

   public void setValue(Object value) 
   {
      this.value = (String) value;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Convierte la instancia en una cadena XHTML que representa el elemento en una página web.
    */
   @Override
   public String render(Workspace workspace)
   {
      return toString();
   }

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      sb.append("    <input type=\"hidden\" id=\"").append(this.name).append("\" name=\"").append(this.name).append("\" value=\"").append(this.value).append("\" />");

      return sb.toString();
   }
}
