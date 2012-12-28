package ath.nik.newAds;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Html;
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


public class AdsList extends ListActivity{
	private ArrayList<StringManager> items2=new ArrayList<StringManager>();
	private WebService ws;
	private ArrayList<WSResults> result;
	private ListView lv;
	private int start;
	private String Cat,Area,Keywords;
	private boolean ContinueSearch=true;
	private ArrayList<String> temp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adslist);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setTextFilterEnabled(true);
        
        temp=new ArrayList<String>();
        for(int i=0;i<VariablesStorage.getInstance().getChosenCategories().size();i++)
        	temp.add(VariablesStorage.getInstance().getChosenCategories().get(i).getId());
        Cat=temp.toString();
        
        temp.clear();
        for(int i=0;i<VariablesStorage.getInstance().getChosenAreas().size();i++)
        	temp.add(VariablesStorage.getInstance().getChosenAreas().get(i).getId());
		Area=temp.toString();
		
		Keywords=VariablesStorage.getInstance().getKeywords().toString();
        Cat=Cat.substring(1, Cat.length()-1);
        if (Cat.equals(""))
        	Cat="-1";
        Area=Area.substring(1, Area.length()-1);
        if (Area.equals(""))
        	Area="-1";
                
        result=new ArrayList<WSResults>();
        start=0;
        makelist();
        
        
		// Διαμόρφωση background color κάθε γραμμής
        
        final int[] colors = new int[] { 0x30000000, 0x30666666 };
        final ArrayAdapter<StringManager> adapt=new ArrayAdapter<StringManager>(this,android.R.layout.simple_list_item_1,items2){
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent) {
        		View view = super.getView(position, convertView, parent);
        		int colorPos = position % colors.length;
        		view.setBackgroundColor(colors[colorPos]);
        		
        		TextView tv =(TextView) view.findViewById(android.R.id.text1);
        		StringManager sm=items2.get(position);
        		tv.setText(Html.fromHtml(sm.getTitleAndInformationWithDots()));
        		
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
					if(ContinueSearch){
						makelist();
						adapt.notifyDataSetChanged();
					}
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
    				txt.setText(Html.fromHtml(items2.get(arg2).getFullTitleAndInformation()));
    				txt.setTag("true");
    			}else{
    				txt.setText(Html.fromHtml(items2.get(arg2).getTitleAndInformationWithDots()));
    				txt.setTag("false");
    			}
    		}
    	});
                
    }
	
	public void makelist(){
		
		ws=new WebService(Cat,Area,Keywords,start);
		result.addAll(ws.getAds());
		
		if(ws.getAds().size()<VariablesStorage.getInstance().getHowManyNumber())
			ContinueSearch=false;
		
        StringManager s1;
        for(int i=start;i<result.size();i++){
           	s1=new StringManager(result.get(i).getTitle());
           	items2.add(s1);
        }
        start+=20;
	}
	
	@Override
	protected void onResume(){
		super.onResume();		
	}
}
