package ath.nik.newAds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class FilterAction extends Activity{
	public static final String PREFS_NAME = "MyPrefsFile";
	TextView txt1;
	TextView txt2;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);
                
        txt1=(TextView) findViewById(R.id.categorytxt);
        txt2=(TextView) findViewById(R.id.areatxt);
        
        // Διαχείριση button
        
        final Button button1 = (Button) findViewById(R.id.areabtn);
        final Button button2 = (Button) findViewById(R.id.categorybtn);
        final Button button3 = (Button) findViewById(R.id.searchads);

        if(!checkInternetConnection()){
        	Toast.makeText(this, "Internet Connection Not Present", 4000).show();
        	button1.setEnabled(false);
        	button2.setEnabled(false);
        	button3.setEnabled(false);
        	
        }else{
        	button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
    				Intent openStartingView = new Intent("ath.nik.newAds.AREAACTIVITYWS");
    				startActivity(openStartingView);
                }
            });
        	button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
    				Intent openStartingView = new Intent("ath.nik.newAds.CATEGORYACTIVITYWS");
    				startActivity(openStartingView);
                }
            });
        	button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
    				Intent openStartingView = new Intent("ath.nik.newAds.ADSVIEW");
    				openStartingView.putExtra("CategoryID", settings.getString("chosenCategoryID", "-1"));
    				openStartingView.putExtra("AreaID", settings.getString("chosenAreaID", "-1"));
    				startActivity(openStartingView);
                }
            });
        }
        
        
        final Button button4 = (Button) findViewById(R.id.searchButton);
        button4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openStartingView = new Intent("ath.nik.newAds.SEARCHACTION");
				startActivity(openStartingView);
			}
		});
        
        // Τέλος διαχείρισης button
        
        
        VariablesStorage.getInstance().initializeVariables(); // Αρχικοποίηση global μεταβλητών.
        
        
        // LOAD
        
        loadCriteria();
            	    	
    	//txt1.setText(settings.getString("chosenCategoryTitle", "false"));
        
       
    	
	}
	
	// Δημιουργία menu και διαχείριση των αντικειμένων του
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 0, "Αποθήκευση Κριτηρίων").setIcon(R.drawable.save);
	    menu.add(1, 2, 1, "Κατάργηση Κριτηρίων").setIcon(R.drawable.delete);
	    return true;
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		switch(item.getItemId()){
		case 1:
			saveCriteria();
	     	Toast.makeText(this, "Τα κριτήρια αποθηκεύτηκαν", 2000).show();
			break;
		case 2:
			deleteCriteria();
	      	break;
		}
     return super.onOptionsItemSelected(item);

    }
	
	// Τέλος δημιουργίας και διαχείρισης menu αντικειμένων
	
	
	
	
	// Διαχείριση δεδομένων
	
	private void saveCriteria(){
		
		// Category save
		
		editor.putString("chosenCategoryDefaultTitle", VariablesStorage.getInstance().getCategoryTitle());
     	editor.putString("chosenCategoryDefaultID", VariablesStorage.getInstance().getCategoryID());
     	
     	// Areas save
     	
     	editor.putInt("areaArraySize", VariablesStorage.getInstance().getChosenAreaIDs().size());
     	for(int i=0;i<VariablesStorage.getInstance().getChosenAreaIDs().size();i++){
     		editor.putString("TitleChoice_"+i, VariablesStorage.getInstance().getChosenAreaTitles().get(i));
     		editor.putString("IDChoice_"+i, VariablesStorage.getInstance().getChosenAreaIDs().get(i));
     	}
     	
     	editor.commit();
	}
	
	private void loadCriteria(){
		
		settings = this.getSharedPreferences(PREFS_NAME, 0);
    	editor = settings.edit();
    	
    	// Load category
    	
    	VariablesStorage.getInstance().setCategoryTitle(settings.getString("chosenCategoryDefaultTitle", "Μη επιλεγμένη κατηγορία"));
    	VariablesStorage.getInstance().setCategoryID(settings.getString("chosenCategoryDefaultID", "-1"));
    	
    	// Load Area
    	
    	for(int i=0;i<settings.getInt("areaArraySize", 0);i++){
    		VariablesStorage.getInstance().addChosenAreaTitle(settings.getString("TitleChoice_"+i,null));
    		VariablesStorage.getInstance().addChosenAreaID(settings.getString("IDChoice_"+i,null));
    	}
    	
    	editor.commit();

	}
	
	private void deleteCriteria(){
		
		VariablesStorage.getInstance().initializeVariables();
		
		// Default category delete
		
		editor.putString("chosenCategoryDefaultTitle", "Μη επιλεγμένη κατηγορία");
		editor.putString("chosenCategoryDefaultID", "-1");
      	
      	// Default area delete
      	
      	editor.putInt("areaArraySize", 0);
      	
      	editor.commit();
      	
      	// Ενημέρωση των text
      	
      	txt1.setText(VariablesStorage.getInstance().getCategoryTitle());
      	txt2.setText("Μη επιλεγμένη κατηγορία");
	}
	
	
	
	@Override
	protected void onResume(){
		super.onResume();
		txt1.setText(VariablesStorage.getInstance().getCategoryTitle());
		if(VariablesStorage.getInstance().getChosenAreaTitles().isEmpty()){
			txt2.setText("Μη επιλεγμένη κατηγορία");
		}
		else{
			String s="";
			for(int i=0;i<VariablesStorage.getInstance().getChosenAreaTitles().size();i++)
				s+=VariablesStorage.getInstance().getChosenAreaTitles().get(i)+", ";
			if (s.length()<30){
				txt2.setText(s.substring(0, s.length()-2));
			}else{
				txt2.setText(s.substring(0, 30)+"...");
			}
		}
	}
	
	private boolean checkInternetConnection() {

		ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);

		// ARE WE CONNECTED TO THE NET

		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
			//Toast.makeText(this, "nai", 4000).show();
			return true;

		} else {
			//Toast.makeText(this, "oxi", 4000).show();
			return false;

		}

	} 
}
