package test;

import java.sql.*;
import java.util.*;

public class Jonathan {
	
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb"; 
    static  String db = "moviedb";
    static  String user = "root";
    static  String pass = "pass";   

	public static void main(String[] args) throws Exception {
		Class.forName(JDBC_DRIVER).newInstance();
		Connection conn = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
		
		
        conn = DriverManager.getConnection(DB_URL, user, pass);            
        Statement stmt = conn.createStatement();
        
        //starsInMovie(156006, stmt);
        starsInMovie("Jurassic Park", stmt);
        
        stmt.close();
        conn.close();

	}
	
	public static List<String> starsInMovie(int id, Statement select) throws Exception{
		List<String> stars = new ArrayList<String>();
		String query = "select stars.* from stars, stars_in_movies "
					 + "where " + id + " = stars_in_movies.movie_id and "
					 + "stars_in_movies.star_id = stars.id";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			String star = rs.getString("first") + " " + rs.getString("last");
			stars.add(star);
		}
		rs.close();
		return stars;
	}
	
	public static List<String> starsInMovie(String title, Statement select) throws Exception{
		List<String> stars = new ArrayList<String>();
		String query = "select stars.* from stars, stars_in_movies, movies "
					 + "where '" + title + "' = movies.title and "
					 + "movies.id = stars_in_movies.movie_id and "
					 + "stars_in_movies.star_id = stars.id";

		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			String star = rs.getString("first") + " " + rs.getString("last");
			stars.add(star);
			System.out.println(star);
		}
		rs.close();
		return stars;
	}
	
	

}
