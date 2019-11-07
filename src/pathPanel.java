import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
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
	public static String division = "";
	public static String productType = "";
	public static String productSku = "";
	static XSSFRow row;

	//*******************************************************************************

	public pathPanel() {
		setSize(100,600);
		
		JButton search = new JButton("Search");
		search.addActionListener(new fileFindBtnListener());
		
		add(new JLabel("Click Search Button to find Excel file to be analyzed: "));
		add(search);
					
		setVisible(true);

	}

	//***********************************************************************************

	public class fileFindBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			//Clear reviews in the system to prevent duplicates
			reviews.clear();
			System.out.println("Starting\n");
			JFileChooser fc = new JFileChooser();
			
			int returnVal = fc.showOpenDialog(new JFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				pathField.setText(file.getAbsolutePath());
				
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
					Parser.setReviews(reviews);
					System.out.println(reviews.size() + " reviews have been added \n"); 
					
					
					//Begin setting up prompt for division name
					String[] options = {"GPC","H&G","HHI", "HPC"};
					Arrays.sort(options);
					
					//Try to prevent from escaping without picking an option. 
					do { 
						division = (String) JOptionPane.showInputDialog( 
							null,
							"Please select company division",
							"Company Division Selection",
							JOptionPane.DEFAULT_OPTION, 
							null, 
							options, 
							(String) options[0]);
					} while(division == "" || division == null);
					
					
					//Now that division is selected, pull out all keywordSets that are 
					//associated with the selected division
					List<keywordSet> keywordSetList = Parser.getKeywordSet();
					List<keywordSet> keywordSetsInDiv = new ArrayList<keywordSet>();
					
					for(int i = 0; i < keywordSetList.size(); i++) {
						if(keywordSetList.get(i).getDivision().equalsIgnoreCase(division)) {
							keywordSetsInDiv.add(keywordSetList.get(i));
						}
					}
					
					//Pull out all product types in keywordSetsInDiv list and put into array
					//to be passed into comboBox
					options = new String[keywordSetsInDiv.size()];

					for (int i = 0; i < keywordSetsInDiv.size(); i++) {
						options[i] = keywordSetsInDiv.get(i).getProductType(); 
					}
					
					Arrays.sort(options);
					
					//Try to prevent from escaping without picking an option. 
					//Prompt for Product Type
					do { 
						productType = (String) JOptionPane.showInputDialog( 
							null,
							"Please select product type",
							"Product Type Selection",
							JOptionPane.DEFAULT_OPTION, 
							null, 
							options, 
							(String) options[0]);
					} while(productType == "" || productType == null);
					
					//Prompt for SKU
					int count = 0;
					do {
						count++;
						productSku = (String) JOptionPane.showInputDialog(null, "Enter " + productType + " SKU");
					} while (productSku == "" && count < 2);
					
					Parser.graphPanel.setProductType(productType);			
					statsPanel.fillStatPanelStats();
					
					//Force total star ratings graph to be displayed on GUI by
					//triggering action listener on radio button
					for(ActionListener a: choicePanel.getOptions()[0].getActionListeners()) {
						a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
					}
					
					choicePanel.getOptions()[0].setSelected(true);
				}

				catch (IOException f) {
					System.out.println("Looks like an issue with the file.");
				}
				
				catch (ArrayIndexOutOfBoundsException f) {
					System.out.println("Please make sure there is no header info and that there are only 5 columns of data.");
				}
				
				catch (NotOfficeXmlFileException f) {
					System.out.println("This is not an excel workbook file");
				}
			}
		}
	}
	
	
	
	public List<Review> getReviewList(){
		return reviews;
	}

}
