package com.cosmo.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * Implementa una estructura de tabla que permite albergar los datos para un <em>grid</em> o <em>report</em>.
 * 
 * @author Gerard Llort
 */
public class GridData 
{
   private int cols;
   private int rows;
   private int width;
   private int height;
   private Object[][] map;       // row, col
   private String[] colFields;   // contiene nombres de campo (utilidad opcional)
   private Boolean[] colKeys;    // indica que columnas son parte de la clave primaria (identificador de fila)
   
   
   //==============================================
   // Contructors
   //==============================================
   
   /**
    * Contructor de la clase.
    */
   public GridData()
   {
      this.rows = 0;
      this.cols = 0;
      this.height = 10;
      this.width = 10;
      this.map = new Object[10][10];
      this.colFields = new String[10];
      this.colKeys = new Boolean[10];
   }
   
   /**
    * Contructor de la clase.
    * 
    * @param rows N�mero de filas.
    * @param rows N�mero de columnas.
    */
   public GridData(int rows, int cols)
   {
      this.rows = 0;
      this.cols = 0;
      this.height = rows;
      this.width = cols;
      this.map = new Object[rows][cols];
      this.colFields = new String[cols];
      this.colKeys = new Boolean[cols];
   }
   
   
   //==============================================
   // Properties
   //==============================================

   /**
    * Devuelve el n�mero de columnas de la tabla.
    */
   public int getColumnCount() 
   {
      return cols + 1;
   }

   /**
    * Devuelve el n�mero de filas de la tabla.
    */
   public int getRowCount() 
   {
      return rows + 1;
   }
   
   
   //==============================================
   // Methods
   //==============================================
   
   /**
    * Establece el valor de una celda de la tabla.
    * 
    * @param row �ndice de la fila (base 0).
    * @param col �ndice de la columna (base 0).
    * @param value Valor a establecer.
    */
   public void setCell(int row, int col, Object value) throws GridDataLimitsException
   {
      // Actualiza fila/columna de datos
      if (this.rows <= row)
      {
         this.rows = row;
      }
      if (this.cols <= col)
      {
         this.cols = col;
      }
      
      if (this.width <= col || this.height <= row)
      {
         this.width = (this.width <= col ? col + 10 : this.width);
         this.height = (this.height <= row ? row + 10 : this.height);

         resize(this.height, this.width);
      }

      map[row][col] = value;
   }
   
   /**
    * Obtiene el contenido de una celda.
    * 
    * @param row �ndice de la fila (base 0).
    * @param col �ndice de la columna (base 0).
    * 
    * @return Un objeto que corresponde al contenido de la celda. En caso de ser una celda vac�a, este m�todo devolver� {@code null}.
    */
   public Object getCell(int row, int col)
   {
      return map[row][col];
   }
   
   /**
    * Obtiene el contenido de una celda.
    * 
    * @param row �ndice de la fila (base 0).
    * @param col �ndice de la columna (base 0).
    * @param defaultValue Valor por defecto que devolver� la llamada si el valor obtenido est� vac�o.
    * 
    * @return Un objeto que corresponde al contenido de la celda. En caso de ser una celda vac�a, este m�todo devolver� {@code null}.
    */
   public Object getCell(int row, int col, Object defaultValue)
   {
      if (map[row][col] == null)
      {
         return defaultValue;
      }
      else
      {
         return map[row][col];
      }
   }
   
   /**
    * Establece los valores de una tabla a partir de una consulta a base de datos.<br />
    * Este m�todo no sabe reconocer los campos ID y por ello no marca los campos que forman la clave primaria.
    * 
    * @param useColNameAsHeadTitle Indica si se deben usar los nombres de columna para la fila de t�tulos.
    * @param rs Una instancia de {@link ResultSet} que contiene los datos para la tabla.
    */
   public void setCells(ResultSet rs, boolean useColNameAsHeadTitle) throws SQLException
   {
      int row;
      
      // Obtiene los metadatos de la estructura
      ResultSetMetaData md = rs.getMetaData();
      
      // Agrega la fila de t�tulos y nombres de campo
      if (useColNameAsHeadTitle)
      {
         for (int col = 0; col < md.getColumnCount(); col++)
         {
            try
            {
               setCell(0, col, md.getColumnLabel(col + 1));
               setColumnField(col, md.getColumnName(col + 1));
            }
            catch (GridDataLimitsException ex)
            {
               // Nothing to do
            }
         }
      }

      // Agrega los datos
      row = (useColNameAsHeadTitle ? 1 : 0);
      while (rs.next())
      {
         for (int col = 0; col < md.getColumnCount(); col++)
         {
            try
            {
               setCell(row, col, rs.getObject(col + 1));
            }
            catch (GridDataLimitsException ex)
            {
               // Nothing to do
            }
         }
         row++;
      }
   }
   
   /**
    * Vac�a la tabla de todos sus datos.
    */
   public void clear()
   {
      initialize();
   }
   
   /**
    * Establece el nombre de un campo (columna).
    * <br /><br />
    * Este array es simplemente una utilidad informativa y s�lo lo usan procesos que precisen almacenar esta informaci�n, 
    * pero no afecta a la presentaci�n o a la carga de datos del grid.
    * 
    * @param col �ndice de la columna (base 0).
    * @param fieldName Una cadena que contiene el nombre del campo de la base de datos correspondiente a la columna.
    */
   public void setColumnField(int col, String fieldName)
   {
      this.colFields[col] = fieldName;
   }
   
   /**
    * Obtiene el nombre de un campo (columna).
    * <br /><br />
    * Este array es simplemente una utilidad informativa y s�lo lo usan procesos que precisen almacenar esta informaci�n, 
    * pero no afecta a la presentaci�n o a la carga de datos del grid.
    * 
    * @param col �ndice de la columna (base 0).
    * 
    * @return Una cadena que representa el nombre del campo en la base de datos.
    */
   public String getColumnField(int col)
   {
      return this.colFields[col];
   }
   
   /**
    * Establece si una columna forma parte de la clave primaria.
    * <br /><br />
    * Este array es simplemente una utilidad informativa y s�lo lo usan procesos que precisen almacenar esta informaci�n, 
    * pero no afecta a la presentaci�n o a la carga de datos del grid.
    * 
    * @param col �ndice de la columna (base 0).
    * @param isPrimaryKey {@code true} si la columna forma parte de la clave primaria o {@code false} si no forma parte.
    */
   public void setColumnPrimaryKey(int col, boolean isPrimaryKey)
   {
      this.colKeys[col] = isPrimaryKey;
   }
   
   /**
    * Indica si una columna forma parte de la clave primaria.
    * <br /><br />
    * Este array es simplemente una utilidad informativa y s�lo lo usan procesos que precisen almacenar esta informaci�n, 
    * pero no afecta a la presentaci�n o a la carga de datos del grid.
    * 
    * @param col �ndice de la columna (base 0).
    * 
    * @return {@code true} si la columna est� marcada como parte de la clave primaria o {@code false} en cualquier otro caso.
    */
   public boolean isColumnPrimaryKey(int col)
   {
      return this.colKeys[col];
   }
   
   /**
    * Obtiene un ID compuesto por todos los valores de las columnas identificador de fila.
    * <br /><br />
    * Formato: {@code colField1=12&colField3=C-0&colField6=2SDD2}
    * 
    * @param data Una instancia de {@link GridData} que contiene los datos del control.
    * @param row �ndice de la fila actual.
    * 
    * @return Una cadena que representa los identificadores �nicos para una fila como par�metros de URL.
    */
   public String getRowId(int row)
   {
      boolean first = true;
      String id = "";
      
      for (int i = 0; i < this.cols; i++)
      {
         if ((this.colKeys[i] != null) && this.colKeys[i] == true)
         {
            id += (first ? "" : "&");
            id += this.colFields[i] + "=" + map[row][i].toString();
            
            first = false;
         }
      }
      
      return id;
   }
   
   /**
    * Convierte la instancia en una cadena de texto que representa la tabla de datos.
    */
   @Override
   public String toString()
   {
      String str = "";
      
      str += "[";
      for (int row = 0; row <= this.rows; row++)
      {
         str += "[";
         for (int col = 0; col <= this.cols; col++)
         {
            str += map[row][col].toString() + ", ";
         }
         str += "]";
      }
      str += "]";
      
      return str;
   }

   
   //==============================================
   // Private members
   //==============================================
   
   /**
    * Inicializa la instancia.
    */
   private void initialize()
   {
      this.rows = 10;
      this.cols = 10;

      map = new Object[10][10];
      colFields = new String[10];
      colKeys = new Boolean[10];
   }
   
   /**
    * Redimensiona la tabla de datos sin p�rdida de los datos.<br />
    * Este m�todo tambi�n redimensiona el array de nombres de campo.
    * 
    * @param rows Nuevo n�mero de filas (superior al anterior).
    * @param cols Nuevo n�mero de columnas (superior al anterior).
    */
   private void resize(int rows, int cols)
   {
      map = (Object[][]) resizeArray(map, rows);
      for (int i = 0; i < map.length; i++) 
      {
         if (map[i] == null) 
         {
            map[i] = new Object[cols];
         }
         else 
         {
            map[i] = (Object[]) resizeArray(map[i], cols);
         } 
      }

      // Redimensiona el array que contiene nombres de columnas y primary keys
      this.colFields = (String[]) resizeArray(this.colFields, cols);
      this.colKeys = (Boolean[]) resizeArray(this.colKeys, cols);
   }

   /**
   * Reallocates an array with a new size, and copies the contents of the old array to the new array.
   * 
   * @param oldArray  the old array, to be reallocated.
   * @param newSize   the new array size.
   * @return          A new array with the same contents.
   */
   @SuppressWarnings("rawtypes")
   private static Object resizeArray(Object oldArray, int newSize) 
   {
      int oldSize = java.lang.reflect.Array.getLength(oldArray);
      Class elementType = oldArray.getClass().getComponentType();
      Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
      int preserveLength = Math.min(oldSize, newSize);
      
      if (preserveLength > 0)
      {
         System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
      }
      
      return newArray; 
   }
}
