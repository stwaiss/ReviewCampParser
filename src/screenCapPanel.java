import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class screenCapPanel extends JPanel {
	JButton submit;
	static int count = 1;
	
	public screenCapPanel(){
		submit = new JButton("Screen Capture");
		submit.addActionListener(new submitBtnListener());
		add(submit);
	}
	
	public class submitBtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				application.window.setLocationRelativeTo(null);
				
				Robot rbt = new Robot();
				Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				BufferedImage background = rbt.createScreenCapture(screenRect);
				String desktopPath = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
				
				String productType = statsPanel.pSkuText.getText();
				String graphType = null;
				try {
					graphType = choicePanel.group.getSelection().getActionCommand();
				} catch(NullPointerException npe) {}
				
				
				if(productType != null && graphType != null) {
					ImageIO.write(background, "png", new File (desktopPath + "\\" + productType +" - " + graphType +".png"));
					System.out.println(productType + " - " +graphType + ".png saved to your desktop\n");
				}
				else {
					ImageIO.write(background, "png", new File (desktopPath + "\\Screenshot" + count +".png"));
					System.out.println("Screenshot" + count + ".png saved to your desktop\n");
					count++;
				}
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}


		}
		
	}
}
