package com.greenhouse.mvadpater;

import java.util.ArrayList;

import com.greenhouse.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class GridItemDataAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> scrollControllers;
	private TextView txtAge;
	private TextView txtController;

	public GridItemDataAdapter(Context mContext, ArrayList<String > scrollControllers) {
		this.context = mContext;
		this.scrollControllers = scrollControllers;
	}

	@Override
	public int getCount() {
		return scrollControllers.size();
	}

	@Override
	public Object getItem(int position) {
		return scrollControllers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		scrollControllers.add(startPosition, (String) endObject);
		scrollControllers.remove(startPosition + 1);
		scrollControllers.add(endPosition, (String) startObject);
		scrollControllers.remove(endPosition + 1);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = LayoutInflater.from(context).inflate(R.layout.controller_icon_item, parent, false);
		txtAge = (TextView) convertView.findViewById(R.id.txt_userAge);
		txtController = (TextView) convertView.findViewById(R.id.txt_controller);		
		if(scrollControllers.get(position).equals("add")){
			txtAge.setBackgroundResource(R.drawable.add_controller);
		}
		else if(scrollControllers.get(position).equals("null")){
			txtAge.setText("");
			txtAge.setBackgroundDrawable(null);
		} else {
			
			String mac = scrollControllers.get(position).toString();
			if (mac.equals("ACCF2357F44E")) {
				txtController.setText("二号棚");
			} else if (mac.equals("ACCF236FA948")) {
				txtController.setText("一号棚");
			} else {
				txtController.setText("控制柜－" + scrollControllers.get(position).toString().substring(8, 12));
			}
			
		}
		return convertView;
	}

}
