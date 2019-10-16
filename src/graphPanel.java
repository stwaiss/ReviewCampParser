import javax.swing.*;

public class graphPanel extends JPanel {
	private static String productType;

	public graphPanel() {

	}

	public void setProductType(String type) {
		if (type != null && type != "") {
			productType = type;
		}
	}

	public graphPanel getThis() {
		return graphPanel.this;
	}
} 
 