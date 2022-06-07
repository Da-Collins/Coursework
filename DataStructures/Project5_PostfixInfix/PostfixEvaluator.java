package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class PostfixEvaluator extends Evaluator {
	
	private LinkedStack<Integer> stack = new LinkedStack<Integer>();
	
	/** return stack object (for testing purpose) */
	public LinkedStack<Integer> getStack() { return stack; }
	
	/** This method performs one step of evaluation of a postfix expression.
	 *  The input is a token. Follow the postfix evaluation algorithm
	 *  to implement this method. If the expression is invalid, throw an
	 *  exception with the corresponding exception message.
	 */
	public void evaluate_step(String token) throws Exception {
		if(isOperand(token)) {
			stack.push(Integer.valueOf(token));
		} else {
			if(token.equals("!")) {
				try {
					stack.push(-stack.pop());
					System.out.println(stack.size());
					return;
				}
				catch(Exception e) {
					throw new Exception("too few operands");
				}
			}
			if(token.equals("/")) {
				int operandTwo = stack.pop();
				int operandOne = stack.pop();
				try {
					stack.push(operandOne / operandTwo);
					return;
				}
				catch(Exception e) {
					throw new Exception("division by zero");
				}
			}
			if(!token.equals("+") && !token.equals("-") && !token.equals("*")) {
				throw new Exception("invalid operator");
			}
			try {
				int operandTwo = stack.pop();
				int operandOne = stack.pop();
				if(token.equals("*")) {
					stack.push(operandOne * operandTwo);
				}
				else if(token.equals("-")) {
					stack.push(operandOne - operandTwo);
				}
				else if(token.equals("+")) {
					stack.push(operandOne + operandTwo);
				}
			}
			catch(Exception e) {
				throw new Exception("too few operands");
			}
		}		
	}
	/** This method evaluates a postfix expression (defined by expr)
	 *  and returns the evaluation result. It throws an Exception
	 *  if the postfix expression is invalid.
	 */
	public Integer evaluate(String expr) throws Exception {
		
		for(String token : ArithParser.parse(expr)) {
			evaluate_step(token);
		}
		// The stack should have exactly one operand after evaluation is done
		if(stack.size()>1) {
			throw new Exception("too many operands");
		} else if (stack.size()<1) {
			throw new Exception("too few operands");
		}
		return stack.pop(); 
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new PostfixEvaluator().evaluate("50 25 ! / 3 +"));
	}
}