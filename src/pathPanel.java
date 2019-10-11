import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;


public class pathPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField pathField = new JTextField(50);
	private List<Review> reviews = new ArrayList<Review>();
	public static String productType = "";

	//*******************************************************************************
	
	public pathPanel() {
		setSize(100,600);

		
		JButton pathSubmit = new JButton("Submit");
		pathSubmit.addActionListener(new pathSubmitBtnListener());
	
		 
		add(new JLabel("Enter path here:"));
		add(pathField);
		add(pathSubmit);
				
		setVisible(true);
		
	}
	
	//***********************************************************************************
	
	public class pathSubmitBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
				
			
			// Define path for where file will be pulled from
			String path = pathField.getText();
			File file = new File(path);
			
			//System.out.println("I see you clicked a button...\n");
			
			//every time submit is clicked, clear list before starting
			reviews.clear();
			
			
			try{
				// Create new scanner that will iterate across CSV and delimit using provided delimiter tool
				Scanner inputStream = new Scanner(file);
				inputStream.useDelimiter("\t");			
				
				
				
				// While stuff still exists in the stream...
				while(inputStream.hasNext()) {
					
					String data = inputStream.nextLine();
					data.trim();
					
					// Split each unprocessed review into individual data points
					String[] splitData = data.split("\t", 5); 

					// Create a new review object and populate
					Review r = new Review(splitData[0],splitData[1],splitData[2],
							splitData[3],splitData[4]);
					
					// Add new review object to list
					reviews.add(r);
				}
				
				// Close the stream
				inputStream.close();
				
				//Create pop up box for selecting product type
				Object[] options = {"Coffee Maker", "Grill", "Groomer", "Shaver"};
				int count = 0;
				
				//Try to prevent from escaping without picking an option. 
				do {
					count++;
					productType = (String) JOptionPane.showInputDialog(
							null,
							"Please select product type\nAttempt " + (count),
							"Product Type Selection",
							JOptionPane.DEFAULT_OPTION,
							null,
							options,
							(String) options[0]);
				}
				while(productType == "" && count < 2);
				
				//System.out.println("You've selected a " + productType);
				
				//Write to console and begin populating statistics boxes
				Parser.setReviews(reviews);
				System.out.println(reviews.size() + " reviews have been added \n");
				statsPanel.fillStatPanelStats();
				
				
				
			}
			
			// In the event the file path DNE...
			catch(FileNotFoundException e){
				System.out.println("File path does not exist. Please try again\n");
				pathField.setText("");
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Looks like something isn't right... Perhaps the file type?\nPlease try again\n");
			}
		}
	}

	public List<Review> getReviewList(){
		return reviews;
	}

}
