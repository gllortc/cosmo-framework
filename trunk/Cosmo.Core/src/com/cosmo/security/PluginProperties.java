package com.cosmo.security;

import java.util.HashMap;

import com.cosmo.util.StringUtils;

/**
 * Implementa un agente de configuraci�n de seguridad.
 * @author Gerard Llort
  */
public class PluginProperties 
{
   private String id;
   private String moduleClass;
   private HashMap<String, String> params;
   
   /**
    * Constructor de la clase.
    */
   public PluginProperties()
   {
      this.id = "";
      this.moduleClass = "";
      this.params = new HashMap<String, String>();
   }
   
   /**
    * Constructor de la clase.
    * 
    * @param id Identificador del agente.
    * @param moduleClass Nombre completo de la clase (incluyendo el package). 
    */
   public PluginProperties(String id, String moduleClass)
   {
      this.id = id;
      this.moduleClass = moduleClass;
      this.params = new HashMap<String, String>();
   }
   
   /**
    * Devuelve el identificador del agente.
    */
   public String getId() 
   {
      return id;
   }
   
   /**
    * Establece el identificador del agente.
    */
   public void setId(String id) 
   {
      this.id = id;
   }
   
   /**
    * Devuelve el nombre completo de la clase (incluyendo el package) del agente. 
    */
   public String getModuleClass() 
   {
      return moduleClass;
   }

   /**
    * Establece el nombre completo de la clase (incluyendo el package) del agente.
    */
   public void setModuleClass(String moduleClass) 
   {
      this.moduleClass = moduleClass;
   }

   /**
    * Devuelve un {@link HashMap} con todos los par�metros definidos para el agente.
    */
   public HashMap<String, String> getParams() 
   {
      return params;
   }
   
   /**
    * Establece un valor de confioguraci�n del agente.
    * 
    * @param key Clave de configuraci�n.
    * @param value Valor asociado a la clave.
    */
   public void setParam(String key, String value)
   {
      params.put(key, value);
   }
   
   /**
    * Permite obtener el valor asociado a una clave de configuraci�n.
    * 
    * @param key Clave de configuraci�n.
    * 
    * @return Una cadena que representa el valor asociado a la clave definida por {@value key}.
    */
   public String getParamString(String key)
   {
      return params.get(key);
   }
   
   /**
    * Permite obtener el valor asociado a una clave de configuraci�n de tipo num�rico (entero).
    * 
    * @param key Clave de configuraci�n.
    * @param defaultValue Valor que se devolver� en caso de no encontrar el valor en los par�metros.
    * 
    * @return Una cadena que representa el valor asociado a la clave definida por {@value key}.
    */
   public int getParamInteger(String key, int defaultValue)
   {
      String sval;
      
      sval = params.get(key);
      if (sval != null && StringUtils.isNumeric(sval))
      {
         return Integer.parseInt(params.get(key));
      }
      else
      {
         return defaultValue;
      }
   }
   
   /**
    * Permite obtener el valor asociado a una clave de configuraci�n de tipo booleano.
    * 
    * @param key Clave de configuraci�n.
    * @param defaultValue Valor que se devolver� en caso de no encontrar el valor en los par�metros.
    * 
    * @return Un valor booleano que representa el valor asociado a la clave definida por {@value key} o el valor de {@value defaultValue} en caso de no encontrarse.
    */
   public boolean getParamBoolean(String key, boolean defaultValue)
   {
      String sval;
      
      sval = params.get(key);
      if (sval == null)
      {
         return defaultValue;
      }
      
      if (StringUtils.isNumeric(sval))
      {
         return (Integer.parseInt(params.get(key)) > 0);
      }
      else
      {
         sval = sval.trim().toLowerCase();
         
         if (sval.trim().toLowerCase().equals("true"))
         {
            return true;
         }
         else if (sval.trim().toLowerCase().equals("false"))
         {
            return false;
         }
         else
         {
            return defaultValue;
         }
      }
   }
   
   /**
    * Permite obtener el valor asociado a una clave de configuraci�n.<br />
    * Si la clave no tiene un valor asociado, devuelve 0.
    * 
    * @param key Clave de configuraci�n.
    * 
    * @return Una cadena que representa el valor asociado a la clave definida por {@value key}.
    */
   public int getParamInteger(String key)
   {
      return getParamInteger(key, 0);
   }
   
   /**
    * Transforma la informaci�n de la instancia en una cadena con informaci�n comprensible. 
    */
   @Override
   public String toString()
   {
      return "[Cosmo Security Agent Info]\n" +
             " ID     = " + this.getId() + "\n" +
             " DRIVER = " + this.getModuleClass() + "\n" +
             " PARAMS = " + this.params.toString() + "\n";
   }
}
