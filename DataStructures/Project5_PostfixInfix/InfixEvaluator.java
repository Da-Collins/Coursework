package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class InfixEvaluator extends Evaluator {
	
	private LinkedStack<String> operators = new LinkedStack<String>();
	private LinkedStack<Integer> operands  = new LinkedStack<Integer>();
	
	/** return stack object (for testing purpose) */
	public LinkedStack<String> getOperatorStack() { return operators; }
	public LinkedStack<Integer> getOperandStack()  { return operands;  }
	
	
	/** This method performs one step of evaluation of a infix expression.
	 *  The input is a token. Follow the infix evaluation algorithm
	 *  to implement this method. If the expression is invalid, throw an
	 *  exception with the corresponding exception message.
	 */	
	public void evaluate_step(String token) throws Exception {
		if(isOperand(token)) {
			operands.push(Integer.valueOf(token));
		} else {
			if(token.equals("(")){
				operators.push(token);
			}
			else if(token.equals(")")) {
				try {
					while(!operators.top().equals("(")) {
						process_operators();
					}
					operators.pop();
					return;
				}
				catch(Exception e) {
					throw new Exception("Missing (");
				}
			}
			else if(operators.top() == null) {
				operators.push(token);
			}
			else if(precedence(operators.top()) < precedence(token)){
				operators.push(token);
			}
			else {
				while(operators.top() != null && precedence(operators.top()) >= precedence(token)) {
					process_operators();
				}
				operators.push(token);
			}
		}	
	}
	
	public void process_operators() throws Exception {
		if(operators.top().equals("(")) {
			return;
		}
		if(operators.top().equals("!")) {
			try {
				int operand = operands.pop();
				operators.pop();
				operands.push(-operand);
				return;
			}
			catch(Exception e) {
				throw new Exception("too few operands");
			}
		}
		if(operators.top().equals("/")) {
			try {
				int operandTwo = operands.pop();
				int operandOne = operands.pop();
				operators.pop();
				operands.push(operandOne / operandTwo);
				return;
			}
			catch(Exception e) {
				throw new Exception("division by zero");
			}
		}
		if(!operators.top().equals("+") && !operators.top().equals("-") && !operators.top().equals("*")) {
			throw new Exception("invalid operator");
		}
		try {
			int operandTwo = operands.pop();
			int operandOne = operands.pop();
			if(operators.top().equals("+")) {
				operands.push(operandOne + operandTwo);
				operators.pop();
			}
			else if(operators.top().equals("-")) {
				operands.push(operandOne - operandTwo);
				operators.pop();
			}
			else if(operators.top().equals("*")){
				operands.push(operandOne * operandTwo);
				operators.pop();
			}
		}
		catch(Exception e){
			throw new Exception("too few operands");		
		}
	}
	/** This method evaluates an infix expression (defined by expr)
	 *  and returns the evaluation result. It throws an Exception object
	 *  if the infix expression is invalid.
	 */
	public Integer evaluate(String expr) throws Exception {
		
		for(String token : ArithParser.parse(expr)) {
			evaluate_step(token);
		}
		
		while(operators.size() != 0) {
			process_operators();
		}
		
		// The operand stack should have exactly one operand after the evaluation is done
		if(operands.size()>1)
			throw new Exception("too many operands");
		else if(operands.size()<1)
			throw new Exception("too few operands");
		
		return operands.pop();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new InfixEvaluator().evaluate("5+(5+2*(5+9))/!8"));
	}
}
