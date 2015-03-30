import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Prem kumar Murgesan
 * MainClass contains main method for this project.
 * Inputs are given from command line arguments. 
 * Sample Input: 
 * java MainClass <filename1> <filename2>
 * <filename1> = filename1.txt
 * <filename2> = filename2.txt
 * These 2 files should have complete strings with no newline or space character
 */

public class MainClass {
	
	public static void main(String[] args) throws IOException {
		
		String file1 = args[0];
		String file2 = args[1];
		BufferedReader fileReader1 = new BufferedReader(new FileReader(file1));
		BufferedReader fileReader2 = new BufferedReader(new FileReader(file2));
		String s1 = fileReader1.readLine();
		String s2 = fileReader2.readLine();
		fileReader1.close();
		fileReader2.close();
		
		
		//Objective 1 - Normalized Edit Distance
		System.out.println("Normalized Edit Distance: "+EditDistance.findNormalizedEditDistance(s1.toLowerCase(), s2.toLowerCase()));
		
		//Objective 2 - LCS using Non Linear memory
		System.out.println("Longest Common Subsequence(Non-Linear): "+LCS.NonLinear.computeLCS(s1, s2));
		
		//Objective 3 - LCS using Linear memory
		System.out.println("Longest Common Subsequence(Linear): "+LCS.Linear.computeLCS(s1, s2));
		
		// Sample Inputs
		//String s1 = "agtacgtcat";
		//String s2 = "gtatcgtat";
		
	}
	
}