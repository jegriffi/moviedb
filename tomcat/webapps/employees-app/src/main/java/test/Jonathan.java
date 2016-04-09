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
        
        //userLogin("a@email.com","a2", stmt);
        //beginsWithLetter('b', stmt);
        
        stmt.close();
        conn.close();
        
	}
	
	public static Set<Movie> beginsWithLetter(char letter, Statement select) throws Exception {
		Set<Movie> movies = new HashSet<Movie>();
		String query = "select movies.*, genres.name from movies, genres_in_movies, genres where title like '" + letter + "%'"
					   + "and movies.id = genres_in_movies.movie_id and genres_in_movies.genre_id = genres.id";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Movie m = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
								rs.getString(6), rs.getString(7) );
			movies.add(m);
		}
		
		rs.close();
		return movies;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param select
	 * @return null if no user found, returns Customer class if user is found
	 * @throws Exception
	 */
	public static Customer userLogin(String username, String password, Statement select) throws Exception{
		
		String query = "select * from customers where email='" + username + "' and password='" + password + "'";
		print(query);
		ResultSet rs = select.executeQuery(query);
		if(!rs.next())
			return null;
		
		Customer user = new Customer(rs.getInt(1), rs.getString(2), 
									 rs.getString(3), rs.getString(4), 
									 rs.getString(5), rs.getString(6), 
									 rs.getString(7));
		
		rs.close();
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
		}
		
		rs.close();
		return movies;
	}
	
	public static Set<Star> starsInMovie(int id, Statement select) throws Exception{
		Set<Star> stars = new HashSet<Star>();
		String query = "select stars.* from stars, stars_in_movies "
					 + "where " + id + " = stars_in_movies.movie_id and "
					 + "stars_in_movies.star_id = stars.id";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Star star = new Star(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			stars.add(star);
		}
		
		rs.close();
		return stars;
	}
	
	public static Set<Star> starsInMovie(String title, Statement select) throws Exception{
		Set<Star> stars = new HashSet<Star>();
		String query = "select stars.* from stars, stars_in_movies, movies "
					 + "where '" + title + "' = movies.title and "
					 + "movies.id = stars_in_movies.movie_id and "
					 + "stars_in_movies.star_id = stars.id";

		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Star star = new Star(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			stars.add(star);
		}
		
		rs.close();
		return stars;
	}
	
	

}
