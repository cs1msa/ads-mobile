package ath.nik.newAds;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class OptionLists extends ListActivity{
	public static final String PREFS_NAME = "MyPrefsFile";
	private ArrayList<String> items,father;
	private ArrayList<TreeNode> list,temp;
	OptionListsAdapt ola;
	ListView lv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_lists);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setTextFilterEnabled(true);
        father=new ArrayList<String>();
	    father.add("-1");
	    
	    list=new ArrayList<TreeNode>();
	    items=new ArrayList<String>();
	    
	    makeList("-1");
	    
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
				
				VariablesStorage.getInstance().unCheckAll();
				finish();
				Intent openStartingView = new Intent("ath.nik.newAds.OptionLists");
				startActivity(openStartingView);
			}
		});
        
        lv.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				father.add(list.get(arg2).getId());
				makeList(list.get(arg2).getId());
			}
	    });	
    }
	public void makeList(String num){
		//ws=new WebService(VariablesStorage.getInstance().getCategoryOrArea(),num);
	    //temp=ws.getList();
	    //ws.finalize();
		temp=VariablesStorage.getInstance().getList(num);
		if(!(temp.size()==0)){
	    	list=temp;
	       	items.clear();
	    	for(int i=0;i<list.size();i++)
		       	items.add(list.get(i).getTitle()/*+" ("+list.get(i).getCount()+")"*/);
	    	ola=new OptionListsAdapt(this,list,items);
			lv.setAdapter(ola);
	    }else{
	    	father.remove(father.size()-1);
	    }
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if (father.size()==1){
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

