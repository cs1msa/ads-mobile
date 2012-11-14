package ath.nik.newAds;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class AreaActivityWS extends ListActivity{
	public static final String PREFS_NAME = "MyPrefsFile";
	private WebService ws;
	private ArrayList<String> items,father;
	private ArrayList<WSResults> list,temp=new ArrayList<WSResults>();

	ListView lv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setTextFilterEnabled(true);
        father=new ArrayList<String>();
	    father.add("0");
	    makeList(father.get(father.size()-1));
	    
	    final Button button1 = (Button) findViewById(R.id.listsreturn);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	finish();
            }
        });  
        
        final Button button2 = (Button) findViewById(R.id.listsdelete);
        button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VariablesStorage.getInstance().initializeArea();
				finish();
				Intent openStartingView = new Intent("ath.nik.newAds.AREAACTIVITYWS");
				startActivity(openStartingView);
			}
		});
        
        
    }
	public void makeList(String num){
		ws=new WebService("ReturnArea",num);
	    temp=ws.getAreaList();
	    ws.finalize();
	    if(temp.size()==0){
	       	this.finish();
	    }else{
	       	list=temp;
	       	items=new ArrayList<String>();
			lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));
	       	for(int i=0;i<list.size();i++)
		       	items.add(list.get(i).getTitle()+" ("+list.get(i).getCount()+")");
			lv.setAdapter(new Adapt(this,list,items));
			lv.setOnItemClickListener(new OnItemClickListener(){
			
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					for(int i=0;i<lv.getCount();i++){
						if(lv.getItemAtPosition(arg2).toString().contains(list.get(i).getTitle())){				
					    	father.add(list.get(i).getId());
							makeList(list.get(arg2).getId());
						}	
					}
				}
		    });					
	    }
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if (father.get(father.size()-1).equals("0")){
	    		finish();
	    	}else{
	    		father.remove(father.size()-1);
		    	makeList(father.get(father.size()-1));
		    	return true;
	    	}
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

