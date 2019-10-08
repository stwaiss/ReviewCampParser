import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;


public class pathPanel extends JPanel{
	private JTextField pathField = new JTextField(50);
	private List<Review> reviews = new ArrayList<Review>();
	
	
	public pathPanel() {
		setSize(100,600);

		
		JButton pathSubmit = new JButton("Submit");
		pathSubmit.addActionListener(new pathSubmitBtnListener());
	
		 
		add(new JLabel("Enter Path of Review Camp CSV file:"));
		add(pathField);
		add(pathSubmit);
		
		setVisible(true);
		
	}
	
	public class pathSubmitBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
				
			
			// Define path for where file will be pulled from
			String path = pathField.getText();
			
			
			File file = new File(path);
			
			try{
				// Create new scanner that will iterate across CSV and delimit using provided delimiter tool
				Scanner inputStream = new Scanner(file);
				//inputStream.useDelimiter("\n");			
							
				// While stuff still exists in the stream...
				while(inputStream.hasNext()) {
					
					String data = inputStream.nextLine();
					data.trim();
					
					// Split each unprocessed review into individual datapoints
					String[] splitData = data.split(",", 5); 

					// Create a new review object and populate
					Review r = new Review();
					r.date = splitData[0];
					r.website = splitData[1];
					r.star = splitData[2];
					r.title = splitData[3];
					r.body = splitData[4];
					
					// Add new review object to list
					reviews.add(r);
				}
				
				// Close the stream
				inputStream.close();
				
				reportOut(reviews);
			}
			
			// In the event the file path DNE...
			catch(FileNotFoundException e){
				e.printStackTrace();
				
			}
		}
	}

	public List<Review> getReviewList(){
		return reviews;
	}
	
	public int getReviewCount(List<Review> reviews) {
		return reviews.size();
	}
		
	public static void reportOut(List<Review> reviews) {
		System.out.println("#####    REPORT    ##### \n \n");
		
		System.out.println("Total number of reviews: " + reviews.size() + "\n");
		
		numDistribution(reviews);
		System.out.println("\n");
		
		computeAverage(reviews);
		System.out.println("\n");
		
		sortByYear(reviews);
	}

	public static void computeAverage(List<Review> reviews) {
		double sum = 0;
		
		for(int i = 0; i < reviews.size(); i++) {
			int rating = Integer.valueOf(reviews.get(i).star);
			sum += rating;
			
		}
		
		System.out.printf("Star average is : %2.2f stars", sum/reviews.size());
	}
	
	public static void numDistribution(List<Review> reviews) {
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
		
		for(int i = 0; i < 5; i++) {
			System.out.printf("Number of " + (i+1) +"-star reviews: "
					+ counts[i] + " : " + "%2.2f %% %n", (((double)counts[i]/reviews.size())*100));
		}
		
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
