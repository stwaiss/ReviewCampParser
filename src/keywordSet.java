import java.util.*;

public class keywordSet {
	private String division;
	private String productType;
	public List<String> keywordList;
	
//	public keywordSet() {
//		division = "";
//		productType = "";
//		keywordList = new ArrayList<String>();
//	}
	
	
	public keywordSet(String div, String type, List<String> keywords) {
		division = div;
		productType = type;
		keywordList = keywords;
	}
	
	public String getDivision() {
		return division;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public List<String> getKeywordList(){
		return keywordList;
	}
	
	public String toString() {
		String line = division + " - " + productType + " - ";

		//If there's nothing in the list, just return "division - productType - "
		if(keywordList.size() == 0) {
			return line + " \n";
		}
		
		//Else, iterate through the list and then return
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
