package com.greenhouse.ui;

import com.greenhouse.R;
import com.greenhouse.database.JackService;
import com.greenhouse.networkservice.NetBroadcastReceiver;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.networkservice.ThreadPoolManager;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EditJack extends Activity implements View.OnClickListener {

	private ImageView ImageJack;
	private EditText JackName;
	private Button Timer_btn;
	private Button changename_btn;
	private TextView txt_rename;
	public static int flag_same_name;
	public  static AlertDialog alertDialog;
	
	private JackService jackService;
	
	public BroadcastReceiver myReceiver;
	
	private static ProgressBar title_waiting;
	
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Const.SOCKET_RECONNECTED:
				title_waiting.setVisibility(View.GONE);
				SocketInputTask.getHandler().sendEmptyMessage(Const.SOCKET_CONNECTED);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SocketOutputTask.getHandler().sendEmptyMessage(Const.TIME);
				handler.sendEmptyMessageDelayed(Const.BACK_TO_LAUNCHER, 2000);
				break;
			case Const.TIME_SERVICE_FINISHED:
				ThreadPoolManager.getInstance().startSocketServerAccept();
				break;
			case Const.BACK_TO_LAUNCHER:
				ToastUtil.TextToastLong(EditJack.this, "网络异常");
				Launcher.client.setState(Const.SOCKET_DISCONNECTED);
				Launcher.server.setServerState(Const.SOCKET_DISCONNECTED);
				Launcher.client.destroy();
				Launcher.server.destroy();
				Intent intent = new Intent(EditJack.this, Launcher.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;	
			case Const.SOCKET_CONNECTING:
				title_waiting.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jack_edit);
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting); 

		jackService = new JackService(this);
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction("com.greenhosue.backtolauncheraction");
        myReceiver = new NetBroadcastReceiver(handler);
        this.registerReceiver(myReceiver, filter);
		
		ImageView localImageView = (ImageView) findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back);
		localImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditJack.this.finish();
				onBackPressed();
			}
		});
		// title_bar theme
		TextView localTextView = (TextView) findViewById(R.id.title);
		localTextView.setText(R.string.jackSet);

		txt_rename = (TextView) findViewById(R.id.txt_rename);
		JackName = (EditText) findViewById(R.id.socket_name);
		ImageJack = (ImageView) findViewById(R.id.jack_icon);
		changename_btn = (Button) findViewById(R.id.changname_btn);
//		txt_rename.setText(JackFragmentShowinfo.selectJack.getName());//20160521 xiufuzhong

		Timer_btn = (Button) findViewById(R.id.timer_btn);
		Timer_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				startActivity(new Intent(EditJack.this,AlarmclockListView.class));
			}
		});

		changename_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (JackName.getText().toString() == null | "".equals(JackName.getText().toString())) {
					ToastUtil.TextToastLong(getApplicationContext(), "重命名前请输入名称");
				} else if(JackName.getText().toString().length() > 10) {
					ToastUtil.TextToastLong(getApplicationContext(),"请重新输入名称（要求不多余十个汉字）");
				} else {
					final String jackName = JackName.getText().toString();
					//20160521 xiufuzhong
//					final Integer jackId = JackFragmentRe.selectJack.getJackId();//20160521 xiufuzhong
					
					//modify 当前界面缓存／第二界面缓存／数据库源本地保存
					txt_rename.setText(jackName);					
//					JackFragmentShowinfo.jacks.get(jackId-1).setName(jackName);//20160521 xiufuzhong
//					jackService.modifyName(jackId, jackName);//20160521 xiufuzhong
					
					JackFragmentShowinfo.handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message msg = JackFragmentShowinfo.handler.obtainMessage();
//							msg.arg1 = jackId - 1;
							JackFragmentShowinfo.handler.sendMessage(msg);
						}
						
					}, 200);
					
					EditJack.this.finish();
				}
				
			}
		});
	}
	
	
	
    @Override
    protected  void onDestroy() {
    	// TODO Auto-generated method stub
    	unregisterReceiver(myReceiver);
    	super.onDestroy();
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			return;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			onBackPressed();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}




}
