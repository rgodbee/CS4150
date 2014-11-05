/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class Id implements UnaryExpression
{
	private char ch;
	/**
	 * @param ch
	 */
	public Id(char ch)
	{
		this.ch = ch;
	}
	public int evaluate ()
	{
		return Memory.fetch (ch);
	}
	public char getCh()
	{
		return ch;
	}
}

