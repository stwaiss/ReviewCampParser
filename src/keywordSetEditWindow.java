import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class keywordSetEditWindow extends JFrame{
	
	static JComboBox comboBox;
	static JTextField input;
	static JButton addButton;
	static JTextArea textArea = new JTextArea(20,10);
	
	
	public keywordSetEditWindow() {
		//Boilerplate JFrame methods
		setTitle("Review Camp Parser - Keyword Viewer");
		setSize(400,500);
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
			allKeywordSetsList.add(0, new keywordSet(""));
		}
		
		String[] allProductTypesArray = new String[allKeywordSetsList.size()];
		//save just the product types into an array
		for(int i = 0; i < allProductTypesArray.length; i++) {
			allProductTypesArray[i] = allKeywordSetsList.get(i).getProductType();
		}
		
		Arrays.sort(allProductTypesArray);
			
		//populate the drop down list and add the action listener
		comboBox = new JComboBox(allProductTypesArray);
		comboBox.addActionListener(new comboBoxActionListener());
		
		//initialize extra components
		input = new JTextField(8);
		addButton = new JButton("Add");
		addButton.addActionListener(new addButtonListener());
		
		//create a wrapper to hold the controlling components
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(2,1,5,5));
		
		//create panel to hold a label and the dropdown
		JPanel northUpper = new JPanel();
		northUpper.add(new JLabel("Select product type"));
		northUpper.add(comboBox);
		
		north.add(northUpper);
		
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
		
		//add the scrollable wrapper to the panel, and then add to the frame
		JPanel keywordPanel = new JPanel();
		keywordPanel.setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		keywordPanel.add(scrollPane);
		add(keywordPanel, BorderLayout.CENTER);
		
		
		repaint();
		validate();
	}
	
	
	public class comboBoxActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("In the listener");
			
			//pull the string that was selected from the drop down
			JComboBox cb = (JComboBox) e.getSource();
			String selectedProductType = (String) cb.getSelectedItem();

			//pull the list of all keyword sets and save locally
			List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
			
			keywordSet selection = new keywordSet();
			
			//iterate over all the keyword sets to find the one with the selected product type
			for (int i = 0; i < allKeywordSetsList.size(); i++) {
				if(allKeywordSetsList.get(i).getProductType().equalsIgnoreCase(selectedProductType)) {
					selection = allKeywordSetsList.get(i);
				}
			}
			
			keywordSetEditWindow.textArea.setText("");
		
			//pull the list of keywords from that product type and print to the text area
			for(int i = 0; i < selection.keywordList.size(); i++) {
				keywordSetEditWindow.textArea.append(selection.keywordList.get(i) + "\n");
			}
							
		}
	}

	public class addButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String selectedProductType = "";
			selectedProductType = comboBox.getSelectedItem().toString();
			
			String newKeyword = "";
			newKeyword = input.getText().trim();
			
			if(!selectedProductType.isEmpty() && !newKeyword.isEmpty()) {
				//pull the list of all keyword sets and save locally
				List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
				
				keywordSet selection = new keywordSet();
				
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
			}			
		}
	}

}
