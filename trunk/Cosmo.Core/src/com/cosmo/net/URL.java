package com.cosmo.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.cosmo.Cosmo;

/**
 * <p>Representa una URL con par�metros.</p>
 * 
 * <p>Esta clase permite construir URLs con par�metros estrayendo la problem�tica de la codificaci�n de los par�metros.</p>
 * 
 * <p>{@code http://www.mycompany.com/?para1=aaa&param2=bbb#myanchor}</p>
 * 
 * @author Gerard Llort
 */
public class URL 
{
   private static final String TOKEN_PARAM_INIT = "?";
   private static final String TOKEN_PARAM_SEPARATOR = "&";
   private static final String TOKEN_ANCHOR = "#";

   // Declaraci�n de variables locales
   private String url;
   private String anchor;
   private ArrayList<UrlParameter> params;


   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase {@link URL}.
    * 
    * @param url Una cadena que representa la URL base (p. ej. www.ibm.es o http://www.microsoft.com).
    */
   public URL(String url)
   {
      this.url = url;
      this.anchor = "";
      this.params = new ArrayList<URL.UrlParameter>();
   }


   //==============================================
   // Properties
   //==============================================

   /**
    * Devuelve la URL base.
    */
   public String getUrl() 
   {
      return url;
   }

   /**
    * Establece la URL base.
    */
   public void setUrl(String url)
   {
      this.url = url;
   }

   /**
    * Devuelve el nombre del enlace interno (<em>anchor</em>).
    */
   public String getAnchor() 
   {
      return anchor;
   }

   /**
    * Establece el nombre del enlace interno (<em>anchor</em>).
    * 
    * @param anchor Una cadena que representa el nombre del enlace (<em>anchor</em>).
    */
   public void setAnchor(String anchor)
   {
      this.anchor = anchor;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Agrega una nueva carpeta a la URL.
    * 
    * @param name Una cadena que contiene el nombre de la carpeta a agregar a la URL.
    */
   public void addFolder(String name)
   {
      this.url += (this.url.endsWith("/") ? "" : "/") + name;
   }

   /**
    * Agrega un nuevo par�metro a la URL.
    * 
    * @param name Nombre del par�metro.
    * @param value Valor del par�metro.
    */
   public void addParameter(String name, String value)
   {
      this.params.add(new UrlParameter(name, value));
   }

   /**
    * Agrega un nuevo par�metro a la URL.
    * 
    * @param name Nombre del par�metro.
    * @param value Valor del par�metro.
    */
   public void addParameter(String name, Integer value)
   {
      this.params.add(new UrlParameter(name, value.toString()));
   }

   /**
    * Obtiene el valor de un par�metro de tipo {@link String}.
    * 
    * @param name Nombre del par�metro.
    * 
    * @return Una cadena que corresponde al valor asociado al par�metro especificado.
    */
   public String getParameterString(String name)
   {
      return getParameterString(name, "");
   }

   /**
    * Obtiene el valor de un par�metro de tipo {@link String}.
    * 
    * @param name Nombre del par�metro.
    * @param defaultValue En caso de no encontrar el par�metro, valor por defecto que se devolver�.
    * 
    * @return Una cadena que corresponde al valor asociado al par�metro especificado.
    */
   public String getParameterString(String name, String defaultValue)
   {
      name = name.trim().toLowerCase();

      for (UrlParameter param : this.params)
      {
         if (name.equals(param.name))
         {
            return param.value;
         }
      }

      return defaultValue;
   }

   /**
    * Obtiene el valor de un par�metro de tipo {@link Integer}.
    * 
    * @param name Nombre del par�metro.
    * 
    * @return Una valor entero que corresponde al valor asociado al par�metro especificado.
    */
   public Integer getParameterInt(String name)
   {
      return getParameterInt(name, 0);
   }

   /**
    * Obtiene el valor de un par�metro de tipo {@link Integer}.
    * 
    * @param name Nombre del par�metro.
    * @param defaultValue En caso de no encontrar el par�metro, valor por defecto que se devolver�.
    * 
    * @return Una valor entero que corresponde al valor asociado al par�metro especificado.
    */
   public Integer getParameterInt(String name, Integer defaultValue)
   {
      name = name.trim().toLowerCase();

      for (UrlParameter param : this.params)
      {
         if (name.equals(param.name))
         {
            try
            {
               return Integer.valueOf(param.value);
            }
            catch (NumberFormatException ex)
            {
               return defaultValue;
            }
         }
      }

      return defaultValue;
   }

   /**
    * Convierte la URL en una cadena de texto para usar.
    * 
    * @param enc Tipo de codificaci�n a usar para la codificaci�n. Puede usar una de las constantes declaradas en {@link Cosmo} {@code CHARSET_XXXX}.
    * 
    * @return Devuelve la direcci�n URL v�lida.
    */
   public String build(String enc) 
   {
      boolean first = true;
      String url = "";
      String encodedValue;

      url = this.url;

      if (!this.params.isEmpty())
      {
         url += URL.TOKEN_PARAM_INIT;
         for (UrlParameter param : this.params)
         {
            try 
            {
               encodedValue =  (param.value == null ? "" : URLEncoder.encode(param.value, enc));
            } 
            catch (Exception e) 
            {
               encodedValue =  param.value;
            }

            url += (first ? "" : URL.TOKEN_PARAM_SEPARATOR) + param.name + "=" + encodedValue;
            first = false;
         }
      }

      if (!this.anchor.isEmpty())
      {
         try 
         {
            encodedValue = URL.TOKEN_ANCHOR + URLEncoder.encode(anchor, enc);
         } 
         catch (UnsupportedEncodingException e) 
         {
            encodedValue =  anchor;
         }

         url += URL.TOKEN_ANCHOR + encodedValue;
      }

      return url;
   }

   /**
    * Convierte la URL en una cadena de texto para usar.
    * 
    * @return Devuelve la URL v�lida y lista para usar en WWW.
    */
   public String build()
   {
      return build(Cosmo.CHARSET_UTF_8);
   }

   /**
    * Convierte la URL en una cadena de texto para usar.
    * 
    * @return Devuelve la URL v�lida y lista para usar en WWW.
    */
   @Override
   public String toString()
   {
      return build(Cosmo.CHARSET_UTF_8);
   }


   //==============================================
   // Subclasses
   //==============================================

   /**
    * Implementa un par�metro de URL.
    */
   class UrlParameter
   {
      private String name;
      private String value;

      // Constructors
      
      public UrlParameter(String name, String value)
      {
         this.name = name;
         this.value = value;
      }

      // Properties
      
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

      public void setValue(String value) 
      {
         this.value = value;
      }
   }
}
