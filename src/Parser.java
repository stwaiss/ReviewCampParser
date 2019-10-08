import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.BorderLayout;


public class Parser extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static pathPanel pp;
	public static ResultsPanel rp;
	private static List<Review> reviews = new ArrayList<Review>();
				
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
		consolePanel.add(scroll);
		
		rp = new ResultsPanel();
		add(rp,BorderLayout.CENTER);
		
		repaint();
		
		
		validate();
		 
	}
	
	public static List<Review> getReviews() {
		return reviews;
	}
	
	public static void setReviews(List<Review> r){
		reviews = r;
	}
}	