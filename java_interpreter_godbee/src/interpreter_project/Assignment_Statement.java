/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class Assignment_Statement implements Statement {
	private Id identity;
	private Expression expr;
	public Assignment_Statement () {
	}
	public Assignment_Statement (Id identity, Expression expr) {
		/**
		 * preconditions: Expression not null
		 * @param identity
		 * @param expr
		 * @throws IllegalArgumentException if arguments == null
		 */
		if (identity == null || expr == null)
			throw new IllegalArgumentException ("null expression argument");
		this.identity = identity;
		this.expr = expr;

	}
	@Override
	public void execute() {
		Memory.store(identity.getCh(),  expr.evaluate());
	}
}
