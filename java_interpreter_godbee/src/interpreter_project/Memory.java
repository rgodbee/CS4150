/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class Memory {

	private static int[] mem = new int[52];
	
	/************************************************
	 * Our language is not case sensitive so there are
	 * only 26 possible identifiers.
	 */

	/**
	 * @param ch
	 * @return value stored for specified variable
	 */
	public static int fetch(char ch) {
		int index = 0;
		if (Character.isLetter(ch)) {
			char c = ch;
			if (Character.isLowerCase(c))
				index = c - 'a';
			else
				index = 26 + c - 'A';
		}
		else
			throw new IllegalArgumentException ("Memory fetch with non-character");
		return mem[index];
	}

	/**
	 * postcondition: value has been stored as the value of the specified
	 * variable
	 * 
	 * @param ch
	 * @param value
	 */
	public static void store(char ch, int value) {
		char c = ch;
		int index = 0;
		if (Character.isLetter(ch)) {
			if (Character.isLowerCase(c))
				index = c - 'a';
			else
				index = 26 + c - 'A';
		}
		else
			throw new IllegalArgumentException ("Memory store with illegal argument");
		mem[index] = value;
	}
}
