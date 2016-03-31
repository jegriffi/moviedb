package javatosql;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 *
 * @author James Griffin And Johnathan Nguyen
 */
public class JavaToSQL {
    
    
    public static void main(String[] args) {
        consolePrompt();
        while(true) {
            try {
                runConsole(); 
            }
            catch(IOException e) {
                System.err.printf("\n***IOException error***\n");
            }
        }        
    }
    private static void consolePrompt() {
        System.out.println("MOVIE DATABASE");
        System.out.println("--------------\n");
        System.out.println("1.) Print out movies featuring a star");
        System.out.println("2.) Insert new star into database");
        System.out.println("3.) Insert customer into database");
        System.out.println("4.) Delete a customer from database");
        System.out.println("5.) Enter valid SQL command");
        System.out.println("6.) Exit the menu");
        System.out.println("7.) Exit Program");
    }
    private static void runConsole() throws IOException {
        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));        
        
        consolePrompt();
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
                    deleteCustomer(in);
                break;
            case 5:
                provideMetadata();
                break;
            case 6:
                //exitMenu();
                break;
            case 7:
                //exitProgram();
                break;
            default:
                break;
        }
    }
    
    private static void printOutMoviesFeaturingStars(BufferedReader in) {
        
    }
    private static void insertStar(BufferedReader in) throws IOException {
        String[] fullName = nameArr(in);        
        String lastName = fullName.length == 1 ? fullName[1] : fullName[0]; //if star only has last name
        String firstName = fullName.length > 1 ? fullName[0] : "";
        
    }
    private static void insertCustomer(BufferedReader in) throws IOException {
        String[] fullName = nameArr(in);        
        String lastName = fullName.length == 1 ? fullName[1] : fullName[0]; //if star only has last name
        String firstName = fullName.length > 1 ? fullName[0] : "";
        
        //gets credit card
        System.out.print("Enter credit card info");
        String cardInfoStr = in.readLine();
        int cardInfoInt = Integer.parseInt(cardInfoStr);
    }
    private static String[] nameArr(BufferedReader in) throws IOException {
        System.out.print("Enter Star's full name: ");
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
    private static void deleteCustomer(BufferedReader in) throws IOException {
        
    }
    private static void provideMetadata() {
        
    }
}
