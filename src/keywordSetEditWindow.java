import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class keywordSetEditWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	static JComboBox divisionComboBox, productTypeComboBox;
	static JTextField input;
	static JButton addButton;
	static JTextArea textArea = new JTextArea(20,10);
	
	
	@SuppressWarnings("unchecked")
	public keywordSetEditWindow() {
		//Boilerplate JFrame methods
		setTitle("Telescope " + application.VERSION_NUMBER + " - Keyword Viewer");
		setSize(400,550);
		setLayout(new BorderLayout());
		//Try and open up the icon file to be assigned to the window.
		try {
			setIconImage(ImageIO.read(new File("SPB icon.png")));
		} catch (IOException e) {
			
		}
		
		//save all the keyword sets locally
		List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
		
		//insert a blank one for the drop down list
		if(!allKeywordSetsList.get(0).getProductType().isEmpty()) {
			allKeywordSetsList.add(0, new keywordSet("", "", new ArrayList<String>()));
		}

		//Create combo box for divisions
		String[] allDivisionsArray = {"GPC", "HHI", "HPC", "H&G"};
			
		Arrays.sort(allDivisionsArray);
		
		
		//populate the drop down list and add the action listener
		divisionComboBox = new JComboBox(allDivisionsArray);
		divisionComboBox.addActionListener(new divisionToProductTypeComboBoxActionListener());		
			
		
		//populate the drop down list and add the action listener
		productTypeComboBox = new JComboBox();
		productTypeComboBox.addActionListener(new productTypetoKeywordComboBoxActionListener());
		
		//initialize extra components
		input = new JTextField(8);
		input.addKeyListener(new inputEnterListener());
		addButton = new JButton("Add");
		addButton.addActionListener(new addButtonListener());
		
		//create a wrapper to hold the controlling components
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(3,1,5,5));
		
		//create panel to hold a label and the division dropdown
		JPanel northUpper = new JPanel();
		northUpper.add(new JLabel("Select division"));
		northUpper.add(divisionComboBox);
		
		north.add(northUpper);
		
		
		//create panel to hold a label and the product type dropdown
		JPanel northMiddle = new JPanel();
		northMiddle.add(new JLabel("Select product type"));
		northMiddle.add(productTypeComboBox);
		
		north.add(northMiddle);
		
		//create a panel to hold a label, the input bar, and the button
		JPanel northLower = new JPanel();
		northLower.add(new JLabel("Enter keyword to be added"));
		northLower.add(input);
		northLower.add(addButton);
		
		north.add(northLower);
		
		add(north, BorderLayout.NORTH);
		
		//Create a scrollable wrapper for the list of keywords
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		JScrollPane scrollPane = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//Look and Feel changes font for the text field, so change it back!
		textArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		
		//add the scrollable wrapper to the panel, and then add to the frame
		JPanel keywordPanel = new JPanel();
		keywordPanel.setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		keywordPanel.add(scrollPane);
		add(keywordPanel, BorderLayout.CENTER);
		
		
		repaint();
		validate();
	}
	
	public class divisionToProductTypeComboBoxActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			//pull the string that was selected from the drop down
			JComboBox cb = (JComboBox) e.getSource();
			String selectedDivision = (String) cb.getSelectedItem();
			
			//pull the list of all keyword sets and save locally
			List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
			List<keywordSet> allKeywordSetsInDivision = new ArrayList<keywordSet>();
			
			//iterate over all the keyword sets to find the ones with the selected division
			for(int i = 0; i < allKeywordSetsList.size(); i++) {
				if(allKeywordSetsList.get(i).getDivision().equalsIgnoreCase(selectedDivision)) {
					allKeywordSetsInDivision.add(allKeywordSetsList.get(i));
				}
			}

			//create new array to hold strings of keywords from the division that was selected
			String[] keywordStringArray = new String[allKeywordSetsInDivision.size()];
			
			//populate array
			for(int i = 0; i < keywordStringArray.length; i++) {
				keywordStringArray[i] = allKeywordSetsInDivision.get(i).getProductType();
			}
			
			Arrays.sort(keywordStringArray);
			
			keywordSetEditWindow.productTypeComboBox.removeAllItems();
			
			for(int i = 0; i < keywordStringArray.length; i++) {
				keywordSetEditWindow.productTypeComboBox.addItem(keywordStringArray[i]);
			}
		}
	}
	
	
	
	public class productTypetoKeywordComboBoxActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("In the listener");
			
			//pull the string that was selected from the drop down
			JComboBox cb = (JComboBox) e.getSource();
			String selectedProductType = (String) cb.getSelectedItem();

			//pull the list of all keyword sets and save locally
			List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
			
			keywordSet selection = new keywordSet("","", null);
			
			//iterate over all the keyword sets to find the one with the selected product type
			for (int i = 0; i < allKeywordSetsList.size(); i++) {
				if(allKeywordSetsList.get(i).getProductType().equalsIgnoreCase(selectedProductType)) {
					selection = allKeywordSetsList.get(i);
				}
			}
			
			keywordSetEditWindow.textArea.setText("");
		
			
			if(selection.getKeywordList() != null) {
				//pull the list of keywords from that product type and print to the text area
				for(int i = 0; i < selection.keywordList.size(); i++) {
					keywordSetEditWindow.textArea.append(selection.keywordList.get(i) + "\n");
				}
			}						
		}
	}

	public class addButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String selectedProductType = "";
			selectedProductType = productTypeComboBox.getSelectedItem().toString();
			
			String newKeyword = "";
			newKeyword = input.getText().trim();
			
			if(!selectedProductType.isEmpty() && !newKeyword.isEmpty()) {
				//pull the list of all keyword sets and save locally
				List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
				
				keywordSet selection = new keywordSet("","",null);
				
				//iterate over all the keyword sets to find the one with the selected product type
				for (int i = 0; i < allKeywordSetsList.size(); i++) {
					if(allKeywordSetsList.get(i).getProductType().equalsIgnoreCase(selectedProductType)) {
						selection = allKeywordSetsList.get(i);
					}
				}
				
				selection.keywordList.add(newKeyword);
				
				keywordSetEditWindow.textArea.setText("");
				
				//pull the list of keywords from that product type and print to the text area
				for(int i = 0; i < selection.keywordList.size(); i++) {
					keywordSetEditWindow.textArea.append(selection.keywordList.get(i) + "\n");
				}
				
				Parser.writeOutKeywordSets();
				
				input.setText("");
			}			
		}
	}
	
	public class inputEnterListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				String selectedProductType = "";
				selectedProductType = productTypeComboBox.getSelectedItem().toString();
				
				String newKeyword = "";
				newKeyword = input.getText().trim();
				
				if(!selectedProductType.isEmpty() && !newKeyword.isEmpty()) {
					//pull the list of all keyword sets and save locally
					List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
					
					keywordSet selection = new keywordSet("","",null);
					
					//iterate over all the keyword sets to find the one with the selected product type
					for (int i = 0; i < allKeywordSetsList.size(); i++) {
						if(allKeywordSetsList.get(i).getProductType().equalsIgnoreCase(selectedProductType)) {
							selection = allKeywordSetsList.get(i);
						}
					}
					
					selection.keywordList.add(newKeyword);
					
					keywordSetEditWindow.textArea.setText("");
					
					//pull the list of keywords from that product type and print to the text area
					for(int i = 0; i < selection.keywordList.size(); i++) {
						keywordSetEditWindow.textArea.append(selection.keywordList.get(i) + "\n");
					}
					
					Parser.writeOutKeywordSets();
					
					input.setText("");
				}			
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
