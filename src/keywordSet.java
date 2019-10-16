import java.util.*;

public class keywordSet {
	private String productType;
	public List<String> keywordList;
	
	public keywordSet(String type) {
		productType = type;
		keywordList = new ArrayList<String>();
	}
	
	public keywordSet(String type, List<String> keywords) {
		productType = type;
		keywordList = keywords;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public String toString() {
		String line = productType + " - ";
		
		for(int i = 0; i < keywordList.size(); i++) {
			if(i < keywordList.size()-1) {
				line += (keywordList.get(i) + ", ");
			}
			else {
				line += keywordList.get(i) + "\n";
			}
			
		}
		
		return line;
	}
}
