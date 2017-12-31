// Postfix Calculator Applet
//
// CS 201 HW 8  
// Connor Hanify
// Professor Scharstein

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; // for Stack

import apple.laf.JRSUIConstants.Size;

public class Calc extends Applet implements ActionListener {

    // instance variables

    Label result;         // label used to show result
    Stack<Integer> stack; // stack used for calculations
    int current;          // current number being entered
    boolean entered;      // whether current number has been entered
                          // if so, show number in red
    
    // local color constants
    static final Color black = Color.black;
    static final Color white = Color.white;
    static final Color red = Color.red;
    static final Color green = Color.green;
    static final Color blue = Color.blue;
    static final Color yellow = Color.yellow;
    static final Color dred = new Color(160, 0, 100);
    static final Color dgreen = new Color(0, 120, 90);
    static final Color dblue = Color.blue.darker();

    // init is main method of applet and creates the calc
    public void init() {
    	stack = new Stack<Integer>(); //initialize stack
    	current = 0; //set current to 0
    	entered = false; //set entered to false

        this.setLayout(new BorderLayout()); //overall layout is Border, but
        // other layouts will be nested inside each panel.
        this.setBackground(blue);
        // set font, makes it bold and sets size
        this.setFont(new Font("Times", Font.BOLD, 20));
        //calls panels bottomDisplay, numberDisplay, topDisplay and 
        //functionDisplay
        this.add("South", bottomDisplay());
        this.add("Center", numberDisplay());
        this.add("North", topDisplay());
        this.add("East", functionDisplay());
        
        //initialize the first number(0) to be green
        show (current); 
    }
    
    //Initializes the "result" display at the top of the calc
    // Used a border layout within this panel to create a blue
    // border. Set alignment of label to the right in order
    // to have result numbers justified right.
    protected Panel topDisplay(){
        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout());
        result = new Label("result");
        Panel empty1 = new Panel();
        Panel empty2 = new Panel();
        Panel empty3 = new Panel();
        Panel empty4 = new Panel();
        Panel result1 = new Panel(); 
        
        result1.setLayout(new BorderLayout());
        result.setAlignment(Label.RIGHT);
        result1.add(result);
        
        p2.setBackground(white);
        empty1.setBackground(blue);
        empty2.setBackground(blue);
        empty3.setBackground(blue);
        empty4.setBackground(blue);
        
        p2.add("Center", result1);
        p2.add("North", empty2);
        p2.add("East", empty3);
        p2.add("South", empty1);
        p2.add("West", empty4);
        return p2;
    }
    
    //Initializes the number pad display
	protected Panel numberDisplay(){
    	Panel p1 = new Panel();
    	p1.setLayout(new GridLayout(4, 3, 2, 2));
        p1.add(CButton("7", dgreen, yellow));
        p1.add(CButton("8", dgreen, yellow));
        p1.add(CButton("9", dgreen, yellow));
        p1.add(CButton("4", dgreen, yellow));
        p1.add(CButton("5", dgreen, yellow));
        p1.add(CButton("6", dgreen, yellow));
        p1.add(CButton("1", dgreen, yellow));
        p1.add(CButton("2", dgreen, yellow));
        p1.add(CButton("3", dgreen, yellow));
        p1.add(CButton("0", dgreen, yellow));
        p1.add(CButton("(-)", dgreen, yellow));
        p1.add(CButton("Pop", dgreen, yellow));  
        return p1;
	}
	
	//Initializes the + - / and * buttons on the right
	protected Panel functionDisplay(){
        Panel p3 = new Panel();
        p3.setLayout(new GridLayout(4, 1));
        p3.add(CButton("+", dgreen, dred));
        p3.add(CButton("-", dgreen, dred));
        p3.add(CButton("*", dgreen, dred));
        p3.add(CButton("/", dgreen, dred));
        return p3;
	}
	
	//Initializes bottom enter and clear buttons
    protected Panel bottomDisplay() {
    	Panel p4 = new Panel();
        p4.setLayout(new GridLayout(1, 2));
        p4.add(CButton("Enter", blue, dgreen));
        p4.add(CButton("Clear", blue, dgreen));
        return p4;
    }
    
    // a useful helper methods, given to you for free!
    // create a colored button
    protected Button CButton(String s, Color fg, Color bg) {
        Button b = new Button(s);
        b.setBackground(bg);
        b.setForeground(fg);
        b.addActionListener(this);
        return b;
    }

    // handle button clicks
    // Have to pass some methods the parameter int n which
    // is converted from string to integer.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            String label = ((Button)e.getSource()).getLabel();
            if (label.equals("+"))
                add(Integer.parseInt(result.getText()));
            else if (label.equals("-"))
                sub(Integer.parseInt(result.getText()));
            else if (label.equals("*"))
                mult(Integer.parseInt(result.getText()));
            else if (label.equals("/"))
                div(Integer.parseInt(result.getText()));
            else if (label.equals("(-)"))
                negative(Integer.parseInt(result.getText()));
            else if (label.equals("Pop"))
                pop(Integer.parseInt(result.getText()));
            else if (label.equals("Enter"))
                enter(Integer.parseInt(result.getText()));
            else if (label.equals("Clear"))
                clear();
            else {     // number buttons
                int n = Integer.parseInt(label);
                number(n);
            }
        }
    }

    // display number n in result label.
    // Include check whether entered is true or not to determine
    // coloring of the number in result panel. (true = red, false
    // = green).
    protected void show(int n) {
    	if (entered == true) {
        	result.setForeground(red);
        } else {
        	result.setForeground(dgreen);
        }
        result.setText(Integer.toString(n));
    }
    
    //Add method gets passed int n parameter. If number is not
    // entered then enter it and then add. However, if stack size
    // is 1 or 0 then just show the number.
    protected void add(int n) {
    	if (entered == false){
    		enter(n);
    	} 
    	if (stack.size() >= 2) {
	        int number1 = stack.pop();
	        int number2 = stack.pop();
	        int number3 = (number2 + number1);
	        stack.push(number3);
	        show(number3);
	        System.out.println(stack);
    	} else {
    		show(n);
    	}
    }
    
    // Sub method gets passed int n parameter. If number is not
    // entered then enter it and then do subtraction. However, if stack
    // size is 1 or 0 then just show the negative of the number.
    protected void sub(int n) {
    	if (entered == false){
    		enter(n);
    	} 
    	if (stack.size() >= 2) {
	        int number1 = stack.pop();
	        int number2 = stack.pop();
	        int number3 = (number2 - number1);
	        stack.push(number3);
	        show(number3);
	        System.out.println(stack);
		} else {
			show(-n);
		}
    }
    
    // Mult method gets passed int n parameter. If number is not
    // entered then enter it and then multiply. However, if stack
    // size is 1 or 0 in size then just show the number.
    protected void mult(int n) {
    	if (entered == false){
    		enter(n);
    	} 
    	if (stack.size() >= 2) {
	        int number1 = stack.pop();
	        int number2 = stack.pop();
	        int number3 = (number2 * number1);
	        stack.push(number3);
	        show(number3);
	        System.out.println(stack);
    	} else {
    		show(n);
    	}
    }
    
    // Div method gets passed int n parameter. If number is not
    // entered then enter it and then divide. However, if stack
    // is 1 or 0 in size then just show the number.
    protected void div(int n) {
       	if (entered == false){
    		enter(n);
    	} 
    	if (stack.size() >= 2) {
	        int number1 = stack.pop();
	        int number2 = stack.pop();
	        int number3 = (number2 / number1);
	        stack.push(number3);
	        show(number3);
	        System.out.println(stack);
    	} else {
    		show(n);
    	}
    }
    
    // Enter method takes int n as a parameter and pushes it on the 
    // stack, changes entered to true and current to 0, then shows n.
    protected void enter(int n) {
    	stack.push(n);
    	entered = true;
    	current = 0; 
    	show(n);
    	//debug(); // print statements for debugging
    }
    
    // Clear method clears the stack (using built in clear method)
    // , sets current to 0, entered to false and calls number 0. 
    protected void clear() {
    	stack.clear();
    	current = 0;
    	entered = false;
    	number(0);
    	//debug();
    }
    
    // Negative method takes int n as a parameter. If n is not entered, then
    // enter it as a negative. If n is entered, then show it as a negative of
    // itself. 
    protected void negative(int n) {
    	if (entered == false){
    		enter(-n);
    		//debug();
    	} else {
    		show(-n);
    	}
    }
    
    // Pop method takes int n as a parameter. Method must account
    // for if number has been entered yet and if stack has enough
    // entries to pop one off. 
    protected void pop(int n) {
    	if (entered == true){
    		stack.pop();
    		entered = true;
    		if (stack.size() > 1){
		    	show(stack.peek());
		    	current = stack.peek();
		    	show(n);
		    	debug();
    		} else {
    			current = 0;
		    	show(0);
		    	debug();
    		}
    	} else if (entered == false) {
    		current = 0;
			entered = true;
    		if (stack.size() >= 1){
	    		show(stack.peek());
	    		debug();
    		} else {
    			show(0);
	    		debug();
    		}
    	}
    }
    
    // Debugging method used to print statements
    protected void debug(){
    	System.out.println(stack);
    	System.out.println(entered);
    	System.out.println(current);
    }
    
    // handle number buttons. Takes int n as a parameter.
    // Changes entered to false, sets result to be green and
    // changes current to allow for multiple digit numbers.
    // Finally shows current. 
    protected void number(int n) {
    	entered = false; 
    	result.setForeground(dgreen);
    	current = ((current * 10) + n);
        show(current);
    }
}
