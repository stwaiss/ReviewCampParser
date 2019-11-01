import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;


public class statsPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//JLabels for star rating statistics
	public static JLabel[] starStatLabels = new JLabel[7];
	
	//JTextFields for data from star rating statistics
	public static JTextField[] starStatTextFields = {new JTextField(4), new JTextField(4), 
			new JTextField(4), new JTextField(4), new JTextField(4), new JTextField(4),
			new JTextField(4)};
	
	//JLabels for item seller statistics
	public static JLabel[] sellerStatLabels = new JLabel[5];
	
	//JTextFields for data from seller statistics
	public static JTextField[] sellerStatTextFields = {new JTextField(4), new JTextField(4), 
			new JTextField(4), new JTextField(4), new JTextField(4)};
	
	//JLabels for item year statistics
	public static JLabel[] yearStatLabels = new JLabel[5];
		
	//JTextFields for data from year statistics
	public static JTextField[] yearStatTextFields = {new JTextField(4), new JTextField(4), 
			new JTextField(4), new JTextField(4), new JTextField(4)};
	
	
	public static JTextField pSkuText = new JTextField("");
	
	private static List<Review> reviews = new ArrayList<Review>();
	
	public static int yearPosition;

	//*********************************************************************************
	
	//constructor
	public statsPanel() {
		reviews = Parser.getReviews();
		
		fillStatPanelStats();
		for(int i = 0; i < 7; i++) {
			starStatLabels[i].setHorizontalAlignment(JLabel.CENTER);
			add(starStatLabels[i]);
			
			starStatTextFields[i].setHorizontalAlignment(JTextField.CENTER);
			starStatTextFields[i].setEditable(false);
			starStatTextFields[i].setBackground(Color.white);
			add(starStatTextFields[i]);
			
			if(i<5) {
				sellerStatLabels[i].setHorizontalAlignment(JLabel.CENTER);
				add(sellerStatLabels[i]);
				
				sellerStatTextFields[i].setHorizontalAlignment(JTextField.CENTER);
				sellerStatTextFields[i].setEditable(false);
				sellerStatTextFields[i].setBackground(Color.white);
				add(sellerStatTextFields[i]);
				
				yearStatLabels[i].setHorizontalAlignment(JLabel.CENTER);
				add(yearStatLabels[i]);
				
				yearStatTextFields[i].setHorizontalAlignment(JTextField.CENTER);
				yearStatTextFields[i].setEditable(false);
				yearStatTextFields[i].setBackground(Color.white);
				add(yearStatTextFields[i]);
				
			}
			
			//add dummy labels to keep grid formatting correct
			else if(i == 5) {
				add(new JLabel(""));
				add(new JLabel(""));
				add(new JLabel(""));
				add(new JLabel(""));
			}
			
			else {
				add(new JLabel(""));
				add(new JLabel(""));
				
				JLabel ptypeLabel = new JLabel("SKU");
				ptypeLabel.setHorizontalAlignment(JLabel.CENTER);
				add(ptypeLabel);
				
				pSkuText.setHorizontalAlignment(JTextField.CENTER);
				pSkuText.setEditable(false);
				pSkuText.setBackground(Color.white);
				add(pSkuText);
			}
				
		}		
	}
	
	//**********************************************************************************
	
	public void setReviews(List<Review> r) {
		reviews = r;
	}
	
	//getter for size of list
	public static int getReviewCount() {
		return reviews.size();
	}
	
	//Method used to fill in the JTextFields and refresh GUI
	public static void fillStatPanelStats() {
		reviews = Parser.getReviews();
		
		//create variable array to hold counts of stars, sellers, and years
		int[] starCounts;
		String[][] sellerCounts;
		String[][] yearCounts;
		
		//if the files haven't been parsed and there's consequently nothing in the reviews list
		if(getReviewCount() == 0) {
			//define the array as blank
			starCounts = new int[5];
			sellerCounts = new String[5][2];
			yearCounts = new String[5][2];

			
			//Make table of labels and text fields to populate the count, average, and # of star reviews
			starStatLabels[0] = new JLabel("Count");
			starStatLabels[1]= new JLabel("Average");
			starStatLabels[2] = new JLabel("5 Star");
			starStatLabels[3] = new JLabel("4 Star");
			starStatLabels[4] = new JLabel("3 Star");
			starStatLabels[5] = new JLabel("2 Star");
			starStatLabels[6] = new JLabel("1 Star");
			
			//Set Count and Average to 0
			starStatTextFields[0].setText("0");
			starStatTextFields[1].setText("0");

		
			sellerStatLabels[0] = new JLabel("Seller 1");
			sellerStatLabels[1] = new JLabel("Seller 2");
			sellerStatLabels[2] = new JLabel("Seller 3");
			sellerStatLabels[3] = new JLabel("Seller 4");
			sellerStatLabels[4] = new JLabel("Seller 5");
			
			yearStatLabels[0] = new JLabel("Year 1");
			yearStatLabels[1] = new JLabel("Year 2");
			yearStatLabels[2] = new JLabel("Year 3");
			yearStatLabels[3] = new JLabel("Year 4");
			yearStatLabels[4] = new JLabel("Year 5");
		}
		
		//else, run through distribution methods to fill the arrays
		else {
			starCounts = starDistribution(); 
			sellerCounts = sellerDistribution();
			yearCounts = yearDistribution();
			
			//Set Count and Average to correct values
			starStatTextFields[0].setText(Integer.toString(getReviewCount()));
			starStatTextFields[1].setText(Double.toString(computeAverage(reviews)));
			starStatTextFields[2].setText(Integer.toString(starCounts[4]));
			starStatTextFields[3].setText(Integer.toString(starCounts[3]));
			starStatTextFields[4].setText(Integer.toString(starCounts[2]));
			starStatTextFields[5].setText(Integer.toString(starCounts[1]));
			starStatTextFields[6].setText(Integer.toString(starCounts[0]));
			
			//Set JLabel text values to actual seller and year values
			for(int i=0; i<5; i++) {
				sellerStatLabels[i].setText(sellerCounts[i][0]);
				sellerStatTextFields[i].setText(sellerCounts[i][1]);
				
				yearStatLabels[i].setText(yearCounts[i][0]);
				yearStatTextFields[i].setText(yearCounts[i][1]);
			}
		}
				
		pSkuText.setText(pathPanel.productSku);
		
	}
	
	//Method to compute average of all star reviews from parsed file
	public static double computeAverage(List<Review> reviews) {
		double sum = 0;
		
		//Iterate over list and sum the star ratings
		for(int i = 0; i < reviews.size(); i++) {
			Double doubleRating = Double.valueOf(reviews.get(i).getStar());
			int rating = doubleRating.intValue();
			sum += rating;
			
		}
		
		//divide sum by result and round to #.## format
		double result = sum/getReviewCount();
		result = result*100;
		result = Math.floor(result);
		result = result/100;
		
		return result;
	}
	
	//Method to determine the distribution of star ratings from a list
	public static int[] starDistribution() {
		
		//pull static review list and save locally
		List<Review> reviews = Parser.getReviews();
		
		//create an array of length 5 to hold values of each star rating
		//[0] is number of 1 star ratings
		//[1] is number of 2 star ratings, etc...
		int[] counts = {0,0,0,0,0};
		
		//iterate over list
		for(int i = 0; i < reviews.size(); i++) {
						
			Double thisStarRatingDouble = Double.valueOf(reviews.get(i).getStar());
			int thisStarRatingInt = thisStarRatingDouble.intValue();
			
			//switch case depending on value, and then add to that count
			switch(thisStarRatingInt) {
				case 1:
					counts[0]++;
					break;
				case 2:
					counts[1]++;
					break;
				case 3:
					counts[2]++;
					break;
				case 4:
					counts[3]++;
					break;
				case 5:
					counts[4]++;
					break;
				default:
					break;
			}
		}
		
		//return the array
		return counts;
		
	}
	
	//Method to determine the distribution of sellers from a list
	public static String[][] sellerDistribution() {
		// create new string array to store all the years of each review
		String[] allSellers = new String[getReviewCount()];
		
		// create new review array to treat list like array
		Review[] allReviewsArray = new Review[reviews.size()];
		reviews.toArray(allReviewsArray);
		
		// iterate over new review array, delimit each date by /, and store year value into allYears
		for(int i = 0; i< allReviewsArray.length; i++) {
			allSellers[i] = allReviewsArray[i].getSeller();
		}
		
		// sort allYears
		Arrays.sort(allSellers);
		
		
		// create new array, first column is seller name, second is count
		String[][] table = new String[5][2];
		int nthSeller = 0;
		int nthSellerCount = 0;
		
		table[0][0] = allSellers[0];

		// iterate over allYears to count occurrences of each seller and put into table
		for(int i = 0; i < allSellers.length; i++) {
			
			// if  current value of allSellers equals current seller in table, increment count
			if(allSellers[i].equals(table[nthSeller][0])){
				nthSellerCount++;
			}
			
			else {
				// assign count of nthSeller to occurrence column
				table[nthSeller][1] = Integer.toString(nthSellerCount);
				
				// reset count
				nthSellerCount = 0;
				
				// increment nthSeller
				nthSeller++;
				
				// move down to next row and set the current seller
				table[nthSeller][0] = allSellers[i];
				nthSellerCount++;
			}
		}
		// save final nthSeller count for nthSeller in table
		table[nthSeller][1] = Integer.toString(nthSellerCount);
		
		/*
		 * //print to console counts per seller for(int i = 0; i < 5; i++) {
		 * if(table[i][0] != null) { System.out.println(table[i][0] + " had " +
		 * table[i][1] + " reviews"); } }
		 */
		
		return table;
		
	}
	
	public static String[][] yearDistribution() {
		// create new string array to store all the years of each review
		String[] allYears = new String[reviews.size()];
		
		// create new review array to treat list like array
		Review[] allReviewsArray = new Review[reviews.size()];
		reviews.toArray(allReviewsArray);
		
		// iterate over new review array, delimit each date by /, and store year value into allYears
		for(int i = 0; i< allReviewsArray.length; i++) {
			String[] thisDate = allReviewsArray[i].getDate().split("-");
			
			// determine formatting of date string and find which position the year is located in.
			for(yearPosition = 0; yearPosition < 3; yearPosition++) {
				if(thisDate[yearPosition].length() == 4) {
					allYears[i] = thisDate[yearPosition];
					break;
				}
			}
			
		}
		
		// sort allYears
		Arrays.sort(allYears);
		
		//Find the value of the most recent year
		int allYearsMax = Integer.valueOf(allYears[allYears.length-1]);
		
		//Find the year 4 years before that
		int lowerBound = allYearsMax - 4;
		
		//Iterate over allYears to find the index of the first lowerBound year
		int startingPoint = 0;
		for(int i = 0; i < allYears.length; i++ ) {
			if(Integer.valueOf(allYears[i]) == lowerBound) {
				startingPoint = i;
				break;
			}
		}
		
		// create new array
		String[][] table = new String[5][2];
		int nthYear = 0;
		int nthYearCount = 0;
		
		table[0][0] = allYears[startingPoint];

		// iterate over allYears to count occurrences of each year into table
		for(int i = startingPoint; i < allYears.length; i++) {
			
			// if  current value of allYears equals current year, increment count
			if(allYears[i].equalsIgnoreCase(table[nthYear][0])) {
				nthYearCount++;
				
				//Make sure last year is counted
				if (i == allYears.length - 1) {
					table[nthYear][1] = Integer.toString(nthYearCount);
				}
			}
			
			
		
			else {
				// assign count of nth Year to occurrence column
				table[nthYear][1] = Integer.toString(nthYearCount);
				
				// reset count
				nthYearCount = 0;
				
				// increment nthYear
				nthYear++;
				
				// move down to next row and set the current year
				try{
					table[nthYear][0] = allYears[i];
					nthYearCount++;
					
					//Make sure last year is counted
					if (i == allYears.length - 1) {
						table[nthYear][1] = Integer.toString(nthYearCount);
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					System.out.println(i);
					e.printStackTrace();
					break;
				}
			}
		}
		// save final nthYear count for nthYear in table
		//table[nthYear][1] = Integer.toString(nthYearCount);
		
		/*
		 * //print to console counts per seller for(int i = 0; i < 5; i++) {
		 * if(table[i][0] != null) { System.out.println(table[i][0] + " had " +
		 * table[i][1] + " reviews"); } }
		 */
		
		return table;
	}
}