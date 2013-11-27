package com.cosmo.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implementa una clase para la gesti�n de conexiones a BBDD.
 * 
 * @author Gerard Llort
 */
@Deprecated
public class DataConnection
{
   private DataSource pds;
   private Connection conn = null;
   private String lastSqlStatement = "";
   private boolean autoCommit = true;


   //==============================================
   // Constructors
   //==============================================

   /**
    * Constructor de la clase.
    * 
    * @param datasource Una instancia de {@link DataSource} que describe la conexi�n con la base de datos.
    * 
    * @throws Exception
    */
   public DataConnection(DataSource datasource)
   {
      this.pds = datasource;
   }

   /**
    * Constructor de la clase.
    * 
    * @param host Host del servidor de bases de datos.
    * @param port Puerto de conexi�n.
    * @param schema Nombre de la base de datos (y/o esquema).
    * @param userLogin Login del usuario.
    * @param userPassword Contrase�a del usuario.
    */
   public DataConnection(String host, String port, String schema, String userLogin, String userPassword, String jdbcDriver, String cormDriver)
   {
      this.pds = new DataSource("custom", host, port, schema, userLogin, userPassword, jdbcDriver, cormDriver);
   }

   //==============================================
   // Properties
   //==============================================

   public boolean isAutoCommit()
   {
      return autoCommit;
   }

   public void setAutoCommit(boolean autoCommit)
   {
      this.autoCommit = autoCommit;
   }

   public String getLastSQLStatement()
   {
      return this.lastSqlStatement;
   }


   //==============================================
   // Methods
   //==============================================

   /**
    * Obre una connexi� amb la base de dades seleccionada.
    * 
    * @throws SQLException
    * @throws ClassNotFoundException
    * @throws DataException 
    */
   public void connect() throws SQLException, ClassNotFoundException, DataException 
   {
      if (pds == null)
      {
         throw new DataException("Impossible connectar a BBDD: No hi ha cap DataSource seleccioant");
      }

      try 
      {
         Class.forName(pds.getJdbcDriver());

         conn = DriverManager.getConnection(pds.getConnectionUrl() + ":" + pds.getPort(), pds.getLogin(), pds.getPassword());
         conn.setAutoCommit(this.autoCommit);
      } 
      catch (SQLException e)
      {
         throw e;
      }
   }

   /**
    * Tanca qualsevol connexi� oberta amb la base de dades activa.
    */
   public void disconnect()
   {
      if (conn != null)
      {
         try
         {
            if (!conn.isClosed())
            {
               conn.close();
            }
         } 
         catch (SQLException e)
         {
            // Nothing to do here
         }
      }
   }

   /**
    * Indica si la connexi�n est� abierta y disponible.
    * 
    * @return Retorna {@code true} si la connexi� est� disponible o {@code false} en qualsevol altre cas.
    */
   public boolean isOpened()
   {
      if (conn == null)
      {
         return false;
      }

      try
      {
         return !conn.isClosed();
      } 
      catch (SQLException e)
      {
         return false;
      }
   }

   /**
    * Envia els canvis a la base de dades.
    * 
    * @throws SQLException 
    */
   public void commit() throws SQLException
   {
      conn.commit();
   }

   public ResultSet executeSql(String sql) throws SQLException, ClassNotFoundException, DataException
   {
      // Memoriza la sent�ncia SQL
      this.lastSqlStatement = sql;

      // Obliga a tener la conexi�n abierta
      if (conn == null)
      {
         this.connect();
      }

      Statement st = conn.createStatement();

      return st.executeQuery(sql);
   }

   /**
    * Executa una sent�ncia SQL de la que no s'espera cap resultat (p. ex. INSERT, UPDATE, DELETE).
    * 
    * @param sql Sent�ncia SQL a executar (consulta).
    * 
    * @return {@code True} si l'execuci� ha tingut �xit o {@code False} en qualsevol altre cas.
    */
   public boolean execute(String sql) throws SQLException, ClassNotFoundException, DataException
   {
      // Memoritza la sent�ncia SQL
      this.lastSqlStatement = sql;

      // Obliga a tener la conexi�n abierta
      if (conn == null)
      {
         this.connect();
      }

      Statement st = conn.createStatement();

      return st.execute(sql);
   }

   /**
    * Executa una consulta SQL i retorna el valor enter ({@link Integer} de la primera fila i primera columna.
    * 
    * @param sql Sent�ncia SQL a executar (consulta).
    * 
    * @return Un valor enter correspòn al valor de la cel·la 0,0.
    */
   public Integer executeScalar(String sql) throws SQLException, ClassNotFoundException, DataException, Exception 
   {
      // Memoritza la sent�ncia SQL
      this.lastSqlStatement = sql;

      // Obliga a tener la conexi�n abierta
      if (conn == null)
      {
         this.connect();
      }

      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (!rs.next())
      {
         throw new Exception("ERROR: No es pot obtenir un ID v�lid per la nova relaci�.");
      }

      return rs.getInt(1);
   }

   /**
    * Ejecuta una consulta SQL y devuelve la cadena de texto (o el valor en formato {@link String}) de la primera fila y primera columna.
    * 
    * @param sql Sent�ncia SQL a ejecutar (consulta).
    * 
    * @return Una cadena de texto que corresponde al valor de la celda 0,0.
    */
   public String executeString(String sql) throws SQLException, ClassNotFoundException, DataException, Exception 
   {
      // Memoritza la sent�ncia SQL
      this.lastSqlStatement = sql;

      // Obliga a tener la conexi�n abierta
      if (conn == null)
      {
         this.connect();
      }

      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (!rs.next())
      {
         throw new Exception("ERROR: No es pot obtenir un ID v�lid per la nova relaci�.");
      }

      return rs.getString(1);
   }


   //==============================================
   // Static members
   //==============================================

   /**
    * Permet recuperar una cadena d'una fila d'un {@link ResultSet} convertint a cadena buida els valors {@code NULL}.
    * 
    * @param rs Una instancia de {@link ResultSet} que la cadena a estreure d'un camp de la fila actual.
    * @param column �ndex de la columna (1 per la primera).
    * 
    * @return Una cadena que contiene el valor de la columna indicada por {@code column} en la fila actual.
    */
   public static String getNotNullString(ResultSet rs, int column) throws SQLException
   {
      String value = rs.getString(column);

      return (value == null ? "" : value);
   }

   /**
    * Formatea una cadena de texto pera ser usada en una sent�ncia SQL.
    * 
    * @param text Cadena de texto a formatear.
    * 
    * @return Una cadena de texto que se puede usar en cualquier sent�ncia SQL.
    */
   public static String sqlFormatTextValue(String text)
   {
      String ftext = text.trim().replace("'", "''");

      return ftext;
   }


   //==============================================
   // Private members
   //==============================================

}
