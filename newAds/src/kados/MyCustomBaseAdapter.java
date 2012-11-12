package kados;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/*public class MyCustomBaseAdapter extends BaseAdapter{
	public static final String PREFS_NAME = "MyPrefsFile";
	private static ArrayList<WSResults> searchArrayList;
	private LayoutInflater mInflater;
	private Context con;
	public MyCustomBaseAdapter(Context context, ArrayList<WSResults> results) {
		con=context;
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return searchArrayList.size();
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_item_row, null);

			holder = new ViewHolder();
			holder.TitleText = (TextView) convertView.findViewById(R.id.txtTitle);
			holder.CheckEdit = (CheckBox) convertView.findViewById(R.id.checkBoxEdit);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
  
		holder.TitleText.setText(searchArrayList.get(position).getTitle()+"  ("+searchArrayList.get(position).getCount()+")");
		holder.TitleText.setId(Integer.parseInt(searchArrayList.get(position).getId()));
		holder.TitleText.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				switch(arg1.getAction()){
				case MotionEvent.ACTION_DOWN:
					holder.TitleText.setBackgroundColor(R.color.white);
					holder.TitleText.setClickable(true);
					break;
				case MotionEvent.ACTION_UP:
					holder.TitleText.setBackgroundColor(R.color.white);
					Intent newActivity = new Intent("ath.nik.newAds.AREAACTIVITYWS");
					newActivity.putExtra("id",String.valueOf(holder.TitleText.getId()));
					con.startActivity(newActivity);
					((Activity)con).finish();
					break;
				}
				
				return true;
			}
			
		});
		//holder.TitleText.setOnClickListener(new OnClickListener(){
		//	@Override
		//	public void onClick(View v) {
		//		// TODO Auto-generated method stub
				
		//	}
		//});
		holder.CheckEdit.setId(Integer.parseInt(searchArrayList.get(position).getId()));
		holder.CheckEdit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences settings = con.getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("chosenAreaID", searchArrayList.get(position).getId());
				editor.putString("chosenAreaTitle", searchArrayList.get(position).getTitle());
				editor.commit();
				((Activity)con).finish();
			}
   
		});
		return convertView;
	}
	static class ViewHolder {
		TextView TitleText;
		CheckBox CheckEdit;
	}
}*/