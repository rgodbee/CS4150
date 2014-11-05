/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LexicalAnalyzer
{
	private List<Token> tokens;
	/**
	 * precondition: fileName is not null
	 * @param fileName
	 * @throws LexException 
	 * @throws FileNotFoundException if file is not found
	 * @throws IllegalArgumentException is fileName is null
	 */
	public LexicalAnalyzer(String fileName) throws LexException, FileNotFoundException
	{
		if (fileName == null)
			throw new IllegalArgumentException ("null string argument");
		tokens = new LinkedList<Token>();
		Scanner input = new Scanner (new File (fileName));
		int lineNum = 0;
		while (input.hasNext())
		{
			String line = input.nextLine();
			lineNum++;
			processLine (line, lineNum);
		}
		tokens.add(new Token (TokenType.EOS, "$", lineNum + 1, 1));
		input.close();
	}
	private void processLine(String line, int rowNum) throws LexException
	{
		assert line != null;
		assert rowNum > 0;
		int index = 0;
		boolean done = false;
		do
		{
			index = skipWhiteSpace (line, index);
			if (index == line.length())
				done = true;
			else
			{
				int columnNum = index + 1;
				String lexeme = null;
				TokenType tok = null;
				if (Character.isLetter(line.charAt(index)))
				{
					int i = index;
					while (i < line.length() && (Character.isLetter(line.charAt(i))))
						i++;
					lexeme = line.substring(index, i);
					index = i;

					/*******************************************************
					 * our language is not case sensitive so reserved words
					 * can be any combination of lower & upper case
					 */
					if(lexeme.equals("feature"))
						tok = TokenType.feature_reserved;
					else if(lexeme.equals("is"))
						tok = TokenType.is_reserved;
					else if(lexeme.equals("do"))
						tok = TokenType.do_reserved;
					else if(lexeme.equals("end"))
						tok = TokenType.end_reserved;
					else if(lexeme.equals("if"))
						tok = TokenType.if_reserved;
					else if(lexeme.equals("then"))
						tok = TokenType.then_reserved;
					else if(lexeme.equals("else"))
						tok = TokenType.else_reserved;
					else if(lexeme.equals("print"))
						tok = TokenType.print_reserved;
					else if(lexeme.equals("from"))
						tok = TokenType.from_reserved;
					else if(lexeme.equals("loop"))
						tok = TokenType.loop_reserved;
					else if(lexeme.equals("until"))
						tok = TokenType.until_reserved;
					else
						tok = TokenType.id;
				}
				else if (Character.isDigit(line.charAt(index)))
				{
					int i = index;
					while (i < line.length() && Character.isDigit(line.charAt(i)))
						i++;
					lexeme = line.substring(index, i);
					index = i;
					tok = TokenType.literal_integer;
				}
				
				// := assignment operator
				else if(line.charAt(index) == ':'){
					if (line.charAt(index+1) == '=')
					{
						lexeme = (new Character (line.charAt(index))).toString();
						tok = TokenType.assignment_operator;
						index+=2;
					}
				}
				
				// == equals operator 
				else if (line.charAt(index) == '=')
				{
					if (line.charAt(index+1) == '=')
					{
						lexeme = line.substring(index, index+2);
						index += 2;
						tok = TokenType.eq_operator;
					}
				}

				// <= check and operator assignment
				else if (line.charAt(index) == '<')
				{
					if (line.charAt(index+1) == '=')
					{
						lexeme = line.substring(index, index+2);
						index += 2;
						tok = TokenType.le_operator;
					}
					else{
						lexeme = (new Character (line.charAt(index))).toString();
						tok = TokenType.lt_operator;
						index++;
					}
				}

				// >= check and operator assignment
				else if (line.charAt(index) == '>')
				{
					if (line.charAt(index+1) == '=')
					{
						lexeme = line.substring(index, index+2);
						index += 2;
						tok = TokenType.ge_operator;
					}
					else{
						lexeme = (new Character (line.charAt(index))).toString();
						tok = TokenType.gt_operator;
						index++;
					}
				}

				// /= (not equal boolean) check and operator assignment
				else if (line.charAt(index) == '/')
				{
					if (line.charAt(index+1) == '=')
					{
						lexeme = line.substring(index, index+2);
						index += 2;
						tok = TokenType.ne_operator;
					}
					else{
						lexeme = (new Character (line.charAt(index))).toString();
						tok = TokenType.div_operator;
						index++;
					}
				}
				else
				{
					switch (line.charAt(index))
					{
					case '+':
						tok = TokenType.add_operator;
						break;
					case '-': 
						tok = TokenType.sub_operator;
						break;
					case '*':
						tok = TokenType.mul_operator;
						break;
					case ';':
						tok = TokenType.EOS;
						break;
					case '(':
						tok = TokenType.left_par_reserved;
						break;
					case ')':
						tok = TokenType.right_par_reserved;
						break;
					default:
						throw new LexException ("invalid lexeme", rowNum, columnNum);
					}
					lexeme = (new Character (line.charAt(index))).toString();
					index++;
				}
				Token t = new Token (tok, lexeme, rowNum, columnNum);
				tokens.add(t);
			}
		}
		while (!done);
	}

	private int skipWhiteSpace(String line, int index)
	{
		assert line != null;
		assert index >= 0;
		while (index < line.length() && Character.isWhitespace(line.charAt(index)))
			index++;
		return index;
	}

	/**
	 * precondition: tokens is not empty
	 * @return token at front of list removing the token from the list
	 * @throws RuntimeException if tokens is empty
	 */
	public Token getNextToken()
	{
		if (tokens.isEmpty())
			throw new RuntimeException ("no more tokens");
		return tokens.remove(0);
	}

	/**
	 * precondition: tokens is not empty
	 * @return token at front of list leaving token on list
	 * @throws RuntimeException if tokens is empty
	 */
	public Token getLookaheadToken()
	{
		if (tokens.isEmpty())
			throw new RuntimeException ("no more tokens");
		return tokens.get(0);
	}

	/**
	 * @return true if there are more tokens - false otherwise
	 */
	public boolean moreTokens ()
	{
		return !tokens.isEmpty();
	}
}

