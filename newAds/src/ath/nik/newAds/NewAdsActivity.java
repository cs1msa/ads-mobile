package ath.nik.newAds;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
public class NewAdsActivity extends Activity {
	private ProgressDialog progressDialog;
	private Context con;
	boolean flag;
	@Override
	protected void onCreate(Bundle starting) {
		// TODO Auto-generated method stub
		super.onCreate(starting);
		setContentView(R.layout.main);		
		con=this;
		
		String[] s=this.fileList();
		flag=false;
		for(int i=0;i<s.length;i++){
			if(s[i].equals("Category"))
				flag=true;
		}
		if (flag){
			progressDialog = ProgressDialog.show(this, "", "Φόρτωση Δεδομένων");
		}else{
			progressDialog = ProgressDialog.show(this, "", "Λήψη Δεδομένων");
		}
		

		new Thread() {

			public void run() {

				try{
					VariablesStorage.getInstance().initializeVariables(); // Αρχικοποίηση global μεταβλητών.
					// LOAD
			        try {
						VariablesStorage.getInstance().getDataFromXML(con,flag);
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        //VariablesStorage.getInstance().loadCriteria(con);
			        //Toast.makeText(this, VariablesStorage.getInstance().getChosenCategories().get(0).getId(), 2000).show();
			        //Toast.makeText(this, VariablesStorage.getInstance().getChosenCategories().get(1).getId(), 2000).show();
			        //Toast.makeText(this, VariablesStorage.getInstance().getChosenCategories().get(2).getId(), 2000).show();


				} catch (Exception e) {

					Log.e("tag", e.getMessage());

				}

				// dismiss the progress dialog

				progressDialog.dismiss();
				Intent openStartingView = new Intent("ath.nik.newAds.DataDefinition");
				startActivity(openStartingView);
			}

		}.start();

		
		/*Thread timer = new Thread(){
			public void run(){
				try{
					sleep(0);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingView = new Intent("ath.nik.newAds.DataDefinition");
					startActivity(openStartingView);
				}
			}
		};
		timer.start();*/
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}