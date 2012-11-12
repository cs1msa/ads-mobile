package ath.nik.newAds;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

public class SearchAction extends Activity{
	MultiAutoCompleteTextView myMultiAutoCompleteTextView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.searchscreen);
	    
	   
       final Button button1 = (Button) findViewById(R.id.buttonInSearchScreen);
       button1.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
				
           }
       });
	}
}
