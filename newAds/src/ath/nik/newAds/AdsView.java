package ath.nik.newAds;

//import android.app.Activity;
import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class AdsView extends ListActivity{
	public static final String PREFS_NAME = "MyPrefsFile";
	private ArrayList<String> items2=new ArrayList<String>();
	//private ArrayList<String> items3=new ArrayList<String>();
	//private ArrayList<WSResults> list=new ArrayList<WSResults>();
	private WebService ws;
	private SoapObject so;
	private Bundle b;
	private ListView lv;
	private SharedPreferences prefs;
	private String start,howmany;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adsscreen);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setTextFilterEnabled(true);
        b = getIntent().getExtras();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
       	start="0";
       	howmany=prefs.getString("listPref", "10");
        makelist(start,howmany);
        
        final TextView txt = (TextView) findViewById(R.id.showNumbersOfAds);
        txt.setText(start+" - "+howmany);
        
        // Διαχείριση button επόμενο-προηγουμενο
        
        final Button button1 = (Button) findViewById(R.id.next);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	items2=new ArrayList<String>();
            	start=String.valueOf(Integer.valueOf(start)+Integer.valueOf(howmany));
            	makelist(start,howmany);
            	txt.setText(start+" - "+String.valueOf(Integer.valueOf(start)+Integer.valueOf(howmany)));
            }
        }); 
        final Button button2 = (Button) findViewById(R.id.previous);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	items2=new ArrayList<String>();
            	start=String.valueOf(Integer.valueOf(start)-Integer.valueOf(howmany));
            	if(Integer.valueOf(start)<0){
            		start="0";	
            	}
            	makelist(start,howmany);
            	txt.setText(start+" - "+String.valueOf(Integer.valueOf(start)+Integer.valueOf(howmany)));
            }
        });  
        
        // Τέλος διαχείρισης button επόμενο-προηγούμενο
        
    }
	public void makelist(String from, String to){
		ws=new WebService("ReturnAds",b.getString("CategoryID"),b.getString("AreaID"),from,to);
        //Toast.makeText(this, b.getString("CategoryID")+" - "+b.getString("AreaID")+" - "+from+" - "+to, 3000).show();
		so = ws.getso();
        try{
        	String s1,s2;
            for(int i=0;i<so.getPropertyCount()-1;i++){
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
            final int[] colors = new int[] { 0x30000000, 0x30666666 };
            lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items2){
            	@Override
            	public View getView(int position, View convertView, ViewGroup parent) {
            		View view = super.getView(position, convertView, parent);
            		int colorPos = position % colors.length;
            		view.setBackgroundColor(colors[colorPos]);
            		return view;
            	}

            });
            lv.setOnItemClickListener(new OnItemClickListener(){
    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				// TODO Auto-generated method stub
    				TextView txt=(TextView) arg1;
    				if(txt.getTag()==null){
    					txt.setText(((SoapObject)so.getProperty(arg2)).getProperty(0).toString());
    					txt.setTag("true");
    				}else if(txt.getTag().equals("false")){
    					txt.setText(((SoapObject)so.getProperty(arg2)).getProperty(0).toString());
    					txt.setTag("true");
    				}else{
    					txt.setText(items2.get(arg2));
    					txt.setTag("false");
    				}
    				
    			}
            });
        }catch(Exception ex){
        	if(so==null){
        		Toast.makeText(this, "Null so", 3000).show();
        	}else if (so.getPropertyCount()==0){
        		Toast.makeText(this, String.valueOf(so.getPropertyCount()), 3000).show();
        	}
            
        }
		
        
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 0, "Εμφάνιση Αγγελιών");
	    return true;
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     if(item.getItemId()==1){   	 
    	 startActivity(new Intent(this, SetPreferences.class));
     }else{  	 
      	 
     }
     return super.onOptionsItemSelected(item);

    }
	@Override
	protected void onResume(){
		super.onResume();		
	}
}
