package com.cosmo.data.adapter;

import java.sql.SQLException;

import com.cosmo.data.DataConnection;
import com.cosmo.data.DataException;

public abstract class CosmoOrm 
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
   public CosmoOrm(DataConnection connection)
   {
      this.lastSqlSentence = "";
      this.connection = connection;
   }
   
   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve el nombre del proveedor.<br />
    * Debe especificar claramente para qu� BBDD est� implementado.
    */
   public abstract String getProviderName(); 
   
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
    * Genera una sent�ncia INSERT INTO a partir de una instancian de clase.
    * 
    * @param data Clase que contiene los datos a insertar.
    * 
    * @throws InvalidMappingException
    * @throws DataException 
    * @throws SQLException 
    * @throws Exception 
    */
   public abstract void insert(Object data) throws InvalidMappingException, SQLException, DataException, Exception;
}
