import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class keywordSetViewOnlyWindow extends JFrame{
	
	static JComboBox comboBox;
	static JTextArea textArea;
	
	
	public keywordSetViewOnlyWindow() {
		//Boilerplate JFrame methods
		setTitle("Review Camp Parser - Keyword Viewer");
		setSize(500,500);
		setLayout(new BorderLayout());
		
		List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
		String[] allProductTypesArray = new String[allKeywordSetsList.size()];
		
		for(int i = 0; i < allProductTypesArray.length; i++) {
			allProductTypesArray[i] = allKeywordSetsList.get(i).getProductType();
		}
			
		comboBox = new JComboBox(allProductTypesArray);
		comboBox.addActionListener(new comboBoxActionListener());
		
		JPanel north = new JPanel();
		north.add(new JLabel("Select Product Type"));
		north.add(comboBox);
		
		
		
		add(north, BorderLayout.NORTH);
		
		JTextArea textArea = new JTextArea(15,30);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		JScrollPane scrollPane = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel keywordPanel = new JPanel();
		keywordPanel.setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		add(keywordPanel,BorderLayout.WEST);
		keywordPanel.add(scrollPane);
		
		add(keywordPanel, BorderLayout.CENTER);
		
		
		repaint();
		validate();
	}
	
	
	public class comboBoxActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String selectedProductType = (String) cb.getSelectedItem();
			
			List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
			
			keywordSet selection = new keywordSet();;
			
			for (int i = 0; i < allKeywordSetsList.size(); i++) {
				if(allKeywordSetsList.get(i).getProductType().equalsIgnoreCase(selectedProductType)) {
					selection = allKeywordSetsList.get(i);
				}
			}
			
			
			String data = "";
			
			for(int i = 0; i < selection.keywordList.size(); i++) {
				data += selection.keywordList.get(i) + "\n";
			}
			
			keywordSetViewOnlyWindow.textArea.setText(data);
			
			
			
		}
	}
}
