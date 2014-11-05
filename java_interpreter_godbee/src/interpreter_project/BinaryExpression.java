/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public class BinaryExpression implements Expression
{
	private ArithmeticOperator op;
	private Expression expr1;
	private Expression expr2;
	public BinaryExpression (){
		
	}

	/**
	 * precondition: expr1 & expr2 are not null
	 * @param op
	 * @param expr1
	 * @param expr2
	 * @throws IllegalArgumentException if either expr1 or expr2 is null
	 */
	public BinaryExpression(ArithmeticOperator op, Expression expr1, Expression expr2)
	{
		if (expr1 == null || expr2 == null)
			throw new IllegalArgumentException ("null expression argument");
		this.op = op;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	/**
	 * @return value of the expression
	 */
	public int evaluate()
	{
		int value = 0;
		switch (op)
		{
		case add_operator:
			value = expr1.evaluate() + expr2.evaluate();
			break;
		case sub_operator:
			value = expr1.evaluate() - expr2.evaluate();
			break;
		case mul_operator:
			value = expr1.evaluate() * expr2.evaluate();
			break;
		case div_operator:
			value = expr1.evaluate() / expr2.evaluate();
			break;
		}
		return value;
	}
}

