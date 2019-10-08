import java.io.*;
import java.util.List;
import java.util.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;

public class Parser extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static pathPanel pp;
	
	private JTextField pathField = new JTextField(50);
	private JTextField totalReviewCount = new JTextField(5);
	
	public static void main(String[] args) {
		Parser window = new Parser();
		window.setVisible(true);
		}
		
	public Parser() {
		//Boilerplate JFrame methods
		setTitle("Review Camp Parser");
		setSize(1000,1000);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Add PathPanel at top of JFrame
		pp = new pathPanel();
		add(pp, BorderLayout.NORTH);
		
		//Set default console output as JTextArea
		//Create console
		JTextArea taConsole = new JTextArea(10,30);
		
		//Create a scroll-able wrapper
		JScrollPane scroll = new JScrollPane(taConsole);
		PrintStream out = new PrintStream (new TextAreaOutputStream (taConsole));
		System.setOut(out);
		
		//Add a new JPanel for the console and add to JFrame
		JPanel consolePanel = new JPanel();
		add(consolePanel,BorderLayout.WEST);
		//consolePanel.setSize(200,600);
		consolePanel.add(scroll);
		
		
		
		
		validate();
		 
	}
}	