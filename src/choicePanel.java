import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class choicePanel extends JPanel{
	
	private static JRadioButton[] options = new JRadioButton[4];
	
	public choicePanel() {
		
		//set border and layout type
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder((new Insets(20,20,20,20))));
		
		//define options array to include 4 JRadioButtons
		options[0] = new JRadioButton("By Star Rating");
		options[1] = new JRadioButton("By Star Rating vs Year");
		options[2] = new JRadioButton("By Star Rating vs Seller");
		options[3] = new JRadioButton("By Star Rating vs Seller");
		
		//add a submit button at the bottom
		JButton submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(100,10));
		submit.addActionListener(new submitBtnListener());
		submit.setHorizontalAlignment(JButton.CENTER);
		
		//Create a new button group for all the radio buttons
		ButtonGroup group = new ButtonGroup();
		
		//add the radio buttons to the group and to the pane, and also add separators for space
		for (JRadioButton b: options) {
			group.add(b);
			add(b);
			this.add(Box.createRigidArea(new Dimension(0,10)));
		}
		//add the submit button to the panel too!
		add(submit);
	
		
	}
	
	public class submitBtnListener implements ActionListener{

		//eventually, it'll pull the radio button that was selected and make a pretty graph. 
		//BUT WE ARENT THERE YET!
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
