package javatosql;

import java.sql.*;
import java.util.*; 

public class Metadata {
	
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
			System.out.println(rs.getString(4) + " -- " + rs.getString(6) + "(" + rs.getString(7) + ")");
		
		rs.close();
	}
	
	 public static void getDatabaseMetaData(String db, String username, String password)
	    {
	        try {
	        	Class.forName("com.mysql.jdbc.Driver").newInstance();
	            Connection connection = DriverManager.getConnection("jdbc:mysql:///"+db,username, password);
	            
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
		String db = "moviedb";
		String username = "root";
		String pwd = "pass";
       getDatabaseMetaData(db,username,pwd);

	}

}
