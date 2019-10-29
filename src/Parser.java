import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;


public class Parser extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static pathPanel pathPanel;
	public static statsPanel statPanel;
	public static graphPanel graphPanel;
	public static choicePanel choicePanel;
	public static screenCapPanel screenCapPanel;
	public static parserMenuBar menuBar;
	public static JTextArea taConsole;
	private static List<Review> reviews = new ArrayList<Review>();
	private static List<keywordSet> masterKeywordSet = new ArrayList<keywordSet>();
	
	//**********************************************************************
	
	public Parser() {
		//Boilerplate JFrame methods
		setFont(new Font("SansSerif", Font.PLAIN, 20));
		setTitle("Review Camp Parser " + application.VERSION_NUMBER);
		setSize(1000,750);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Try and open up the icon file to be assigned to the window.
		try {
			setIconImage(ImageIO.read(new File("SPB icon.png")));
		} catch (IOException e) {
			
		}
		
		//Add menu bar to the top of the frame
		menuBar = new parserMenuBar();
		setJMenuBar(menuBar);
		
		//Add PathPanel at top of JFrame
		pathPanel = new pathPanel();
		add(pathPanel, BorderLayout.NORTH);
	
		//Add new JPanel to encapsulate everything not the path panel
		JPanel lowerWrapper = new JPanel();
		lowerWrapper.setLayout(new BorderLayout());
		add(lowerWrapper, BorderLayout.CENTER);
		
		//Add new JPanel to encapsulate console and choices and add to lower wrapper
		JPanel consoleAndChoiceWrapper = new JPanel();
		consoleAndChoiceWrapper.setLayout(new GridLayout(2,1,5,5));
		consoleAndChoiceWrapper.setSize(300,600);
		lowerWrapper.add(consoleAndChoiceWrapper, BorderLayout.WEST);
		
		//Set default console output as JTextArea
		//Create console
		taConsole = new JTextArea(15,30);
		taConsole.setWrapStyleWord(true);
		taConsole.setLineWrap(true);
		taConsole.setEditable(false);
		taConsole.setFocusable(false);
		
		//Look and Feel changes font for the text field, so change it back!
		taConsole.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		//Force console to auto-scroll to bottom of window
		DefaultCaret caret = (DefaultCaret)taConsole.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//Create a scroll-able wrapper
		JScrollPane scroll = new JScrollPane(taConsole,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		PrintStream out = new PrintStream (new TextAreaOutputStream (taConsole));
		System.setOut(out);
		
		//this has to be down here to force the output to display in the gui
		readInKeywordSets();
		writeOutKeywordSets();
		
		System.out.println("Delete all header data, and save your excel file."
				+ "\nEnter the file path into the box above! \n");
		
		//Add a new JPanel for the console and add to JFrame
		JPanel consolePanel = new JPanel();
		consolePanel.setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		add(consolePanel,BorderLayout.WEST);
		consolePanel.add(scroll);
		consoleAndChoiceWrapper.add(consolePanel);
		
		//Add choice Panel to console and choice panel
		choicePanel = new choicePanel();
		consoleAndChoiceWrapper.add(choicePanel);
		
		
		//Create new wrapper to hold stats and graphs
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,1,5,5));
		
		//add lower components to frame
		add(consoleAndChoiceWrapper, BorderLayout.WEST);
		add(infoPanel, BorderLayout.CENTER);
		//Add a new JPanel for numerical results
		statPanel = new statsPanel();
		statPanel.setLayout(new GridLayout(7,6,5,5));
		statPanel.setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		infoPanel.add(statPanel);
		
		//Add graphPanel to the infoPanel Wrapper
		graphPanel = new graphPanel();
		graphPanel.setLayout(new BorderLayout());
		graphPanel.setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		infoPanel.add(graphPanel);
				
		screenCapPanel = new screenCapPanel();
		add(screenCapPanel, BorderLayout.SOUTH);
		
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		repaint();
		validate();
		
	}
	
	//********************************************************************************
	
	public static List<Review> getReviews() {
		return reviews;
	}
	
	public static void setReviews(List<Review> r){
		reviews = r;
	}
	
	public static List<keywordSet> getKeywordSet(){
		return masterKeywordSet;
	}
	
	//This method pulls all the saved keywords from the txt file and makes objects out of them
	public static void readInKeywordSets() {
		
		try {
			//Open the .txt file
			File keywordSetsFile = new File("keywordSets.txt");
			Scanner scanner = new Scanner(keywordSetsFile);
			scanner.useDelimiter("\n");
			
			//while there are more lines delimited with a \n
			while(scanner.hasNext()) {
				
				//Save the data
				String line = scanner.nextLine();
				
				//Split the line by the hyphen, trim the whitespace, and save the product type
				String productType = line.split("-")[0].trim();
				
				//Use everything else to save into a string array, split by commas
				String keywordDataArrayString = line.split("-")[1].trim();
				
				String keywordDataArray[];
				
				if(keywordDataArrayString.isBlank()) {
					keywordSet k = new keywordSet(productType);
					masterKeywordSet.add(k);
				}
				
				else {
					keywordDataArray = keywordDataArrayString.split(",");
					
					//Create a List to finish the keywordSet object
					List<String> keywordDataList = new ArrayList<String>();
					
					//iterate over the array and save the individual keywords into the list
					for(String s : keywordDataArray) {
						keywordDataList.add(s.trim());
					}
					
					//Create a new keywordSet and add to master;
					keywordSet k = new keywordSet(productType, keywordDataList);
					masterKeywordSet.add(k);	
				}
			}
			
			for(keywordSet ks : masterKeywordSet) {
				//System.out.print(ks.toString());
			}
			
			//System.out.println(masterKeywordSet.size() + " keyword sets were added\n");
		
			scanner.close();		
			
			//If there's not text file in the directory, create a new one and add one product type;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			File file = new File("keywordSets.txt");

			keywordSet k = new keywordSet("Toaster");
			masterKeywordSet.add(k);
		}
	}
	
	public static void writeOutKeywordSets() {
		try {
			FileWriter fileWriter = new FileWriter("keywordSets.txt");
			
			//List<String> test = new ArrayList<String>();
			//test.add("This is a test!");
			//masterKeywordSet.add(new keywordSet("Hello", test));
			
			for(keywordSet k : masterKeywordSet) {
				fileWriter.write(k.toString());
			}
			
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error writing text file\n");
		}
		
	}
}	