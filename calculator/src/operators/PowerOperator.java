package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator
{
    @Override
    public int priority()
    {
        //Returns the operator's priority.
        return 3;
    }

    @Override
    public Operand execute(Operand op1, Operand op2 )
    {
        //Execute the operation between op1 and op2 and then create a new Operand
        // from the integer result and return it.
        int result = (int)Math.pow(op1.getValue(), op2.getValue());
        Operand newOperand = new Operand(result);
        return newOperand;
    }
}
