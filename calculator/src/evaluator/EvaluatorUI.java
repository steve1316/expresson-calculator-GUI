package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorUI extends JFrame implements ActionListener {

    private TextField txField = new TextField();
    private Panel buttonPanel = new Panel();

    private static boolean check = false;

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] bText = {
        "7", "8", "9", "+", "4", "5", "6", "- ", "1", "2", "3",
        "*", "0", "^", "=", "/", "(", ")", "C", "CE"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression, clears last entry up until the last operator.
     */
    private Button[] buttons = new Button[bText.length];

    public static void main(String argv[]) {
        EvaluatorUI calc = new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.txField.setPreferredSize(new Dimension(600, 50));
        this.txField.setFont(new Font("Courier", Font.BOLD, 28));

        add(txField, BorderLayout.NORTH);
        txField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        Button bt;
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            bt = new Button(bText[i]);
            bt.setFont(new Font("Courier", Font.BOLD, 28));
            buttons[i] = bt;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent arg0) {
        //The string token will take in the string form of the operand or operator button that the user clicked.
        String token = arg0.getActionCommand();

        //If the check boolean was set to true, that means that the calculator already computed an expression.
        //So when the user enters in a new expression, clear the text field and then set check to false for the next expression.
        if(check)
        {
            this.txField.setText("");
            check = false;
        }

        if(token.equalsIgnoreCase("C") || token.equalsIgnoreCase("CE"))
        {
            this.txField.setText("");
        }
        else if(token.equalsIgnoreCase("="))
        {
            //Create a Evaluator object.
            Evaluator myEval = new Evaluator();
            //Send the expression to the evaluator to compute it.
            int result = myEval.eval(this.txField.getText());
            //Convert the result from integer to string.
            String stringResult = Integer.toString(result);
            this.txField.setText(stringResult);
            check = true;
        }
        else
        {
            //If the user did not clear or equaled the calculator, add the operand or operator to the text field.
            this.txField.setText(this.txField.getText()+arg0.getActionCommand());
        }
    }
}
