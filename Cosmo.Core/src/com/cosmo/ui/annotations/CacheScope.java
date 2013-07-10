package com.cosmo.ui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaci�n que permite indicar a una p�gina como debe <em>cachear</em> el contenido.
 * 
 * @author Gerard Llort
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface CacheScope 
{
   /**
    * Enumera los distintos �mbitos que puede tener la cach� de p�gina:<br />
    * <ul>
    * <li>{@code SCOPE_APPLICATION}: El contenido de la p�gina es com�n a todos y s�lo se configura una vez</li>
    * <li>{@code SCOPE_SESSION}: El contenido de la p�gina es com�n a todos y s�lo se configura una vez</li>
    * <li>{@code NO_CACHE}: La cach� estar� deshabilirtada para la p�gina</li>
    * </ul>
    * 
    * @author Gerard Llort
    */
   public enum PageCacheScopes
   {
      /** El contenido de la p�gina estar� disponible para todos los usuarios */
      SCOPE_APPLICATION,
      /** El contenido de la p�gina estar� disponible para todos los usuarios */
      SCOPE_SESSION,
      /** La cach� estar� deshabilirtada para la p�gina */
      NO_CACHE
   }
   
   public PageCacheScopes scope();
}
