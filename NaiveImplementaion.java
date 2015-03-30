import java.util.Arrays;

/*
 * Naive implementation is comparing two input strings with all possible combinations
 * to find a edit distance when match found. This algorithm work only smaller string 
 * lengths, because it involves computing factorial and nCr, which are limited to only
 * smaller numbers because of the integer range. 
 * 
 * Hence this method is not effective.
 */

public class NaiveImplementaion {
	public static float findNormalisedEditDistance(String s1, String s2) {

		int editDistance = 0;
		float normalisedEditDistance = 0.0F;
		String[] s1Array;
		String[] s2Array;
		int s1N = s1.length();
		int s2N = s2.length();
		
		// Initializing array based on the maximum size that it could take by
		// using nCr
		s1Array = new String[(int) computenCr(s1N, s1N / 2)];
		s2Array = new String[(int) computenCr(s2N, s2N / 2)];

		int minLength, maxLength;
		minLength = Math.min(s1N, s2N);
		maxLength = Math.max(s1N, s2N);
		
		// nCrVariable is used to vary the 'r' value in the formula, it takes
		// values from (maxLength-minLength) to (maxLength-1)
		int nCrVarible = (maxLength - minLength);

		outerloop: for (int i = nCrVarible; i < maxLength; i++) {
			int r;
			r = maxLength - i;

			// Creating string arrays of possible combinations for both the
			// strings to compare
			s1Array = makeCombination(s1, r, s1Array);
			s2Array = makeCombination(s2, r, s2Array);

			// compare all the elements in the first array with all the elements
			// in the second array

			for (int j1 = 0; j1 < s1Array.length; j1++) {
				
				for (int j2 = 0; j2 < s2Array.length; j2++) {
					
					if (s1Array[j1] == null || s2Array[j2] == null) {
						// System.out.println("Null sometimes");
						continue;
					} else if (s1Array[j1].equals(s2Array[j2])) {
						editDistance = (s1N - r) + (s2N - r);
						System.out.println("VOila");
						break outerloop;
					}

				}
			
			}

			// Preparing for the next set of combinations with lesser length
			Arrays.fill(s1Array, null);
			Arrays.fill(s2Array, null);
		}

		// This below condition is for, when two given strings are completely
		// unequal, then the above for loops try out all possible
		// combination of the 2 string and then fail to set
		// normalisedEditDistance variable.
		// For example, if the string1 = "ab" and string2 = "cd", then the for
		// loop section check all the combination {(ab == cd),
		// (a == c), (a == d), (b == c), (b == d)}. After that it will come out
		// of the loop.
		// This condition should be checked at the last, so its written here

		if (normalisedEditDistance == 0) {
			if (s1 != s2) {
				editDistance = s1N + s2N;
			}
		}
		
		normalisedEditDistance = (float)((s1.length()+s2.length()-editDistance)/(s1.length()+s2.length()));
		return normalisedEditDistance;
	}

	// MAKE COMBINATION STRING ARRAY
	private static String[] makeCombination(String inputString,
			int lengthCombination, String[] outputString) {

		if (inputString.length() < lengthCombination) {
			System.out.println("String length is less than expected length");
			return null;
		}

		char[] inputChar = inputString.toCharArray();
		char[] tempChar = new char[lengthCombination];
		String[] finalString = outputString;

		combinationUtil(inputChar, tempChar, 0, inputString.length() - 1, 0,
				lengthCombination, finalString);
		outputString = finalString;
		
		// Resetting the arrayIndex for the next set of combination i.e if the
		// comparison between the previous set
		// haven't found any LCS then it will reduce the length by one and form
		// new set of combinations
		// Example, {(abc == adc) --> fails, then (ab, ac, bc) == (ad, ac, dc)}
		// this new set needs to be overwrite the previous string array
		stringArrayIndex = 0;
		return outputString;
	}

	// COMBINATION UTILITY
	private static int stringArrayIndex = 0;

	private static void combinationUtil(char[] inputChar, char[] tempChar,
			int tempStart, int tempEnd, int index, int length,
			String[] outputString) {

		if (index == length) {
			outputString[stringArrayIndex] = String.valueOf(tempChar);
			stringArrayIndex++;
			return;
		}

		for (int i = tempStart; i <= tempEnd
				&& (tempEnd - i + 1) >= (length - index); i++) {
			tempChar[index] = inputChar[i];
			combinationUtil(inputChar, tempChar, i + 1, tempEnd, index + 1,
					length, outputString);
		}

	}

	// Factorial and nCr functions

	private static long computenCr(int n, int r) {
		
		return factorial(n) / (factorial(r) * factorial(n - r));
	}

	private static long factorial(int number) {

		if (number == 0) {
			return 1;
		} else if (number < 0) {
			return 0;
		}
		return number * factorial(number - 1);
	}

}
