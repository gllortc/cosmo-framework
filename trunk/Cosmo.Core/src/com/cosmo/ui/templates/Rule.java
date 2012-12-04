package com.cosmo.ui.templates;

/**
 * Representa una regla de aplicación de plantillas.
 * @author Gerard
 */
public class Rule 
{
   private RuleType type;
   private String pattern;
   private int templateId;
   
   /**
    * Describe los distintos tipos de regla soportados.
    */
   public enum RuleType
   {
      BrowserAgent
   }

   //==============================================
   // Contructors
   //==============================================
   
   /**
    * Contructor de la clase.
    */
   public Rule()
   {
      initialize();
   }
   
   /**
    * Contructor de la clase.
    * 
    * @param type Tipo de regla.
    * @param pattern patrón de búsqueda.
    * @param templateId Identificador de la plantilla a aplicar en caso que se cumpla la regla.
    */
   public Rule(RuleType type, String pattern, int templateId)
   {
      this.type = type;
      this.pattern = pattern;
      this.templateId = templateId;
   }
   
   //==============================================
   // Properties
   //==============================================
   
   public RuleType getType() 
   {
      return type;
   }

   public void setType(RuleType type) 
   {
      this.type = type;
   }

   public String getPattern() 
   {
      return pattern;
   }

   public void setPattern(String pattern) 
   {
      this.pattern = pattern.toLowerCase();
   }

   public int getTemplateId() 
   {
      return templateId;
   }

   public void setTemplateId(int templateId) 
   {
      this.templateId = templateId;
   }
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Evalua la regla para un determinado BrowserAgent.
    * 
    * @param browserAgent La cadena de texto que proporciona el navegador (BrowserAgent).
    * @return {@code true} si la regla se cumple o {@code false} en cualquier otro caso.
    */
   public boolean matchRule(String browserAgent)
   {
      return browserAgent.toLowerCase().contains(pattern);
   }

   //==============================================
   // Private members
   //==============================================
   
   /**
    * Inicializa la instancia.
    */
   private void initialize()
   {
      type = RuleType.BrowserAgent;
      pattern = "";
      templateId = 0;
   }
}