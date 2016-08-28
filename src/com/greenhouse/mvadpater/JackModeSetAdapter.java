package com.greenhouse.mvadpater;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import com.greenhouse.R;
import com.greenhouse.model.Jack;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.ui.AlarmclockListView;
import com.greenhouse.ui.JackFragmentMaster;
import com.greenhouse.ui.JackFragmentModeSet;
import com.greenhouse.ui.Launcher;
import com.greenhouse.ui.SensorRecyclerView;
import com.greenhouse.ui.Timer;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class JackModeSetAdapter extends BaseAdapter {
	private static final String TAG = "GridSwitchModelTestAdapter.java";
	private List<Jack> jacks;
	private LayoutInflater mInflater;
	private Context context;
	public static Queue<String> sendMsgQueue = new LinkedList<>();
	private ViewHolder holder;
	
	public JackModeSetAdapter(Context context, List<Jack> jacks) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.jacks = jacks;
	}
	@Override
	public int getCount() {
		// TODO 
		return jacks.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO 
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO 
		return 0;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO 
		holder = new ViewHolder();
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.modeset_item, null);
			holder.tvJackName = (TextView) convertView.findViewById(R.id.jackname1);
			holder.btnTimeModel = (Button) convertView.findViewById(R.id.jacksettimebutton);
			holder.btnSensModel = (Button) convertView.findViewById(R.id.jackgoingbutton);
			holder.btnRemoModel = (Button) convertView.findViewById(R.id.remomode);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvJackName.setText(jacks.get(position).getName().toString());
		
		//选择定时模式，保存点击的插座编号
		holder.btnTimeModel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 
				JackFragmentModeSet.sJackId = (position + 1);
				Intent intent=new Intent();
				intent.setClass(context,AlarmclockListView.class);
				context.startActivity(intent);
			}
		});
		
		//选择联动模式，保存点击的插座编号
		holder.btnSensModel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 
				JackFragmentModeSet.sJackId = (position + 1);
				Intent intent=new Intent();
				intent.setClass(context,SensorRecyclerView.class);
				context.startActivity(intent);
			}
		});
		
		holder.btnRemoModel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 
				new AlertDialog.Builder(context).setTitle("提示")
				.setMessage(
						"确定删除解除开关联动模式？")
				.setNegativeButton("否",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int which) {
								// TODO Auto-generated method stub
							}
						})
				.setPositiveButton("是",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int whichButton) {
								int intJackId = position + 1;
								String strJackId= "00";
								if (intJackId < 10) {
									strJackId = "0" + intJackId; 
								} else {
									strJackId = intJackId + "";
								}
								//发送一次
								Message msg = SocketOutputTask.getHandler().obtainMessage(Const.REMO, strJackId);
								SocketOutputTask.getHandler().sendMessage(msg);
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//发送两次
								Message msg1 = SocketOutputTask.getHandler().obtainMessage(Const.REMO, strJackId);
								SocketOutputTask.getHandler().sendMessage(msg1);
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//发送两次
								Message msg2 = SocketOutputTask.getHandler().obtainMessage(Const.REMO, strJackId);
								SocketOutputTask.getHandler().sendMessage(msg2);
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//发送两次
								Message msg3 = SocketOutputTask.getHandler().obtainMessage(Const.REMO, strJackId);
								SocketOutputTask.getHandler().sendMessage(msg3);
							}
						}).show();
			}
		});
		
		return convertView;
	}
	public final class ViewHolder {
		public Button btnTimeModel;
		public Button btnSensModel;
		public Button btnRemoModel;
		public TextView tvJackName;
	}

}
