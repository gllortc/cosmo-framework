package com.cosmo.structures;

import java.util.HashMap;

import com.cosmo.Cosmo;

/**
 * Implementa un contenedor para almacenar los datos de un formulario para una determinada sesi�n.
 * 
 * @author Gerard Llort
 */
public class FormData 
{
   private String formId;
   private HashMap<String, String> map;
   
   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.
    * 
    * @param formId Identificador �nico del formulario.
    */
   public FormData(String formId)
   {
      this.formId = formId;
      this.map = null;
   }

   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve el identificador �nico del formulario.
    */
   public String getFormId() 
   {
      return formId;
   }

   //==============================================
   // Methods
   //==============================================
   
   /**
    * Almacena el valor de un campo del formulario.
    * 
    * @param name Nombre del campo.
    * @param value Valor del campo.
    */
   public void addParameterValue(String name, String value)
   {
      // Instancia el mapa la primera vez que se usa
      if (this.map == null)
      {
         this.map = new HashMap<String, String>();
      }
      
      // Almacena el valor
      this.map.put(name, value);
   }
   
   /**
    * Obtiene el valor de un campo del formulario.
    * 
    * @param name Nombre del campo.
    * @return Devuelve una cadena que contiene el valor asociado al campo o {@code null} si no se encuentra el campo en el contenedor.
    */
   public String getParameterValue(String name)
   {
      // Instancia el mapa la primera vez que se usa
      if (this.map == null)
      {
         return "";
      }
      
      // Devuelve el valor
      return this.map.get(name);
   }
   
   /**
    * Genera una clave para almacenar/recuperar los datos de un formulario en cach�.
    * 
    * @return Una cadena de texto que corresponde a la clave de acceso a los datos del formulario en cach�.
    */
   public String getFormDataCacheToken()
   {
      return FormData.getFormDataCacheToken(this.formId);
   }

   //==============================================
   // Static members
   //==============================================
   
   /**
    * Genera una clave para almacenar/recuperar los datos de un formulario en cach�.
    * 
    * @param formId Identificador �nico del formulario.
    * @return Una cadena de texto que corresponde a la clave de acceso a los datos del formulario en cach�.
    */
   public static String getFormDataCacheToken(String formId)
   {
      return Cosmo.KEY_CACHE_SESSION_FORMDATA + formId;
   }
}
