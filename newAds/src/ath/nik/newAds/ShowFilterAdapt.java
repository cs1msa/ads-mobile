package ath.nik.newAds;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

class ShowFilterAdapt extends ArrayAdapter<String>{

	private String label;
	
	public ShowFilterAdapt(Context con, ArrayList<String> items, String label) {
		super(con, R.layout.list_with_button, R.id.list_with_button_txt, items);
		// TODO Auto-generated constructor stub
		this.label=label;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row=super.getView(position, convertView, parent);
		ViewHolder holder=(ViewHolder)row.getTag();
		if (holder==null) {
			holder=new ViewHolder(row);
			row.setTag(holder);
		}
		
		//holder.DeleteButton.setId();
		holder.DeleteButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//if(true){
					//VariablesStorage.getInstance().deleteAreaTitleAndID(searchArrayList.get(position).getId(), searchArrayList.get(position).getTitle());
				//}else{
					//VariablesStorage.getInstance().addChosenAreaTitle(searchArrayList.get(position).getTitle());
					//VariablesStorage.getInstance().addChosenAreaID(searchArrayList.get(position).getId());
				//}
			}
		});
		
		return row;
	}
	static class ViewHolder {
		TextView TitleText;
		Button DeleteButton;
		ViewHolder(View row) {
			this.TitleText=(TextView)row.findViewById(R.id.list_with_button_txt);
			this.DeleteButton=(Button)row.findViewById(R.id.list_with_button_btn);
			}
	}
	
}