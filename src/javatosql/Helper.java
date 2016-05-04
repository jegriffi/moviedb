package javatosql;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class Helper {
	
	public static void main (String[] args){
		Scanner in = new Scanner(System.in);
		//getName(in);
		getDate(in);
		
	}
	
	public static  String[] nameArr(String tableOption, Scanner in){
		System.out.print("Enter " + tableOption + " full name: ");
		String input = in.nextLine();
		int i = input.lastIndexOf(" ");
		if (-1 == i) i = input.length();
		String[] name = {input.substring(0,i).trim(), input.substring(i).trim()};
		return name;
	}
	
    public static String[] nameArr(String tableOption, BufferedReader in) throws IOException {        
        System.out.print("Enter "+ tableOption + " full name: ");
        String input = in.readLine();
        
		int i = input.lastIndexOf(" ");
		if (-1 == i) i = input.length();
		String[] name = {input.substring(0,i).trim(), input.substring(i).trim()};
		return name;
        
		/*
		 * JAMES
        System.out.print("\n");
        
        
        
        String[] fullName = new String[5];
        int pos = 0;
        StringTokenizer tok = new StringTokenizer(name, " ");
        while (tok.hasMoreTokens()) {
            fullName[pos++] = tok.nextToken();
        }
        return fullName;  
        */      
    }
	
	public static String getDate(Scanner in){
		// NEED TO DO CHECKS HERE
		
		System.out.print("Year: ");
		String year = in.nextLine().split("\\s+")[0];
		System.out.print("Month: ");
		String month = in.nextLine().split("\\s+")[0];
		System.out.print("Day: ");
		String day = in.nextLine().split("\\s+")[0];
		
		return year + "-" + month + "-" + day;
	}
	
	public static String prompt(String attr, Scanner in){
		System.out.print("Enter " + attr + ": ");
		String input = in.nextLine();
		return input;
	}
	
	public static String prompt(String attr, BufferedReader in) throws Exception{
		System.out.print("Enter " + attr + ": ");
		String input = in.readLine();
		return input;
	}
	
	
	

}
