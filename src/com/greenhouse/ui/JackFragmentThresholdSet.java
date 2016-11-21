package com.greenhouse.ui;

import java.util.List;
import com.greenhouse.R;
import com.greenhouse.database.ControllerService;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.ToastUtil;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/24 PM 5:11:32 
* @version      1.0  
*/
public class JackFragmentThresholdSet extends Fragment {
	
	private EditText airtemp, airhum, co2, soiltemp, soilhum, ph, illum;
	private Button button1, button2, button3, button4, button5, button6, button7;
	private TextView history1, history2, history3, history4, history5, history6, history7;
	
	private Integer thre1=0, thre2=0, thre3=0, thre4=0, thre5=0, thre6=0, thre7=0; //用户设置的阈值初始化
	private String form_thre1 = "00", form_thre2 = "00",form_thre3 = "00",form_thre4 = "00",form_thre5 = "00",form_thre6 = "00000",form_thre7 = "00000";
	private List<Integer> thredsholds; 													  //从数据库读取的阈值历史记录
	
	private Context context;
	private ControllerService controllerService = new ControllerService(GreenHouseApplication.getContext());
	
	private Handler handler = new Handler();
	
	private Runnable refreshThreshold = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			thredsholds = controllerService.getAllThredshold();
			history1.setText("当前阈值: " + thredsholds.get(0) + "℃");
			history2.setText("当前阈值: " + thredsholds.get(1) + "%");
			history3.setText("当前阈值: " + thredsholds.get(2) + ".0");
			history4.setText("当前阈值: " + thredsholds.get(3) + "℃");
			history5.setText("当前阈值: " + thredsholds.get(4) + "%");
			history6.setText("当前阈值: " + thredsholds.get(5) + "ppm");
			history7.setText("当前阈值: " + thredsholds.get(6) + "lux");
		}
	};
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View vw = inflater.inflate(R.layout.jack_fragment_thresholdset, container, false);	
		context = getActivity();
		
		thredsholds = controllerService.getAllThredshold();
		
		airtemp = (EditText) vw.findViewById(R.id.airtemp);
		airhum = (EditText) vw.findViewById(R.id.airhum);
		co2 = (EditText) vw.findViewById(R.id.co2);
		soiltemp = (EditText) vw.findViewById(R.id.soiltemp);
		soilhum = (EditText) vw.findViewById(R.id.soilhum);
		ph = (EditText) vw.findViewById(R.id.ph);
		illum = (EditText) vw.findViewById(R.id.illum);
		
		thre1 = thredsholds.get(0);
		thre2 = thredsholds.get(1);
		thre3 = thredsholds.get(2);
		thre4 = thredsholds.get(3);
		thre5 = thredsholds.get(4);
		thre6 = thredsholds.get(5);
		thre7 = thredsholds.get(6);
		
		form_thre1 = "0"+thre1;
		
		if (thre2 < 10) {
			form_thre2 = "0"+thre2;
		} else {
			form_thre2 = thre2+"";
		}
		
		form_thre3 = "0"+thre3;
		
		form_thre4 = "0"+thre4;
		
		if (thre5 < 10) {
			form_thre5 = "0"+thre5;
		} else {
			form_thre5 = thre5+"";
		}
		
		if (thre6 < 10) {
			form_thre6 = "0000"+thre6;
		} else if (thre6 >=10 && thre6 < 100){
			form_thre6 = "000"+thre6;
		} else if (thre6 >=100 && thre6 < 1000){
			form_thre6 = "00"+thre6;
		} else {
			form_thre6 = "0"+thre6;
		}
		
		if (thre7 < 10) {
			form_thre7 = "0000"+thre7;
		} else if (thre7 >=10 && thre7 < 100){
			form_thre7 = "000"+thre7;
		} else if (thre7 >=100 && thre7 < 1000){
			form_thre7 = "00"+thre7;
		} else {
			form_thre7 = "0"+thre7;
		}
		
		airtemp.setText(thre4+"");
		airhum.setText(thre5+"");
		co2.setText(thre6+"");
		soiltemp.setText(thre1+"");
		soilhum.setText(thre2+"");
		ph.setText(thre3+"");
		illum.setText(thre7+"");
		
		button1 = (Button)vw.findViewById(R.id.Button1);
		button2 = (Button)vw.findViewById(R.id.Button2);
		button3 = (Button)vw.findViewById(R.id.Button3);
		button4 = (Button)vw.findViewById(R.id.Button4);
		button5 = (Button)vw.findViewById(R.id.Button5);
		button6 = (Button)vw.findViewById(R.id.Button6);
		button7 = (Button)vw.findViewById(R.id.Button7);
		
		history1 = (TextView)vw.findViewById(R.id.TextView11);
		history2 = (TextView)vw.findViewById(R.id.TextView22);
		history3 = (TextView)vw.findViewById(R.id.TextView33);
		history4 = (TextView)vw.findViewById(R.id.TextView44);
		history5 = (TextView)vw.findViewById(R.id.TextView55);
		history6 = (TextView)vw.findViewById(R.id.TextView66);
		history7 = (TextView)vw.findViewById(R.id.TextView77);
		
		history1.setText("当前阈值: " + thredsholds.get(0) + "℃");
		history2.setText("当前阈值: " + thredsholds.get(1) + "%");
		history3.setText("当前阈值: " + thredsholds.get(2)/10 + "." + thredsholds.get(5)%10  + "");
		history4.setText("当前阈值: " + thredsholds.get(3) + "℃");
		history5.setText("当前阈值: " + thredsholds.get(4) + "%");
		history6.setText("当前阈值: " + thredsholds.get(5) + "ppm");
		history7.setText("当前阈值: " + thredsholds.get(6) + "lux");
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp = soiltemp.getText().toString();
				if (temp.equals("")) {
					ToastUtil.TextToastShort(context, "请正确设置土壤温度阈值，允许范围：0-5℃");
				} else {
					thre1 = Integer.valueOf(temp);
					if (thre1 <= 5 && thre1 >= 0) {
						Message msg = Message.obtain();
						msg.arg1 = 1;
						msg.arg2 = thre1;					
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						controllerService.modifyThredshold1(thre1);
						ToastUtil.TextToastShort(context, "土壤温度阈值设置成功！");
						handler.postDelayed(refreshThreshold, 3000);
					} else {
						ToastUtil.TextToastShort(context, "请正确设置土壤温度阈值，允许范围：0-5℃");
					}
				}
				
				
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp = soilhum.getText().toString();
				if (temp.equals("")) {
					ToastUtil.TextToastShort(context, "请正确设置土壤湿度阈值，允许范围：0-10");
				} else {
					thre2 = Integer.valueOf(temp);
					if (thre2 <= 10 && thre2 >= 0) {
						Message msg = Message.obtain();
						msg.arg1 = 2;
						msg.arg2 = thre2;	
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						controllerService.modifyThredshold2(thre2);
						ToastUtil.TextToastShort(context, "土壤湿度阈值设置成功！");
						handler.postDelayed(refreshThreshold, 2000);
					} else {
						ToastUtil.TextToastShort(context, "请正确设置土壤湿度阈值，允许范围：0-10");
					}
				}
				
			}
		});

		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp = ph.getText().toString();
				if (temp.equals("")) {
					ToastUtil.TextToastShort(context, "请正确设置土壤酸碱度阈值，允许范围：0-2");
				} else {
					thre3 = Integer.valueOf(temp);
					if (thre3 <= 2 && thre3 >= 0) {
						Message msg = Message.obtain();
						msg.arg1 = 3;
						msg.arg2 = thre3;					
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						controllerService.modifyThredshold3(thre3);
						ToastUtil.TextToastShort(context, "土壤酸碱度阈值设置成功！");
						handler.postDelayed(refreshThreshold, 2000);
					} else {
						ToastUtil.TextToastShort(context, "请正确设置土壤酸碱度阈值，允许范围：0-2");
					}
				}
			}
		});
		
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp = airtemp.getText().toString();
				if (temp.equals("")) {
					ToastUtil.TextToastShort(context, "请正确设置空气温度阈值，允许范围：0-5℃");
				} else {
					thre4 = Integer.valueOf(temp);
					if (thre4 <= 5 && thre4 >= 0) {
						Message msg = Message.obtain();
						msg.arg1 = 4;
						msg.arg2 = thre4;					
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						controllerService.modifyThredshold4(thre4);
						ToastUtil.TextToastShort(context, "空气温度阈值设置成功！");
						handler.postDelayed(refreshThreshold, 2000);
					} else {
						ToastUtil.TextToastShort(context, "请正确设置空气温度阈值，允许范围：0-5℃");
					}
				}
			}
		});
		
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp = airhum.getText().toString();
				if (temp.equals("")) {
					ToastUtil.TextToastShort(context, "请正确设置空气湿度阈值，允许范围：0-10");
				} else {
					thre5 = Integer.valueOf(temp);
					if (thre5 <= 10 && thre5 >= 0) {
						Message msg = Message.obtain();
						msg.arg1 = 5;
						msg.arg2 = thre5;					
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						controllerService.modifyThredshold5(thre5);
						ToastUtil.TextToastShort(context, "空气湿度阈值设置成功！");
						handler.postDelayed(refreshThreshold, 2000);
					} else {
						ToastUtil.TextToastShort(context, "请正确设置空气湿度阈值，允许范围：0-10");
					}
				}
			}
		});
		
		button6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp = co2.getText().toString();
				if (temp.equals("")) {
					ToastUtil.TextToastShort(context, "请正确设置CO2浓度阈值，允许范围：0-200");
				} else {
					thre6 = Integer.valueOf(temp);
					if (thre6 <= 200 && thre6 >= 0) {
						Message msg = Message.obtain();
						msg.arg1 = 6;
						msg.arg2 = thre6;					
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						controllerService.modifyThredshold6(thre6);
						ToastUtil.TextToastShort(context, "CO2浓度阈值设置成功！");
						handler.postDelayed(refreshThreshold, 2000);
					} else {
						ToastUtil.TextToastShort(context, "请正确设置CO2浓度阈值，允许范围：0-200");
					}
				}
			}
		});
		
		
		button7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp = illum.getText().toString();
				if (temp.equals("")) {
					ToastUtil.TextToastShort(context, "请正确设置光照度阈值，允许范围：0-2000");
				} else {
					thre7 = Integer.valueOf(temp);
					if (thre7 <= 2000 && thre7 >= 0) {
						Message msg = Message.obtain();
						msg.arg1 = 7;
						msg.arg2 = thre7;
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						SocketOutputTask.sendMsgQueue.addLast(createTRESmsg(msg));
						controllerService.modifyThredshold7(thre7);
						ToastUtil.TextToastShort(context, "光照度阈值设置成功！");
						handler.postDelayed(refreshThreshold, 2000);
					} else {
						ToastUtil.TextToastShort(context, "请正确设置光照度阈值，允许范围：0-2000");
					}
				}
			}
		});
		
		return vw;

	}
	
	public String createTRESmsg(Message message) {
		String msg = "HFUT" + Launcher.selectMac + "TRES" + form_thre1 + form_thre2 + form_thre3 +
				form_thre4 + form_thre5 + form_thre6 + form_thre7 + "WANG";
		
		switch(message.arg1) {
		case 1:
			msg = msg.substring(0, 20) + "0" + message.arg2 + msg.substring(22, 44);
			break;
		case 2:
			if (message.arg2 >= 10) {
				msg = msg.substring(0, 22)  + message.arg2 + msg.substring(24, 44);
			} else {
				msg = msg.substring(0, 22)  + "0" + message.arg2 + msg.substring(24, 44);
			}
			break;
		case 3:
			if (message.arg2 > 10) {
				msg = msg.substring(0, 24)  + message.arg2 + msg.substring(26, 44);
			} else {
				msg = msg.substring(0, 24)  + "0" + message.arg2 + msg.substring(26, 44);
			}
			break;
		case 4:
			msg = msg.substring(0, 26)  + "0" + message.arg2 + msg.substring(28, 44);
			break;
		case 5:
			if (message.arg2 >= 10) {
				msg = msg.substring(0, 28)  + message.arg2 + msg.substring(28, 44);
			} else {
				msg = msg.substring(0, 28)  + "0" + message.arg2 + msg.substring(28, 44);
			}
			break;
		case 6:
			if (message.arg2 < 10) {
				msg = msg.substring(0, 30) + "0000" + message.arg2 + msg.substring(35, 44);
			} else if (message.arg2 >= 10 && message.arg2 < 100) {
				msg = msg.substring(0, 30) + "000" + message.arg2 + msg.substring(35, 44);
			} else if (message.arg2 >= 100 && message.arg2 < 1000) {
				msg = msg.substring(0, 30) + "00" + message.arg2 + msg.substring(35, 44);
			} else if (message.arg2 >= 1000 && message.arg2 < 10000) {
				msg = msg.substring(0, 30)  + "0"+ message.arg2 + msg.substring(35, 44);
			}
			break;
		case 7:
			if (message.arg2 < 10) {
				msg = msg.substring(0, 35) + "0000" + message.arg2 + msg.substring(40, 44);
			} else if (message.arg2 >= 10 && message.arg2 < 100) {
				msg = msg.substring(0, 35) + "000" + message.arg2 + msg.substring(40, 44);
			} else if (message.arg2 >= 100 && message.arg2 < 1000) {
				msg = msg.substring(0, 35) + "00" + message.arg2 + msg.substring(40, 44);
			} else if (message.arg2 >= 1000 && message.arg2 < 10000) {
				msg = msg.substring(0, 35)  + "0"+ message.arg2 + msg.substring(40, 44);
			}
			break;
			default:
				break;
		}
		return msg;
	}
	
	


}
