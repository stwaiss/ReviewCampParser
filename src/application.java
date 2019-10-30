import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/* Telescope - A Review Camp Excel Parser
 * 10/21/2019
 * Sean Waiss - Quality Engineer
 * Spectrum Brands, INC
 * Home and Personal Care Division
 * Global Quality
 * 
 * This program is designed to accept excel-file exports from www.reviewcamp.com, 
 * a star review aggregator used to evaluate field performance of consumer goods.
 * 
 * This program accepts excel files with 5 columns of information: Date, Seller, Star, Title, Body
 * and save locally to memory. Once all the reviews in the excel file have been parsed, the user can
 * view statistics about the reviews, and generate graphs to see trends about how products and 
 * keywords in reviews perform. The user can then export their findings to .png photo files and 
 * send via email to other colleagues to share their findings.
 * 
 * 
 * This software is created and owned by Spectrum Brands, INC, Copyright 2019, and may not be
 * replicated, duplicated, disseminated, or transmitted by any other means. 
 * 
 */


public class application {
	public static Parser window;
	public final static String VERSION_NUMBER = "1.0.2"; 
	
	public static void main(String[] args) {
		
		  try {
		  //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		  }
		  catch (ClassNotFoundException | InstantiationException |
				  IllegalAccessException | UnsupportedLookAndFeelException e) { // TODO
			  // Auto-generated catch block 
			  e.printStackTrace(); 
			  }
		 
		window = new Parser();
		window.setVisible(true);
	}
}