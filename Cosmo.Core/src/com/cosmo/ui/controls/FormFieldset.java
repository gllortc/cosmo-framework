package com.cosmo.ui.controls;

import java.util.ArrayList;
import java.util.Iterator;

import com.cosmo.Workspace;
import com.cosmo.util.StringUtils;

/**
 * Implementa un grupo de controles de un formulario web.
 */
public class FormFieldset extends FormField
{
   // Declaraci�n de variables internas
   private String name;
   private String title;
   private String description;
   private ArrayList<FormField> fields;
   // private FormControl control;
   private boolean haveCaptcha;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link FormFieldset}.
    * 
    * @param title T�tulo visible del grupo de controles.
    */
   public FormFieldset(String title)
   {
      this.title = title;
      this.description = StringUtils.EMPTY;
      this.fields = new ArrayList<FormField>();
      // this.control = control;
      this.haveCaptcha = false;
   }

   /**
    * Constructor de la clase {@link FormFieldset}.
    * 
    * @param title T�tulo visible del grupo de controles.
    * @param description Descripci�n del contenido del grupo.
    * @param control La instancia de {@link FormControl} al que pertenece.
    */
   public FormFieldset(String title, String description, FormControl control)
   {
      this.title = title;
      this.description = description;
      this.fields = new ArrayList<FormField>();
      // this.control = control;
      this.haveCaptcha = false;
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
   
   public String getTitle() 
   {
      return title;
   }

   public void setTitle(String title) 
   {
      this.title = title;
   }

   public String getDescription() 
   {
      return description;
   }

   public void setDescription(String description) 
   {
      this.description = description;
   }
   
   public boolean haveCaptcha()
   {
      return this.haveCaptcha;
   }

   /**
    * Devuelve un iterador que permite recorrer los campos de un grupo.
    */
   public Iterator<FormField> getFields()
   {
      return this.fields.iterator();
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Establece el valor del campo.
    */
   @Override
   public void setValue(Object value)
   {
      // No aplica en este tipo de control
      // Se omite la llamada a este m�todo
   }

   /**
    * Agrega un nuevo campo al formulario.<br />
    * NOTA: Un grupo (y formulario) s�lo puede contener un control {@link FormFieldCaptcha}.
    * 
    * @param field Una instancia de un objeto que extienda {@link FormField}.
    */
   public void addField(FormField field) // throws MalformedFormException
   {
      /*
      // Evita incorporar m�s de un campo captcha en un grupo
      if (field instanceof FormFieldCaptcha)
      {
         if (this.haveCaptcha)
         {
            throw new MalformedFormException("Only ONE captcha field is allowen in a form.");
         }
         else
         {
            this.haveCaptcha = true;
         }
      }*/

      fields.add(field);
   }

   /**
    * Convierte la instancia en una cadena XHTML que representa el elemento en una p�gina web.
    */
   @Override
   public String render(Workspace workspace) 
   {
      return toString();
   }

   @Override
   public String toString()
   {
      StringBuilder str = new StringBuilder();

      str.append("    <fieldset name=\"").append(this.name).append("\">").append("\n");
      str.append("    <legend>").append(this.title).append("</legend>").append("\n");
      for (FormField field : this.fields)
      {
         if (field instanceof FormFieldHidden)
         {
            str.append(field.render(null)).append("\n");
         }
      }
      for (FormField field : this.fields)
      {
         if (!(field instanceof FormFieldHidden))
         {
            str.append(field.render(null)).append("\n");
         }
      }
      str.append("    </fieldset>");

      return str.toString();
   }
}
