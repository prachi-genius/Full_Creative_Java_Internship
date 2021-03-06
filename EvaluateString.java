package com.StackImplementation;


import java.util.Scanner;

/* A Java program to evaluate a 
given expression where tokens 
are separated by space. 
*/
import java.util.Stack;

public class EvaluateString
{
	public static double evaluate(String expression)
	{
		char[] tokens = expression.toCharArray();
		//System.out.println(tokens);

		// Stack for numbers: 'values'
		Stack<Double> values = new
							Stack<Double>();

		// Stack for Operators: 'ops'
		Stack<Character> ops = new
							Stack<Character>();

		for (int i = 0; i < tokens.length; i++)
		{
			
			// Current token is a 
			// whitespace, skip it
			if (tokens[i] == ' ')
				continue;
			// Current token is a number, 
			// push it to stack for numbers
			try {
				if (tokens[i] >= '0' && 
						tokens[i] <= '9')
				{
					StringBuffer sbuf = new StringBuffer();
				
					// There may be more than one 
					// digits in number
					while (i < tokens.length && 
							tokens[i] >= '0' && 
							tokens[i] <= '9')
					{
						try {
							sbuf.append(tokens[i++]);
							values.push(Double.parseDouble(sbuf.toString()));
						}
						catch(ClassCastException e) {
							System.out.print(e);
						}
					}
			
				// right now the i points to 
				// the character next to the digit,
				// since the for loop also increases 
				// the i, we would skip one 
				// token position; we need to 
				// decrease the value of i by 1 to
				// correct the offset.
				i--;
				}
			}
			catch(Exception e) {
				System.out.print("Not a number");
			}

			// Current token is an opening brace, 
			// push it to 'ops'
			if(tokens[i]!='(' && tokens[i]!=')' && tokens[i] != '+' &&
					tokens[i] != '-' && tokens[i] != '*' &&
						tokens[i] != '/' && tokens[i]!='^' && tokens[i]!='0'
						&& tokens[i]!='1' && tokens[i]!='2' && tokens[i]!='3'
						&& tokens[i]!='4' && tokens[i]!='5' && tokens[i]!='6'
						&& tokens[i]!='7' && tokens[i]!='8' && tokens[i]!='9')
				throw new UnsupportedOperationException("Not an operand or a number");
				
			else if (tokens[i] == '(')
				ops.push(tokens[i]);

			// Closing brace encountered, 
			// solve entire brace
			else if (tokens[i] == ')')
			{
				while (ops.peek() != '(')
				values.push(applyOp(ops.pop(), 
								values.pop(), 
								values.pop()));
				ops.pop();
			}

			// Current token is an operator.
			else if (tokens[i] == '+' || 
					tokens[i] == '-' ||
					tokens[i] == '*' || 
						tokens[i] == '/'||tokens[i]=='^')
			{
				// While top of 'ops' has same 
				// or greater precedence to current
				// token, which is an operator.
				// Apply operator on top of 'ops'
				// to top two elements in values stack
				while (!ops.empty() && 
					hasPrecedence(tokens[i], 
									ops.peek()))
				{
				
					values.push(applyOp(ops.pop(), 
								values.pop(),
								values.pop()));
					System.out.print(values);
				}
				
				// Push current token to 'ops'.
				ops.push(tokens[i]);
			}
			//}
			
		}

		// Entire expression has been 
		// parsed at this point, apply remaining
		// ops to remaining values
		while (!ops.empty())
		{
			values.push(applyOp(ops.pop(), 
							values.pop(), 
						values.pop()));
		}
		// Top of 'values' contains 
		// result, return it
		return values.pop();
		
	}

	// Returns true if 'op2' has higher 
	// or same precedence as 'op1',
	// otherwise returns false.
	public static boolean hasPrecedence(
						char op1, char op2)
	{
		
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '*' || op1 == '/') && 
			(op2 == '+' || op2 == '-'))
			return false;
		if ((op1 == '^') && (op2 == '+' || op2 == '-')) 
            return false;
		if ((op1 == '^') && (op2 == '*' || op2 == '/')) 
            return false;
		if(op1=='^'&&op2=='^')
        	return true;
		else
			return true;
	}

	// A utility method to apply an 
	// operator 'op' on operands 'a' 
	// and 'b'. Return the result.
	public static double applyOp(char op, 
						double b, double a)
	{
		switch (op)
		{
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			if (b == 0)
				throw new
				UnsupportedOperationException(
					"Cannot divide by zero");
			return a / b;
		case '^':
			if (b>1000)
				throw new 
				UnsupportedOperationException("Exponent can't be more than 4 digit");
				
			return Math.pow(a, b);
		//default : return evaluate("");
		}
		return 0;
	}

	// Main method to execute above methods
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the Expression to calculate:");
		String exp = sc.nextLine();
		System.out.print(EvaluateString.evaluate(exp));
		
//		
//		System.out.println(EvaluateString.
//						evaluate("10 + 2 * 6"));
//		System.out.println(EvaluateString.
//					evaluate("100 * 2 + 12"));
//		System.out.println(EvaluateString.
//				evaluate("100 * ( 2 + 12 )"));
//		System.out.println(EvaluateString.
//			evaluate("100 * ( 2 + 12 ) / 14"));
	}
}
