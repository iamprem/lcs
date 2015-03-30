import java.util.Arrays;

/*
 * Computing Normalized edit distance using dynamic programming
 * This algorithm doesn't store an entire table, instead it remember only
 * the last two rows computed in the table.
 */

public class EditDistance {
	
	public static float findNormalizedEditDistance(String s1, String s2){
		
		int[] topRow = new int[s1.length()+1];
		int[] bottomRow = new int[s1.length()+1];
		int topDownIndex = 0;
		float normalizedEditDistance = 0.0F;
		
		// Initialize topRow once
		for (int i = 0; i < topRow.length; i++) {
			topRow[i] = i;
		}
		
		//Method call to compute the bottomRow - Recursive Method
		computeNextRow(s1.toCharArray(), s2.toCharArray(), topRow, topDownIndex, bottomRow);
		
		// Print the last row
		//System.out.println(Arrays.toString(bottomRow));
		
		// Last value of the last row is the edit distance
		// Computing Normalized edit distance using the formula dN = (|s1|+|s2|-d)/(|s1|+|s2|)
		normalizedEditDistance = (float)(s1.length()+s2.length()-bottomRow[bottomRow.length-1])/(float)(s1.length()+s2.length());
		
		return normalizedEditDistance;
	}
	
	private static void computeNextRow(char[] s1, char[] s2, int[] topRow, int topDownIndex, int[] bottomRow){
		
		
		// Computing the current row using the values of previous row
		bottomRow[0] = topDownIndex+1;
		for (int i = 1; i < bottomRow.length; i++) {
			
			if (s2[topDownIndex] == s1[i-1]) {
				bottomRow[i] = topRow[i-1];
			}  else {
				bottomRow[i] = (bottomRow[i-1] < topRow[i])? bottomRow[i-1] + 1 : topRow[i] + 1;
			}
		}
		
		// [***Exit Check***]
		//Check for the 'topDownIndex' reached the last symbol of the top-down string
		if(topDownIndex == s2.length - 1){
			return;
		}
		
		//Since arrays are passed by reference, copying the bottom row to top row and making bottom row to empty
		for (int i = 0; i < topRow.length; i++) {
			topRow[i] = bottomRow[i];
		}
		Arrays.fill(bottomRow, 0);
		
		//Tail recursion will takes place until it matched the exit check
		computeNextRow(s1, s2, topRow, topDownIndex+1, bottomRow);
	}
	
}