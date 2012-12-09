package ath.nik.newAds;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.os.StrictMode;

public class WebService {
	
	private final String NAMESPACE = "http://tempuri.org/";
    private final String URL = "http://ads.salampasis.gr/Service1.asmx";
    private String SOAP_ACTION= "http://tempuri.org/";
    private String METHOD_NAME;
    
    private SoapObject result=null;
    
    public WebService(String MethodName, String IDNumber) {
    	if (android.os.Build.VERSION.SDK_INT>8){
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy); 
    	}
        SOAP_ACTION+="Return"+MethodName;
    	METHOD_NAME="Return"+MethodName;
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);     
            request.addProperty(MethodName+"IDParam", IDNumber);
            request.addProperty("countShow", "false");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);                       
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            result=(SoapObject)envelope.getResponse();
        }
        catch (Exception e) {
        }
    }
    
    public WebService(String CategoryID, String AreaID, String Keywords, int start) {
    	if (android.os.Build.VERSION.SDK_INT>8){
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy); 
    	}
    	try{
    		SoapObject request;
    		if(Keywords.equals("")){
        		SOAP_ACTION+="ReturnAds";
            	METHOD_NAME="ReturnAds";
            	request = new SoapObject(NAMESPACE, METHOD_NAME); 
            	request.addProperty("IDCategory", CategoryID);
        		request.addProperty("IDArea", AreaID);
        		request.addProperty("startNumber", String.valueOf(start));
        		request.addProperty("howMany", String.valueOf(VariablesStorage.getInstance().getHowManyNumber()));
        	}else{
        		SOAP_ACTION+="SearchQuery";
            	METHOD_NAME="SearchQuery";
            	request = new SoapObject(NAMESPACE, METHOD_NAME); 
            	request.addProperty("query",Keywords);
            	request.addProperty("Start", String.valueOf(start));
        		request.addProperty("HowMany", String.valueOf(VariablesStorage.getInstance().getHowManyNumber()));
        	}
    		
    		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);                       
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            result=(SoapObject)envelope.getResponse();
    	}
    	catch (Exception e) {
    		result=null;
        }
    }
    
    public ArrayList<WSResults> getList(){
    	ArrayList<WSResults> l=new ArrayList<WSResults>();
    	for(int i=0;i<result.getPropertyCount()-1;i++){
    		SoapObject so = (SoapObject) result.getProperty(i);
    		
    		if(so.getPropertyCount()==2){
    			l.add(new WSResults(so.getProperty(0).toString(),so.getProperty(1).toString(),"0"/*,so.getProperty(2).toString()*/));	
    		}
    		else{
    			l.add(new WSResults(so.getProperty(0).toString(),so.getProperty(1).toString(),so.getProperty(2).toString(),"0"/*,so.getProperty(3).toString()*/));
        	}
    	}
    	return l;
    	
    }
    
    public ArrayList<WSResults> getAds(){
    	ArrayList<WSResults> l=new ArrayList<WSResults>();
    	if(result!=null){
    		for(int i=0;i<result.getPropertyCount()-1;i++){
        		SoapObject so = (SoapObject) result.getProperty(i);
        		l.add(new WSResults(so.getProperty(0).toString(),so.getProperty(1).toString()));
        	}
    	}
    	return l;
    }
    
    @Override
    public void finalize(){
    	try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
