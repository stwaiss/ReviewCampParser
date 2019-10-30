import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class parserMenuBar extends JMenuBar {
	static int count = 1;
	
	public parserMenuBar() {
		//Create a new Menu for basic window control
		JMenu fileMenu = new JMenu("File");
		
		//Create a button to screen capture
		JMenuItem screenCap = new JMenuItem("Screen Capture");
		screenCap.addActionListener(new screenCapListener());
		fileMenu.add(screenCap);
		
		//Create a button to exit the program
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new exitListener());
		fileMenu.add(exit);
		
		//Add the file menu to the menu bar
		add(fileMenu);
		
		//Create a new Menu for managing settings
		JMenu settingsMenu = new JMenu("Settings");
				
		//Create button to add product types (grills, shavers, irons, etc...)
		JMenuItem addProductTypes = new JMenuItem("Add Product Types");
		settingsMenu.add(addProductTypes);
		addProductTypes.addActionListener(new addProductTypesListener());
		
		//Create menu to edit the existing sets in the system
		JMenuItem editAllSets = new JMenuItem("Edit Keyword Sets");
		settingsMenu.add(editAllSets);
		editAllSets.addActionListener(new editAllSetsListener());
				
		//Add the settings menu to the menu bar
		add(settingsMenu);
		
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem aboutMenu = new JMenuItem("About");
		helpMenu.add(aboutMenu);
		aboutMenu.addActionListener(new aboutMenuListener());
		
		JMenuItem versionHistoryMenu = new JMenuItem("Version History");
		helpMenu.add(versionHistoryMenu);
		versionHistoryMenu.addActionListener(new versionHistoryMenuListener());
		
		add(helpMenu);
		
	}
	

	
	public class screenCapListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				
				application.window.setLocationRelativeTo(null);
				
				Robot rbt = new Robot();
				Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				BufferedImage background = rbt.createScreenCapture(screenRect);
				String desktopPath = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
				ImageIO.write(background, "png", new File (desktopPath + "\\screenshot" + count +".png"));
				
				System.out.println("Screenshot" + count + ".png saved to your desktop");
				count++;
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
	}

	public class exitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	public class editAllSetsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			keywordSetEditWindow window = new keywordSetEditWindow();
			window.setVisible(true);
		}
	}
	
	public class addProductTypesListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String newProductType = (String) JOptionPane.showInputDialog(null, "Enter new product type");
			
			if (newProductType != null && !newProductType.equalsIgnoreCase("")) {
				List<keywordSet> master = Parser.getKeywordSet();
				master.add(new keywordSet(newProductType));
				Parser.writeOutKeywordSets();
				JOptionPane.showInternalMessageDialog(null, newProductType + " has been added!");
			}			
		}
	}

	
	public class aboutMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			JOptionPane.showInternalMessageDialog(null, 
					"Telescope - A Review Camp Analysis Tool\nVersion Number: " + application.VERSION_NUMBER + 
					"\nCreated by: Sean Waiss\nCopyright: Spectrum Brands, " + year);
		}
	}
	
	public class versionHistoryMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showInternalMessageDialog(null, 
					"Review Camp Analyzer Version History Log:\n\n"
					+ "1.0.0 : (24 OCT 2019) Initial release\n"
					+ "1.0.1 : (29 OCT 2019) Corrected year formatting issues\n"
					+ "1.0.2 : (30 OCT 2019) Project Rename and fixed Keyword Sets\n"
					);
			
		}
	}
}
