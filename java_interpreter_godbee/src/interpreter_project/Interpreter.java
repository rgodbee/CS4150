/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             
 *  											  
 *  This project was started with examples        
 *  provided in class by Dr. Gayler.			  
 *  											  
 *  It was accomplished with assistance from	  
 *  Richard Bradley.		  			  
 */

package interpreter_project;

import java.io.FileNotFoundException;

public class Interpreter
{
	public static void main(String[] args)
	{
		String testing = "test.txt";
		try
		{
			Parser p = new Parser(testing); 	//calls parser() which creates lexical analyzer object from input
			Feature feat = p.parse();			//Feature object feat from the return of parse()
			feat.execute(); 					//executes the returned feature
		}
		catch (FileNotFoundException e)
		{
			System.out.println("source file not found");
			e.printStackTrace();
		}
		catch (LexException e)
		{
			System.out.println(e.getMessage() + " row: " + e.getRowNumber() + " column: " + e.getColumnNumber());
			e.printStackTrace();
		}			
		catch (ParserException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			System.out.println (e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("unknown error occurred - terminating");
			e.printStackTrace();
		}
	}
}

/***********************************************
 * 95/100 Please see my comments in your code.
 */
