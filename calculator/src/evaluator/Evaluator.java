package edu.csc413.calculator.evaluator;



import edu.csc413.calculator.operators.Operator;

import java.util.Objects;
import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer tokenizer;
  private static final String DELIMITERS = "+-*^/() ";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }


  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Evaluator evaluator = (Evaluator) o;
    return Objects.equals(operandStack, evaluator.operandStack) &&
            Objects.equals(operatorStack, evaluator.operatorStack) &&
            Objects.equals(tokenizer, evaluator.tokenizer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operandStack, operatorStack, tokenizer);
  }

  public int eval(String expression)
  {
    String token;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.tokenizer = new StringTokenizer( expression, DELIMITERS, true );

    while(this.tokenizer.hasMoreTokens())
    {
      // filter out spaces
      if(!(token = this.tokenizer.nextToken()).equals(" "))
      {
        // check if token is an operand
        if(Operand.check(token))
        {
          operandStack.push(new Operand(token));
        }
        else
          {
          if(!Operator.check(token))
          {
            System.out.println("*****invalid token******");
            throw new RuntimeException("*****invalid token******");
          }

          //If the operator is an open parenthesis, create an object from it and then push it onto the stack
          //and then proceed with the next token.
          if(token.equals("("))
          {
            Operator newOperator = Operator.getOperator(token);
            operatorStack.add(newOperator);
            continue;
          }

          //If the operator is a closed parenthesis, pop out the operators whose priority is above the open
          //parenthesis' priority of 0 and then pop out the operands that was associated with it. Execute
          //them and then push the result onto the operandStack.
          if(token.equals(")"))
          {
            while(operatorStack.peek().priority() > 0)
            {
              Operator oldInsideOpr = operatorStack.pop();
              Operand insideOp2 = operandStack.pop();
              Operand insideOp1 = operandStack.pop();
              operandStack.push( oldInsideOpr.execute(insideOp1, insideOp2));
            }
            //After that is done, pop the open parenthesis out of the operatorStack and then continue
            //to the next token.
            operatorStack.pop();
            continue;
          }

          // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.

          //Create a new concrete operator object from the token string indicating what operator it is.
          Operator newOperator = Operator.getOperator(token);

          //If the operator stack is empty, then add the new operator and skip the while loop below.
          if(operatorStack.isEmpty())
          {
            operatorStack.add(newOperator);
            continue;
          }

          //This while loop will make sure that subsequent operators in the expression are properly in their place in the stack.
          while(operatorStack.peek().priority() >= newOperator.priority())
          {
            // note that when we eval the expression 1 - 2 we will
            // push the 1 then the 2 and then do the subtraction operation
            // This means that the first number to be popped is the
            // second operand, not the first operand - see the following code
            Operator oldOpr = operatorStack.pop();
            Operand op2 = operandStack.pop();
            Operand op1 = operandStack.pop();
            operandStack.push( oldOpr.execute( op1, op2 ));
            //Exit the while loop if the operator stack is empty.
            if(operatorStack.isEmpty())
            {
              break;
            }
          }

          operatorStack.push( newOperator );
        }
      }
    }

    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks, 
    // that is, we should keep evaluating the operator stack until it is empty;
    // Suggestion: create a method that takes an operator as argument and
    // then executes the while loop.

    //After all operators and operands are properly in their stacks, pop them out in order
    //and then process them one by one.
    while(operatorStack.peek().priority() > 0)
    {
      Operator myOperator = operatorStack.pop();
      Operand myOperand2 = operandStack.pop();
      Operand myOperand1 = operandStack.pop();
      operandStack.push(myOperator.execute(myOperand1, myOperand2));
      //Exit the while loop if the operator stack is empty.
      if(operatorStack.isEmpty())
      {
        break;
      }
    }

    //Return the final value from the given expression back to the caller.
    return operandStack.pop().getValue();
  }
}
