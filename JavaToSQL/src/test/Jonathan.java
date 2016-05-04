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
        
        Map<Integer, Movie> movies_id = moviesFromDatabase(stmt);
        Map<Integer, Star> stars_id = starsFromDatabase(stmt);
        Map<Integer, Genre> genres_id = genresFromDatabase(stmt);
        movie_star(movies_id, stars_id, stmt);
        movie_genre(movies_id, genres_id, stmt);
        
        Map<String, Star> stars_first = starsByName(stars_id);
        
        System.out.println(stars_first.get("Jude Law"));
        
        
        
        stmt.close();
        conn.close();
        
	}
	
	public static Map<String, Star> starsByName(Map<Integer, Star> stars){
		Map<String, Star> toReturn = new HashMap<String, Star>();
		
		for(Star s : stars.values()){
			String key = s.getFirst() + " " + s.getLast();
			toReturn.put(s.getFirst() + " " + s.getLast(), s);
		}
		
		return toReturn;
	}
	
	public static Map<String, Genre> genresByName(Map<Integer, Genre> genres){
		Map<String, Genre> toReturn = new HashMap<String, Genre>();
		
		for(Genre g : genres.values()){
			toReturn.put(g.getGenre(), g);
		}
		
		return toReturn;
	}
	
	public static Map<String, Movie> moviesByTitle(Map<Integer, Movie> movies){
		Map<String, Movie> toReturn = new HashMap<String, Movie>();
		
		for(Movie m : movies.values()){
			toReturn.put(m.getTitle(), m);
		}
		
		return toReturn;
	}
	
	public static void movie_star(Map<Integer, Movie> movies, Map<Integer, Star> stars, Statement select ) throws Exception{
		String query = "select * from stars_in_movies";
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			int star_id = rs.getInt(1);
			int movie_id = rs.getInt(2);
			
			Star s = stars.get(star_id);
			Movie m = movies.get(movie_id);
			
			s.addMovie(m);
			m.addStar(s);
		}
		
		rs.close();
	}
	
	public static void movie_genre(Map<Integer, Movie> movies, Map<Integer, Genre> genres, Statement select) throws Exception{
		String query = "select * from genres_in_movies";
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			int genre_id = rs.getInt(1);
			int movie_id = rs.getInt(2);
			
			Genre g = genres.get(genre_id);
			Movie m = movies.get(movie_id);
			
			g.addMovie(m);
			m.addGenre(g);
		}
		
		rs.close();
	}
	
	public static Map<Integer, Movie> moviesFromDatabase(Statement select) throws Exception{
		Map<Integer, Movie> movies = new HashMap<Integer, Movie>();
		String query = "select * from movies";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Movie m = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
					rs.getString(6));
			movies.put(m.getId(), m);
		}
		
		rs.close();
		return movies;
	}
	
	public static Map<Integer, Star> starsFromDatabase(Statement select) throws Exception{
		Map<Integer, Star> stars = new HashMap<Integer, Star>();
		
		String query = "select * from stars";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Star s = new Star(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			stars.put(s.getId(), s);
		}
		
		rs.close();
		return stars;
	}
	
	public static Map<Integer, Genre> genresFromDatabase(Statement select) throws Exception{
		Map<Integer, Genre> genres = new HashMap<Integer, Genre>();
		
		String query = "select * from genres";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Genre g = new Genre(rs.getInt(1), rs.getString(2));
			genres.put(g.getId(), g);
		}
		
		rs.close();
		return genres;
	}
	
	public static Set<Movie> beginsWithLetter(char letter, Statement select) throws Exception {
		Set<Movie> movies = new HashSet<Movie>();
		//String query = "select movies.* from movies, genres_in_movies, genres where title like '" + letter + "%'"
		//			   + "and movies.id = genres_in_movies.movie_id and genres_in_movies.genre_id = genres.id";
		String query = "select * from movies where title like '" + letter + "%'";
		
		ResultSet rs = select.executeQuery(query);
		
		while(rs.next()){
			Movie m = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
								rs.getString(6));
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
			Movie m = null;
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
			Movie m = null;
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
