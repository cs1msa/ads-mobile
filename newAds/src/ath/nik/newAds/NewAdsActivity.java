package ath.nik.newAds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
public class NewAdsActivity extends Activity {
	@Override
	protected void onCreate(Bundle starting) {
		// TODO Auto-generated method stub
		super.onCreate(starting);
		setContentView(R.layout.main);		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingView = new Intent("ath.nik.newAds.FILTERACTION");
					startActivity(openStartingView);
				}
			}
		};
		timer.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}