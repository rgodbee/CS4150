/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class Loop_Statement implements Statement{
	//<Until_statement -> while <boolean_expression> do <code_block> end
	private Booln boolexpr;
	private Compound comp1;


	public Loop_Statement(Booln boolexpr, Compound comp1) {
		/**
		 * preconditions: boolexpr, block1 are not null
		 * @param boolexpr
		 * @param block1
		 * @throws IllegalArgumentException if arguments == null
		 */
		if (boolexpr == null)
			throw new IllegalArgumentException ("null boolean expression in while statement argument");
		if (comp1 == null)
			throw new IllegalArgumentException ("null Code Block expression in while statement argument");
		this.boolexpr = boolexpr;
		this.comp1 = comp1;
	}

	@Override
	public void execute() {
		while (!boolexpr.execute()) {
			comp1.execute();
		}
	}
}
