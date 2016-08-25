package com.greenhouse.networkservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.DataFormatConversion;
import com.greenhouse.util.MessageCheck;
import com.greenhouse.util.MessageHandleService;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/** 
* class <code>SocketInputThread</code> APP－client端接收
* 
* 和MessageHandleTask()线程配合，对接收到的报文进行处理
* 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/8  PM4:21:32 
* @version      1.0  
*/
public class SocketInputTask implements Runnable{
	
	private static final String TAG = "SocketInputThread.java";

	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024*100); //保存从inputStream读取字节流
	private byte[] buffer = new byte[1024*100]; 											   //从保存的字节流中读取的报文放在buffer中
	private byte[] receiveByte;                							  					   //从保存的字节流的buffer中一次性读取的报文（字节类型）
	private String receive = "";             												   //从保存的字节流的buffer中一次性读取的报文
	public static String MESSAGE = ""; 														   //单条报文
	
	public Handler mainHandler;           //主线程handler
	private static Handler sInputHandler; //接收线程handler
	
	private MessageHandleService msgHandlerService; //
	
	
	/**
	 * 每次重新启动Socket Input Task
	 * @param mainHandler 主线程Handler
	 */
	public SocketInputTask(Handler mainHandler) {
		this.mainHandler = mainHandler;
		msgHandlerService = new MessageHandleService(mainHandler);
	}
	
	
	public void getEncodeData(InputStream inputStream) throws IOException{
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, length);
			receiveByte = byteArrayOutputStream.toByteArray();
			receive = DataFormatConversion.bytesToHexString(receiveByte);
			if (MessageCheck.fromController(receive)) {                       //1-首尾校验
				while (receive.length() >= 1) {
					int startIndex = receive.indexOf(Const.HFUT);
					int offsetIndex = receive.indexOf(Const.WAN) + 8;
					if (startIndex != -1 && offsetIndex == 88) {              //2-长度校验
						MESSAGE = receive.substring(startIndex, offsetIndex); //失去了强引用的receive.substring()对象可以被GC
						receive = receive.substring(offsetIndex);
						try {
							msgHandlerService.MessageHandleFromController(MESSAGE);//异常报文捕获并继续进入message handle循环
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		
					}
				}
			} 
			byteArrayOutputStream = new ByteArrayOutputStream();
		}
	}
	
	public void run() {
		ThreadPoolManager.INPUT_IsRUNNING = true;
		Log.d(TAG, "[Input-Run]");
		Looper.prepare();		
		sInputHandler = new Handler() {
			public void handleMessage (Message msg) {
				switch (msg.what) {
				case Const.SOCKET_CONNECTED:
					Log.e(TAG, "Input Task 阻塞在receive，等待接收数据");
					Socket client = Launcher.client.getSocket();
					try {
						getEncodeData(client.getInputStream());//一直阻塞在这里除非socket接收异常
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d(TAG, "[Socket输入流异常]");
						Launcher.client.setState(Const.SOCKET_DISCONNECTED);
						try {
							Thread.sleep(3000);//等待，网络状态变化需要一个最终趋于稳定的过程，从NONE－WIFI／GPSRS
						} catch (InterruptedException interruptException) {
							// TODO Auto-generated catch block
							interruptException.printStackTrace();
						}
					} 
					
					mainHandler.sendEmptyMessage(Const.BACK_TO_LAUNCHER);
					
					// special version 有问题，需要调试
//					if (!Launcher.BACK_TO_LAUNCHER && Launcher.client.getRunningState()) {
//						new AsyncNetChangedSocketReq(mainHandler).execute();
//					}
					break;
				default:
					break;
				}
			}
		};
		
		Looper.myQueue();
		Looper.loop();
		ThreadPoolManager.INPUT_IsRUNNING = false;
		Log.d(TAG, "[Input-End]");
	}
	

	public static Handler getHandler() {
		return sInputHandler; 
	}

}

