package proj3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import objects.Movie;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SaxParserCasts extends DefaultHandler {
	final String XMLfile ="stanford-movies/casts124.xml";

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb"; 
    static  String db = "moviedb";
    static  String user = "root";
    static  String pass = "futurama5";
    Connection conn;
    Statement select;
    PreparedStatement ps;
    
    Map<String, Integer> movieId;
    Map<String, Integer> starId;
    
    int count = 0;
    String tempVal;
    String director;
    Integer movie;
    String first;
    String last;
    Integer star;
    
    public SaxParserCasts(){
    	movieId = new HashMap<String, Integer>();
    	starId = new HashMap<String, Integer>();
    	try{
    		Class.forName(JDBC_DRIVER).newInstance();
    		conn = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
    		conn.setAutoCommit(false);
    		ps = conn.prepareStatement("insert into stars_in_movies(star_id, movie_id) values (?, ?)");
    	} catch (Exception e) { e.printStackTrace(); }
    }
    
    public void setMovieId(Map<String, Integer> movieId){
    	this.movieId = movieId;
    }
    
    public void setStarId(Map<String, Integer> starId){
    	this.starId = starId;
    }
    
    public void run(){
    	parseDocument();
    	
    	close();
    }
    
    private void close(){
    	try{
    		ps.executeBatch();
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	try{
    		conn.commit();
    		ps.close();
    		conn.close();
    	} catch (Exception e){ 
    		e.printStackTrace();
    	}
    }
    
    private void parseDocument(){
    	
    	//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(XMLfile, this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
    }
    
    //Event Handlers
  	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
  		//reset
  		tempVal = "";
  		if(qName.equalsIgnoreCase("dirfilms") || qName.equalsIgnoreCase("is")) {
  			director = "";
  			
  		}
  	}
    
  	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
  	
  	public void endElement(String uri, String localName, String qName) throws SAXException {

  		if(qName.equalsIgnoreCase("is") && tempVal.length() > 0){
  			director = tempVal;
  			
  		} else if(qName.equalsIgnoreCase("t")){
  			queryMovieId();
  			movie = movieId.get(tempVal);
  			
  		} else if(qName.equalsIgnoreCase("a")){
  			processActor();
  		}
		
	}
  	
  	private void queryMovieId(){
  		
  	}
  	
  	private void processActor(){
  		star = starId.get(tempVal);
		if(star != null && movie != null){
			try{
				ps.setInt(1, star);
				ps.setInt(2, movie);
				ps.addBatch();
				
				if(++count % 1000 == 0){
					ps.executeBatch();
				}
					
			} catch (Exception e){
				e.printStackTrace();
			}
		} else if (star == null){
			//System.out.println("star == null");
		}
  	}    
    
}
