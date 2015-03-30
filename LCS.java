import java.util.Arrays;

public class LCS {

	// LCS using Non-Linear memory algorithm
	static class NonLinear {

		public static String computeLCS(String s1, String s2) {

			// Calculating the edit distance table and storing in 2-D array
			int[][] edTable = new int[s2.length() + 1][s1.length() + 1];
			computeEditDistanceTable(s1, s2, edTable);

			// Stitch the Longest common subsequence by Backtracking
			return computeLCSUtil(s1.toCharArray(), s2.toCharArray(), edTable);
		}

		private static void computeEditDistanceTable(String s1, String s2,
				int[][] edTable) {

			// Initial Check for empty strings as input
			if (s1.length() == 0 || s2.length() == 0) {
				// Print Normalized edit distance
				// System.out.println("Normalized Edit Distance is: "
				// + (float)(s1.length()+s2.length()-Math.max(s1.length(),
				// s2.length()))/(float)(s1.length()+s2.length()) );
				return;
			}

			int topDownIndex = 0;

			// Initializing the first row of the table
			for (int i = 0; i < edTable[0].length; i++) {
				edTable[0][i] = i;
			}

			// This call is to build the remaining rows of the table using the
			// buildTable() method
			buildTable(s1.toCharArray(), s2.toCharArray(), topDownIndex,
					edTable);

			// Print the computed table
			//printTable(edTable);
			// Print Normalized edit distance
			// System.out.println("Normalized Edit Distance is: "
			// +
			// (float)(s1.length()+s2.length()-edTable[s2.length()][s1.length()])/(float)(s1.length()+s2.length()));

		}

		private static void buildTable(char[] s1, char[] s2, int topDownIndex,
				int[][] edTable) {

			// Using the values of previous row and left cell of the current
			// row, computing the remaining values of current row
			edTable[topDownIndex + 1][0] = topDownIndex + 1;
			for (int i = 1; i < edTable[topDownIndex + 1].length; i++) {

				if (s2[topDownIndex] == s1[i - 1]) {
					edTable[topDownIndex + 1][i] = edTable[topDownIndex][i - 1];
				} else {
					edTable[topDownIndex + 1][i] = (edTable[topDownIndex + 1][i - 1] < edTable[topDownIndex][i]) ? edTable[topDownIndex + 1][i - 1] + 1
							: edTable[topDownIndex][i] + 1;
				}
			}

			// [***EXIT CHECK***]
			// Check for the 'topDownIndex' reached the last symbol of the
			// top-down string
			if (topDownIndex == s2.length - 1) {
				return;
			}

			// Tail recursion to build the next row
			buildTable(s1, s2, topDownIndex + 1, edTable);
		}

		private static String computeLCSUtil(char[] s1, char[] s2,
				int[][] edTable) {

			// Initial check of empty strings in given inputs
			if (s1.length == 0 || s2.length == 0) {
				return "Empty String!!";
			}

			int row = s2.length;
			int column = s1.length;
			String lcs = "";

			// Backtracking the edTable to find the Longest common subsequence
			for (int i = 0; i < s1.length + s2.length + 2 && row > 0
					&& column > 0; i++) {

				if (s1[column - 1] == s2[row - 1]) {
					lcs = String.valueOf(s1[column - 1]) + lcs;
					row--;
					column--;
				} else {
					if (edTable[row][column - 1] <= edTable[row - 1][column]) {
						column--;
					} else {
						row--;
					}
				}
			}

			return lcs;

		}

		private static void printTable(int[][] edTable) {

			for (int i = 0; i < edTable.length; i++) {
				for (int j = 0; j < edTable[i].length; j++) {
					System.out.print(edTable[i][j] + " ");
				}
				System.out.println();
			}

		}

	}

	// LCS using Linear memory algorithm
	static class Linear {

		// Reason for using this static variable 'finalLCS' instead of passing
		// as argument is,
		// to avoid stack overflow error on recursive calls. Because java keep
		// track of recursive calls in a
		// stack, when the string length is very large, there will be so many
		// recursive calls and eventually
		// that will cause the stack overflow error.
		// JVM does not provide tail calls in its byte code, so the stack
		// implementation

		private static String finalLCS = "";

		public static String computeLCS(String s1, String s2) {

			computeLCSUtil(s1, s2);
			return finalLCS;

		}

		private static void computeLCSUtil(String s1, String s2) {

			// Check for the base case, whether the divided table has either one
			// row or one column
			if (s1.length() == 1) {

				for (int i = 0; i < s2.length(); i++) {
					if (s1.toCharArray()[0] == s2.toCharArray()[i]) {
						finalLCS = finalLCS + s1;
						return;
					}
				}
			} else if (s2.length() == 1) {

				for (int i = 0; i < s1.length(); i++) {
					if (s2.toCharArray()[0] == s1.toCharArray()[i]) {
						finalLCS = finalLCS + s2;
						return;
					}
				}
			}
			// Else
			// Compute the middle two rows
			// Find the minimum edit distance pair and make a vertical and
			// horizontal split of the table
			// Discard the 1st and 3rd quadrant of the table and repeat this
			// process until it reaches the base case
			else {

				int[] topRow = new int[s1.length() + 1];
				int[] forwardMiddleRow = new int[s1.length() + 1];
				int[] reverseMiddleRow = new int[s1.length() + 1];
				int topDownIndex = 0;

				// Initialize topRow once
				for (int i = 0; i < topRow.length; i++) {
					topRow[i] = i;
				}

				// Forward Middle Row
				computeMiddleRows(s1.toCharArray(),
						s2.substring(0, s2.length() / 2).toCharArray(), topRow,
						topDownIndex, forwardMiddleRow);

				// Reverse Middle Row
				int[] tempReverseMiddleRow = new int[s1.length() + 1];
				computeMiddleRows(new StringBuilder(s1).reverse().toString()
						.toCharArray(),
						new StringBuilder(s2.substring(s2.length() / 2))
								.reverse().toString().toCharArray(), topRow,
						topDownIndex, tempReverseMiddleRow);

				// tempReverseMiddle row is computed like forwardMiddle row,
				// Hence we need to reverse it to get the actual
				// reverseMiddleRow
				for (int i = 0, j = tempReverseMiddleRow.length - 1; i < tempReverseMiddleRow.length; i++) {
					reverseMiddleRow[i] = tempReverseMiddleRow[j];
					j--;
				}

				// Finding the vertical and horizontal splits
				int verticalSplit = 0, horizontalSplit = 0;
				horizontalSplit = s2.length() / 2;
				int smallest = forwardMiddleRow[0] + reverseMiddleRow[0];
				// Compute Using the middle rows
				for (int i = 1; i < forwardMiddleRow.length; i++) {
					if (smallest > forwardMiddleRow[i] + reverseMiddleRow[i]) {
						smallest = forwardMiddleRow[i] + reverseMiddleRow[i];
						verticalSplit = i;
					}
				}

				// Splitting the strings for the recursive calls
				String s1_front = s1.substring(0, verticalSplit);
				String s1_back = s1.substring(verticalSplit, s1.length());
				String s2_front = s2.substring(0, horizontalSplit);
				String s2_back = s2.substring(horizontalSplit, s2.length());

				// Recursive calls
				computeLCSUtil(s1_front, s2_front);
				computeLCSUtil(s1_back, s2_back);

				// Print two rows
				// System.out.println(Arrays.toString(forwardMiddleRow));
				// System.out.println(Arrays.toString(reverseMiddleRow));

			}

		}

		private static void computeMiddleRows(char[] s1, char[] s2,
				int[] topRow, int topDownIndex, int[] computedRow) {

			// Computing the current row using the previous row values
			computedRow[0] = topDownIndex + 1;
			for (int i = 1; i < computedRow.length; i++) {

				if (s2[topDownIndex] == s1[i - 1]) {
					computedRow[i] = topRow[i - 1];
				} else {
					computedRow[i] = (computedRow[i - 1] < topRow[i]) ? computedRow[i - 1] + 1
							: topRow[i] + 1;
				}
			}

			// [***Exit Check***]
			// Check for the 'topDownIndex' reached the last symbol of the
			// top-down string
			if (topDownIndex == s2.length - 1) {
				return;
			}

			// Since arrays are passed by reference, copying just the values of
			// bottom row to top row and making bottom row empty
			for (int i = 0; i < topRow.length; i++) {
				topRow[i] = computedRow[i];
			}
			Arrays.fill(computedRow, 0);
			computeMiddleRows(s1, s2, topRow, topDownIndex + 1, computedRow);

		}

	}

}