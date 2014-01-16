package com.cosmo.ui;

/**
 * Interface que describe como debe ser una p�gina de Cosmo.
 * 
 * @author Gerard Llort
 */
public interface PageInterface
{
   /**
    * Renderiza el contenido de una p�gina, transform�ndola en una cadena XHTML.
    * 
    * @throws PageRenderException 
    */
   public void render(PageContext pc);
}
