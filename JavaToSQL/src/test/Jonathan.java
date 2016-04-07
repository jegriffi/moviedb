package test;

import java.sql.*;
import java.util.*;

public class Jonathan {
	
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb"; 
    static  String db = "moviedb";
    static  String user = "root";
    static  String pass = "pass";   
    
    public static void print(Object o){
    	System.out.println(o);
    }

	public static void main(String[] args) throws Exception {
		
		Class.forName(JDBC_DRIVER).newInstance();
		Connection conn = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
		
		
        conn = DriverManager.getConnection(DB_URL, user, pass);            
        Statement stmt = conn.createStatement();
        
        //starsInMovie(156006, stmt);
        //System.out.println(starsInMovie("Jurassic Park", stmt));
        //print(starsInMovie("Jurassic Park", stmt));
        
        moviesInGenre("advanture", stmt);
        
        stmt.close();
        conn.close();

	}
	
	public static Set<String> moviesInGenre(int id, Statement select) throws Exception{
		Set<String> movies = new HashSet<String>();
		String query = "select * from genres_in_movies, movies where "
				     + id + "=genres_in_movies.genre_id and genres_in_movies.movie_id = movies.id";
		System.out.print(query);
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			movies.add(rs.getString("title"));
		}
		
		
		return null;
	}
	
	public static Set<String> moviesInGenre(String genre, Statement select) throws Exception{
		Set<String> movies = new HashSet<String>();
		String query = "select * from movies, genres, genres_in_movies where "
				     + "'" + genre +"' =genres.name and genres.id = genres_in_movies.genre_id "
				     + "and genres_in_movies.movie_id = movies.id";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			movies.add(rs.getString("title"));
		}
		
		return movies;
	}
	
	public static Set<String> starsInMovie(int id, Statement select) throws Exception{
		Set<String> stars = new HashSet<String>();
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
	
	public static Set<String> starsInMovie(String title, Statement select) throws Exception{
		Set<String> stars = new HashSet<String>();
		String query = "select stars.* from stars, stars_in_movies, movies "
					 + "where '" + title + "' = movies.title and "
					 + "movies.id = stars_in_movies.movie_id and "
					 + "stars_in_movies.star_id = stars.id";

		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			String star = rs.getString("first") + " " + rs.getString("last");
			stars.add(star);
		}
		rs.close();
		return stars;
	}
	
	

}
