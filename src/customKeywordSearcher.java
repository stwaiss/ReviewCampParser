import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class customKeywordSearcher {
	
	public JFrame popUpWindow = new JFrame();
	public static String[] customKeywords;
	public JTextField input;
	public JTextArea textArea;
	public int count = 0;
	public String newKeyword;
	
	public customKeywordSearcher() {
		newKeyword = "";
		
		customKeywords = new String[5];
		customKeywords[0] = "";
		customKeywords[1] = "";
		customKeywords[2] = "";
		customKeywords[3] = "";
		customKeywords[4] = "";
		
		if(Parser.getReviews().size() != 0) {				
					
			//Boilerplate JFrame methods
			popUpWindow.setTitle("Review Camp Parser - Custom Keyword Search");
			popUpWindow.setSize(400,350);
			popUpWindow.setLayout(new BorderLayout());
			//Try and open up the icon file to be assigned to the window.
			try {
				popUpWindow.setIconImage(ImageIO.read(new File("SPB icon.png")));
			} catch (IOException e) {
				
			}
			
			input = new JTextField(8);
			input.setText("");
			input.setFocusable(true);
			input.addKeyListener(new inputEnterKeyListener());
			JButton addButton = new JButton("Add");
			addButton.addActionListener(new addButtonListener());
			
			//create a wrapper to hold the controlling components
			JPanel north = new JPanel();

			north.add(new JLabel("Enter up to 5 keywords"));
			north.add(input);
			north.add(addButton);
			
			popUpWindow.add(north, BorderLayout.NORTH);
							
			textArea = new JTextArea(10,10);
			
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
			
			popUpWindow.add(keywordPanel, BorderLayout.CENTER);
			
			JButton runButton = new JButton("Run");
			runButton.addActionListener(new runButtonListener());
			
			JPanel south = new JPanel();
			south.add(runButton);
			popUpWindow.add(south, BorderLayout.SOUTH);
			
			popUpWindow.setVisible(true);
			
		}
		else {
			return;
		}
		
//		popUpWindow.setVisible(false);
//		textArea.setText("");
//		input.setText("");
//		newKeyword = "";
//		
//		JFreeChart barChart = ChartFactory.createBarChart(
//				"Star Ratings of Custom Keywords",
//				"Keyword",
//				"Count",
//				createDataset(),
//				PlotOrientation.VERTICAL,
//				true,
//				true,
//				false);
//		
//		ChartPanel myChart = new ChartPanel(barChart);
//		Parser.graphPanel.add(myChart, BorderLayout.CENTER);
//		Parser.graphPanel.validate();
	
	}	

		
	private static CategoryDataset createDataset() {
			
			
		// [0] is keyword
		// [1] - [5] is star counts
		// [6] is sum of star counts
		String[][] table = {
				{"", "0", "0" ,"0", "0" ,"0", "0"},
				{"", "0", "0" ,"0", "0" ,"0", "0"},
				{"", "0", "0" ,"0", "0" ,"0", "0"},
				{"", "0", "0" ,"0", "0" ,"0", "0"},
				{"", "0", "0" ,"0", "0" ,"0", "0"}
		};
		
		//create labels for each bar in the bar graph
		final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"};
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		
		List<Review> allReviewsList = Parser.getReviews();
		
		String[] kw = getCustomKeywords();
		//copy the custom keywords into the first column of the table
		for(int i = 0; i < 5; i++) {
			table[i][0] = kw[i];
		}
		
		//Iterate over all reviews
		for(int i = 0; i < allReviewsList.size(); i++) {
			//Iterate over all keywords
			for(int j = 0; j < 5; j++) {
				if(allReviewsList.get(i).getBody().contains(table[j][0])) {
					//switch case depending on value, and then add to that count 
					double thisStarDouble = Double.valueOf(allReviewsList.get(i).getStar());
					int thisStarInt = (int) thisStarDouble; 
					
					
					int star = 0;
					switch(thisStarInt) {
					case 1:
						star = Integer.valueOf(table[j][1]);
						table[j][1] = String.valueOf(++star);
						break;
					case 2:
						star = Integer.valueOf(table[j][2]);
						table[j][2] = String.valueOf(++star);
						break;
					case 3:
						star = Integer.valueOf(table[j][3]);
						table[j][3] = String.valueOf(++star);
						break;
					case 4:
						star = Integer.valueOf(table[j][4]);
						table[j][4] = String.valueOf(++star);
						break;	
					case 5:
						star = Integer.valueOf(table[j][5]);
						table[j][5] = String.valueOf(++star);
						break;	
					default:
						break;	
					}//End switch
				}//End if
			}//End inner loop
		}//End outer loop
		
		
		//save data to graph
		for(int i = 0; i < 5; i++) {
			
			if(!table[i][0].isEmpty()) {
				//Calculate total sum of each keyword
				int sum = 0;
				
				for(int j = 1; j < 6; j++) {
					sum += Integer.valueOf(table[i][j]);
				}
				
				//value, series, label
				dataset.addValue(Integer.valueOf(table[i][1]), starLabels[0], table[i][0]);
				dataset.addValue(Integer.valueOf(table[i][2]), starLabels[1], table[i][0]);
				dataset.addValue(Integer.valueOf(table[i][3]), starLabels[2], table[i][0]);
				dataset.addValue(Integer.valueOf(table[i][4]), starLabels[3], table[i][0]);
				dataset.addValue(Integer.valueOf(table[i][5]), starLabels[4], table[i][0]);
				//dataset.addValue(sum, "Total", table[i][0]);
			}
		
		}
		
			
		return dataset;
	}
	
	public class addButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String newKeyword = "";
			newKeyword = input.getText().trim().toLowerCase();
			
			if(!newKeyword.isEmpty() && count < 5) {
				customKeywords[count] = newKeyword;
				count++;
				
				textArea.append(newKeyword + "\n");
				input.setText("");
				input.setFocusable(true);
			}
		}
	}


	public class inputEnterKeyListener implements KeyListener{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				String newKeyword = "";
				newKeyword = input.getText().trim().toLowerCase();
				
				if(!newKeyword.isEmpty() && count < 5) {
					customKeywords[count] = newKeyword;
					count++;
					
					textArea.append(newKeyword + "\n");
					input.setText("");
					input.setFocusable(true);
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	public class runButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			JFreeChart barChart = ChartFactory.createBarChart(
					statsPanel.pSkuText.getText() + " - Star Ratings of Custom Keywords",
					"Keyword",
					"Count",
					createDataset(),
					PlotOrientation.VERTICAL,
					true,
					true,
					false);
			
			ChartPanel myChart = new ChartPanel(barChart);
			Parser.graphPanel.removeAll();
			Parser.graphPanel.updateUI();
			Parser.graphPanel.add(myChart, BorderLayout.CENTER);
			Parser.graphPanel.validate();
			
			for(int i = 0; i<5 ; i++) {
				customKeywords[i] = "";
			}
			popUpWindow.setVisible(false);
			textArea.setText("");
			input.setText("");
		}
	}
	
	public String getNewKeyword() {
		return newKeyword;
	}
	
	public void setNewKeyword(String s) {
		newKeyword = s;
	}
	
	public static String[] getCustomKeywords() {
		return customKeywords;
	}	
}
