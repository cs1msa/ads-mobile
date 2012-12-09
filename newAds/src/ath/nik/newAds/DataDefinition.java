package ath.nik.newAds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DataDefinition extends Activity{
	public static final String PREFS_NAME = "MyPrefsFile";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	Button button1,button2,button3,button4;
	TextView keywordText;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datadefinition);
                
        button1 = (Button) findViewById(R.id.Area);
        button2 = (Button) findViewById(R.id.Category);
        button3 = (Button) findViewById(R.id.searchads);
        button4 = (Button) findViewById(R.id.overview);
        keywordText = (TextView) findViewById(R.id.keywords);
        
        // Διαχείριση button
 
        if(!checkInternetConnection()){
        	Toast.makeText(this, "Internet Connection Not Present", 4000).show();
        	button1.setEnabled(false);
        	button2.setEnabled(false);
        	button3.setEnabled(false);
        	
        }else{
        	button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	VariablesStorage.getInstance().setCategoryOrArea(button1.getTag().toString());
    				Intent openStartingView = new Intent("ath.nik.newAds.OptionLists");
    				startActivity(openStartingView);
                }
            });
        	button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	VariablesStorage.getInstance().setCategoryOrArea(button2.getTag().toString());
    				Intent openStartingView = new Intent("ath.nik.newAds.OptionLists");
    				startActivity(openStartingView);
                }
            });
        	button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	VariablesStorage.getInstance().addKeywords(keywordText.getText().toString());
                	Intent openStartingView = new Intent("ath.nik.newAds.AdsList");
    				startActivity(openStartingView);
                }
            });
        }
        
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				Intent openStartingView = new Intent("ath.nik.newAds.ShowSelectedFilters");
				startActivity(openStartingView);
            }
        });
        
        // Τέλος διαχείρισης button
        
        
        VariablesStorage.getInstance().initializeVariables(); // Αρχικοποίηση global μεταβλητών.
        
        // LOAD
        VariablesStorage.getInstance().updateCategoryXMLFile(this, "Category");
        VariablesStorage.getInstance().loadCriteria(this);
        
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
			 VariablesStorage.getInstance().saveCriteria();
	     	Toast.makeText(this, "Τα κριτήρια αποθηκεύτηκαν", 2000).show();
			break;
		case 2:
			deleteCriteria();
			this.onResume();
	      	break;
		}
     return super.onOptionsItemSelected(item);

    }
	
	// Τέλος δημιουργίας και διαχείρισης menu αντικειμένων
	
	private void deleteCriteria(){
		
		// Static variables initialize
		
		VariablesStorage.getInstance().initializeCategory();
		VariablesStorage.getInstance().initializeArea();
		
		// Default category and area delete
		
		editor.putInt("CategoryArraySize", 0);
      	editor.putInt("AreaArraySize", 0);
      	
      	editor.commit();
      	
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(!VariablesStorage.getInstance().getChosenAreaIDs().isEmpty())
			button1.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.checkedmapicon), null, null);
		else
			button1.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.mapicon), null, null);
		if(!VariablesStorage.getInstance().getChosenCategoryIDs().isEmpty())	
			button2.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.checkedchecklist), null, null);
		else
			button2.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.checklist), null, null);
	}
	
	private boolean checkInternetConnection() {

		ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);

		// ARE WE CONNECTED TO THE NET

		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
			return true;

		} else {
			return false;

		}

	} 
	
	
}
