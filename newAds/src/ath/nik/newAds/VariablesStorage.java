package ath.nik.newAds;

import java.util.ArrayList;

import android.content.SharedPreferences;

public class VariablesStorage
{
	public static final String PREFS_NAME = "MyPrefsFile";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	private static VariablesStorage instance;
	private ArrayList<String> chosenAreasTitles;
	private ArrayList<String> chosenAreasIDs;
	private String chosenCategoryTitle,chosenCategoryID;
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
	public void initializeVariables(){
		chosenAreasTitles=new ArrayList<String>();
		chosenAreasIDs=new ArrayList<String>();
		chosenCategoryTitle="Μη επιλεγμένη κατηγορία";
		chosenCategoryID="-1";
	}
	public boolean IDAreaExist(String id){
		return chosenAreasIDs.contains(id);
	}
  
  
  
  
	// Category Methods
	public void setCategoryTitle(String cat){
		chosenCategoryTitle=cat;
	}
	public String getCategoryTitle(){
		return chosenCategoryTitle;
	}
	public void setCategoryID(String id){
		chosenCategoryID=id;
	}
	public String getCategoryID(){
		return chosenCategoryID;
	}
	
  
  
  // SAVE, LOAD AND DELETE

}
