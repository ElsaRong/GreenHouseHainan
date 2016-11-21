package com.greenhouse.networkservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.DataFormatConversion;
import com.greenhouse.util.MessageCheck;
import com.greenhouse.util.MessageHandle;
import android.os.Handler;
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
	
	private static final String TAG = "SocketInputTask";
	private InputStream inputStream = null;

	private ByteArrayOutputStream byteArrayOutputStream; //保存从inputStream读取字节流
	private byte[] buffer = new byte[1024*10]; 		//从保存的字节流中读取的报文放在buffer中
	private byte[] recvByte;                	    //从保存的字节流的buffer中一次性读取的报文（字节类型）
	private String recvStr = null;             		//从保存的字节流的buffer中一次性读取的报文
	public static String MESSAGE = ""; 				//单条报文
	
	public void readInputStream(InputStream inputStream) {
		while (Launcher.client != null && Launcher.client.isConnected() && !Launcher.client.isClosed()) {		
			int length = 0;
			try {
				length = inputStream.read(buffer);
				if (length != -1) {
					byteArrayOutputStream = new ByteArrayOutputStream(1024*10);
					byteArrayOutputStream.write(buffer, 0, length);
					recvByte = byteArrayOutputStream.toByteArray();
					recvStr = DataFormatConversion.bytesToHexString(recvByte);
					while (recvStr.length() > 1) {
						int startIndex = recvStr.indexOf(Const.HFUT);
						int offsetIndex = recvStr.indexOf(Const.WAN) + 8;
						if (startIndex != -1 && offsetIndex == 88) {              //2-长度校验
							MESSAGE = recvStr.substring(startIndex, offsetIndex); //失去了强引用的receive.substring()对象可以被GC
							recvStr = recvStr.substring(offsetIndex);
							try {
								MessageHandle.MessageHandleFromController(MESSAGE);//异常报文捕获并继续进入message handle循环
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
						}
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				if (Launcher.client != null) {
					try {
						Launcher.client.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Launcher.client = null;
				}
				
			}
		}
	}
	
	public void run() {
		Log.d(TAG, "Input Task Run");
		
		if (Launcher.client != null) {
			try {
				inputStream = Launcher.client.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (inputStream != null) {
				readInputStream(inputStream);
			}
		}
		
		Log.d(TAG, "Input Task End");
	}
	
}

