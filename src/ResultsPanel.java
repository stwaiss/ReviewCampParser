import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;


public class ResultsPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JLabel[] starStatLabels = new JLabel[7];
	private static JTextField[] starStatTextFields = {new JTextField(4), new JTextField(4), 
			new JTextField(4), new JTextField(4), new JTextField(4), new JTextField(4),
			new JTextField(4)};
	
	private static List<Review> reviews = new ArrayList<Review>();

	
	public ResultsPanel() {
		reviews = Parser.getReviews();
		
		fillStarStats();
		for(int i = 0; i < 7; i++) {
			starStatLabels[i].setHorizontalAlignment(JLabel.CENTER);
			add(starStatLabels[i]);
			starStatTextFields[i].setHorizontalAlignment(JTextField.CENTER);
			add(starStatTextFields[i]);
		}		
	}
	
	public void setReviews(List<Review> r) {
		reviews = r;
	}
	
	public static void fillStarStats() {
		reviews = Parser.getReviews();
		
		int[] starCounts; 
		if(reviews == null) {
			starCounts = new int[5];
		}
		
		else {
			starCounts = numDistribution(reviews); 
		}
		
		
		//Make a 7x2 table of labels and text fields to populate the count, average, and # of star reviews
		starStatLabels[0] = new JLabel("Count");
		starStatLabels[1]= new JLabel("Average");
		starStatLabels[2] = new JLabel("5 Star");
		starStatLabels[3] = new JLabel("4 Star");
		starStatLabels[4] = new JLabel("3 Star");
		starStatLabels[5] = new JLabel("2 Star");
		starStatLabels[6] = new JLabel("1 Star");
			
	
		if(reviews == null) {
			starStatTextFields[0].setText("0");
			starStatTextFields[1].setText("0");
		}
		
		else {
			starStatTextFields[0].setText(Integer.toString(getReviewCount()));
			starStatTextFields[1].setText(Double.toString(computeAverage(reviews)));
		}
		starStatTextFields[2].setText(Integer.toString(starCounts[4]));
		starStatTextFields[3].setText(Integer.toString(starCounts[3]));
		starStatTextFields[4].setText(Integer.toString(starCounts[2]));
		starStatTextFields[5].setText(Integer.toString(starCounts[1]));
		starStatTextFields[6].setText(Integer.toString(starCounts[0]));
	}
	
	public static int getReviewCount() {
		return reviews.size();
	}

	public static double computeAverage(List<Review> reviews) {
		double sum = 0;
		
		for(int i = 0; i < reviews.size(); i++) {
			int rating = Integer.valueOf(reviews.get(i).star);
			sum += rating;
			
		}
		
		double result = sum/reviews.size();
		result = result*100;
		result = Math.floor(result);
		result = result/100;
		
		return result;
	}
	
	public static int[] numDistribution(List<Review> reviews) {
		reviews = Parser.getReviews();
		int[] counts = {0,0,0,0,0};
		
		for(int i = 0; i < reviews.size(); i++) {
			
			int thisStarRating = Integer.valueOf(reviews.get(i).star);
			switch(thisStarRating) {
			
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
		
		return counts;
		
	}
	
	public static void sortByYear(List<Review> reviews) {
		// create new string array to store all the years of each review
		int[] allYears = new int[reviews.size()];
		
		// create new review array to treat list like array
		Review[] allReviewsArray = new Review[reviews.size()];
		reviews.toArray(allReviewsArray);
		
		// iterate over new review array, delimit each date by /, and store year value into allYears
		for(int i = 0; i< allReviewsArray.length; i++) {
			String[] thisDate = allReviewsArray[i].date.split("/");
			allYears[i] = Integer.valueOf(thisDate[2]);
		}
		
		// sort allYears
		Arrays.sort(allYears);
		
		
		// create new array
		int[][] table = new int[20][2];
		int nthYear = 0;
		int nthYearCount = 0;
		
		table[0][0] = allYears[0];

		// iterate over allYears to count occurences of each year into table
		for(int i = 0; i < allYears.length; i++) {
			
			// if  current value of allYears equals current year, increment count
			if(allYears[i] == table[nthYear][0]) {
				nthYearCount++;
			}
			
		
			else {
				// assign count of nth Year to occurence column
				table[nthYear][1] = nthYearCount;
				
				// reset count
				nthYearCount = 0;
				
				// increment nthYear
				nthYear++;
				
				// move down to next row and set the current year
				table[nthYear][0] = allYears[i];
			}
		}
		// save final nthYear count for nthYear in table
		table[nthYear][1] = nthYearCount;
		
		for(int i = 0; i < 20; i++) {
			if(table[i][0] == 0) {
				break;
			}
			System.out.println(table[i][0] + " had " + table[i][1] + " reviews");
		}
	}
}