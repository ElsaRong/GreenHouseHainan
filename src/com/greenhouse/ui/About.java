package com.greenhouse.ui;

import com.greenhouse.R;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

public class About extends Activity implements View.OnClickListener{
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		Intent intent = getIntent(); 
		
		ImageView localImageView = (ImageView)findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back );
		localImageView.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.title_btn:
			onBackPressed();
			return;
		default:
			return;
		}
	}

}
