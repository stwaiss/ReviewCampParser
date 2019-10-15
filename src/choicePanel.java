import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class choicePanel extends JPanel{
	
	private static JRadioButton[] options = new JRadioButton[4];
	ButtonGroup group = new ButtonGroup();
	public choicePanel() {
		
		//set border and layout type
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		
		//define options array to include 4 JRadioButtons
		options[0] = new JRadioButton("By Star Rating");
		options[0].addActionListener(new graphPanel.totalStarRatingListener());
		
		options[1] = new JRadioButton("By Star Rating vs Year");
		options[1].addActionListener(new graphPanel.starRatingVsYearListener());
		
		options[2] = new JRadioButton("By Star Rating vs Seller");
		options[2].addActionListener(new graphPanel.starRatingVsSellerListener());
		
		options[3] = new JRadioButton("Keyword Pareto");
		options[3].addActionListener(new graphPanel.keywordParetoListener());
		
		
		//add the radio buttons to the group and to the pane, and also add separators for space
		for (JRadioButton b: options) {
			group.add(b);
			add(b);
			this.add(Box.createRigidArea(new Dimension(0,10)));
		}

	
		
	}
	
}
