package com.cosmo.ui.controls;

import java.util.UUID;

import com.cosmo.Cosmo;
import com.cosmo.Workspace;
import com.cosmo.util.StringUtils;

/**
 * Interface que debe implementar cualquier control de contenido.<br />
 * Los controles de contenido son componentes que combinados entre s�, rellenan la zona de contenidos.
 * 
 * @author Gerard Llort
 */
public abstract class Control
{
   private String id;
   private String uuid;
   private Workspace workspace;

   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.<br />
    * Usar este constructor si el control es est�tico y no modifica sus valores nunca.
    */
   public Control(Workspace workspace)
   {
      this.id = this.uuid = UUID.randomUUID().toString();
      this.workspace = workspace;
   }
   
   /**
    * Constructor de la clase.<br />
    * Usar este constructor si el control es din�mico y puede alterar sus valores (por ejemplo, los valores de un GRID).
    * 
    * @param workspace Una instancia de {@link Workspace} que representa el workspace actual.
    * @param id Identificador �nico del control.
    */
   public Control(Workspace workspace, String id)
   {
      this.id = id;
      this.uuid = UUID.randomUUID().toString();
      this.workspace = workspace;
   }
   
   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve un identificador �nico del tipo de control.
    */
   public abstract String getControlTypeId();
   
   /**
    * Devuelve un identificador �nico de control.
    * Cada control de una p�gina tendr� un ID �nico.
    */
   public String getId() 
   {
      return id;
   }

   /**
    * Establece el identificador �nico del control.
    */
   public void setId(String id) 
   {
      this.id = id;
   }
   
   /**
    * Devuelve un UUID �nico para el control.
    */
   public String getUuid()
   {
      return this.uuid;
   }
   
   /**
    * Devuelve la instancia de {@link Workspace} usada para generar el control.
    */
   public Workspace getWorkspace()
   {
      return this.workspace;
   }
   
   //==============================================
   // Methods
   //==============================================

   /**
    * Renderiza el control y genera el c�digo XHTML de representaci�n.
    *
    * @return Devuelve una cadena en formato XHTML que representa el control. 
    */
   public abstract String render();
   
   /**
    * Genera una clave para almacenar/recuperar los datos de un control en cach� de sessi�n de usuario.
    * 
    * @param controlId Identificador �nico del control.
    * @return Una cadena de texto que corresponde a la clave de acceso a los datos del control en cach�.
    */
   public String getSessionControlData()
   {
      return Cosmo.KEY_CACHE_SESSION_CTRLDATA + this.uuid;
   }
   
   //==============================================
   // Static members
   //==============================================
   
   /**
    * Reemplaza un TAG de contenido.
    * Soporta TAGs condicionales.
    * 
    * @param sb Una instancia de {@link StringBuilder} que contiene el texto a evaluar.
    * @param tag Una cadena que contiene el TAG a reemplazar. El TAG se especifica s�lo con car�cteres alfanum�ricos, sin formato final.
    * @param text Texto que debe reemplazar el TAG.
    */
   public static void replaceTag(StringBuilder sb, String tag, String text)
   {
      int idx;
      String cTag = getTag(tag);
      String cTagBegin = getConditionalTagBegin(tag);
      String cTagEnd = getConditionalTagEnd(tag);
      
      idx = sb.indexOf(cTagBegin);
      
      // Evita contenido nulo
      if (text == null)
      {
         text = "";
      }
      
      // Existe condicional y hay contenido
      if (idx >= 0 && !StringUtils.isNullOrEmpty(text))
      {
         sb.delete(idx, idx + cTagBegin.length());
         
         idx = sb.indexOf(cTagEnd);
         if (idx >= 0)
         {
            sb.delete(idx, idx + cTagEnd.length());
         }
         
         idx = sb.indexOf(cTag);
         {
            sb.replace(idx, idx + cTag.length(), text);
         }
         
         return;
      }
      
      // Existe condicional y NO hay contenido
      if (idx >= 0 && StringUtils.isNullOrEmpty(text))
      {
         sb.delete(idx, idx + cTagBegin.length());
         
         idx = sb.indexOf(cTagEnd);
         if (idx >= 0)
         {
            sb.delete(idx, idx + cTagEnd.length());
         }
         
         idx = sb.indexOf(cTagEnd);
         if (idx >= 0)
         {
            sb.delete(idx, idx + cTag.length());
         }
         
         return;
      }
      
      // No existe condicional (el contenido se reemplaza tanto si lo hay como si no)
      idx = sb.indexOf(cTag);
      if (idx < 0)
      {
         sb.replace(idx, idx + cTag.length(), text);
      }
   }
   
   /**
    * Reemplaza un TAG de contenido.
    * Soporta TAGs condicionales.
    * 
    * @param xhtml Una instancia de {@link StringBuilder} que contiene el texto a evaluar.
    * @param tag Una cadena que contiene el TAG a reemplazar. El TAG se especifica s�lo con car�cteres alfanum�ricos, sin formato final.
    * @param text Texto que debe reemplazar el TAG.
    */
   public static String replaceTag(String xhtml, String tag, String text)
   {
      int idx;
      String cTag = Control.getTag(tag);
      String cTagBegin = Control.getConditionalTagBegin(tag);
      String cTagEnd = Control.getConditionalTagEnd(tag);

      StringBuilder sb = new StringBuilder(xhtml);
      
      // Existe condicional y hay contenido
      if (xhtml.contains(cTagBegin) && !StringUtils.isNullOrEmpty(text))
      {
         idx = sb.indexOf(cTagBegin);
         if (idx >= 0)
         {
            sb.delete(idx, idx + cTagBegin.length());
         }
         
         idx = sb.indexOf(cTagEnd);
         if (idx >= 0)
         {
            sb.delete(idx, idx + cTagEnd.length());
         }
         
         idx = sb.indexOf(cTag);
         if (idx >= 0)
         {
            sb.replace(idx, idx + cTag.length(), text);
         }

         return sb.toString();
      }
      
      // Existe condicional y NO hay contenido
      if (xhtml.contains(cTagBegin) && StringUtils.isNullOrEmpty(text))
      {
         sb.delete(sb.indexOf(cTagBegin), sb.indexOf(cTagEnd) + cTagEnd.length());
         return sb.toString();
      }
      
      // No existe condicional (el contenido se reemplaza tanto si lo hay como si no)
      if (xhtml.contains(cTag))
      {
         xhtml = xhtml.replace(cTag, text);
         
         return xhtml;
      }
      
      return xhtml;
   }
   
   //==============================================
   // Private members
   //==============================================

   private static String getTag(String name)
   {
      return "[@" + name.trim().toUpperCase() + "]";
   }
   
   private static String getConditionalTagBegin(String name)
   {
      return "[#IF " + name.trim().toUpperCase() + "]";
   }
   
   private static String getConditionalTagEnd(String name)
   {
      return "[#ENDIF " + name.trim().toUpperCase() + "]";
   }
}
