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
	
	private static JRadioButton[] options = new JRadioButton[6];
	public static ButtonGroup group = new ButtonGroup();
	
	public choicePanel() {
		
		//set border and layout type
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		
		//define options array to include 4 JRadioButtons
		options[0] = new JRadioButton("Total Star Rating");
		options[0].setActionCommand("Total Star Rating");
		options[0].addActionListener(new totalStarRatingListener());
		
		options[1] = new JRadioButton("Star Rating vs Year");
		options[1].setActionCommand("Star Rating vs Year");
		options[1].addActionListener(new starRatingVsYearListener());
		
		options[2] = new JRadioButton("Star Rating vs Seller");
		options[2].setActionCommand("Star Rating vs Seller");
		options[2].addActionListener(new starRatingVsSellerListener());
		
		options[3] = new JRadioButton("Keyword Pareto");
		options[3].setActionCommand("Keyword Pareto");
		options[3].addActionListener(new keywordParetoListener());
		
		options[4] = new JRadioButton("Custom Keyword Search");
		options[4].setActionCommand("Custom Keyword Search");
		options[4].addActionListener(new customKeywordSearchListener());
		
		options[5] = new JRadioButton("Keyword vs Year");
		options[5].setActionCommand("Keyword vs Year");
		options[5].addActionListener(new keywordVsYearListener());
		
		//add the radio buttons to the group and to the pane, and also add separators for space
		for (JRadioButton b: options) {
			group.add(b);
			add(b);
			this.add(Box.createRigidArea(new Dimension(0,15)));
		}	
	}
	
	public static JRadioButton[] getOptions() {
		return options;
	}
	
	//*******************************************************************************************************
	public static class totalStarRatingListener  implements ActionListener {		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				JFreeChart barChart = ChartFactory.createBarChart(
						statsPanel.pSkuText.getText() + " - Total Star Ratings",
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
						statsPanel.pSkuText.getText() + " - Star Ratings Per Year",
						"Star Rating",
						"Percentage (%)",
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
			int[][] reviewsPerYear = new int[5][7];

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
				
				if(reviewsPerYear[i][0] == 0) {
					break;
				}
				for(Review r : allReviewsList) {

					//Pull the year out from the date of the current review 
					String thisReviewsYear = r.getDate().split("-")[statsPanel.yearPosition];
	
					//check to see if the year from this current review is the same as the year under analysis 
					if(Integer.valueOf(thisReviewsYear) == reviewsPerYear[i][0]) {
	
						//switch case depending on value, and then add to that count 
						double thisStarDouble = Double.valueOf(r.getStar());
						int thisStarInt = (int) thisStarDouble; 
										
						
						switch(thisStarInt) { 
							case 1: 
								reviewsPerYear[i][1]++; 
								reviewsPerYear[i][6]++; 
								break;
			
							case 2: 
								reviewsPerYear[i][2]++;
								reviewsPerYear[i][6]++;
								break; 
							
							case 3: 
								reviewsPerYear[i][3]++; 
								reviewsPerYear[i][6]++;
								break;
							
							case 4: 
								reviewsPerYear[i][4]++; 
								reviewsPerYear[i][6]++;
								break; 
								
							case 5: 
								reviewsPerYear[i][5]++; 
								reviewsPerYear[i][6]++;
								break;
							default: 
								break;
						}//end switch case 
					}//end if 
				}//end inner loop

				dataset.addValue(((double) reviewsPerYear[i][1]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[0]);
				dataset.addValue(((double) reviewsPerYear[i][2]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[1]);
				dataset.addValue(((double) reviewsPerYear[i][3]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[2]);
				dataset.addValue(((double) reviewsPerYear[i][4]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[3]);
				dataset.addValue(((double) reviewsPerYear[i][5]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[4]);
					
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
						statsPanel.pSkuText.getText() + " - Star Ratings vs Seller",
						"Star Rating",
						"Percentage (%)",
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
			  
			// create a Seller x 7 array to hold star ratings per Seller 
			String[][] reviewsPerSeller = { 
					{null,"0","0","0","0","0", "0"}, 
					{null,"0","0","0","0","0", "0"}, 
					{null,"0","0","0","0","0", "0"}, 
					{null,"0","0","0","0","0", "0"}, 
					{null,"0","0","0","0","0", "0"}					
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
			  
						//Pull the Seller out from the current review 
						String thisReviewsSeller = r.getSeller();
						  
						//check to see if the seller from this current review is the same as the seller under analysis
						if(thisReviewsSeller.equalsIgnoreCase(reviewsPerSeller[i][0])) {
						  
							//switch case depending on value, and then add to that count 
							double thisStarDouble = Double.valueOf(r.getStar()); 
							int thisStarInt = (int) thisStarDouble;
							  
							int oldValue = 0; int newValue = 0;
							 
							//Since this is a string array, pull the old string out, convert to int
							//increment the old value and save, convert new value to string and save new
							//value into array
							  
							  
							switch(thisStarInt) { 
								case 1: 
									//increment 1-star counts
									oldValue = Integer.valueOf(reviewsPerSeller[i][1]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][1] = String.valueOf(newValue); 
									
									//increment sum
									oldValue = Integer.valueOf(reviewsPerSeller[i][6]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][6] = String.valueOf(newValue); 
									break; 
								case 2: 
									//increment 2-star counts
									oldValue = Integer.valueOf(reviewsPerSeller[i][2]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][2] = String.valueOf(newValue); 
									
									//increment sum
									oldValue = Integer.valueOf(reviewsPerSeller[i][6]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][6] = String.valueOf(newValue); 
									break; 
								case 3: 
									//increment 3-star counts
									oldValue = Integer.valueOf(reviewsPerSeller[i][3]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][3] = String.valueOf(newValue); 
									
									//increment sum
									oldValue = Integer.valueOf(reviewsPerSeller[i][6]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][6] = String.valueOf(newValue); 
									break; 
								case 4: 
									//increment 4-star counts
									oldValue = Integer.valueOf(reviewsPerSeller[i][4]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][4] = String.valueOf(newValue); 
									
									//increment sum
									oldValue = Integer.valueOf(reviewsPerSeller[i][6]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][6] = String.valueOf(newValue); 
									break; 
								case 5: 
									//increment 5-star counts
									oldValue = Integer.valueOf(reviewsPerSeller[i][5]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][5] = String.valueOf(newValue); 
									
									//increment sum
									oldValue = Integer.valueOf(reviewsPerSeller[i][6]); 
									newValue = oldValue + 1;
									reviewsPerSeller[i][6] = String.valueOf(newValue); 
									break; 
								default: 
									break;
							}//end switch case 
						}//end if 
					}//end inner loop
				
					
				dataset.addValue((Double.valueOf(reviewsPerSeller[i][1])/Double.valueOf(reviewsPerSeller[i][6])) * 100, String.valueOf(reviewsPerSeller[i][0]), starLabels[0]);
				dataset.addValue((Double.valueOf(reviewsPerSeller[i][2])/Double.valueOf(reviewsPerSeller[i][6])) * 100, String.valueOf(reviewsPerSeller[i][0]), starLabels[1]);
				dataset.addValue((Double.valueOf(reviewsPerSeller[i][3])/Double.valueOf(reviewsPerSeller[i][6])) * 100, String.valueOf(reviewsPerSeller[i][0]), starLabels[2]);
				dataset.addValue((Double.valueOf(reviewsPerSeller[i][4])/Double.valueOf(reviewsPerSeller[i][6])) * 100, String.valueOf(reviewsPerSeller[i][0]), starLabels[3]);
				dataset.addValue((Double.valueOf(reviewsPerSeller[i][5])/Double.valueOf(reviewsPerSeller[i][6])) * 100, String.valueOf(reviewsPerSeller[i][0]), starLabels[4]);
					
					
				
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
						statsPanel.pSkuText.getText() + " - Product Type Keyword Hits",
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
				
			}
			else {
				return;
			}


		}
		
		private CategoryDataset createDataset() {		
			List<Review> allReviews = Parser.getReviews();
			
			String productType = Parser.pathPanel.productType;
			keywordSet selection = new keywordSet("","",null);
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
				
		public void actionPerformed(ActionEvent e) {
			customKeywordSearcher cks = new customKeywordSearcher();
		}
	}

	//*******************************************************************************************************
	public static class keywordVsYearListener implements ActionListener {
		public String keyword = "";
		
		public void actionPerformed(ActionEvent e) {

			//Prompt for keyword
			int count = 0;
			keyword = "";
			do {
				count++;
				keyword = (String) JOptionPane.showInputDialog(null, "Enter keyword to analyze");
			} while (keyword == "" && count < 2);
			
			keyword = keyword.toLowerCase();
			
			
			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				JFreeChart barChart = ChartFactory.createBarChart(
						statsPanel.pSkuText.getText() + " - \"" + keyword + "\" vs Year ",
						"Star Rating",
						"Percentage (%)",
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
				
			}
			else {
				return;
			}
		}

		private CategoryDataset createDataset() {	

			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"}; 
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
					
			List<Review> allReviewsList = Parser.getReviews();
			List<Review> reviewListContainingKeyword = new ArrayList<Review>();
			
			//If review contains keyword, add to list
			for(Review review : allReviewsList) {
				try {
					if(review.getBody().toLowerCase().contains(keyword)) {
						reviewListContainingKeyword.add(review);
					}
				} catch(NullPointerException npe) {
					
				}
				
			}
				
			// create a year x 6 array to hold star ratings per year 
			int[][] reviewsPerYear = new int[5][7];	
			
			// pull the years from year distribution 
			String[][] yearDistribution = statsPanel.yearDistribution();
			
			//iterate through array and assign the years into the reviewsPerYear, year column 
			for(int i = 0; i < 5; i++) { 
				if(yearDistribution[i][0] != null) {
					reviewsPerYear[i][0] = Integer.valueOf(yearDistribution[i][0]); 
				} 
			}
			
			//Iterate over all the years 
			for(int i = 0; i < 5; i++) {		
				
				//Iterate over all the reviews
				if(reviewsPerYear[i][0] == 0) {
					break;
				}
				for(Review r : reviewListContainingKeyword) {
	
					//Pull the year out from the date of the current review 
					String thisReviewsYear = r.getDate().split("-")[statsPanel.yearPosition];
	
					//check to see if the year from this current review is the same as the year under analysis 
					if(Integer.valueOf(thisReviewsYear) == reviewsPerYear[i][0]) {
	
						//switch case depending on value, and then add to that count 
						double thisStarDouble = Double.valueOf(r.getStar());
						int thisStarInt = (int) thisStarDouble; 
										
						
						switch(thisStarInt) { 
							case 1: 
								reviewsPerYear[i][1]++; 
								reviewsPerYear[i][6]++; 
								break;
			
							case 2: 
								reviewsPerYear[i][2]++;
								reviewsPerYear[i][6]++;
								break; 
							
							case 3: 
								reviewsPerYear[i][3]++; 
								reviewsPerYear[i][6]++;
								break;
							
							case 4: 
								reviewsPerYear[i][4]++; 
								reviewsPerYear[i][6]++;
								break; 
								
							case 5: 
								reviewsPerYear[i][5]++; 
								reviewsPerYear[i][6]++;
								break;
							default: 
								break;
						}//end switch case 
					}//end if 
				}//end inner loop
	
				dataset.addValue(((double) reviewsPerYear[i][1]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[0]);
				dataset.addValue(((double) reviewsPerYear[i][2]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[1]);
				dataset.addValue(((double) reviewsPerYear[i][3]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[2]);
				dataset.addValue(((double) reviewsPerYear[i][4]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[3]);
				dataset.addValue(((double) reviewsPerYear[i][5]/ (double) reviewsPerYear[i][6]) * 100, String.valueOf(reviewsPerYear[i][0]), starLabels[4]);
					
			}//end outer loop			
			
			return dataset;			
		}
	}
}