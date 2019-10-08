import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;


public class ResultsPanel extends JPanel{
	private JTextField pathField = new JTextField(50);
	
	public ResultsPanel() {
		setSize(100,600);

		
		JButton pathSubmit = new JButton("Submit");
		
	
		 
		add(new JLabel("Enter Path of Review Camp CSV file:"));
		add(pathField);
		add(pathSubmit);
		
		setVisible(true);
		
	}
}	