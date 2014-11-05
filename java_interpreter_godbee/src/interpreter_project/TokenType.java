/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

public enum TokenType
{
	feature_reserved, is_reserved, end_reserved, if_reserved, then_reserved, else_reserved,
	while_reserved, do_reserved, print_reserved, puts_reserved, until_reserved,
	left_par_reserved, right_par_reserved, from_reserved, loop_reserved,
	literal_integer, id, assignment_operator, le_operator, lt_operator,
	ge_operator, gt_operator, eq_operator, ne_operator,
	add_operator, sub_operator, mul_operator, div_operator, EOS
}

