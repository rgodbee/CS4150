/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

//<boolean_expression> -> <relational_operator> <expression> <expression>
public class BooleanExpression implements Booln
{
	private RelationalOperator op;
	private Expression expr1;
	private Expression expr2;
	public BooleanExpression (){

	}

	/**
	 * precondition: expr1 & expr2 are not null
	 * @param op
	 * @param expr1
	 * @param expr2
	 * @throws IllegalArgumentException if either expr1 or expr2 is null
	 */
	public BooleanExpression(RelationalOperator op, Expression expr1, Expression expr2)
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
	public boolean execute()
	{
		boolean test = false;
		switch (op)
		{
		case le_operator:
			test = (expr1.evaluate() <= expr2.evaluate());
			break;
		case lt_operator:
			test = (expr1.evaluate() < expr2.evaluate());
			break;
		case ge_operator:
			test = (expr1.evaluate() >= expr2.evaluate());
			break;
		case gt_operator:
			test = (expr1.evaluate() > expr2.evaluate());
			break;
		case eq_operator:
			test = (expr1.evaluate() == expr2.evaluate());
			break;
		case ne_operator:
			test = (expr1.evaluate() != expr2.evaluate());
			break;
		}
		return test;
	}
}
