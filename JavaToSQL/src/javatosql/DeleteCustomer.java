package javatosql;
import java.sql.*;
import java.util.*;


public class DeleteCustomer {

	
	
	public static void main(String[] args) throws Exception{
		String db = "moviedb";
		String user = "root";
		String pass = "pass";
		deleteCustomer(db, user, pass);
	}

	public static void deleteCustomer(String db, String user, String pwd)
			throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql:///"+db,user, pwd);
        Statement select = connection.createStatement();
        
        String query = getQuery();
        
		int count = select.executeUpdate(query);
		System.out.println("Deleted " + count + " rows");
		select.close();
		connection.close();
	}

	private static String getQuery() {
		Scanner in = new Scanner(System.in);
		String name[] = Helper.nameArr("customer's", in);
		
		String first = name[0];
		String last = name[1];
		
		String query = "DELETE FROM customers " +
					   "WHERE first = '" + first + "' AND " +
					   "last = '" + last + "'";
		System.out.println(query);
		return query;
	}

}
