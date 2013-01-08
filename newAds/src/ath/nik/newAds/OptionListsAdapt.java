package ath.nik.newAds;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class OptionListsAdapt extends ArrayAdapter<String>{
	private static ArrayList<TreeNode> searchArrayList;
	private Context con;
	
	OptionListsAdapt(Context con, ArrayList<TreeNode> results, ArrayList<String> items) {
		super(con, R.layout.row_with_checkbox, R.id.txtTitle, items);
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
		
		if (searchArrayList.get(position).isChecked()){
			holder.CheckEdit.setChecked(true);
		}else{
			holder.CheckEdit.setChecked(false);
		}
		holder.CheckEdit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!((CheckBox)v).isChecked()){
					searchArrayList.get(position).unCheck();
				}else{
					searchArrayList.get(position).check();
				}
			}
		});
		
		if(VariablesStorage.getInstance().getCategoryOrArea().equals("Category")){
			// Διαγραφή checkboxes αν υπάρχει υπομενού
			
			if (!VariablesStorage.getInstance().getList(searchArrayList.get(position).getId()).isEmpty()){
				holder.CheckEdit.setVisibility(View.INVISIBLE);
			}else{
				holder.CheckEdit.setVisibility(View.VISIBLE);
			}
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