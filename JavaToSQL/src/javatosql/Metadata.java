package javatosql;

import java.sql.*;
import java.util.*; 

public class Metadata {
	
	private static final String user = "root";
	private static final String pwd = "limeaide";
	private static final String db = "moviedb";
	
	public static List<String> getTableNames(Connection conn) throws Exception{
		List<String> tableNames = new ArrayList<String>();
		DatabaseMetaData dbmd = conn.getMetaData();
		
		ResultSet rs = dbmd.getTables(null, null, "%", null);
		while(rs.next())
			tableNames.add(rs.getString(3));
		
		rs.close();
		
		return tableNames;
	}
	
	public static void printAttributes(String table, Connection conn) throws Exception{
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getColumns(null,null,table,null);
		
		while(rs.next())
			System.out.println(rs.getString(4) + " -- " + rs.getString(7));
		
		rs.close();
	}
	
	 public static void getDatabaseMetaData()
	    {
	        try {
	        	Class.forName("com.mysql.jdbc.Driver").newInstance();
	            Connection connection = DriverManager.getConnection("jdbc:mysql:///"+db,user, pwd);
	            
	            List<String> tableNames = getTableNames(connection);
	            for(String table : tableNames){
	            	System.out.println(table);
	            	printAttributes(table, connection);
	            	System.out.println("");
	            }
	            
	            connection.close();
	        } 
	            catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	public static void main(String[] args) throws Exception {

        // Connect to the test database
       
       getDatabaseMetaData();
//       DatabaseMetaData metadata = connection.getMetaData();
//       ResultSet rs = md.getTables(null, null, "%", null);
       
       /*
       // Create an execute an SQL statement to select all of table"Stars" records
       Statement select = connection.createStatement();
       
       ResultSet result = select.executeQuery("Select * from stars");

       // Get metatdata from stars; print # of attributes in table
       System.out.println("The results of the query");
       ResultSetMetaData metadata = result.getMetaData();
       System.out.println("There are " + metadata.getColumnCount() + " columns");

       // Print type of each attribute
       for (int i = 1; i <= metadata.getColumnCount(); i++)
               System.out.println(metadata.getColumnLabel(i) + " is " + metadata.getColumnTypeName(i));

       // print table's contents, field by field
       while (result.next())
       {
               System.out.println("Id = " + result.getInt(1));
               System.out.println("Name = " + result.getString(2) + result.getString(3));
               System.out.println("DOB = " + result.getString(4));
               System.out.println("photoURL = " + result.getString(5));
               System.out.println();
       }
       */

	}

}
