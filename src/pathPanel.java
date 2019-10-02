import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;


public class pathPanel extends JPanel{
	private JTextField pathField = new JTextField(50);
	
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
				
				
				// Create a new Array List of Reviews
				List<Review> reviews = new ArrayList<Review>();
				
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
				
				//reportOut(reviews);
			}
			
			// In the event the file path DNE...
			catch(FileNotFoundException e){
				e.printStackTrace();
				
			}
		}
	}

}
