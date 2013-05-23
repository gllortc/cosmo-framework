package com.cosmo.ui.render;

import com.cosmo.ui.Page;
import com.cosmo.ui.templates.TemplateUnavailableException;

/**
 * Interface que deben implementar todas las implementaciones de <em>renderer</em> para p�ginas de Cosmo.
 * 
 * @author Gerard Llort
 */
public interface PageRender
{
   /**
    * Renderiza la p�gina convirtiendo la l�gica de clases en c�digo XHTML.
    * 
    * @param page Una instancia de {@link Page} que representa la p�gina a renderizar.
    * @return Una cadena que contiene el c�digo XHTML.
    * 
    * @throws TemplateUnavailableException
    * @throws PageRenderException 
    */
   public abstract String render(Page page) throws TemplateUnavailableException, PageRenderException;
}
