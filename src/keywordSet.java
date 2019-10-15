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
	
}
