package javatosql;
import java.sql.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;
/**
 *
 * @author James Griffin And Jonathan Nguyen
 */
public class JavaToSQL {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///moviedb"; 
    static final String user = "root";
    static final String pass = "limeaide";   
    
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
        System.out.print("User: ");
        String user = in.readLine();
        System.out.print("Password: ");
        String pass = in.readLine();
        if (user.equals("user") && pass.equals("pass")) {
            System.out.println("Login Successful...");
            return true;        
        }
        return false;
    }
    private static void consolePrompt() {
        System.out.println("\nMOVIE DATABASE");
        System.out.println("--------------");
        System.out.println("1.) Print out movies featuring a star");
        System.out.println("2.) Insert new star into database");
        System.out.println("3.) Insert customer into database");
        System.out.println("4.) Delete a customer from database");
        System.out.println("5.) Metadata");
        System.out.println("6.) Exit the menu");
        System.out.println("7.) Exit Program");
        System.out.println("8.) Return to login screen");
        System.out.print("\nEnter option: ");
    }
    private static void runConsole() throws Exception {
        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));        
        
        String text = in.readLine();
        int num = Integer.parseInt(text);
        switch(num) {
            case 1:
                printOutMoviesFeaturingStars(in);
                break;
            case 2:
                insertStar(in);
                break;
            case 3:
                insertCustomer(in);
                break;
            case 4:
                DeleteCustomer.deleteCustomer();
                break;
            case 5: 
                Metadata.getDatabaseMetaData();
                break;
            case 6:
                programFlow();
                break;
            case 7:
                System.exit(0);
                break;
            case 8:
            default:
                programFlow();
                break;
        }
    }    
    private static boolean checkIfInserted(String table, String first, String last) {
        if (true) {
            //do sql query to check that it is inserted
            return true;
        }
        return false;        
    } 
    
    private static void select(String first, String last) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
        Class.forName(JDBC_DRIVER);
    	//Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(DB_URL, user, pass);
        //Connection connection = DriverManager.getConnection("jdbc:mysql:///"+,user, pwd);
        stmt = conn.createStatement();
        String sql = "select m.* from stars as s, movies as m, stars_in_movies as sm "
                + "where s.first = '" + first + "' and s.last = '" + last + "' and s.id=sm.star_id and sm.movie_id=m.id";
        System.err.println(sql);
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
        rs.close();
        stmt.close();
        conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    private static void printOutMoviesFeaturingStars(BufferedReader in) throws IOException {
        String[] fullName = nameArr("star's", in);
        select(fullName[0], fullName[1]);
    }
    
    private static void insertStarSQL(String first, String last, String dob, String photo) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();
            String sql = "INSERT INTO star (first, last, dob, photo) VALUES (" 
                    + first + ", " + last + ", " + dob + ", " 
                    + photo + ");";
            stmt.executeQuery(sql);
            
            if (!checkIfInserted("star", first, last)) {
                System.out.println("PROBLEM ENTERING STAR");            
            }
        
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void insertStar(BufferedReader in) throws IOException {
        String[] fullName = nameArr("star's", in);        
        String lastName = fullName.length == 1 ? fullName[1] : fullName[0]; //if star only has last name
        String firstName = fullName.length > 1 ? fullName[0] : "";
        
        System.out.print("\nEnter star's dob (optional): ");
        String dob = in.readLine();
        System.out.print("\nEnter star's photo url (optional): ");
        String photo = in.readLine();
        
        insertStarSQL(firstName, lastName, dob, photo);
    }
    private static void insertCustomerSQL(String first, String last, String cc, String exp, String addr, 
            String email, String password) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();
            //credit card insert FIRST
            String sql = "INSERT INTO creditcards (id, first, last, experation) VALUES (" + 
                    cc + first + last + exp + ");"; 
            stmt.executeQuery(sql);
            
            stmt = conn.createStatement();
            sql = "INSERT INTO customer (first, last, cc, address, email, password) VALUES (" 
                    + first + last + cc + addr + email + password + ");";
            stmt.executeQuery(sql);
            
            if (!checkIfInserted("customer", first, last)) {
                System.out.println("PROBLEM ENTERING CUSTOMER");
            }
            stmt.close();
            conn.close();
            
        }catch (Exception e) {
            e.printStackTrace();
        }        
    }
    private static void insertCustomer(BufferedReader in) throws IOException {
        String cardNumStr = "";
        String[] fullName = nameArr("customer's", in);        
        String lastName = fullName.length == 1 ? fullName[1] : fullName[0]; //if star only has last name
        String firstName = fullName.length > 1 ? fullName[0] : "";
        do {
            //gets credit card
            System.out.print("Enter credit card number: ");
            cardNumStr = in.readLine();        
            if (cardNumStr.equals(""))
                System.out.println("Need credit card info to add Customer");
        } while(cardNumStr.equals(""));
        
        System.out.print("\nEnter credit card expiration date: ");
        String expDateStr = in.readLine();
        System.out.print("\nEnter address: ");
        String addr = in.readLine();
        System.out.print("\nEnter email: ");
        String email = in.readLine();
        System.out.print("\nEnter password: ");
        String pass = in.readLine();
        
        insertCustomerSQL(firstName, lastName, cardNumStr, expDateStr, addr, email, pass);        
    }
    
    private static String[] nameArr(String tableOption, BufferedReader in) throws IOException {        
        System.out.print("Enter "+ tableOption + " full name: ");
        String name = in.readLine();
        System.out.print("\n");
        
        String[] fullName = new String[5];
        int pos = 0;
        StringTokenizer tok = new StringTokenizer(name, " ");
        while (tok.hasMoreTokens()) {
            fullName[pos++] = tok.nextToken();
        }
        return fullName;        
    }
}
