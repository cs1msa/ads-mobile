package ath.nik.newAds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;


public class VariablesStorage
{
	public static final String PREFS_NAME = "MyPrefsFile";
	ArrayList<String> test=new ArrayList<String>();
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	private static VariablesStorage instance;
	private String categoryOrArea,keywords;
	private int howMany=20;
	private ArrayList<WSResults> Categories,Areas;
	private ArrayList<WSResults> chosenCategories,chosenAreas;
	
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
		Categories=new ArrayList<WSResults>();
		Areas=new ArrayList<WSResults>();
	}
	
	public void initializeArea(){
		chosenAreas=new ArrayList<WSResults>();
	}
	
	public void initializeCategory(){
		chosenCategories=new ArrayList<WSResults>();
	}
	
	// Area Methods

	public void addChosenArea(WSResults o){
		chosenAreas.add(o);
	}
	public ArrayList<WSResults> getChosenAreas()
	{
		return chosenAreas;
	}
	public void deleteArea(WSResults o){
		chosenAreas.remove(o);
	}
	public boolean IDExistOnChosenAreas(String id){
		for(int i=0;i<chosenAreas.size();i++){
			if(chosenAreas.get(i).getId().equals(id))
				return true;
		}
		return false;
	}
	public WSResults findAreafromXML(String id){
		for(int i=0;i<Areas.size();i++){
			if(Areas.get(i).getId().equals(id))
				return Areas.get(i);
		}
		return new WSResults();
	}
	
	// Category Methods
	
	public void addChosenCategory(WSResults o){
		chosenCategories.add(o);
	}
	public ArrayList<WSResults> getChosenCategories()
	{
		return chosenCategories;
	}
	public void deleteCategory(WSResults o){
		chosenCategories.remove(o);
	}
	public boolean IDExistOnChosenCategories(String id){
		for(int i=0;i<chosenCategories.size();i++){
			if(chosenCategories.get(i).getId().equals(id))
				return true;
		}
		return false;
	}
	public WSResults findCategoryfromXML(String id){
		for(int i=0;i<Categories.size();i++){
			if(Categories.get(i).getId().equals(id))
				return Categories.get(i);
		}
		return new WSResults();
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
		
    	// Load category
    	
    	for(int i=0;i<settings.getInt("CategoryArraySize", 0);i++){
    		addChosenCategory(findCategoryfromXML(settings.getString("CategoryIDChoice_"+i,null)));
    		//Toast.makeText(con, i+settings.getString("CategoryIDChoice_"+i,null), 1000).show();
    	}
    	// Load Area
    	
    	for(int i=0;i<settings.getInt("AreaArraySize", 0);i++)
    		addChosenArea(findAreafromXML(settings.getString("AreaIDChoice_"+i,null)));
 			

	}
	
	// Save Criteria
	
	public void saveCriteria(Context con){
		
		settings = con.getSharedPreferences(PREFS_NAME, 0);
    	editor = settings.edit();
		
		// Category save
		
		editor.putInt("CategoryArraySize", chosenCategories.size());
		for(int i=0;i<chosenCategories.size();i++){
     		editor.putString("CategoryIDChoice_"+i, chosenCategories.get(i).getId());
		}
     	// Areas save
     	
     	editor.putInt("AreaArraySize", chosenAreas.size());
     	for(int i=0;i<chosenAreas.size();i++)
     		editor.putString("AreaIDChoice_"+i, chosenAreas.get(i).getId());
     	
     	editor.commit();
	}
	
	// XML methods
	
	
	public void getDataFromXML(Context con) throws XmlPullParserException, IOException{
		String[] s=con.fileList();
		boolean flag=false;
		for(int i=0;i<s.length;i++){
			if(s[i].equals("Category"))
				flag=true;
		}
		if(!flag){
			updateCategoryXMLFile(con, "Category");
			updateCategoryXMLFile(con, "Area");
		}else{
			File f1=con.getFileStreamPath("Category");
			File f2=con.getFileStreamPath("Area");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(false);
		    XmlPullParser xpp = factory.newPullParser();

		    // create an input stream to be read by the stream reader.
		    FileInputStream fis1 = new FileInputStream(f1);
		    FileInputStream fis2 = new FileInputStream(f2);
		    // set the input for the parser using an InputStreamReader
		    xpp.setInput(new InputStreamReader(fis1));
		    int eventType = xpp.getEventType();
		    String title,id,idfather,count;
		    title="";
		    id="";
		    idfather="";
		    count="";
		    while (eventType != XmlPullParser.END_DOCUMENT){
		    	if (eventType == XmlPullParser.START_TAG ){ 
		    		if ( xpp.getName().equals("title")){
		    			xpp.next();
		    			title=xpp.getText();
		    		} else if(xpp.getName().equals("id")){
		    			xpp.next();
		    			id=xpp.getText();
		    		} else if(xpp.getName().equals("idfather")){
		    			xpp.next();
		    			idfather=xpp.getText();
		    		} else if (xpp.getName().equals("count")){
		    			xpp.next();
		    			count=xpp.getText();
		    		}
		    	} else if (eventType == XmlPullParser.END_TAG){
		    		if ( xpp.getName().equals("row")){
		    			Categories.add(new WSResults(title,id,idfather,count));
		    		}
		    	}
		    	
		    	xpp.next();
		    	eventType = xpp.getEventType();
		    }
		    xpp.setInput(new InputStreamReader(fis2));
		    eventType = xpp.getEventType();
		    title="";
		    id="";
		    idfather="";
		    count="";
		    while (eventType != XmlPullParser.END_DOCUMENT){
		    	if (eventType == XmlPullParser.START_TAG ){ 
		    		if ( xpp.getName().equals("title")){
		    			xpp.next();
		    			title=xpp.getText();
		    		} else if(xpp.getName().equals("id")){
		    			xpp.next();
		    			id=xpp.getText();
		    		} else if(xpp.getName().equals("idfather")){
		    			xpp.next();
		    			idfather=xpp.getText();
		    		} else if (xpp.getName().equals("count")){
		    			xpp.next();
		    			count=xpp.getText();
		    		}
		    	} else if (eventType == XmlPullParser.END_TAG){
		    		if ( xpp.getName().equals("row")){
		    			Areas.add(new WSResults(title,id,idfather,count));
		    		}
		    	}
		    	
		    	xpp.next();
		    	eventType = xpp.getEventType();
		    }
		   
		}
		for(int i=0;i<test.size();i++){
			if(test.get(i)==null)
				test.set(i, "null");
		}
	}
	
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
	
	// list methods
	
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
