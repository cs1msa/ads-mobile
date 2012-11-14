package ath.nik.newAds;

import java.util.ArrayList;

//import android.content.SharedPreferences;

public class VariablesStorage
{
	//public static final String PREFS_NAME = "MyPrefsFile";
	//SharedPreferences settings;
	//SharedPreferences.Editor editor;
	private static VariablesStorage instance;
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
  
  
  // SAVE, LOAD AND DELETE

}
