import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
		
		//Create button to edit product types (grills, shavers, irons, etc...)
		JMenuItem editProductTypes = new JMenuItem("Edit Product Types");
		settingsMenu.add(editProductTypes);
		
		//Create button to edit keywords ({Grills,[don't work, stopped working]}
		JMenuItem editKeywords = new JMenuItem("Edit Keywords");
		settingsMenu.add(editKeywords);
		
		//Add the settings menu to the menu bar
		add(settingsMenu);
		
	}
	

	
	public class screenCapListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
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

}
