package com.greenhouse.networkservice;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;

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
	
	private static final String TAG = "SocketOutputThread";
	
	private OutputStream outputStream;				       //Socket输出流
	public static LinkedList<Object> sendMsgQueue = new LinkedList<Object>(); //发送消息队列

	
	public void run() {
		Log.d(TAG, "Output Task Run");
		try {
			outputStream = Launcher.client.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendMsgQueue.addLast(createTIMEmsg());
		sendMsgQueue.addLast(createTIMEmsg());
		sendMsgQueue.addLast(createTIMEmsg());
		
		if (outputStream != null) {	
			while (Launcher.client != null && Launcher.client.isConnected() && !Launcher.client.isClosed()) {
				Object msg = null;
					
					synchronized (sendMsgQueue) {
						if (!sendMsgQueue.isEmpty()) {			
							try {
								msg = sendMsgQueue.getFirst();
								sendMsgQueue.removeFirst();
							} catch (Exception nsee) {
								// TODO Auto-generated catch block
								nsee.printStackTrace();
							}
						}
					}
					
					if (msg == null) {
//						Log.e(TAG, "msg==null (synchronized sendMsgQueue exception)");
					} else if (msg.toString().contains("HFUT") || msg.toString().contains("WANG")) {
						sendMessageStr(msg.toString());
					} else {
						sendMessageByte(toByteArray(msg));
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		}
		
		Log.d(TAG, "Output Task End");
	}
	
    public byte[] toByteArray (Object obj) {      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
//        Log.d(TAG, "转换后的byte: " + bytes);
        return bytes;    
    }
	
	private String createTIMEmsg() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String Year = Integer.toString(year);
		String Month = Integer.toString(month);
		String Day = Integer.toString(day);
		String Hour = Integer.toString(hour);
		String Minute = Integer.toString(minute);
		String Second = Integer.toString(second);
		if (month < 10) {
			Month = "0" + Month;
		}
		if (day < 10) {
			Day = "0" + Day;
		}
		if (hour < 10) {
			Hour = "0" + Hour;
		}
		if (minute < 10) {
			Minute = "0" + Minute;
		}
		if (second < 10) {
			Second = "0" + Second;
		}
		final String msg = "HFUT" + Launcher.selectMac + "TIME" + Year + Month + Day + Hour + Minute + Second + "00" + "0000" + "WANG";
		return msg;
	}
	

	public void sendMessageByte(byte[] byteMsg) {
		DataOutputStream writer  = new DataOutputStream(outputStream);
		try {
			writer.write(byteMsg);
			writer.flush(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "[Send:Byte]" + byteMsg);
	}

	private Integer sendMessageStr(String msg) {
		Integer ret = Integer.valueOf(-1);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		try {
			writer.write(msg.replace("", ""));
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "[Send: Str]" + msg);
		return ret;
	}

}
