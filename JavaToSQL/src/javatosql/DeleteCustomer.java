package javatosql;
import java.sql.*;
import java.util.*;


public class DeleteCustomer {
	private static final String user = "root";
	private static final String pwd = "limeaide";
	private static final String db = "moviedb";
	
	public static void main(String[] args) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql:///"+db,user, pwd);
        
        Statement select = connection.createStatement();
        
        String name[] = null;
        String input = null;
        do{
        	Scanner in = new Scanner(System.in);
        	System.out.print("Customer to delete: ");
        	input = in.nextLine();
        	name = input.split("\\s+");
        } while(input.length() == 0);
		
		String first = name.length > 0 ? name[0] : "";
		String last = name.length > 1  ? name[1] : "";
		
		String query = "DELETE FROM customers " +
					   "WHERE first = '" + first + "' AND " +
					   "last = '" + last + "'";
		System.out.println(query);
		
//		select.executeUpdate(query);
		select.close();
		connection.close();
	}

}
