package proj3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import objects.Movie;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SaxParserCasts extends DefaultHandler {
	final String XMLfile ="source_XML_parsing/casts124.xml";

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb_project3_grading"; 
    static  String db = "moviedb_project3_grading";
    static  String user = "classta";
    static  String pass = "classta";
    
    Connection conn;
    Statement select;
    PreparedStatement ps;
    
    Map<String, Integer> starId;
    Set<String> bad;
    
    int count = 0;
    String tempVal;
    String director;
    Integer movie;
    String first;
    String last;
    Integer star;
    boolean filmc;
    
    public SaxParserCasts(){
    	bad = new HashSet<String>();
    	starId = new HashMap<String, Integer>();
    	try{
    		Class.forName(JDBC_DRIVER).newInstance();
    		conn = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
    		conn.setAutoCommit(false);
    		ps = conn.prepareStatement("insert into stars_in_movies(star_id, movie_id) values (?, ?)");
    	} catch (Exception e) { e.printStackTrace(); }
    }
    
    public void setStarId(Map<String, Integer> starId){
    	this.starId = starId;
    }
    
    public void run(){
    	parseDocument();
    	
    	close();
    	
    	for(String s : bad){
    		System.out.println(s);
    	}
    	
    	System.out.println("BAD MOVIES: " + bad.size());
    }
    
    private void close(){
    	try{
    		ps.executeBatch();
    	} catch(Exception e){
    		System.err.println("ERROR IN BATCH");
  			System.err.println(e.getMessage());
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
  			
  		} else if (qName.equalsIgnoreCase("filmc")){
  			filmc = true;
  		}
  	}
    
  	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
  	
  	public void endElement(String uri, String localName, String qName) throws SAXException {

  		if(qName.equalsIgnoreCase("is") && tempVal.length() > 0){
  			int index = tempVal.lastIndexOf('.');
  			if(index == -1)
  				director = tempVal;
  			else
  				director = tempVal.substring(index + 1);
  			
  		} else if(qName.equalsIgnoreCase("t") && filmc){
  			filmc = false;
  			movie = queryMovieId();
  			if(movie == null){
  				System.out.println("Bad movie: " + tempVal + "\t Director: " + director);
  				bad.add(tempVal);
  			}
  			
  		} else if(qName.equalsIgnoreCase("a") && movie != null){
  			processActor();
  		}
		
	}
  	
  	private Integer queryMovieId(){
  		Integer id = null;
  		try{
  			String title = tempVal.replaceAll("\\\\", "").replaceAll("'", "\\\\'");
  			Statement select = conn.createStatement();
  			String sql = "select id from movies where director like '%" + director + "%' and title = '" + title + "';";
  			
  			ResultSet rs = select.executeQuery(sql);
  			
  			if(rs.next()){
  				id = rs.getInt(1);
  			}
  			
  			rs.close();
  			select.close();
  		} catch (Exception e){
  			e.printStackTrace();
  		}
  		
  		return id;
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
					System.out.println("CAST BATCH");
				}
					
			} catch (Exception e){
				System.err.println("ERROR IN BATCH");
	  			System.err.println(e.getMessage());
			}
		} else if (star == null){
			//System.out.println("star == null");
		}
  	}    
    
}
