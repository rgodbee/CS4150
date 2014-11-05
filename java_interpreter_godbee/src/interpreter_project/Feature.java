/**
 *  Richard Godbee                                
 *  CS 4510 - Concepts of Programming             
 *  Java Interpreter                                   
 *  September 30, 2014                             		  			  
 */

package interpreter_project;

//<feature> -> feature <id> is do <compound> end 
public class Feature {
	private Compound cp = new Compound();
	public Feature(Compound comp) {
		this.cp = comp;
	}
	public  void execute() {
		cp.execute();
	}
}
