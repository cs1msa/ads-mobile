package ath.nik.newAds;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class AdsViewEndlessScroll extends ListActivity{
	private ArrayList<String> items2=new ArrayList<String>();
	private WebService ws;
	private SoapObject so;
	private ListView lv;
	private int start;
	private String Cat,Area,Keywords;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adsscreen);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setTextFilterEnabled(true);
        Cat=VariablesStorage.getInstance().getChosenCategoryIDs().toString();
		Area=VariablesStorage.getInstance().getChosenAreaIDs().toString();
		Keywords=VariablesStorage.getInstance().getKeywords().toString();
        Cat=Cat.substring(1, Cat.length()-1);
        Area=Area.substring(1, Area.length()-1);
        Keywords=Keywords.substring(1, Keywords.length()-1);
		start=0;
        makelist();
        
        
		// Διαμόρφωση background color κάθε γραμμής
        
        final int[] colors = new int[] { 0x30000000, 0x30666666 };
        final ArrayAdapter<String> adapt=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items2){
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent) {
        		View view = super.getView(position, convertView, parent);
        		int colorPos = position % colors.length;
        		view.setBackgroundColor(colors[colorPos]);
        		return view;
        	}
        };
        lv.setAdapter(adapt);
        
        // Endless Scroll
        
        lv.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(totalItemCount-visibleItemCount==firstVisibleItem){
					//Toast.makeText(arg0.getContext(), String.valueOf(so.getPropertyCount()), 2000).show();
					//if(so.getPropertyCount()<10)
						makelist();
					adapt.notifyDataSetChanged();
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        // Διαχείριση expand
        
        lv.setOnItemClickListener(new OnItemClickListener(){
    		@Override
    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    			// TODO Auto-generated method stub
    			TextView txt=(TextView) arg1;
    			if(txt.getTag()==null || txt.getTag().equals("false")){
    				txt.setText(((SoapObject)so.getProperty(arg2)).getProperty(0).toString());
    				txt.setTag("true");
    			}else{
    				txt.setText(items2.get(arg2));
    				txt.setTag("false");
    			}
    		}
    	});
                
    }
	
	public void makelist(){
		ws=new WebService(Cat,Area,Keywords,start);
		so = ws.getso();
		Toast.makeText(this, String.valueOf(so.getPropertyCount()), 2000).show();
		try{
        	String s1,s2;
            for(int i=0;i<10;i++){
            	s1=((SoapObject)so.getProperty(i)).getProperty(0).toString();
            	s2="";
            	for(int j=0;j<3;j++){
            		if(s1.indexOf(",")!=-1){
            			s2+=s1.substring(0, s1.indexOf(","));
            			s1=s1.substring(s1.indexOf(",")+1, s1.length());
            		}
            	}
            	if(s2.length()<2){
            		s2=((SoapObject)so.getProperty(i)).getProperty(0).toString().substring(0, 20);
            	}
            	items2.add(s2+"...");
            }
            start=+10;
        }catch(Exception ex){
        	if(so==null){
        		Toast.makeText(this, "Null so", 3000).show();
        	}
        }  
	}
	
	@Override
	protected void onResume(){
		super.onResume();		
	}
}
