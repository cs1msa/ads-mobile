package ath.nik.newAds;

import java.util.ArrayList;

//import android.content.SharedPreferences;

public class VariablesStorage
{
	private static VariablesStorage instance;
	private String categoryOrArea,keywords;
	private int howMany=20;
	private ArrayList<String> chosenAreasTitles,chosenAreasIDs,chosenCategorysTitles,chosenCategorysIDs;
	public static void initInstance()
	{
		if (instance == null)
		{
			// Create the instance
			instance = new VariablesStorage();
		}
	}
	public static VariablesStorage getInstance()
	{
		// Return the instance
		return instance;
	}
	private VariablesStorage()
	{
		// Constructor hidden because this is a singleton
	}
  
	
	public void initializeVariables(){
		initializeArea();
		initializeCategory();
	}
	
	public void initializeArea(){
		chosenAreasTitles=new ArrayList<String>();
		chosenAreasIDs=new ArrayList<String>();
	}
	
	public void initializeCategory(){
		chosenCategorysTitles=new ArrayList<String>();
		chosenCategorysIDs=new ArrayList<String>();
	}
	
	// Area Methods
  
	public void addChosenAreaTitle(String title)
	{
		chosenAreasTitles.add(title);
	}
	public void addChosenAreaID(String id)
	{
		chosenAreasIDs.add(id);
	}
	public ArrayList<String> getChosenAreaIDs()
	{
		return chosenAreasIDs;
	}
	public ArrayList<String> getChosenAreaTitles()
	{
		return chosenAreasTitles;
	}
	public void deleteAreaTitleAndID(String id, String Title)
	{
		chosenAreasIDs.remove(id);
		chosenAreasTitles.remove(Title);
	}
	public String allChosensAreas(){
		return chosenAreasTitles.toString();
	}
	public boolean IDAreaExist(String id){
		return chosenAreasIDs.contains(id);
	}
  
	// Category Methods
	
	public void addChosenCategoryTitle(String cat){
		chosenCategorysTitles.add(cat);
	}
	public void addChosenCategoryID(String id){
		chosenCategorysIDs.add(id);
	}
	public ArrayList<String> getChosenCategoryIDs()
	{
		return chosenCategorysIDs;
	}
	public ArrayList<String> getChosenCategoryTitles()
	{
		return chosenCategorysTitles;
	}
	public void deleteCategoryTitleAndID(String id, String Title)
	{
		chosenCategorysIDs.remove(id);
		chosenCategorysTitles.remove(Title);
	}
	public String allChosensCategorys(){
		return chosenCategorysTitles.toString();
	}
	public boolean IDCategoryExist(String id){
		return chosenCategorysIDs.contains(id);
	}
  
  
  // keywords method

	public void addKeywords(String keywords){
		this.keywords=keywords;
	}
	public String getKeywords()
	{
		return keywords;
	}
	
	// categoryOrArea methods
	
	public String getCategoryOrArea(){
		return categoryOrArea;
	}
	
	public void setCategoryOrArea(String s){
		categoryOrArea=s;
	}
	
	// HowMany ads methods
	
	public int getHowManyNumber(){
		return howMany;
	}
	
}
