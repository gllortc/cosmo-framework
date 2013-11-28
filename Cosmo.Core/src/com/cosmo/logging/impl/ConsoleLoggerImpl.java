package com.cosmo.logging.impl;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

import com.cosmo.logging.LogAppender;
import com.cosmo.util.PluginProperties;
import com.cosmo.util.StringUtils;

/**
 * Agente de logging que representa las trazas en la c�nsola.
 * <br /><br />
 * Par�metros de configuraci�n necesarios (en el archivo XML de configuraci�n de Cosmo):
 * <ul>
 * <li><em>pattern</em>: Patr�n de representaci�n de la informaci�n.</li>
 * <li><em>threshold</em>: Nivel de traza m�nimo.</li>
 * <li><em>immediateFlush</em>: Indica si se vac�a el buffer de salida cada vez que se escribe en el log.</li>
 * </ul>
 * 
 * @see <a href="http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/ConsoleAppender.html">ConsoleAppender</a>
 * 
 * @author Gerard Llort
 */
public class ConsoleLoggerImpl extends LogAppender
{
   private static final String PARAM_PATTERN = "pattern";
   private static final String PARAM_THRESHOLD = "threshold";
   private static final String PARAM_IMMEDIATEFLUSH = "immediateFlush";

   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase {@link ConsoleLoggerImpl}.
    * 
    * @param properties Una instancia de {@link PluginProperties} que contiene los par�metros de configuraci�n del agente de <em>logging</em>.
    */
   public ConsoleLoggerImpl(PluginProperties properties)
   {
      super(properties);
   }


   //==============================================
   // Constructors
   //==============================================

   /**
    * Devuelve una instancia de {@link Appender} que ser� usada por <em>Log4j</em>.
    */
   @Override
   public Appender getAppender()
   {
      String pattern;

      // Obtiene el patr�n de escritura en el LOG
      pattern = this.getProperties().getParamString(PARAM_PATTERN);
      if (StringUtils.isNullOrEmptyTrim(pattern))
      {
         pattern = LogAppender.DEFAULT_PATTERN;
      }

      // Crea y configura la instancia del appender
      ConsoleAppender console = new ConsoleAppender();
      console.setName(this.getProperties().getId());
      console.setLayout(new PatternLayout(pattern));
      console.setThreshold(LogAppender.stringToLevel(this.getProperties().getParamString(PARAM_THRESHOLD)));
      console.setImmediateFlush(this.getProperties().getParamBoolean(PARAM_IMMEDIATEFLUSH, false));
      console.activateOptions();

      return console;
   }
}
