/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

import java.io.FileNotFoundException;

public class Parser
{
	private LexicalAnalyzer lex;
	/**
	 * precondition: fileName is not null
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws LexException 
	 * @throws IllegalArgumentException if fileName is null
	 */
	public Parser (String fileName) throws FileNotFoundException, LexException
	{
		if (fileName == null)
			throw new IllegalArgumentException ("null string argument");
		lex = new LexicalAnalyzer (fileName);
	}

	/**
	 * postcondition: Program object has been created from source code
	 * @return
	 * @throws ParserException if a parse error occurred
	 */
	public Feature parse () throws ParserException
	{
		//<feature> -> feature <id> is do <compound> end
		Token tok1 = lex.getNextToken();
		match (tok1, TokenType.feature_reserved);
		
		Token tok2 = lex.getNextToken();
		match (tok2, TokenType.id);
		
		Token tok3 = lex.getNextToken();
		match (tok3, TokenType.is_reserved);
		
		Token tok4 = lex.getNextToken();
		match (tok4, TokenType.do_reserved);

		Feature prog = new Feature(getCompound());

		Token tok5 = lex.getNextToken();
		match (tok5, TokenType.end_reserved);
		return prog;
	}


	//<compound> -> <statement> | <compound>� <statement>�
	public Compound getCompound() throws ParserException
	{
		Compound comp = new Compound();
		Statement stmnt;
		Token tok = lex.getNextToken();  //get because there has to be at least one
		if (tok.getTokenType() == TokenType.id || tok.getTokenType() == TokenType.print_reserved ||
				tok.getTokenType() == TokenType.from_reserved || tok.getTokenType() == TokenType.if_reserved || 
				tok.getTokenType() == TokenType.until_reserved) {


			stmnt = getStatement(tok); //gets statement
			comp.addStatement(stmnt); //adds it to statement list in block object (see add() in Code_Block
		}
		else
			throw new ParserException("Statement identifier expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());


		Token tok1 = lex.getLookaheadToken(); //lookahead because there may be more or there may not be
		while (tok1.getTokenType() == TokenType.id || tok1.getTokenType() == TokenType.print_reserved ||
				tok1.getTokenType() == TokenType.from_reserved ||
				tok1.getTokenType() == TokenType.if_reserved || 
				tok1.getTokenType() == TokenType.until_reserved) {
			//while there is a statement next, get token and use it to getStatement
			Token tok2 = lex.getNextToken();
			stmnt = getStatement(tok2);

			// put statement in codeblock
			comp.addStatement(stmnt); //adds additional statements to statementlist of block

			tok1 = lex.getLookaheadToken();

		}
		return comp;
	}

	//<statement> -> <assignment_statement> | <print_statement> | <if_statement> | <loop_statement>
	private Statement getStatement(Token tok) throws ParserException
	{

		Statement stmnt = null;

		if (tok.getTokenType() == TokenType.if_reserved) {
			stmnt = getIfStatement();
		}
		else if (tok.getTokenType() == TokenType.print_reserved){
			stmnt = getPrintStatement();
		}
		else if (tok.getTokenType() == TokenType.from_reserved){
			stmnt = getFromStatement();
		}
		else if (tok.getTokenType() == TokenType.until_reserved) {
			stmnt = getUntilStatement();
		}
		else if (tok.getTokenType() == TokenType.id) {
			stmnt = getAssignmentStatement(tok);
		}
		else
			throw new ParserException("reserved word or id expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
		return stmnt;
	}


	// <if_statement> -> if <boolean_expression> then <compound> else <compound> end�
	private Statement getIfStatement() throws ParserException
	{
		Compound comp1, comp2;
		Booln boolexpr = getBoolean();
		
		Token tok1 = lex.getNextToken();  //verify next token is then keyword
		if (tok1.getTokenType() == TokenType.then_reserved)
			comp1 = getCompound();
		else
			throw new ParserException("reserved word expected at line number: "
					+ tok1.getLineNumber() + " column number: " + tok1.getColumnNumber());

		Token tok2 = lex.getNextToken();  //verify next token is else keyword
		if (tok2.getTokenType() == TokenType.else_reserved)
			comp2 = getCompound();
		else
			throw new ParserException("reserved word expected at line number: "
					+ tok2.getLineNumber() + " column number: " + tok2.getColumnNumber());

		//verify next token is end_reserved token
		Token verify = lex.getNextToken();
		match (verify, TokenType.end_reserved);
		return new If_Statement (boolexpr, comp1, comp2);
	}

	// <print_statement> -> print� ( <expression>� )
	private Statement getPrintStatement() throws ParserException 
	{
		Expression expr;
		Token tok1 = lex.getNextToken();  //verify next token is then keyword
		if (tok1.getTokenType() == TokenType.left_par_reserved)
			 expr = getExpression();
		else
			throw new ParserException("'(' expected at line number: "
					+ tok1.getLineNumber() + " column number: " + tok1.getColumnNumber());
		
		
		//verify next token is ) token
		Token tok2 = lex.getNextToken();
		if (tok2.getTokenType() != TokenType.right_par_reserved)
			throw new ParserException("')' expected at line number: "
					+ tok1.getLineNumber() + " column number: " + tok1.getColumnNumber());

		return new Print_Statement(expr);
	}

	
	
	//<loop_statement> -> from <assignment_statement> until <boolean_expression> loop <compound> end
	private Statement getFromStatement() throws ParserException{
		
		Expression expr;
		Token tok = lex.getNextToken();
		//reassign token passed from getStatement and create expression
		Id identity = getId(tok); // id
		//get assignment operator token
		Token assignment = lex.getNextToken();
		match (assignment, TokenType.assignment_operator);

		//get expression
		if (tok.getTokenType() == TokenType.id || tok.getTokenType() == TokenType.literal_integer ||
				tok.getTokenType() == TokenType.add_operator || tok.getTokenType() == TokenType.sub_operator ||
				tok.getTokenType() == TokenType.mul_operator || tok.getTokenType() == TokenType.div_operator) 
			expr = getExpression();
		else
			throw new ParserException("expression expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());

		return new Assignment_Statement (identity, expr);
		
	}
	// loop cont.
	private Statement getUntilStatement() throws ParserException {
		
		/***************************************************
		 * There is no until statement in our language
		 */
		Booln boolexpr = getBoolean();
		Compound comp1;
		Token tok1 = lex.getNextToken();  //verify next token is do keyword
		if (tok1.getTokenType() == TokenType.loop_reserved)
			comp1 = getCompound();
		else
			throw new ParserException("'loop' reserved word expected at line number: "
					+ tok1.getLineNumber() + " column number: " + tok1.getColumnNumber());

		//verify next token is end_reserved token
		Token verify = lex.getNextToken();
		match (verify, TokenType.end_reserved);
		
		return new Loop_Statement (boolexpr, comp1);
	}
	
	// <assignment_statement> -> <id> assignment_operator <expression>
	private Statement getAssignmentStatement(Token tok) throws ParserException {

		Expression expr;
		//reassign token passed from getStatement and create expression
		Id identity = getId(tok); // id
		//get assignment operator token
		Token assignment = lex.getNextToken();
		match (assignment, TokenType.assignment_operator);

		//get expression
		if (tok.getTokenType() == TokenType.id || tok.getTokenType() == TokenType.literal_integer ||
				tok.getTokenType() == TokenType.add_operator || tok.getTokenType() == TokenType.sub_operator ||
				tok.getTokenType() == TokenType.mul_operator || tok.getTokenType() == TokenType.div_operator) 
			expr = getExpression();
		else
			throw new ParserException("expression expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());

		return new Assignment_Statement (identity, expr);
	}

	private Booln getBoolean() throws ParserException
	{

		BooleanExpression boolexpr = null;
		Token tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.le_operator || tok.getTokenType() == TokenType.lt_operator || 
				tok.getTokenType() == TokenType.ge_operator || tok.getTokenType() == TokenType.gt_operator || 
				tok.getTokenType() == TokenType.eq_operator || tok.getTokenType() == TokenType.ne_operator)
		{

			RelationalOperator op = getRelationalOperator();
			Expression expr1 = getExpression();
			Expression expr2 = getExpression();

			boolexpr = new BooleanExpression (op, expr1, expr2);
		}
		else 
			throw new ParserException("Statement identifier expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());

		return boolexpr;

	}

	private RelationalOperator getRelationalOperator() throws ParserException
	{
		RelationalOperator op;
		Token tok = lex.getNextToken();
		if (tok.getTokenType() == TokenType.le_operator)
			op = RelationalOperator.le_operator;
		else if (tok.getTokenType() == TokenType.lt_operator)
			op = RelationalOperator.lt_operator;
		else if (tok.getTokenType() == TokenType.ge_operator)
			op = RelationalOperator.ge_operator;
		else if (tok.getTokenType() == TokenType.gt_operator)
			op = RelationalOperator.gt_operator;
		else if (tok.getTokenType() == TokenType.eq_operator)
			op = RelationalOperator.eq_operator;
		else if (tok.getTokenType() == TokenType.ne_operator)
			op = RelationalOperator.ne_operator;
		else
			throw new ParserException ("relational operator expected at line number: " + tok.getLineNumber() + 
					" column number: " + tok.getColumnNumber());
		return op;
	}

	//<expression> -> <arithmetic_operator> <expression> <expression> | <id> | literal_integer
	private Expression getExpression() throws ParserException
	{
		Expression expr;
		Token tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.id || tok.getTokenType() == TokenType.literal_integer)
			expr = getUnaryExpression();
		else if (tok.getTokenType() == TokenType.add_operator || tok.getTokenType() == TokenType.sub_operator ||
				tok.getTokenType() == TokenType.mul_operator || tok.getTokenType() == TokenType.div_operator)
		{
			expr = getBinaryExpression();
		}
		else
			throw new ParserException("identifier, literal integer, or arithmetic operator expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
		return expr;
	}

	// unary expression is either TokenType.id or TokenType.literal_integer
	private UnaryExpression getUnaryExpression() throws ParserException
	{
		UnaryExpression expr;
		Token tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.id)
			expr = getId();
		else if (tok.getTokenType() == TokenType.literal_integer)
			expr = getLiteralInteger();
		else
			throw new ParserException("identifier or literal integer expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());

		return expr;
	}

	private LiteralInteger getLiteralInteger() throws ParserException
	{
		Token tok = lex.getNextToken();
		int value = 0;
		try
		{
			value = Integer.parseInt(tok.getLexeme());
		}
		catch (NumberFormatException e)
		{
			throw new ParserException ("literal integer expected at line number: " + tok.getLineNumber() + 
					" column number: " + tok.getColumnNumber());
		}
		return new LiteralInteger (value);
	}

	private Id getId() throws ParserException
	{
		Token tok = lex.getNextToken();
		return new Id (tok.getLexeme().charAt(0));
	}

	private Id getId(Token tok) throws ParserException
	{
		return new Id (tok.getLexeme().charAt(0));
	}

	// binary expression is <arithmetic_operator><expression><expression>
	private Expression getBinaryExpression() throws ParserException
	{
		ArithmeticOperator op = getArithmeticOperator();
		Expression expr1 = getExpression();
		Expression expr2 = getExpression();

		return new BinaryExpression (op, expr1, expr2);
	}


	//<arithmetic_operator> -> add_operator | sub_operator | mul_operator | div_operator
	private ArithmeticOperator getArithmeticOperator() throws ParserException
	{
		ArithmeticOperator op;
		Token tok = lex.getNextToken();
		if (tok.getTokenType() == TokenType.add_operator)
			op = ArithmeticOperator.add_operator;
		else if (tok.getTokenType() == TokenType.sub_operator)
			op = ArithmeticOperator.sub_operator;
		else if (tok.getTokenType() == TokenType.mul_operator)
			op = ArithmeticOperator.mul_operator;
		else if (tok.getTokenType() == TokenType.div_operator)
			op = ArithmeticOperator.div_operator;
		else
			throw new ParserException ("arithmetic operator expected at line number: " + tok.getLineNumber() + 
					" column number: " + tok.getColumnNumber());
		return op;
	}



	private void match(Token tok, TokenType expected) throws ParserException
	{
		assert expected != null;
		assert tok != null;
		if (expected != tok.getTokenType())
			throw new ParserException (expected + " expected at line number: " + tok.getLineNumber() + 
					" column number: " + tok.getColumnNumber());
	}

}
