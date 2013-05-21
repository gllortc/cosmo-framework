package com.cosmo.security;

/**
 * Implementa un rol de usuario (o grupo).
 * 
 * @author Gerard Llort
 */
public class Role 
{
   private String id;
   private String description;
   private String applicationId;
   private boolean superUser;
   
   /**
    * Constructor de la clase.
    */
   public Role() 
   {
      this.id = "";
      this.description = "";
      this.applicationId = "";
      this.superUser = false;
   }
   
   /**
    * Devuelve el identificador del rol.
    */
   public String getId() 
   {
      return id;
   }
   
   /**
    * Establece el identificador del rol.
    */
   public void setId(String id) 
   {
      this.id = id;
   }
   
   /**
    * Devuelve la descripci�n del rol.
    */
   public String getDescription() 
   {
      return description;
   }
   
   /**
    * Establece la descripci�n del rol.
    */
   public void setDescription(String description) 
   {
      this.description = description;
   }
   
   /**
    * Devuelve el identificador de la aplicaci�n.
    * <br /><br />
    * Una cadena vac�a indica que el rol es gen�rico y se aplica a todas las aplicacions. 
    * Si contiene un identificador indica que el rol s�lo se aplica a la aplicaci�n concreta.
    */
   public String getApplicationId() 
   {
      return applicationId;
   }
   
   /**
    * Establece el identificador de la aplicaci�n a la que pertenece el rol.
    * <br /><br />
    * Una cadena vac�a indica que el rol es gen�rico y se aplica a todas las aplicacions. 
    * Si contiene un identificador indica que el rol s�lo se aplica a la aplicaci�n concreta.
    */
   public void setApplicationId(String applicationId) 
   {
      this.applicationId = applicationId;
   }
   
   /**
    * Indica si el rol es de super usuario (tiene permiso sobre todo).
    */
   public boolean isSuperUser() 
   {
      return superUser;
   }

   /**
    * Establece el indicador de super usuario (tiene permiso sobre todo).
    */
   public void setSuperUser(boolean superUser) 
   {
      this.superUser = superUser;
   }

   /**
    * Transforma la informaci�n de la instancia en una cadena con informaci�n comprensible. 
    */
   @Override
   public String toString()
   {
      return this.id + " (" + this.description + ")" + (this.superUser ? " Super User" : "");
   }
}
