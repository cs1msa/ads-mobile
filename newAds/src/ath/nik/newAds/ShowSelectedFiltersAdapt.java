package ath.nik.newAds;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

class ShowSelectedFiltersAdapt extends ArrayAdapter<String>{

	private String label;
	Context con;
	
	public ShowSelectedFiltersAdapt(Context con, ArrayList<String> items, String label) {
		super(con, R.layout.row_with_button, R.id.list_with_button_txt, items);
		// TODO Auto-generated constructor stub
		this.con=con;
		this.label=label;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row=super.getView(position, convertView, parent);
		ViewHolder holder=(ViewHolder)row.getTag();
		if (holder==null) {
			holder=new ViewHolder(row);
			row.setTag(holder);
		}
		
		if(label.equals("Category"))
			holder.DeleteButton.setId(Integer.parseInt(VariablesStorage.getInstance().getChosenCategories().get(position).getId()));
		else
			holder.DeleteButton.setId(Integer.parseInt(VariablesStorage.getInstance().getChosenAreas().get(position).getId()));
		
		holder.DeleteButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(label.equals("Category")){
					//Toast.makeText(con, VariablesStorage.getInstance().getChosenCategoryIDs().get(position)+" - "+VariablesStorage.getInstance().getChosenCategoryTitles().get(position), 2000).show();
					VariablesStorage.getInstance().deleteCategory(VariablesStorage.getInstance().getChosenCategories().get(position));
				}else{
					//Toast.makeText(con, VariablesStorage.getInstance().getChosenAreaIDs().get(position)+" - "+VariablesStorage.getInstance().getChosenAreaTitles().get(position), 2000).show();
					VariablesStorage.getInstance().deleteArea(VariablesStorage.getInstance().getChosenAreas().get(position));
				}
				Intent openStartingView = new Intent("ath.nik.newAds.ShowSelectedFilters");
				con.startActivity(openStartingView);
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