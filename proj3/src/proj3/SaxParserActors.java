package proj3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import objects.Star;

public class SaxParserActors extends DefaultHandler {
	final String XMLfile ="stanford-movies/actors63.xml";
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb"; 
    static  String db = "moviedb";
    static  String user = "root";
    static  String pass = "futurama5";
    Connection conn;
    Statement select;
    
    List<Star> stars;
    
    private String tempVal;
    private Star tempStar;
    
    public SaxParserActors(){
    	stars = new ArrayList<Star>();
    	try{
    	conn = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
    	select = conn.createStatement();
    	} catch (Exception e) { e.printStackTrace(); }
    }
    
    public void closeEverything(){
    	try{
    		conn.close();
    		select.close();
    	} catch (Exception e){ e.printStackTrace(); }
    }
	
    public void run(){
    	parseDocument();
    	printList();
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
    
    private void printList(){
    	
    	
    	for(Star s : stars){
    		System.out.println("name: " + s.getFirst() + ' ' + s.getLast());
    		System.out.println("DOB: " + s.getDob() + "\n");
    	}
    	System.out.println("Number of actors: " + stars.size());
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
			//add it to the list
			stars.add(tempStar);
		}else if (qName.equalsIgnoreCase("firstname")) {
			tempStar.setFirst(tempVal);
		}else if (qName.equalsIgnoreCase("familyname")) {
			tempStar.setLast(tempVal);
		}else if (qName.equalsIgnoreCase("dob") && tempVal.length() > 0){
			tempStar.setDob("1-1-" + tempVal);
		}
		
	}
  	
  	public boolean notInDatabase(Star s){
  		String stagename = s.getFirst() + ' ' + s.getLast();
  		String sql = "select from star_stagename where stagename = '" + stagename + "'";
  		
  		return true;
  	}
  	
  	public static void main(String[] args){
  		SaxParserActors s = new SaxParserActors();
  		s.run();
  	}
    
    
    
    
    
    
    
    
    
    
    
    
}
