/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class If_Statement implements Statement{
	//<if_statement> -> if <boolean_expression> then <compound> else <compound> end
	private Booln boolexpr;
	private Compound comp1, comp2;

	public If_Statement (Booln boolexpr, Compound comp1, Compound comp2) {
		/**
		 * preconditions: boolexpr, comp1, and comp2 are not null
		 * @param bexpr
		 * @param lineNum
		 * @throws IllegalArgumentException if arguments == null
		 */
		if (boolexpr == null)
			throw new IllegalArgumentException ("null boolean expression in if statement argument");
		if (comp1 == null || comp2 == null)
			throw new IllegalArgumentException ("null Code Block expression in if statement argument");
		this.boolexpr = boolexpr;
		this.comp1 = comp1;
		this.comp2 = comp2;
	}
	public void execute() {
		if (boolexpr.execute()) 
			comp1.execute();
		else
			comp2.execute();
	}
}