package ath.nik.newAds;

import java.util.ArrayList;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowFilters extends Activity {
	
	ListView lv1,lv2;
	private ArrayList<String> items1,items2;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showfilters);
        lv1 = (ListView) findViewById(R.id.listView1);
        lv1.setTextFilterEnabled(true);
        lv2 = (ListView) findViewById(R.id.listView2);
        lv2.setTextFilterEnabled(true);
        items1=new ArrayList<String>();
        items2=new ArrayList<String>();
        
        for(int i=0;i<VariablesStorage.getInstance().getChosenCategoryIDs().size();i++)	
        	//Toast.makeText(this, i, 2000).show();
        	items1.add(VariablesStorage.getInstance().getChosenCategoryTitles().get(i));
        lv1.setAdapter(new ShowFilterAdapt(this,items1,"Category"));
        for(int i=0;i<VariablesStorage.getInstance().getChosenAreaIDs().size();i++)	
        	items2.add(VariablesStorage.getInstance().getChosenAreaTitles().get(i));
        lv2.setAdapter(new ShowFilterAdapt(this,items2,"Area"));
        
        
        
        
        
	}
		
}


