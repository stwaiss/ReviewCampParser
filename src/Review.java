
public class Review {
	private String date;
	private String seller;
	private String star;
	private String title;
	private String body;
	
	//*********************************************************************************
	public Review(String d, String se, String s, String t, String b) {
		date = d;
		seller = se;
		star = s;
		title = t;
		body = b;
	}
	
	//*********************************************************************************
	public String getDate() {
		return date;
	}

	public String getSeller() {
		return seller;
	}

	public String getStar() {
		return star;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}
	
	
	
}
