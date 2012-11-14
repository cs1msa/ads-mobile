package ath.nik.newAds;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class Adapt extends ArrayAdapter<String>{
	private static ArrayList<WSResults> searchArrayList;
	private Context con;
	
	Adapt(Context con, ArrayList<WSResults> results, ArrayList<String> items) {
		super(con, R.layout.listview_item_row, R.id.txtTitle, items);
		this.con=con;
		searchArrayList = results;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row=super.getView(position, convertView, parent);
		ViewHolder holder=(ViewHolder)row.getTag();
		if (holder==null) {
			holder=new ViewHolder(row);
			row.setTag(holder);
		}
		
		
		holder.CheckEdit.setId(Integer.parseInt(searchArrayList.get(position).getId()));
		
		if(con.getClass().getSimpleName().equals("AreaActivityWS")){
			if (VariablesStorage.getInstance().IDAreaExist(searchArrayList.get(position).getId())){
				holder.CheckEdit.setChecked(true);
			}else{
				holder.CheckEdit.setChecked(false);
			}
			holder.CheckEdit.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(!((CheckBox)v).isChecked()){
						VariablesStorage.getInstance().deleteAreaTitleAndID(searchArrayList.get(position).getId(), searchArrayList.get(position).getTitle());
					}else{
						VariablesStorage.getInstance().addChosenAreaTitle(searchArrayList.get(position).getTitle());
						VariablesStorage.getInstance().addChosenAreaID(searchArrayList.get(position).getId());
					}
				}
			});
		}else{
			if (VariablesStorage.getInstance().IDCategoryExist(searchArrayList.get(position).getId())){
				holder.CheckEdit.setChecked(true);
			}else{
				holder.CheckEdit.setChecked(false);
			}
			
			holder.CheckEdit.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(!((CheckBox)v).isChecked()){
						VariablesStorage.getInstance().deleteCategoryTitleAndID(searchArrayList.get(position).getId(), searchArrayList.get(position).getTitle());
					}else{
						
						VariablesStorage.getInstance().addChosenCategoryTitle(searchArrayList.get(position).getTitle());
						VariablesStorage.getInstance().addChosenCategoryID(searchArrayList.get(position).getId());
					}
				}
	   
			});
		}

		return row;
	}
	static class ViewHolder {
		TextView TitleText;
		CheckBox CheckEdit;
		ViewHolder(View row) {
			this.TitleText=(TextView)row.findViewById(R.id.txtTitle);
			this.CheckEdit=(CheckBox)row.findViewById(R.id.checkBoxEdit);
			}
	}
}