package javatosql;
import java.sql.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import objects.Star;
/**
 *
 * @author James Griffin And Jonathan Nguyen
 */
public class JavaToSQL {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb"; 
    static  String db = "moviedb";
    static  String user = "root";
    static  String pass = "futurama5";   
    
    public static void main(String[] args) throws Exception {
        programFlow();
    }
    
    private static void programFlow() throws Exception {
        boolean key; 
        do {
            key = loginScreen();
        } while (!key);

        while(true) {
            consolePrompt();
            runConsole();
        } 
    }
    private static boolean loginScreen() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Would you like to login: (y/n): ");
        String exit = in.readLine().toLowerCase();
        if(exit.equals("n"))
        	System.exit(0);
        
        System.out.print("User: ");
        user = in.readLine();
        System.out.print("Password: ");
        pass = in.readLine();
        System.out.print("Database: ");
        db = in.readLine();
        
        Connection connection = null;
        boolean valid = false;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = DriverManager.getConnection("jdbc:mysql:///"+db,user, pass);
        	valid = true;
        } catch (Exception e) {
        	e.printStackTrace();
        	//System.out.println(e.getLocalizedMessage());
        } finally { try {connection.close(); } catch  (Exception e2) {  } }
        return valid;
    }
    
    private static void consolePrompt() {
        System.out.println("\nMOVIE DATABASE");
        System.out.println("--------------");
        System.out.println("1.) Print out movies featuring a star");
        System.out.println("2.) Insert new star into database");
        System.out.println("3.) Insert customer into database");
        System.out.println("4.) Delete a customer from database");
        System.out.println("5.) Metadata");
        System.out.println("6.) Enter SQL command");
        System.out.println("7.) Exit the menu");
        System.out.println("8.) Exit Program");
        System.out.print("\nEnter option: ");
    }
    private static void runConsole() throws Exception {
        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));        
        
        String text = in.readLine();
        
        int num = -1;
        if(Character.isDigit(text.charAt(0)))
        		num = Integer.parseInt(text);
        switch(num) {
            case 1:
                printOutMoviesFeaturingStars(in);
                break;
            case 2:
                InsertStar.insertStar(db, user, pass);
                break;
            case 3:
                InsertCustomer.insertCustomer(db, user, pass);
                break;
            case 4:
                DeleteCustomer.deleteCustomer(db, user, pass);
                break;
            case 5: 
                Metadata.getDatabaseMetaData(db, user, pass);
                break;
            case 7:
                programFlow();
                break;
            case 8:
                System.exit(0);
                break;
            case 6:
            	SQL.query(db, user, pass);
                break;
            case -1:
            	break;
            default:
                programFlow();
                break;
        }
    }    
    private static boolean checkIfInserted(String table, String first, String last) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, user, pass);            
            stmt = conn.createStatement();
            String sql = "SELECT * from " + table + " WHERE first = '" + first 
                + "' AND last = '" + last + "';";
            rs = stmt.executeQuery(sql);
            if (rs.absolute(1)) {
                System.out.println(first + " " + last + " inserted into " + table);
                conn.close();
                stmt.close();
                rs.close();
                return true;
            }
            System.out.println("ERROR INSERTING " + first + " " + last + "...");
            
        } catch(Exception e) {
        	System.out.println(e.getLocalizedMessage());
        } finally { try { conn.close(); stmt.close(); rs.close(); } catch (Exception e2) { System.out.println(e2.getLocalizedMessage()); } }
        return false;        
    } 
    
    public static List<Star> selectStars() {
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	List<Star> result = new ArrayList<>();
    	try {
    		Class.forName(JDBC_DRIVER).newInstance();
    		conn = DriverManager.getConnection(DB_URL, user, pass);
    		stmt = conn.createStatement();
    		String sql = "select * from stars limit 10;";
    		rs = stmt.executeQuery(sql);    		
    		
    		while (rs.next()) {
    			result.add(new Star(Integer.parseInt(rs.getString(1)), rs.getString(2)
    					, rs.getString(3), rs.getString(4), rs.getString(5)));
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
    
    private static void selectByName(String first, String last) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
        Class.forName(JDBC_DRIVER).newInstance();
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql = "select m.* from stars as s, movies as m, stars_in_movies as sm "
                + "where s.first like '%" + first + "%' and s.last like '%" + last + "%' and s.id=sm.star_id and sm.movie_id=m.id";
        
		rs = stmt.executeQuery(sql);
		ResultSetMetaData metadata = rs.getMetaData();
		List<String> columnNames = new ArrayList<String>();
		int i;
		for(i = 1; i <= metadata.getColumnCount(); ++i){
			columnNames.add(metadata.getColumnName(i));
		}
		
		i = 0;
		while(i++ < 25 && rs.next()){
			int size = metadata.getColumnCount();
			for(int k = 1; k <= size; ++k){
				System.out.println(columnNames.get(k-1) + ": " + rs.getString(k));
			}
			System.out.println();
		}
        
        
//        System.err.println(sql);
//        rs = stmt.executeQuery(sql);
//
//        while(rs.next()) {
//            List<String> data = new ArrayList<>();
//            data.add(rs.getString("id"));
//            data.add(rs.getString("title"));
//            data.add(rs.getString("year"));
//            data.add(rs.getString("director"));
//            data.add(rs.getString("banner"));
//            data.add(rs.getString("trailer"));
//
//            System.out.println(data);
//        }
        } catch (Exception e) {
        	System.out.println(e.getLocalizedMessage());
        } finally { try { conn.close(); stmt.close(); rs.close(); } catch (Exception e2) { System.out.println(e2.getLocalizedMessage()); } }        
    }
    
    private static void selectById(String id) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();
            String sql = "select m.* from stars as s, movies as m, stars_in_movies as sm "
                + "where s.id = " + id + " and s.id=sm.star_id and sm.movie_id=m.id";
            System.err.println(sql);
            
    		rs = stmt.executeQuery(sql);
    		ResultSetMetaData metadata = rs.getMetaData();
    		List<String> columnNames = new ArrayList<String>();
    		int i;
    		for(i = 1; i <= metadata.getColumnCount(); ++i){
    			columnNames.add(metadata.getColumnName(i));
    		}
    		
    		i = 0;
    		while(i++ < 25 && rs.next()){
    			int size = metadata.getColumnCount();
    			for(int k = 1; k <= size; ++k){
    				System.out.println(columnNames.get(k-1) + ": " + rs.getString(k));
    			}
    			System.out.println();
    		}
            
            /*
            rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                List<String> data = new ArrayList<>();
                data.add(rs.getString("id"));
                data.add(rs.getString("title"));
                data.add(rs.getString("year"));
                data.add(rs.getString("director"));
                data.add(rs.getString("banner"));
                data.add(rs.getString("trailer"));

                System.out.println(data);
            }
            */
        }catch (Exception e) {
        	System.out.println(e.getLocalizedMessage());
        } finally { try { conn.close(); stmt.close(); rs.close(); } catch (Exception e2) { System.out.println(e2.getLocalizedMessage()); } }
    }

    private static void printOutMoviesFeaturingStars(BufferedReader in) throws Exception {
        System.out.print("Query star by name or id? (name/id): ");
        String ans = in.readLine().trim().toLowerCase();
        System.out.println();
        if (ans.equals("name")) {
        	String first = Helper.prompt("first name", in);
        	String last = Helper.prompt("last name", in);
            selectByName(first, last);
        }
        else if (ans.equals("id")){
            System.out.print("Enter star's ID: ");
            ans = in.readLine().trim();
            selectById(ans);
        } else {
        	System.out.println("Invalid input");
        	printOutMoviesFeaturingStars(in);
        }
    }
}
