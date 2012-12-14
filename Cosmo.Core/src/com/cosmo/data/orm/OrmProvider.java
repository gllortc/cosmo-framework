package com.cosmo.data.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.cosmo.Workspace;
import com.cosmo.data.DataConnection;
import com.cosmo.data.DataException;
import com.cosmo.data.DataSource;

/**
 * Implementa un acceso uniforme a los drivers CORM.<br />
 * Esta clase se encarga de instanciar el driver adecuado (seg�n la configuraci�n) y de manejar las llamadas a m�todos.
 * 
 * @author Gerard Llort
 */
public class OrmProvider 
{
   OrmDriver driver;
   
   //==============================================
   // Constructors
   //==============================================
   
   /**
    * Constructor de la clase.
    * 
    * @param connection Un objeto de conexi�n a datos.
    */
   public OrmProvider(String dataSourceId, Workspace workspace) throws OrmDriverException
   {
      this.driver = loadDriver(dataSourceId, workspace);
   }

   //==============================================
   // Properties
   //==============================================
   
   /**
    * Devuelve el nombre del proveedor.<br />
    * Debe especificar claramente para qu� gestor de BBDD est� implementado (nombre del producto).
    */
   public String getProviderName() 
   {
      return driver.getProviderName();
   }

   /**
    * Devuelve el nombre cualificado de la clase que act�a de driver para la conexi�n JDBC.
    */
   public String getJdbcDriver() 
   {
      return driver.getJdbcDriver();
   }
   
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
   public Object add(Class<?> ormObject, HttpServletRequest request) throws InvalidMappingException, SQLException, DataException, Exception
   {
      return driver.add(ormObject, request);
   }
   
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
   public void add(Object data) throws InvalidMappingException, SQLException, DataException, Exception 
   {
      driver.add(data);
   }
   
   //==============================================
   // Private members
   //==============================================
   
   /**
    * Carga el controlador de usuarios.
    * 
    * @throws OrmDriverException 
    */
   private static OrmDriver loadDriver(String dataSourceId, Workspace workspace) throws OrmDriverException
   {
      OrmDriver provider;
      DataSource ds = null;
      DataConnection conn;
      
      try 
      {
         // Genera la instancia
         ds = workspace.getProperties().getDataSource(dataSourceId);
         if (ds == null)
         {
            throw new OrmDriverException("Datasource " + dataSourceId + " not found");
         }
         
         // Genera la conexi�n
         conn = new DataConnection(ds);
         
         // Invoca el constructor del driver
         Class<?> cls = Class.forName(ds.getCormDriver());
         Constructor<?> cons = cls.getConstructor(DataConnection.class);
         provider = (OrmDriver) cons.newInstance(conn);
         
         return provider;
      }
      catch (NoSuchMethodException ex)
      {
         throw new OrmDriverException("CORM driver loader: NoSuchMethodException: " + ds.getCormDriver(), ex);
      }
      catch (InvocationTargetException ex) 
      {
         throw new OrmDriverException("CORM driver loader: InvocationTargetException: " + ds.getCormDriver(), ex);
      }
      catch (ClassNotFoundException ex) 
      {
         throw new OrmDriverException("CORM driver loader: ClassNotFoundException: " + ds.getCormDriver(), ex);
      }
      catch (InstantiationException ex)
      {
         throw new OrmDriverException("CORM driver loader: InstantiationException: " + ds.getCormDriver(), ex);
      }
      catch (IllegalAccessException ex)
      {
         throw new OrmDriverException("CORM driver loader: IllegalAccessException: " + ds.getCormDriver(), ex);
      }
   }
}
