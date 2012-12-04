package com.cosmo.ui.controls;

import com.cosmo.ui.templates.Template;

/**
 * Implementa una barra de botones.
 * Estos botones son enlaces, no son usables en formularios.
 * 
 * @author Gerard Llort
 */
public class ButtonBarControl extends Control
{
   private static final String CONTROL_ID = "CosmoUiCtrlButtonBar";

   //==============================================
   // Contructors
   //==============================================
   
   /**
    * Contructor de la clase.
    */
   public ButtonBarControl()
   {
      initialize();
   }
   
   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve un identificador único del tipo de control.
    */
   @Override
   public String getControlTypeId() 
   {
      return ButtonBarControl.CONTROL_ID;
   }
   
   //==============================================
   // Methods
   //==============================================

   /**
    * Renderiza el control y genera el código XHTML de representación.
    *
    * @return Devuelve una cadena en formato XHTML que representa el control. 
    */
   @Override
   public String render(Template template) 
   {
      throw new UnsupportedOperationException("Not supported yet.");
   }
   
   //==============================================
   // Private members
   //==============================================
   
   /**
    * Inicializa la instancia.
    */
   private void initialize()
   {
      
   }
}
