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
        
        ArrayList<TreeNode> temp=VariablesStorage.getInstance().getCategories().getChosen();
        for(int i=0;i<temp.size();i++)	
        	items1.add(temp.get(i).getTitle());
        lv1.setAdapter(new ShowSelectedFiltersAdapt(this,items1,"Category"));
        temp=VariablesStorage.getInstance().getAreas().getChosen();
        for(int i=0;i<temp.size();i++)	
        	items2.add(temp.get(i).getTitle());
        lv2.setAdapter(new ShowSelectedFiltersAdapt(this,items2,"Area"));
        
	}
		
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}


