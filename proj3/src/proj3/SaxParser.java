package proj3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SaxParser {
	public final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb_project3_grading";     
    static  String db = "moviedb_project3_grading";
    static  String user = "classta";
    static  String pass = "classta";
    
    Connection conn;
	
    public Map<String, Integer> movieId = new HashMap<String, Integer>();
    public Map<String, Integer> starId = new HashMap<String, Integer>();
    public Map<String, Integer> genreId = new HashMap<String, Integer>();
	
	public SaxParser(){
		try{
			Class.forName(JDBC_DRIVER).newInstance();
	    	conn = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
    	} catch (Exception e) { 
    		e.printStackTrace(); 
    	}
	}
	
	public void run(){
		cache();
		
		System.out.println("Starting actors");
		SaxParserActors actors = new SaxParserActors();
		actors.setStarId(starId);
		actors.run();
		
		cacheStars();
		
		System.out.println("Starting Mains");
		SaxParserMains mains = new SaxParserMains();
		mains.setGenreId(genreId);
		mains.setMovieId(movieId);
		mains.run();
		
		System.out.println("Starting Casts");
		SaxParserCasts casts = new SaxParserCasts();
		casts.setStarId(starId);
		casts.run();
		
		close();
	}
	
	private void close(){
		try{
			conn.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void cache(){
		try{
		cacheGenre();
		cacheStars();
		cacheMovies();
		
		// DELETE LATER
		System.out.println(movieId.size());
		System.out.println(starId.size());
		System.out.println(genreId.size());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cacheGenre() {
		try{
			Statement select = conn.createStatement();
			String query = "select * from genres";
			
			ResultSet rs = select.executeQuery(query);
			
			while(rs.next()){
				genreId.putIfAbsent(rs.getString(2).toLowerCase(), rs.getInt(1));
			}
			
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void cacheStars() {
		try{
			Statement select = conn.createStatement();
			String query = "select * from stars";
			
			ResultSet rs = select.executeQuery(query);
			
			while(rs.next()){
				String first =  rs.getString("first");
				String last = rs.getString("last");
				String stagename;
				
				if(first.length() > 0){
					stagename = rs.getString("first") + " " + rs.getString("last");
				} else {
					stagename = rs.getString("last");
				}
				
				int id = rs.getInt(1);
				
				starId.putIfAbsent(stagename, id);
				
			}
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void cacheMovies() {
		try{
			Statement select = conn.createStatement();
			String query = "select * from movies";
			
			ResultSet rs = select.executeQuery(query);
			
			while(rs.next()){
				String title = rs.getString("title").replaceAll("\\\\", "").replaceAll("'", "\\\\'");
				int id = rs.getInt(1);
				
				movieId.putIfAbsent(title.toLowerCase(), id);
			}
			
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SaxParser sp = new SaxParser();
		sp.run();
	}

}
