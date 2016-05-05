package proj3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;


import objects.Star;

public class SaxParserActors extends DefaultHandler {
	final String XMLfile ="source_XML_parsing/actors63.xml";
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb_project3_grading"; 
    static  String db = "moviedb_project3_grading";
    static  String user = "classta";
    static  String pass = "classta";
    Connection conn;
    Statement select;
    PreparedStatement ps;
    
    List<Star> stars;
    Map<String, Integer> starId;
    
    private String tempVal;
    private Star tempStar;
    int count = 0;
    
    public SaxParserActors(){
		

    	stars = new ArrayList<Star>();
    	starId = new HashMap<String, Integer>();
    	try{
    		Class.forName(JDBC_DRIVER).newInstance();
    		conn = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
    		conn.setAutoCommit(false);
    		ps = conn.prepareStatement("insert into stars(first, last, dob) values (?, ?, ?)");
    	} catch (Exception e) { e.printStackTrace(); }
    }
    
    public void close(){
    	try{
    		ps.executeBatch();
    		ps.close();
    		conn.commit();
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
  		if(qName.equalsIgnoreCase("Actor")) {
  			//create a new instance of employee
  			tempStar = new Star();
  		}
  	}
    
  	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
  	
  	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("actor") && notInDatabase(tempStar)) {
			addStarToDatabase(tempStar);
			
		} else if(qName.equalsIgnoreCase("stagename") && tempVal.length() > 0) {
			processStageName(tempStar, tempVal);
			
		}else if (qName.equalsIgnoreCase("dob") && tempVal.length() > 0 && tempVal.matches("[-+]?\\d*\\.?\\d+")){
			tempStar.setDob(tempVal + "-1-1");
		}
		
	}
  	
  	private void processStageName(Star s, String stagename){
  		String[] name = stagename.split("\\s+" , 2);
  		s.setFirst("");
		s.setLast("");
  		switch(name.length){
  			case 2:
				s.setFirst(name[0]);
				s.setLast(name[1]);
				break;
  			case 1:
  				s.setLast(name[0]);
  				break;
  			default:
  				break;
  		}
  	}
  	
  	private void addStarToDatabase(Star s){
  		final int limit = 1000;
  		
  		try {
			ps.setString(1, s.getFirst());
			ps.setString(2, s.getLast());
			ps.setString(3, s.getDob());
			ps.addBatch();
			
			if(++count % limit == 0){
				ps.executeBatch();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
  	}
  	
  	public boolean notInDatabase(Star s){
  		String stagename = s.getFirst() + ' ' + s.getLast();
  		return !starId.containsKey(stagename);
  	}
  	
  	public void setStarId(Map<String, Integer> starId){
  		this.starId = starId;
  	}
  	
  	public Map<String, Integer> getStarId(){
  		return this.starId;
  	}
  	
  	public void run(){
    	parseDocument();
    	close();
    }
  	
  	public static void main(String[] args){
  		SaxParserActors s = new SaxParserActors();
  		s.run();
  		
  	}
    
    
    
    
    
    
    
    
    
    
    
    
}
