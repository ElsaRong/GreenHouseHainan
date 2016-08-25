package com.greenhouse.util;

import com.greenhouse.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016年1月14日下午2:14:19 
* @version		1.0  
* @description			 
*/
public class SlideMenuAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private int[] mMenuIcons;
	private String[] mMenuTexts;

	public SlideMenuAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.mMenuTexts = context.getResources().getStringArray(R.array.slide_menu);
		this.mMenuIcons = new int[] { R.drawable.exit, R.drawable.help,	R.drawable.about, R.drawable.device };
	}

	public int getCount() {
		return this.mMenuTexts.length;
	}

	public String getItem(int paramInt) {
		return this.mMenuTexts[paramInt];
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null)
			convertView = this.mInflater.inflate(R.layout.menu_option, parent, false);
		TextView localTextView = (TextView) convertView;
		localTextView.setCompoundDrawablesWithIntrinsicBounds(this.mMenuIcons[position], 0, 0, 0);
		localTextView.setText(getItem(position));		
		return convertView;
	}


}
