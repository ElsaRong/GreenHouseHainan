package com.greenhouse.mvadpater;

import java.util.List;
import com.greenhouse.R;
import com.greenhouse.model.Jack;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.specialversion.LockCheck;
import com.greenhouse.ui.JackFragmentSwitchTest;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.OnFragmentRefreshInterface;
import com.greenhouse.widget.OptionButton;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class JackSwitchItemAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	public JackSwitchItemAdapter(Context context, List<Jack> jackSwitchInfoList) {
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return JackFragmentSwitchTest.jackSwitchInfoList.size();
		
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		convertView = mInflater.inflate(R.layout.jackswitch_icon_item, null);
		holder.jackname = (TextView) convertView.findViewById(R.id.jackname);
		holder.jackswitch = (OptionButton) convertView.findViewById(R.id.jackswitch);
		convertView.setTag(holder);
		
		holder.jackname.setText(JackFragmentSwitchTest.jackSwitchInfoList.get(position).getName().toString());
		
		if (JackFragmentSwitchTest.jackSwitchInfoList.get(position).getSwitchstate() == 1) {
			holder.jackswitch.setChecked(true);	
		} else {			
			holder.jackswitch.setChecked(false);	
		}

		
		holder.jackswitch.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (position >= 9) {
					JackFragmentSwitchTest.selectSwitch = (position + 1) + "";
				}else{
					JackFragmentSwitchTest.selectSwitch = "0" + (position + 1);
				}
				
				final String id = JackFragmentSwitchTest.selectSwitch;
					
				//打开（打开前需要检查互锁）
				if (JackFragmentSwitchTest.jackSwitchInfoList.get(position).getSwitchstate() == 0) {
					//开关互锁，打开前检查互锁插座是否关闭
					if (LockCheck.CheckLockStateAccordingMac(position, 1, Launcher.selectMac)) {
						JackFragmentSwitchTest.jackSwitchInfoList.get(position).setSwitchstate(1);
						
						SocketOutputTask.sendMsgQueue.addLast(createCONTOPENmsg(id));
						SocketOutputTask.sendMsgQueue.addLast(createCONTOPENmsg(id));
						SocketOutputTask.sendMsgQueue.addLast(createCONTOPENmsg(id));
						SocketOutputTask.sendMsgQueue.addLast(createCONTOPENmsg(id));
						SocketOutputTask.sendMsgQueue.addLast(createCONTOPENmsg(id));
				
					} else {
						//如果需要互锁，插座状态取反并刷新界面
						JackFragmentSwitchTest.jackSwitchInfoList.get(position).setSwitchstate(0);
					}
					
				} else {
					JackFragmentSwitchTest.jackSwitchInfoList.get(position).setSwitchstate(0);
					
					SocketOutputTask.sendMsgQueue.addLast(createCONTCLOSmsg(id));
					SocketOutputTask.sendMsgQueue.addLast(createCONTCLOSmsg(id));
					SocketOutputTask.sendMsgQueue.addLast(createCONTCLOSmsg(id));
					SocketOutputTask.sendMsgQueue.addLast(createCONTCLOSmsg(id));
					SocketOutputTask.sendMsgQueue.addLast(createCONTCLOSmsg(id));					
				}
				
			}
		});

		
		return convertView;
	}
	
	private String createCONTOPENmsg(String id) {
		final String msg = "HFUT" + Launcher.selectMac + "CONT" + "00" + id + "OPEN" + "000000000000WANG";
		return msg;
	}
	
	private String createCONTCLOSmsg(String id) {
		final String msg = "HFUT" + Launcher.selectMac + "CONT" + "00" + id + "CLOS" + "000000000000WANG";
		return msg;
	}
	
	public final class ViewHolder {
		public OptionButton jackswitch;
		public TextView jackname;
	}


}
