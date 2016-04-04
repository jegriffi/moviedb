package javatosql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class InsertStar {

	public static void main(String[] args) throws Exception {
		String db = "moviedb";
		String username = "root";
		String pwd = "pass";
		insertStar(db,username,pwd);
	}
	
	public static void insertStar(String db, String username, String password) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql:///"+db,username, password);
        Statement select = connection.createStatement();
        
        String query = query();
        
        System.err.println(query);
		int count = select.executeUpdate(query);
		System.out.println("\nInserted " + count + " rows");
		select.close();
		connection.close();
	}
	
	private static String query(){
		List<String> starInfo = getStarInfo();
		String first = starInfo.get(0);
		String last  = starInfo.get(1);
		String dob = starInfo.get(2);
		String photo = starInfo.get(3);
		
		String query = "INSERT INTO stars (first, last, dob, photo) "
				+ "VALUES ('" + first + "', '" + last + "', '" + dob + "', '" + photo + "')";
		
		return query;
	}
	
	private static List<String> getStarInfo(){
		List<String> starInfo = new ArrayList<String>();
    	Scanner in = new Scanner(System.in);
    	String input = null;
    	
    	String[] starName = Helper.nameArr("star's", in);
    	String first = starName[0];
    	String last = starName[1];
		
    	String dob = "1-1-1";
		System.out.print("Do you have the star's DOB? (y/n) ");
		input = in.nextLine().split("\\s+")[0];
		if(input.equals("y")) 
			dob = Helper.getDate(in);		
		
		System.out.print("Enter photo URL (optional) : ");
		String photo = in.nextLine().split("\\s+")[0];
		
		starInfo.add(first);
		starInfo.add(last);
		starInfo.add(dob);
		starInfo.add(photo);
				
		return starInfo;
	}

}
