package com.greenhouse.networkservice;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/** 
* class <code>SocketOutputThread</code> 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/8 PM 4:21:32 
* @version      1.0  
*/
public class SocketOutputTask implements Runnable{	
	
	private static final String TAG = "SocketOutputThread.java";
	
	private static Handler outputHandler;                          //发送线程handler
	private static OutputStream outputStream;				       //Socket输出流
	public static Queue<String> sendMsgQueue = new LinkedList<>(); //发送消息队列
	public Handler mainHandler; 								   //主线程handler,用于异步重连
	
	/**
	 * @param mainHandler
	 */
	public SocketOutputTask(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}
	
	/**
	 * @Title:       sendEncodeData
	 * @description: TODO 根据handler传递的消息的msg,调用对应的组装报文方法(在对应的组装方法中有对应的发送方法)
	 * @param        @param msg
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 23, 2016, 3:23:02 PM
	 */
	public void sendEncodeData(Message msg){
		switch (msg.what) {
		case Const.TIME:
			CommProtocol.sendMessageTIME();
			break;
		case Const.TASK:
			CommProtocol.sendMessageTASK();
			break;
		case Const.DELE:
			CommProtocol.sendMessageDELE();
			break;
		case Const.SENS:
			CommProtocol.sendMessageSENS();
			break;
		case Const.PROB:
			CommProtocol.sendMessagePROB();
			break;
		case Const.BUND:
			CommProtocol.sendMessageMultiBund();
			break;
		case Const.CONTOPEN:
			CommProtocol.sendMessageCONTOPEN(msg.obj);
			break;
		case Const.CONTCLOS:
			CommProtocol.sendMessageCONTCLOS(msg.obj);
			break;
		case Const.REMO:
			CommProtocol.sendMessageREMO(msg.obj);
			break;
		case Const.HEARTBEAT:
			CommProtocol.sendMessageHEARTBEAT();
			break;
		case Const.DANI:
			CommProtocol.sendMessageTIMESET(msg);
			break;
		case Const.THRE:
			CommProtocol.sendMessageTHRE(msg);
			break;
		case Const.OUTT:
			CommProtocol.sendMessageOUTT();
			break;
		case Const.INTE:
			CommProtocol.sendMessageINTE();
			break;
		default:
			break;
		}
	}
	
	/**
	 * @Title:       sendMessageStr
	 * @description: TODO 发送字符串格式报文
	 * @param        @param strMsg
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 20, 2016, 7:14:03 PM
	 */
	public static void sendMessageStr(String strMsg) {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		try {
			writer.write(strMsg.replace("", ""));
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "[Send]" + strMsg);
	}
	
	/**
	 * @Title:       sendMessageByte
	 * @description: TODO 发送字节格式报文
	 * @param        @param byteMsg
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 20, 2016, 7:13:48 PM
	 */
	public static void sendMessageByte(byte[] byteMsg) {
		DataOutputStream writer  = new DataOutputStream(outputStream);
		try {
			writer.write(byteMsg);
			writer.flush(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "[Send]" + byteMsg);
	}
	

	public void run() {
		ThreadPoolManager.OUTPUT_IsRUNNING = true;
		Log.d(TAG, "[Output-Run]");
		Looper.prepare();		
		outputHandler = new Handler() {
			public void handleMessage (Message msg) {
				if (Launcher.client != null && Launcher.client.getState() == Const.SOCKET_CONNECTED) {
					try {
						outputStream = Launcher.client.getSocket().getOutputStream();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Launcher.client.setState(Const.SOCKET_DISCONNECTED);
					}
					if (outputStream != null) {
						sendEncodeData(msg); 
					}
				} else {
					Launcher.client.setState(Const.SOCKET_DISCONNECTED);
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException interruptException) {
						// TODO Auto-generated catch block
						interruptException.printStackTrace();
					}
					
					if (!Launcher.BACK_TO_LAUNCHER && Launcher.client.getRunningState()) {
						new AsyncNetChangedSocketReq(mainHandler).execute();
					}
				}
			}
		};
		
		Looper.myQueue();
		Looper.loop();
		ThreadPoolManager.OUTPUT_IsRUNNING = false;
		Log.d(TAG, "[Output-End]");
	}
	

	
	public static Handler getHandler() {
		return outputHandler; 
	}




	

	
	


}
