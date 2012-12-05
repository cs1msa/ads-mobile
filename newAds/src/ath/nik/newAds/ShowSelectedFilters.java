package ath.nik.newAds;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ShowSelectedFilters extends Activity {
	
	ListView lv1,lv2;
	private ArrayList<String> items1,items2;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_selected_filters);
        lv1 = (ListView) findViewById(R.id.listView1);
        lv1.setTextFilterEnabled(true);
        lv2 = (ListView) findViewById(R.id.listView2);
        lv2.setTextFilterEnabled(true);
        items1=new ArrayList<String>();
        items2=new ArrayList<String>();
        
        for(int i=0;i<VariablesStorage.getInstance().getChosenCategoryTitles().size();i++)	
        	items1.add(VariablesStorage.getInstance().getChosenCategoryTitles().get(i));
        lv1.setAdapter(new ShowSelectedFiltersAdapt(this,items1,"Category"));
        for(int i=0;i<VariablesStorage.getInstance().getChosenAreaTitles().size();i++)	
        	items2.add(VariablesStorage.getInstance().getChosenAreaTitles().get(i));
        lv2.setAdapter(new ShowSelectedFiltersAdapt(this,items2,"Area"));
        
	}
		
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}


