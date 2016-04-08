package test;

import objects.*;

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
   
        //moviesInGenre(48008, stmt);
        
        userLogin("a@.com","a2", stmt);
        
        stmt.close();
        conn.close();
	}
	
	public static Customer userLogin(String username, String password, Statement select) throws Exception{
		Customer user = null;
		
		String query = "select * from customers where email='" + username + "' and password='" + password + "'";
		
		ResultSet rs = select.executeQuery(query);
		if(!rs.next()){
			System.out.println("HI");
			return null;
		}
		
		System.out.println(query);
		
		return user;
	}
	
	public static Set<Movie> moviesInGenre(int id, Statement select) throws Exception{
		Set<Movie> movies = new HashSet<Movie>();
		String query = "select movies.*, genres.name from genres, genres_in_movies, movies where "
				     + id + "=genres_in_movies.genre_id and genres_in_movies.movie_id = movies.id and "
				     + id + "=genres.id";
		print(query);
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Movie m = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
								rs.getString(6), rs.getString(7) );
			movies.add(m);
			print(m);
		}
		
		rs.close();
		return movies;
	}
	
	public static Set<Movie> moviesInGenre(String genre, Statement select) throws Exception{
		Set<Movie> movies = new HashSet<Movie>();
		String query = "select movies.* from movies, genres, genres_in_movies where "
				     + "'" + genre +"' =genres.name and genres.id = genres_in_movies.genre_id "
				     + "and genres_in_movies.movie_id = movies.id";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Movie m = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
								rs.getString(6), genre );
			movies.add(m);
			print(m);
		}
		
		
		rs.close();
		return movies;
	}
	
	public static Set<String> starsInMovie(int id, Statement select) throws Exception{
		// TODO return Stars instead of String
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
		// TODO return set<Stars> instead of String
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
