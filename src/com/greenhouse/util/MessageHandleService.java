package com.greenhouse.util;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.ui.Launcher;

import android.os.Handler;
import android.util.Log;

/** 
* 
* 对单条报文进行分类，具体处理在MessageHandle类中执行
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/24 AM 2:14:31 
* @version      1.0  
* description   处理Socket客户端接收的报文
*/
public class MessageHandleService {	
	
	private static final String TAG = "MessageHandleService";
	
	
	private static final String TIME = "54494d45";
	private static final String CONT = "434f4e54";
	private static final String STAT = "53544154";
	private static final String SENS = "53454e53";
	private static final String TASK = "5441534b";
	private static final String REMO = "52454d4f";
	private static final String HAVE = "48415645";
//	private static final String BUUU = "42555555";
	private static final String BUDD = "42554444";
	private static final String BUND = "42554e44";
	private static final String PROB = "50524f42";
	private static final String DELE = "44454c45";
	private static final String STOP = "53544f50";
	private static final String LOST = "4e4f5354";
	private static final String DANI = "44414e49";
	private static final String THRE = "54485245";
	
	
	private Handler mainHandler;
	
	public MessageHandleService(Handler handler) {
		this.mainHandler = handler;
	}
	
	/**
	 * 单条报文在这里进行处理，String报文仍用String处理；十六进制报文用十六进制值转8位二进制函数单独处理
	 */
	public void MessageHandleFromController(String MESSAGE) {
		// TODO Auto-generated method stub
		
		String func_key = MESSAGE.substring(32, 40);
		
		String data;
		
			switch (func_key) {			
				case TIME:
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:TIME]" + SocketInputTask.MESSAGE);
					Launcher.IsStartActivity = Const.TIME_SERVICE_FINISHED;
					Launcher.client.setRunningState(true);
					mainHandler.sendEmptyMessageDelayed(Const.TIME_SERVICE_FINISHED, 8000);
					break;
				case STOP:
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:STOP]" + SocketInputTask.MESSAGE);
					mainHandler.sendEmptyMessage(Const.SOCKET_DISCONNECTED);//弹出“连接失败提示框”
					mainHandler.removeMessages(Const.TIMEOUT);//取消弹出“连接超时提示框”
					break;
				case CONT:
				    //1.解析并截取报文数据段
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:CONT]" + SocketInputTask.MESSAGE);
					data = SocketInputTask.MESSAGE.substring(20, 40);
					//2.从消息队列移除
					if (SocketOutputTask.sendMsgQueue.contains(data)) {
						SocketOutputTask.sendMsgQueue.remove(data);
					}
					//3.本地保存并更新界面
					MessageHandle.setSwitchState(data);
					break;
				case SENS:	
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:SENS]" + SocketInputTask.MESSAGE);
					data = SocketInputTask.MESSAGE.substring(20, 40);
					MessageHandle.setSensorCurrentValue(data);
					break;
				case REMO://传感器解除绑定，bund＝0，传感器值仍然随着报文实时刷新
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:REMO]" + SocketInputTask.MESSAGE);
					data = SocketInputTask.MESSAGE.substring(20, 40);
					MessageHandle.setRemoSensor(data);
					break;
				case PROB:
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:PROB]" + SocketInputTask.MESSAGE);
					break;
				case LOST://lost报文未测试
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					data = SocketInputTask.MESSAGE.substring(20, 40);
					MessageHandle.setSensorLost(data);
					Log.d(TAG, "[Rece:LOST]" + SocketInputTask.MESSAGE);
					break;
				case DANI:
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:DANI]" + SocketInputTask.MESSAGE);
					data = SocketInputTask.MESSAGE.substring(20, 28);
					MessageHandle.setControllerDayNight(data);
					break;
				case THRE:
					SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(SocketInputTask.MESSAGE);
					Log.d(TAG, "[Rece:THRE]" + SocketInputTask.MESSAGE);
					data = SocketInputTask.MESSAGE.substring(20, 28);
					MessageHandle.setControllerThredshold(data);
					break;
				//-----------------------------------------Hex String----------------------------------------------
				case BUDD://插座传感器门限值
					Log.d(TAG, "[Rece:BUDD]" + SocketInputTask.MESSAGE);
					MessageHandle.setJackBundSensorTask(SocketInputTask.MESSAGE.substring(40, 80));
					break;
				case BUND://传感器绑定回执
					if (SocketOutputTask.sendMsgQueue.contains("BUND")) {
						SocketOutputTask.sendMsgQueue.remove("BUND");
					}
					Log.d(TAG, "[Rece:BUND]" + SocketInputTask.MESSAGE);
					MessageHandle.setJackBundSensorTask(SocketInputTask.MESSAGE.substring(40, 80));
					break;
				case STAT://TIME后收到该报文，1-数据库；2-本地缓存；3-通知刷新界面
					Log.d(TAG, "[Rece:STAT]" + SocketInputTask.MESSAGE + "[状态 1-更新缓存 2-更新数据库]");
					MessageHandle.setAllSwitchState(SocketInputTask.MESSAGE.substring(40, 80));
					break;	
				case HAVE:
					Log.d(TAG, "[Rece:HAVE]" + SocketInputTask.MESSAGE + "[状态 1-更新缓存 2-更新数据库]");
					MessageHandle.setAllJackTaskMark(SocketInputTask.MESSAGE.substring(40, 80));
					break;
				case TASK:
					Log.d(TAG, "[Rece:TASK]" + SocketInputTask.MESSAGE);
					MessageHandle.setAllJackTask(SocketInputTask.MESSAGE.substring(40, 80));
					break;
				case DELE:
					Log.d(TAG, "[Rece:DELE]" + SocketInputTask.MESSAGE);
					MessageHandle.deletelJackTask(SocketInputTask.MESSAGE.substring(40, 80));
					break;
			}
	}
	

	
	
	
	
	
	
	


	

	
}
