import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class choicePanel extends JPanel{
	
	private static JRadioButton[] options = new JRadioButton[5];
	ButtonGroup group = new ButtonGroup();
	public choicePanel() {
		
		//set border and layout type
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		
		//define options array to include 4 JRadioButtons
		options[0] = new JRadioButton("By Star Rating");
		options[0].addActionListener(new totalStarRatingListener());
		
		options[1] = new JRadioButton("By Star Rating vs Year");
		options[1].addActionListener(new starRatingVsYearListener());
		
		options[2] = new JRadioButton("By Star Rating vs Seller");
		options[2].addActionListener(new starRatingVsSellerListener());
		
		options[3] = new JRadioButton("Keyword Pareto");
		options[3].addActionListener(new keywordParetoListener());
		
		options[4] = new JRadioButton("Custom Keyword Search");
		options[4].addActionListener(new customKeywordSearchListener());
		
		//add the radio buttons to the group and to the pane, and also add separators for space
		for (JRadioButton b: options) {
			group.add(b);
			add(b);
			this.add(Box.createRigidArea(new Dimension(0,15)));
		}	
	}
	
	//*******************************************************************************************************
	public static class totalStarRatingListener  implements ActionListener {		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				JFreeChart barChart = ChartFactory.createBarChart(
						"Total Star Ratings",
						"Star Rating",
						"Count",
						createDataset(),
						PlotOrientation.VERTICAL,
						true,
						true,
						false);
				
				ChartPanel myChart = new ChartPanel(barChart);
				Parser.graphPanel.add(myChart, BorderLayout.CENTER);
				Parser.graphPanel.validate();
				
			}
			else {
				return;
			}


		}
		
		private CategoryDataset createDataset() {
			//create labels for each bar in the bar graph
			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"};
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			dataset.addValue(Integer.parseInt(statsPanel.starStatTextFields[6].getText()), "Series 1", starLabels[0]);
			dataset.addValue(Integer.parseInt(statsPanel.starStatTextFields[5].getText()), "Series 1", starLabels[1]);
			dataset.addValue(Integer.parseInt(statsPanel.starStatTextFields[4].getText()), "Series 1", starLabels[2]);
			dataset.addValue(Integer.parseInt(statsPanel.starStatTextFields[3].getText()), "Series 1", starLabels[3]);
			dataset.addValue(Integer.parseInt(statsPanel.starStatTextFields[2].getText()), "Series 1", starLabels[4]);
			
			return dataset;
		}
	}
	
	//*******************************************************************************************************
	
	public static class starRatingVsYearListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				JFreeChart barChart = ChartFactory.createBarChart(
						"Star Ratings Per Year",
						"Star Rating",
						"Count",
						createDataset(),
						PlotOrientation.VERTICAL,
						true,
						true,
						false);
				
				ChartPanel myChart = new ChartPanel(barChart);
				Parser.graphPanel.add(myChart, BorderLayout.CENTER);
				Parser.graphPanel.validate();
				
			}
			else {
				return;
			}
		}

		private CategoryDataset createDataset() {	

			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"}; 
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			// pull the years from year distribution 
			String[][] yearDistribution = statsPanel.yearDistribution();

			// create a year x 6 array to hold star ratings per year 
			int[][] reviewsPerYear = new int[5][6];

			//iterate through array and assign the years into the reviewsPerYear, year column 
			for(int i = 0; i < 5; i++) { 
				if(yearDistribution[i][0] != null) {
					reviewsPerYear[i][0] = Integer.valueOf(yearDistribution[i][0]); 
				} 
			}

			//Save the master list of parsed reviews locally 
			List<Review> allReviewsList = Parser.getReviews();

			//Iterate over all the years 
			for(int i = 0; i < 5; i++) { 
				//Iterate over all the reviews 
				for(Review r : allReviewsList) {

					//Pull the year out from the date of the current review 
					String thisReviewsYear = r.getDate().split("-")[0];
	
					//check to see if the year from this current review is the same as the year under analysis 
					if(Integer.valueOf(thisReviewsYear) == reviewsPerYear[i][0]) {
	
						//switch case depending on value, and then add to that count 
						double thisStarDouble = Double.valueOf(r.getStar());
						int thisStarInt = (int) thisStarDouble; 
						
						switch(thisStarInt) { 
							case 1: 
								reviewsPerYear[i][1]++; 
								break;
			
							case 2: 
								reviewsPerYear[i][2]++;
								break; 
							
							case 3: 
								reviewsPerYear[i][3]++; 
								break;
							
							case 4: 
								reviewsPerYear[i][4]++; 
								break; 
								
							case 5: 
								reviewsPerYear[i][5]++; 
								break;
							default: 
								break;
						}//end switch case 
					}//end if 
				}//end inner loop

				dataset.addValue(reviewsPerYear[i][1], String.valueOf(reviewsPerYear[i][0]), starLabels[0]);
				dataset.addValue(reviewsPerYear[i][2], String.valueOf(reviewsPerYear[i][0]), starLabels[1]);
				dataset.addValue(reviewsPerYear[i][3], String.valueOf(reviewsPerYear[i][0]), starLabels[2]);
				dataset.addValue(reviewsPerYear[i][4], String.valueOf(reviewsPerYear[i][0]), starLabels[3]);
				dataset.addValue(reviewsPerYear[i][5], String.valueOf(reviewsPerYear[i][0]), starLabels[4]);
					
			}//end outer loop			
			
			return dataset;
			
		}
	}

	//*******************************************************************************************************
	
	public static class starRatingVsSellerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				JFreeChart barChart = ChartFactory.createBarChart(
						"Star Ratings vs Seller",
						"Star Rating",
						"Count",
						createDataset(),
						PlotOrientation.VERTICAL,
						true,
						true,
						false);
				
				ChartPanel myChart = new ChartPanel(barChart);
				Parser.graphPanel.add(myChart, BorderLayout.CENTER);
				Parser.graphPanel.validate();
				
			}
			else {
				return;
			}


		}
		
		private CategoryDataset createDataset() {
			//create labels for each bar in the bar graph
			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"};
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
						  
			  
			// pull the years from year distribution 
			String[][] sellerDistribution = statsPanel.sellerDistribution();
			  
			// create a Seller x 6 array to hold star ratings per Seller 
			String[][] reviewsPerSeller = { 
					{null,"0","0","0","0","0"}, 
					{null,"0","0","0","0","0"},
					{null,"0","0","0","0","0"}, 
					{null,"0","0","0","0","0"},
					{null,"0","0","0","0","0"}
				};
			  
			//iterate through array and assign the Sellers into the reviewsPerSeller, Seller column
			for(int i = 0; i < 5; i++) { 
				if(sellerDistribution[i][0] != null) { 
					reviewsPerSeller[i][0] = sellerDistribution[i][0];
				}
			}
			  
			//Save the master list of parsed reviews locally 
			List<Review> allReviewsList = Parser.getReviews();
			  
			//Iterate over all the Sellers 
			for(int i = 0; i < 5; i++) {
				if(reviewsPerSeller[i][0] != null) { //Iterate over all the reviews
					for(Review r : allReviewsList) {
			  
						//Pull the Seller out from the date of the current review 
						String thisReviewsSeller = r.getSeller();
						  
						//check to see if the year from this current review is the same as the year under analysis
						if(thisReviewsSeller == reviewsPerSeller[i][0]) {
						  
							//switch case depending on value, and then add to that count 
							double thisStarDouble = Double.valueOf(r.getStar()); 
							int thisStarInt = (int) thisStarDouble;
							  
							int oldValue = 0; int newValue = 0;
							 
							//Since this is a string array, pull the old string out, convert to int
							//increment the old value and save, convert new value to string and save new
							//value into array
							  
							  
							switch(thisStarInt) { 
								case 1: 
									oldValue = Integer.valueOf(reviewsPerSeller[i][1]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][1] = String.valueOf(newValue); 
									break; 
								case 2: 
									oldValue = Integer.valueOf(reviewsPerSeller[i][2]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][2] = String.valueOf(newValue); 
									break; 
								case 3: 
									oldValue = Integer.valueOf(reviewsPerSeller[i][3]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][3] = String.valueOf(newValue); 
									break; 
								case 4: 
									oldValue = Integer.valueOf(reviewsPerSeller[i][4]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][4] = String.valueOf(newValue); 
									break; 
								case 5: 
									oldValue = Integer.valueOf(reviewsPerSeller[i][5]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][5] = String.valueOf(newValue); 
									break; 
								default: 
									break;
							}//end switch case 
						}//end if 
					}//end inner loop
				
				dataset.addValue(Integer.parseInt(reviewsPerSeller[i][1]), String.valueOf(reviewsPerSeller[i][0]), starLabels[0]);
				dataset.addValue(Integer.parseInt(reviewsPerSeller[i][2]), String.valueOf(reviewsPerSeller[i][0]), starLabels[1]);
				dataset.addValue(Integer.parseInt(reviewsPerSeller[i][3]), String.valueOf(reviewsPerSeller[i][0]), starLabels[2]);
				dataset.addValue(Integer.parseInt(reviewsPerSeller[i][4]), String.valueOf(reviewsPerSeller[i][0]), starLabels[3]);
				dataset.addValue(Integer.parseInt(reviewsPerSeller[i][5]), String.valueOf(reviewsPerSeller[i][0]), starLabels[4]);		
				
				}//end outer if 
			}//end outer loop

			return dataset;
		}
	}

	//*******************************************************************************************************
	public static class keywordParetoListener  implements ActionListener {		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				JFreeChart barChart = ChartFactory.createBarChart(
						"Total Star Ratings",
						"Keyword",
						"Count",
						createDataset(),
						PlotOrientation.VERTICAL,
						true,
						true,
						false);
				
				ChartPanel myChart = new ChartPanel(barChart);
				Parser.graphPanel.add(myChart, BorderLayout.CENTER);
				Parser.graphPanel.validate();
				
			}
			else {
				return;
			}


		}
		
		private CategoryDataset createDataset() {		
			List<Review> allReviews = Parser.getReviews();
			
			String productType = Parser.pathPanel.productType;
			keywordSet selection = new keywordSet();
			List<keywordSet> allKeywordSetsList = Parser.getKeywordSet();
			
			//iterate over all the keyword sets to find the one with the selected product type
			for (int i = 0; i < allKeywordSetsList.size(); i++) {
				if(allKeywordSetsList.get(i).getProductType().equalsIgnoreCase(productType)) {
					selection = allKeywordSetsList.get(i);
				}
			}
			
			//save the keywords from the selected keywordSet to an array
			String[] productTypeKeywordsArray = new String[selection.getKeywordList().size()];
			selection.getKeywordList().toArray(productTypeKeywordsArray);
			
			//Make a keywordList row and 2 column table to hold counts for each keyword
			String[][] table = new String[productTypeKeywordsArray.length][2];
			
			
			for(int i = 0; i < productTypeKeywordsArray.length; i++) {
				table[i][0] = productTypeKeywordsArray[i];
				table[i][1] = "0";
			}
			
			
			//Check to see if there are keywords that are associated with the product type
			if(productTypeKeywordsArray.length != 0) {
				
				//create a new int array to count hits from each keyword
				//int[] keywordCounts = new int[productTypeKeywordsArray.length];
				
				//outer loop iterates through all reviews
				for(int i = 0; i < allReviews.size(); i++) {
					
					//save the body of the review under analysis to a local string
					String thisReviewBody = allReviews.get(i).getBody();
					
					//inner loop iterates through all keywords
					for(int j = 0; j < productTypeKeywordsArray.length; j++) {				
						
						//see if the body of the current review contains 
						if(thisReviewBody.contains((String) table[j][0])) {
							int value = Integer.valueOf(table[j][1].toString());
							table[j][1] = String.valueOf(++value);
						}//end if
					}//end inner loop
				}// end outer loop
				
				
				Arrays.sort(table, new Comparator<String[]>(){
					@Override
					public int compare(String[] a, String[] b) {
						Double d1 = Double.valueOf(a[1]);
						Double d2 = Double.valueOf(b[1]);
						
						return -d1.compareTo(d2);
					}
				});
				
				
				//Create dataset and populate with values from table
				final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
				for(int i = 0; i < productTypeKeywordsArray.length; i++) {
					
					//value, series, label
					dataset.addValue(Integer.valueOf((String)table[i][1].toString()), "Series 1", (String) table[i][0]);
				}

				
				return dataset;
			}//end if
			
			
			//If no associated keywords, return an empty graph with a note in the console
			else {
				System.out.println("Cannot create graph because there are no keywords associated with this product type.\n");
			}
			
			return null;
		}//end method
	}// end class

	//*******************************************************************************************************
	public static class customKeywordSearchListener  implements ActionListener {		
		static JFrame popUpWindow = new JFrame();
		public static String[] customKeywords = {"", "", "", "", ""};
		public static JTextField input;
		public static JTextArea textArea;
		public static int count = 0;
		static String newKeyword = "";
		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//Boilerplate JFrame methods
				popUpWindow.setTitle("Review Camp Parser - Custom Keyword Search");
				popUpWindow.setSize(400,300);
				popUpWindow.setLayout(new BorderLayout());
				
				input = new JTextField(8);
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
		}
	
		
		
		
		public static class addButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				
				newKeyword = input.getText().trim().toLowerCase();
				
				if(!newKeyword.isEmpty() && count < 5) {
					customKeywords[count] = newKeyword;
					count++;
					
					textArea.append(newKeyword + "\n");
					input.setText("");
					newKeyword = "";
				}
			}
		}
		
		public static class runButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				
				JFreeChart barChart = ChartFactory.createBarChart(
						"Star Ratings of Custom Keywords",
						"Keyword",
						"Count",
						createDataset(),
						PlotOrientation.VERTICAL,
						true,
						true,
						false);
				
				ChartPanel myChart = new ChartPanel(barChart);
				Parser.graphPanel.add(myChart, BorderLayout.CENTER);
				Parser.graphPanel.validate();
				
				for(int i = 0; i<5 ; i++) {
					customKeywords[i] = "";
				}
				popUpWindow.setVisible(false);
				textArea.setText("");
				input.setText("");
				newKeyword = "";
			}	
		
				
		private CategoryDataset createDataset() {
						
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

			
			//copy the custom keywords into the first column of the table
			for(int i = 0; i < 5; i++) {
				table[i][0] = customKeywords[i];
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
					dataset.addValue(sum, "Total", table[i][0]);
				}

			}
			
				
			return dataset;
			}
		}
	}
}


