package edu.csc413.calculator.operators;



import edu.csc413.calculator.evaluator.Operand;

import java.util.HashMap;

public abstract class Operator
{
    // The Operator class should contain an instance of a HashMap
    // This map will use keys as the tokens we're interested in,
    // and values will be instances of the Operators.
    // ALL subclasses of operator MUST be in their own file.
    // Example:
    // Where does this declaration go? What should its access level be?
    // Class or instance variable? Is this the right declaration?
    // HashMap operators = new HashMap();
    // operators.put( "+", new AdditionOperator() );
    // operators.put( "-", new SubtractOperator() );

    static private final HashMap<String, Operator> myOperators = new HashMap<String, Operator>();
    static {
        myOperators.put("+", new AddOperator());
        myOperators.put("-", new SubtractOperator());
        myOperators.put("*", new MultiplyOperator());
        myOperators.put("/", new DivideOperator());
        myOperators.put("^", new PowerOperator());
        myOperators.put("(", new OpenOperator());
    }

    public abstract int priority();
    public abstract Operand execute(Operand op1, Operand op2 );

    /**
     * determines if a given token is a valid operator.
     * please do your best to avoid static checks
     * for example token.equals("+") and so on.
     * Think about what happens if we add more operators.
     */
    public static boolean check( String token )
    {
        for(int i = 0; i < token.length(); i++)
        {
            //Use a for loop to check each character of token string to see if any character matches operator regex.
            if(String.valueOf(token.charAt(i)).matches("[-+*/^()]"))
            {
                return true;
            }
        }
        //If for loop finishes without any matches to the regex, return false.
        return false;
    }

    public static Operator getOperator(String token)
    {
        for(int i = 0; i < token.length(); i++)
        {
            //Use a for loop to check each character of token string to see if any character matches operator regex.
            if(String.valueOf(token.charAt(i)).matches("[-+*/^(]"))
            {
                if(token.charAt(i) == '+')
                {
                    return myOperators.get("+");
                }
                else if(token.charAt(i) == '-')
                {
                    return myOperators.get("-");
                }
                else if(token.charAt(i) == '*')
                {
                    return myOperators.get("*");
                }
                else if(token.charAt(i) == '/')
                {
                    return myOperators.get("/");
                }
                else if(token.charAt(i) == '^')
                {
                    return myOperators.get("^");
                }
                else if(token.charAt(i) == '(')
                {
                    return myOperators.get("(");
                }
            }
        }
        return null;
    }
}
