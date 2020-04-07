package edu.csc413.calculator.evaluator;

import java.text.NumberFormat;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {

  private int myOperand;

  /**
  * construct operand from string token.
  */  
  public Operand( String token ) {
    myOperand = Integer.parseInt(token);
  }
  /**
   * construct operand from integer
   */
  public Operand( int value ) {
    myOperand = value;
  }
  /**
   * return value of opernad
   */
  public int getValue() {
      return myOperand;
  }
  /**
   * Check to see if given token is a valid
   * operand.
   */
  public static boolean check( String token )
  {
    //Put everything into a try catch block where the Integer class will convert token string to integer and if it failed, return false. Otherwise return true.
    try
    {
      Integer.parseInt(token);
    }
    catch(NumberFormatException e)
    {
      return false;
    }

    return true;
  }
}
