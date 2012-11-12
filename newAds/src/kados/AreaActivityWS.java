package kados;

import java.util.ArrayList;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ath.nik.newAds.R;
import ath.nik.newAds.WSResults;
import ath.nik.newAds.WebService;
import ath.nik.newAds.R.layout;


public class AreaActivityWS extends ListActivity{
	public static final String PREFS_NAME = "MyPrefsFile";
	//private ArrayList<String> items2=new ArrayList<String>();
	private ArrayList<WSResults> list=new ArrayList<WSResults>();
	private WebService ws;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists);
        final ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setTextFilterEnabled(true);
        Bundle b = getIntent().getExtras();       
               
        if(b!=null){
        	ws=new WebService("ReturnArea",b.getString("id"));
        }
        else{
        	ws=new WebService("ReturnArea","0");
        }
        list=ws.getAreaList();
        ws.finalize();
        if (list.size()==0){
        	finish();
        }
        else{
        	//MyCustomBaseAdapter adapter = new MyCustomBaseAdapter(this,list);
            //lv.setAdapter(adapter);
        }  
           
    }
	
		
	@Override
	protected void onPause(){
		super.onPause();
		finish();
	}
}
