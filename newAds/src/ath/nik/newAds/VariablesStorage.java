package ath.nik.newAds;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Xml;

//import android.content.SharedPreferences;

public class VariablesStorage
{
	public static final String PREFS_NAME = "MyPrefsFile";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	private static VariablesStorage instance;
	private String categoryOrArea,keywords;
	private int howMany=20;
	private ArrayList<WSResults> Categories,Areas;
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
	
	// Load Criteria
	
	public void loadCriteria(Context con){
		
		settings = con.getSharedPreferences(PREFS_NAME, 0);
    	editor = settings.edit();
    	
    	// Load category
    	
    	for(int i=0;i<settings.getInt("CategoryArraySize", 0);i++){
    		addChosenCategoryTitle(settings.getString("CategoryTitleChoice_"+i,null));
    		addChosenCategoryID(settings.getString("CategoryIDChoice_"+i,null));
    	}
    	
    	// Load Area
    	
    	for(int i=0;i<settings.getInt("AreaArraySize", 0);i++){
    		addChosenAreaTitle(settings.getString("AreaTitleChoice_"+i,null));
    		addChosenAreaID(settings.getString("AreaIDChoice_"+i,null));
    	}
    	
    	editor.commit();

	}
	
	// Save Criteria
	
	public void saveCriteria(){
		
		// Category save
		
		editor.putInt("CategoryArraySize", getChosenCategoryIDs().size());
		for(int i=0;i<getChosenCategoryIDs().size();i++){
     		editor.putString("CategoryTitleChoice_"+i, getChosenCategoryTitles().get(i));
     		editor.putString("CategoryIDChoice_"+i, getChosenCategoryIDs().get(i));
     	}
		
     	// Areas save
     	
     	editor.putInt("AreaArraySize", getChosenAreaIDs().size());
     	for(int i=0;i<getChosenAreaIDs().size();i++){
     		editor.putString("AreaTitleChoice_"+i, getChosenAreaTitles().get(i));
     		editor.putString("AreaIDChoice_"+i, getChosenAreaIDs().get(i));
     	}
     	
     	editor.commit();
	}
	
	// XML methods
	
	public void updateCategoryXMLFile(Context con, String method){
		WebService ws=new WebService(method,"-1");
		ArrayList<WSResults> temp;
		if (method.equals("Category")){
			Categories=ws.getList();
			temp=Categories;
		}else{
			Areas=ws.getList();
			temp=Areas;
		}
		
		
		try {
			
			FileOutputStream fos = con.openFileOutput(method, Context.MODE_PRIVATE);
			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(fos, "UTF-8");
			serializer.startDocument(null, Boolean.valueOf(true));
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
						
			for(int i=0;i<temp.size();i++){
				serializer.startTag(null, "row");
				
					serializer.startTag(null, "title");
					serializer.text(temp.get(i).getTitle());
					serializer.endTag(null, "title");
					
					serializer.startTag(null, "id");
					serializer.text(temp.get(i).getId());
					serializer.endTag(null, "id");
					
					serializer.startTag(null, "idfather");
					serializer.text(temp.get(i).getIdFather());
					serializer.endTag(null, "idfather");
					
					serializer.startTag(null, "count");
					serializer.text(temp.get(i).getCount());
					serializer.endTag(null, "count");
					
				serializer.endTag(null, "row");
			}
			
			serializer.endDocument();
            //write xml data into the FileOutputStream
            serializer.flush();
            //finally we close the file stream
            fos.close();
			    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 Log.e("Exception","error occurred while creating xml file");
		}
	}
	
	public void getDataFromXML(){
		
	}
	
	public ArrayList<WSResults> getList(String id){
		ArrayList<WSResults> l=new ArrayList<WSResults>();
		if (categoryOrArea.equals("Category")){
			for (int i=0;i<Categories.size();i++){
				if(Categories.get(i).getIdFather().equals(id))
					l.add(Categories.get(i));
			}
		}else{
			for (int i=0;i<Areas.size();i++){
				if(Areas.get(i).getIdFather().equals(id))
					l.add(Areas.get(i));
			}
		}
		
		return l;
	}
	
}
