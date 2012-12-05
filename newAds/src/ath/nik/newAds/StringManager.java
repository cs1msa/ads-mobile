package ath.nik.newAds;

public class StringManager {

	private String title,information;
	
	StringManager(String s){
		title=exploreTitle(s);
		information=exploreInformation(s);
	}
	
	public String exploreTitle(String s){
		if(s.contains("<b>")&&s.contains("</b>")){
			String[] title=s.split("<b>");
			title=title[1].split("</b>");
			return title[0];
		}
		return s;
	}
	public String exploreInformation(String s){
		String[] title=s.split("</b>");
		title=title[1].split("#");
		return title[0];		
	}
	public String getTitle(){
		return title;
	}
	public String getInformation(){
		return information;
	}
	public String getTitleAndInformationWithDots(){
		String[] splt=information.split(",");
		
		if (splt.length>2)
			return "<b>"+getTitle()+"</b><br>"+splt[0]+splt[1]+"...";
		else
			return getFullTitleAndInformation();
	}
	public String getFullTitleAndInformation(){
		return "<b>"+getTitle()+"</b><br>"+information;
	}
}
