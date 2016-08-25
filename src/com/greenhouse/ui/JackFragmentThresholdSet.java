package com.greenhouse.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	private List<Integer> thredsholds; 													  //从数据库读取的阈值历史记录
	
	private Context context;
	private Handler mainHandler;
//	public static Queue<String> sendMsgQueue = new LinkedList<>();
	
	public JackFragmentThresholdSet(Handler handler) {
		mainHandler = handler;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		context = getActivity();
		View vw = inflater.inflate(R.layout.jack_fragment_thresholdset, container, false);	
		
		final ControllerService controllerService = new ControllerService(context);
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
				thre1 = Integer.valueOf(airtemp.getText().toString());
				if (thre1 <= 5 && thre1 >= 0) {
					
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg.arg1 = 1;
					msg.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg2.arg1 = 1;
					msg2.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg3.arg1 = 1;
					msg3.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg3);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					controllerService.modifyThredshold1(thre1);
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_THRESET);
					ToastUtil.TextToastShort(context, "土壤温度阈值设置成功！");
				} else {
					ToastUtil.TextToastShort(context, "请正确设置土壤温度阈值，允许范围：0-5℃");
				}
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thre2 = Integer.valueOf(airhum .getText().toString());
				if (thre2 <= 10 && thre2 >= 0) {
					
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg.arg1 = 2;
					msg.arg2 = thre2;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg2.arg1 = 2;
					msg2.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg3.arg1 = 2;
					msg3.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg3);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					controllerService.modifyThredshold2(thre2);
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_THRESET);
					ToastUtil.TextToastShort(context, "土壤湿度阈值设置成功！");
				} else {
					ToastUtil.TextToastShort(context, "请正确设置土壤湿度阈值，允许范围：0-10");
				}
			}
		});

		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thre3 = Integer.valueOf(co2.getText().toString());
				if (thre3 <= 2 && thre3 >= 0) {
					
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg.arg1 = 3;
					msg.arg2 = thre3;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg2.arg1 = 3;
					msg2.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg3.arg1 = 3;
					msg3.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg3);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					controllerService.modifyThredshold3(thre3);
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_THRESET);
					ToastUtil.TextToastShort(context, "土壤酸碱度阈值设置成功！");
				} else {
					ToastUtil.TextToastShort(context, "请正确设置土壤酸碱度阈值，允许范围：0-2");
				}
			}
		});
		
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thre4 = Integer.valueOf(soiltemp.getText().toString());
				if (thre4 <= 5 && thre4 >= 0) {
					
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg.arg1 = 4;
					msg.arg2 = thre4;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg2.arg1 = 4;
					msg2.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg3.arg1 = 4;
					msg3.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg3);
					
					controllerService.modifyThredshold4(thre4);
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_THRESET);
					ToastUtil.TextToastShort(context, "空气温度阈值设置成功！");
				} else {
					ToastUtil.TextToastShort(context, "请正确设置空气温度阈值，允许范围：0-5℃");
				}
			}
		});
		
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thre5 = Integer.valueOf(soilhum.getText().toString());
				if (thre5 <= 10 && thre5 >= 0) {
					
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg.arg1 = 5;
					msg.arg2 = thre5;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg2.arg1 = 5;
					msg2.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg3.arg1 = 5;
					msg3.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg3);
					
					
					controllerService.modifyThredshold5(thre5);
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_THRESET);
					ToastUtil.TextToastShort(context, "空气湿度阈值设置成功！");
				} else {
					ToastUtil.TextToastShort(context, "请正确设置空气湿度阈值，允许范围：0-10");
				}
			}
		});
		
		button6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thre6 = Integer.valueOf(ph.getText().toString());
				if (thre6 <= 500 && thre6 >= 0) {
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg.arg1 = 6;
					msg.arg2 = thre6;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg2.arg1 = 6;
					msg2.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg3.arg1 = 6;
					msg3.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg3);
					
					controllerService.modifyThredshold6(thre6);
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_THRESET);
					ToastUtil.TextToastShort(context, "CO2浓度阈值设置成功！");
				} else {
					ToastUtil.TextToastShort(context, "请正确设置CO2浓度阈值，允许范围：0-500");
				}
			}
		});
		
		
		button7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thre7 = Integer.valueOf(illum.getText().toString());
				if (thre7 <= 5000 && thre7 >= 0) {
					Message msg = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg.arg1 = 7;
					msg.arg2 = thre7;
					SocketOutputTask.getHandler().sendMessage(msg);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg2.arg1 = 7;
					msg2.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg2);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.THRE);
					msg3.arg1 = 7;
					msg3.arg2 = thre1;
					SocketOutputTask.getHandler().sendMessage(msg3);
					
					controllerService.modifyThredshold7(thre7);
					mainHandler.sendEmptyMessage(Const.UI_REFRESH_FRAG_THRESET);
					ToastUtil.TextToastShort(context, "光照度阈值设置成功！");
				} else {
					ToastUtil.TextToastShort(context, "请正确设置光照度阈值，允许范围：0-5000");
				}
			}
		});
		
		return vw;

	}


}
