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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;


public class VariablesStorage
{
	public static final String PREFS_NAME = "MyPrefsFile";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	private static VariablesStorage instance;
	private String categoryOrArea,keywords;
	private int howMany=20;
	private TreeMenu Categories,Areas;
	
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
		Categories=new TreeMenu();
		Areas=new TreeMenu();
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
    		Categories.find(settings.getString("CategoryIDChoice_"+i,null)).check();
    	}
    	// Load Area
    	
    	for(int i=0;i<settings.getInt("AreaArraySize", 0);i++)
    		Areas.find(settings.getString("AreaIDChoice_"+i,null)).check();
 			
	}
	
	// Save Criteria
	
	public void saveCriteria(Context con){
		
		settings = con.getSharedPreferences(PREFS_NAME, 0);
    	editor = settings.edit();
		
		// Category save
		
    	ArrayList<TreeNode> temp=Categories.getChosen();
		editor.putInt("CategoryArraySize", temp.size());
		for(int i=0;i<temp.size();i++){
     		editor.putString("CategoryIDChoice_"+i, temp.get(i).getId());
		}
     	// Areas save
     	
		temp=Areas.getChosen();
     	editor.putInt("AreaArraySize", temp.size());
     	for(int i=0;i<temp.size();i++)
     		editor.putString("AreaIDChoice_"+i, temp.get(i).getId());
     	
     	editor.commit();
	}
	
	// XML methods
	
	
	public void getDataFromXML(Context con, boolean flag) throws XmlPullParserException, IOException{
		
		if(!flag){
			updateCategoryXMLFile(con, "Category");
			updateCategoryXMLFile(con, "Area");
		}else{
			File f1=con.getFileStreamPath("Category");
			File f2=con.getFileStreamPath("Area");
			
		    // create an input stream to be read by the stream reader.
		    FileInputStream fis1 = new FileInputStream(f1);
		    FileInputStream fis2 = new FileInputStream(f2);
		    retrieveData(fis1,Categories);
		    //retrieveData(fis2,Areas);
		    // set the input for the parser using an InputStreamReader
		}
	}
	
	private void retrieveData(FileInputStream f, TreeMenu temp) throws XmlPullParserException, IOException{
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
	    XmlPullParser xpp = factory.newPullParser();
	    xpp.setInput(new InputStreamReader(f));
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
	    			temp.addNode(new TreeNode(title,id,count), idfather);
	    		}
	    	}
	    	xpp.next();
	    	eventType = xpp.getEventType();
	    }
	    
	}
	
	public void updateCategoryXMLFile(Context con, String method){
		WebService ws=new WebService(method,"-1");
		ArrayList<TreeNode> temp;
		if (method.equals("Category")){
			Categories.organizeWebData(ws.getWebData());
			temp=Categories.getAllData();
		}else{
			Areas.organizeWebData(ws.getWebData());
			temp=Areas.getAllData();
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
					serializer.text(temp.get(i).getFather().getId());
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
	
	public void unCheckAll(){
		if (categoryOrArea.equals("Area"))
			Areas.unCheckAll();
		else
			Categories.unCheckAll();
	}
	
	public ArrayList<TreeNode> getList(String id){
		if (categoryOrArea.equals("Area"))
			return Areas.getSubMenu(id);
		else
			return Categories.getSubMenu(id);
	}
	
	public TreeMenu getAreas(){
		return Areas;
	}
	public TreeMenu getCategories(){
		return Categories;
	}
}
