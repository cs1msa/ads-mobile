package ath.nik.newAds;

import android.app.Application;


public class MyApplication extends Application{
	@Override
	  public void onCreate()
	  {
	    super.onCreate();
	    // Initialize the singletons so their instances
	    // are bound to the application process.
	    initSingletons();
	  }
	  protected void initSingletons()
	  {
	    // Initialize the instance of MySingleton
		  VariablesStorage.initInstance();
	  }
	  public void customAppMethod()
	  {
	    // Custom application method
	  }
}
