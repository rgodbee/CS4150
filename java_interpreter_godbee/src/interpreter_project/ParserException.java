/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class ParserException extends Exception
{
	private static final long serialVersionUID = 1L;
	public ParserException(String message)
	{
		super (message);
	}
}
