package com.greenhouse.ui;

import com.greenhouse.R;
import com.greenhouse.database.ControllerService;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;


/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015��11��24�� ����5:11:32 
* @version      1.0  
*/
public class JackFragmentTimeSet extends Fragment {
	
	private static final String TAG = "JackFragmentBaseSet.java";

	private NumberPicker dayNumberPicker;
	private NumberPicker nightNumberPicker;
	private Button button;
	private TextView timesetHistory;
	
	
	private int day_history;       //白天起点历史值getfrom数据库
	private int night_history;     //夜间起点历史值getfrom数据库
	private Handler mainHandler;   //用于通知主线程handler刷新timeset界面
	
	private Context context;
	
	public JackFragmentTimeSet(Handler handler) {
		this.mainHandler = handler;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		context = getActivity();
		View vw = inflater.inflate(R.layout.jack_fragment_timeset2, container, false);	
		dayNumberPicker = (NumberPicker) vw.findViewById(R.id.dayNumberPicker);
		nightNumberPicker = (NumberPicker) vw.findViewById(R.id.nightNumberPicker);
		button = (Button) vw.findViewById(R.id.button);
		timesetHistory = (TextView)vw.findViewById(R.id.timesethistory);
		
		ControllerService controllerService = new ControllerService(context);
		day_history = controllerService.queryDay(Launcher.selectMac);
		night_history = controllerService.queryNight(Launcher.selectMac);
		timesetHistory.setText("\n  当前设置\n\n白天起点:" + day_history + ":00\n夜间起点:" + night_history + ":00");
		
		dayNumberPicker.setMinValue(1);
		dayNumberPicker.setMaxValue(24);
		dayNumberPicker.setValue(day_history);
		dayNumberPicker.setOnValueChangedListener(daynumberChanged);
		
		nightNumberPicker.setMinValue(1);
		nightNumberPicker.setMaxValue(24);
		nightNumberPicker.setValue(night_history);
		nightNumberPicker.setOnValueChangedListener(nightnumberChanged);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (day_history>0 && day_history<25 && night_history>0 && night_history<25) {
					ToastUtil.TextToastShort(context, "设置成功");
					
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.DANI);
					msg.arg1 = day_history;
					msg.arg2 = night_history;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg1 = SocketOutputTask.getHandler().obtainMessage(Const.DANI);
					msg1.arg1 = day_history;
					msg1.arg2 = night_history;
					SocketOutputTask.getHandler().sendMessage(msg1);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.DANI);
					msg2.arg1 = day_history;
					msg2.arg2 = night_history;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_TIMESET);
					
				} else {
					ToastUtil.TextToastShort(context, "请正确设置时间");
				}
			}
		});
		
		return vw;
	}
	
	
	private NumberPicker.OnValueChangeListener daynumberChanged = new OnValueChangeListener() {
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
//			dayIsSet = true;
			day_history = newVal;
		}
	};
	
	private NumberPicker.OnValueChangeListener nightnumberChanged = new OnValueChangeListener() {
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
//			nightIsSet = true;
			night_history = newVal;
		}
	};
	
	





	
	
}
