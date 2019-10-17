import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class pathPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTextField pathField = new JTextField(50);
	private List<Review> reviews = new ArrayList<Review>();
	public static String productType = "";
	public static String productSku = "";
	static XSSFRow row;

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
			//Clear reviews so that multiple submit clicks doesn't multiply inputs
			reviews.clear();

			//Create path for where file is stored
			String path = pathField.getText(); 
			File file = new File(path);
			FileInputStream fis;

			try {
				
				//Create new spreadsheet and iterators
				fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet spreadsheet = workbook.getSheetAt(0);
				Iterator <Row>  rowIterator = spreadsheet.iterator();
				
				//Create array to save the data from the spreadsheet
				String[] data = new String[5];
				
				//While there is another row to parse, iterate over the cells
				while(rowIterator.hasNext()) {
					row = (XSSFRow) rowIterator.next();
					Iterator <Cell>  cellIterator = row.cellIterator();
					int count = 0;
					
					//While there is another cell to parse, iterate over cells and save to array
					while(cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						data[count] = cell.toString();
						count++;
						//System.out.println(cell.toString());
					}
					
					//Create new array based on saved string data
					Review r = new Review(
							data[0],
							data[1],
							data[2],
							data[3],
							data[4]);
					
					//add new review to reviews list
					reviews.add(r);
				}
				
				fis.close();
				workbook.close();
				
				//Create pop up box for selecting product type
				List<keywordSet> keywordSetList = Parser.getKeywordSet();
				
				
				String[] options = new String[keywordSetList.size()];

				for (int i = 0; i < keywordSetList.size(); i++) {
					options[i] = keywordSetList.get(i).getProductType(); 
				}
				
				Arrays.sort(options);
				 
				//Try to prevent from escaping without picking an option. 
				int count = 0;
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
				} while(productType == "" && count < 2);
				
				count = 0;
				
				do {
					count++;
					productSku = (String) JOptionPane.showInputDialog(null, "Enter " + productType + " SKU\nAttempt" + (count));
				} while (productSku == "" && count < 2);
				
			}

			catch (IOException e) {
				System.out.println("Looks like an issue with the file.");
			}
			
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please make sure there is no header info and that there are only 5 columns of data.");
			}
			/*
			 * catch (AbstractMethodError e) { System.out.println("Abstract Method Error");
			 * }
			 */
			
			 //Write to console and begin populating statistics boxes
			Parser.graphPanel.setProductType(productType);
			Parser.setReviews(reviews); 
			System.out.println(reviews.size() + " reviews have been added \n"); 
			statsPanel.fillStatPanelStats();

		}
	}

	
	public List<Review> getReviewList(){
		return reviews;
	}

}
