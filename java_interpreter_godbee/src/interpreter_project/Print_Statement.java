/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class Print_Statement implements Statement {
	private  Expression expr;
	public Print_Statement (){
	}
	
	/***************************************************
	 * Why do you have a default constructor?
	 */
	public Print_Statement(Expression expr)
	{
		if (expr == null)
			throw new IllegalArgumentException("null expression type argument");
		this.expr = expr;
	}

	@Override
	public void execute() {
		if (expr instanceof UnaryExpression) {
			System.out.println (expr.evaluate());
		}
		else if (expr instanceof BinaryExpression) {
			int value = expr.evaluate();
			System.out.println(value);
		}
	}
}