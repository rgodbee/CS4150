/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class LiteralInteger implements UnaryExpression
{
	private int value;
	public LiteralInteger(int value)
	{
		this.value = value;
	}
	public int evaluate ()
	{
		return value;
	}
}
