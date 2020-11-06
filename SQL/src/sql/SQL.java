    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author allado
 */
public class SQL {

    /**
     * @param args the command line arguments
     */
    private static Connection conn;
    private static String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost/mibd";
    
    
    public SQL(){
        conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                System.out.println("Conexión Establecida");
            }
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar" + e);
        }
    }
    
    public Connection getConnection(){
        return conn;
    }
    
    public void Desconectar(){
        conn = null;
        if (conn == null){
            System.out.println("Conexión Terminada");
        }
    }
    
    public void Resultado(){
    try (PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM alumnos WHERE edad = '20'")) {
    ResultSet rs = stmt.executeQuery();
    
    while (rs.next())
      System.out.println (rs.getString("nombre"));

    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
    }
    }
    
    public void BorrarRegistro(Boolean[] rows, String[] ids, String Table){
    for(int i = 0; i < rows.length; i++) 
      {
       if (rows[i] == true)
       {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + Table + " WHERE id = " + ids[i])) {
        stmt.execute();

        } catch (SQLException sqle) { 
          System.out.println("Error en la ejecución:" 
            + sqle.getErrorCode() + " " + sqle.getMessage());
        }
       }
      }
    }
        
    public String[][] Total(String tab){
    String [][] result;
    try (PreparedStatement stmtt = conn.prepareStatement("SELECT * FROM " + tab)) {
    ResultSet rs = stmtt.executeQuery();
    rs.last();
    int i = rs.getRow();
    result = new String [i][];
    i = 0;
    rs.first();
    
                    do
                    {                       
                        result[i] = new String[]{rs.getString(1), rs.getString(2), rs.getString(3)};
                        //System.out.println(Arrays.toString(result[i]));
                        i++;
                    }while (rs.next());
    //System.out.println(Arrays.deepToString(result));               
    return result;
    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
      return null;
    }
    }
    
    public String[] Cabecera(String tab){
    String [] header;
    try (PreparedStatement stmtt = conn.prepareStatement("SELECT * FROM " + tab)) {
    ResultSet rs = stmtt.executeQuery();
    ResultSetMetaData rsMetaData = rs.getMetaData();
    int count = rsMetaData.getColumnCount();
    header = new String [count];
    
      for(int i = 0; i<count; i++) {
         header[i] = rsMetaData.getColumnName(i+1);
      }
    //System.out.println(Arrays.deepToString(result));               
    return header;
    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
      return null;
    }
    }
    
    public String[] Types(String tab){
    String [] types;
    try (PreparedStatement stmtt = conn.prepareStatement("SELECT * FROM " + tab)) {
    ResultSet rs = stmtt.executeQuery();
    ResultSetMetaData rsMetaData = rs.getMetaData();
    int count = rsMetaData.getColumnCount();
    types = new String [count];
    
      for(int i = 0; i<count; i++) {
         types[i] = rsMetaData.getColumnTypeName(i+1);
      }
    //System.out.println(Arrays.deepToString(result));
        System.out.println(Arrays.toString(types));
    return types;
    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
      return null;
    }
    }
    
        
    public String [] Enseñar(String db){
    String [] tables;
    try (PreparedStatement stmt = conn.prepareStatement("SHOW TABLES")) {
    ResultSet rs = stmt.executeQuery();
    rs.last();
    int i = rs.getRow();
    tables = new String [i];
    rs.first();
    i = 0;
    do{
        tables [i] = (rs.getString("Tables_in_" + db));
        //System.out.println (tables[i]);
        i ++;
    }
    while (rs.next());
    return tables;
    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
      return null;
    }
    }
    
    public String [] Presentar(){
    String [] bases;
    try (PreparedStatement stmt = conn.prepareStatement("SHOW DATABASES")) {
    ResultSet rs = stmt.executeQuery();
    rs.last();
    int i = rs.getRow();
    bases = new String [i];
    rs.first();
    i = 0;
    do{
        bases [i] = (rs.getString(1));
        //System.out.println (bases[i]);
        i ++;
    }
    while (rs.next());
    return bases;
    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
      return null;
    }
    }
    
    public void Crear(){
    try (PreparedStatement stmt = conn.prepareStatement("create database nuevecita")) {
    //ResultSet rs = stmt.executeQuery();
    stmt.execute();
    //while (rs.next())
    //  System.out.println (rs.getString("country"));

    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
    }
    }
    
    public void Insertar(String tabla, String header, String values){
    try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tabla + " (" + header + ") VALUES (" + values + ")")) {
        System.out.println("INSERT INTO " + tabla + " (" + header + ") VALUES (" + values + ")");    
//ResultSet rs = stmt.executeQuery();
    stmt.execute();
    //while (rs.next())
    //  System.out.println (rs.getString("country"));

    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
    }
    }
    
    public void Actualizar(String tabla, String[] header, String[] values){
    String statement = "UPDATE " + tabla + " SET ";
    for (int i = 0; i < header.length; i++)
    {
      statement = statement + header[i] + "='" + values[i] + "'";
      if (i != header.length-1) statement = statement + ",";
    }
    statement = statement + " WHERE " + header[0] + "=" + values[0];
    
    System.out.println(statement);
    try (PreparedStatement stmt = conn.prepareStatement(statement)) {    

    stmt.execute();

    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
    }
    }
     
    public void Usar(String db){
        try (PreparedStatement stmt = conn.prepareStatement("USE " + db)) {
    //ResultSet rs = stmt.executeQuery();
    stmt.execute();
    //while (rs.next())
    //  System.out.println (rs.getString("country"));

    } catch (SQLException sqle) { 
      System.out.println("Error en la ejecución:" 
        + sqle.getErrorCode() + " " + sqle.getMessage());
    }
    }
    /*
    public static void main(String[] args) {
        // TODO code application logic here
        int number = 0;
        System.out.println(number);
    }
    */
}