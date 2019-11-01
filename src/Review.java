
public class Review {
	private String date;
	private String seller;
	private String star;
	private String title;
	private String body;
	
	//*********************************************************************************
	
	public Review(String d, String se, String s, String t, String b) {	
		date = d;
		date = date.replaceAll("[^\\p{ASCII}]", "");
		
		seller = se;
		seller = seller.replaceAll("[^\\p{ASCII}]", "");
		
		star = s;
		star = star.replaceAll("[^\\p{ASCII}]", "");
		
		title = t;
		title = title.replaceAll("[^\\p{ASCII}]", "");
		
		body = b;
		body = body.replaceAll("[^\\p{ASCII}]", "");
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
