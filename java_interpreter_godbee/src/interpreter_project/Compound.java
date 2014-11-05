/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

import java.util.ArrayList;
import java.util.List;

//<compound> -> <statement> | <compound>� <statement>�
public class Compound {
	private List<Statement> stmntlist = new ArrayList<Statement>();
	public Compound (){
	}
	void addStatement (Statement arg) {
		stmntlist.add(arg);
	}
	public void execute() {
		for (int i=0; i<stmntlist.size(); i++)
			stmntlist.get(i).execute();
	}
	public int evaluate()
	{
		return 0;
	}
	
	/**************************************************
	 * Why do have an evaluate method?
	 */
}
