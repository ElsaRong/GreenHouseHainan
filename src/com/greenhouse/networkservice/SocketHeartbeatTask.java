package com.greenhouse.networkservice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.greenhouse.ui.Launcher;
import android.util.Log;

public class SocketHeartbeatTask implements Runnable{
	
	private static final String TAG = "SocketHeartbeatThread";
	private BufferedWriter writer;
	private OutputStream outputStream;
	
	public void run() {	
		Log.d(TAG, "Hearbeat Task Run");
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		while (Launcher.client != null && Launcher.client.isConnected() && !Launcher.client.isClosed()) {
			
			try {
				outputStream = Launcher.client.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			final String heartbeat = "HFUT" + Launcher.selectMac + "HEARTBEAT00000000000WANG";
			writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			try {
				writer.write(heartbeat.replace("", ""));
				writer.flush();
//				Log.d(TAG, "[Send: Heart]" + heartbeat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (Launcher.client != null) {
					try {
						Launcher.client.close();
					} catch (IOException ioe) {
						// TODO Auto-generated catch block
						ioe.printStackTrace();
					}
					Launcher.client = null;
				}
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		Log.e(TAG, "Heartbeat Task End");
	}
	
	
}
	


