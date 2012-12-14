package com.cosmo.data.orm;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.cosmo.data.DataConnection;
import com.cosmo.data.DataException;
import com.cosmo.data.adapter.InvalidMappingException;

/**
 * Declara una clase abstracta que debe servir como base para la implementaci�n de drivers ORM para Cosmo.
 * 
 * @author Gerard Llort
 */
public abstract class OrmDriver 
{
   private String lastSqlSentence;
   private DataConnection connection;
   
   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.
    * 
    * @param connection Un objeto de conexi�n a datos.
    */
   public OrmDriver(DataConnection connection)
   {
      this.lastSqlSentence = "";
      this.connection = connection;
   }
   
   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve el nombre del proveedor.<br />
    * Debe especificar claramente para qu� gestor de BBDD est� implementado (nombre del producto).
    */
   public abstract String getProviderName(); 
   
   /**
    * Devuelve el nombre cualificado de la clase que act�a de driver para la conexi�n JDBC.
    * Debe especificar claramente para qu� BBDD est� implementado.
    */
   public abstract String getJdbcDriver(); 
   
   /**
    * Devuelve la �ltima sent�ncia SQL generada y ejecutada.
    */
   public String getLastSqlSentence() 
   {
      return lastSqlSentence;
   }
   
   /**
    * Establece la �ltima sent�ncia SQL generada y ejecutada.<br />
    * Este m�todo s�lo ser� usado por las implementaciones de los distintos ORM.
    */
   public void setLastSqlSentence(String sql)
   {
      this.lastSqlSentence = sql;
   }
   
   /**
    * Devuelve la conexi�n a BBDD usada por la instancia.
    */
   public DataConnection getConnection() 
   {
      return connection;
   }   
   
   //==============================================
   // Methods
   //==============================================

   /**
    * Recupera los valores, los coloca en una instancia de la clase y agrega los datos en la base de datos.
    * 
    * @param ormObject
    * @param request
    * 
    * @throws Exception 
    * @throws DataException 
    * @throws SQLException 
    * @throws InvalidMappingException 
    */
   public abstract Object add(Class<?> ormObject, HttpServletRequest request) throws InvalidMappingException, SQLException, DataException, Exception;
   
   /**
    * Genera una sent�ncia INSERT INTO a partir de una instancian de clase.
    * 
    * @param data Clase que contiene los datos a insertar.
    * 
    * @throws InvalidMappingException
    * @throws DataException 
    * @throws SQLException 
    * @throws Exception 
    */
   public abstract void add(Object data) throws InvalidMappingException, SQLException, DataException, Exception;
}
