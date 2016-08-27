package com.greenhouse.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.greenhouse.R;
import com.greenhouse.database.JackService;
import com.greenhouse.model.Jack;
import com.greenhouse.networkservice.NetBroadcastReceiver;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.networkservice.ThreadPoolManager;
import com.greenhouse.specialversion.LockCheck;
import com.greenhouse.ui.AlarmclockListView.AlarmClockListAdapter.ViewHolder;
import com.greenhouse.util.Const;
import com.greenhouse.util.DataFormatConversion;
import com.greenhouse.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AlarmclockListView extends Activity {
	
	private static final String TAG = "AlarmclockListView.java";
	
	private ListView listview;
	
	private LayoutInflater mInflater;
	
	private JackService jackService;
	private ArrayList<Jack> jackTasks;
	
	public static List<Map<String, String>> mMultiCheck; //选中的插座id,保存格式eg.{{0=1},{1=2},{2=3},{3=4}}
	
	private Boolean MapIsExist = false;

	
	private static ProgressBar title_waiting;
	
	public BroadcastReceiver myReceiver;
	
	private static AlarmClockListAdapter adapter;
	

	
	/**
	 * 20160107 把handler声明为static public，在MessageHandleTask中就能直接调用了
	 * 存在的问题是：其中要操作的变量，也必须是static，那么这些static变量的多线程安全问题就很难保�??
	 */	
	public static Handler sAlarmListHandler;

	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "[onCreate]");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmclock_list);	
		
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting); 
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction("com.greenhosue.backtolauncheraction");
        myReceiver = new NetBroadcastReceiver(sAlarmListHandler);
        this.registerReceiver(myReceiver, filter);
		
		jackService = new JackService(this);
		jackTasks = jackService.getAllJackTask(Launcher.selectMac);
		
		sAlarmListHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Const.UI_REFRESH:
					Log.d(TAG, "[定时任务列表－刷新]");
					jackTasks = jackService.getAllJackTask(Launcher.selectMac);
					adapter.notifyDataSetChanged();
					break;
				case Const.SOCKET_RECONNECTED:
					title_waiting.setVisibility(View.GONE);
					SocketInputTask.getHandler().sendEmptyMessage(Const.SOCKET_CONNECTED);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SocketOutputTask.getHandler().sendEmptyMessage(Const.TIME);
					sAlarmListHandler.sendEmptyMessageDelayed(Const.BACK_TO_LAUNCHER, 2000);
					break;
				case Const.TIME_SERVICE_FINISHED:
					ThreadPoolManager.getInstance().startSocketServerAccept();
					break;
				case Const.BACK_TO_LAUNCHER:
					ToastUtil.TextToastLong(AlarmclockListView.this, "网络异常");
					Launcher.client.setState(Const.SOCKET_DISCONNECTED);
					Launcher.server.setServerState(Const.SOCKET_DISCONNECTED);
					Launcher.client.destroy();
					Launcher.server.destroy();
					Intent intent = new Intent(AlarmclockListView.this, Launcher.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;	
				case Const.SOCKET_CONNECTING:
					title_waiting.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		
		
		adapter = new AlarmClockListAdapter(this);
		
		listview = (ListView) findViewById(R.id.list);
		listview.setAdapter(adapter);
		
		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mMultiCheck = new ArrayList<Map<String, String>>();

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub				
				ViewHolder vHollder = (ViewHolder) view.getTag();
				vHollder.cBox.toggle();
				isSelected.put(position,vHollder.cBox.isChecked());
				Map<String, String> map = new HashMap<String, String>();
				map.put(position + "", jackTasks.get(position).getJackId().toString());
				Iterator<Map<String, String>> iterator = mMultiCheck.iterator();
				if (!iterator.hasNext()) {
					mMultiCheck.add(map);
				} else {
					while (iterator.hasNext()) {
						Object object = iterator.next();
						if (map.equals(object)) {
							MapIsExist = true;
						}
					}

					if (MapIsExist == false) {
						mMultiCheck.add(map);
					} else {
						mMultiCheck.remove(map);
						MapIsExist = false;
					}
				}
			}
		});

		// title_btn
		ImageView localImageView = (ImageView) findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back);
		localImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		// title_bar theme
		TextView localTextView = (TextView) findViewById(R.id.title);
		localTextView.setText("定时任务列表");

		// add_btn
		Button add_btn = (Button) findViewById(R.id.add_alarmclock);
		add_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Iterator<Map<String, String>> iterator = mMultiCheck.iterator();
				//设置定时任务互锁
				if (LockCheck.checkTimerChosedJack(mMultiCheck)) {
					if (iterator.hasNext()) {
						new AlertDialog.Builder(AlarmclockListView.this).setTitle("")
						.setMessage(
								"您已经选择了"
										+ mMultiCheck.size()
										+ " 个插座,"
										+ " 确定设置这些插座的定时任务？")
						.setNegativeButton("否",
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int which) {
								// TODO Auto-generated method stub
							}
						})
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int whichButton) {
								Count2ndFormatChosedJacks();
								startActivity(new Intent(AlarmclockListView.this,Timer.class));
							}
						}).show();
						
					} else {
						ToastUtil.TextToastShort(AlarmclockListView.this, "请至少�?�择�?个插座进行设�?");
					}
				}

			}
		});

		Button cancel_btn = (Button) findViewById(R.id.cancel_alarmclock);
		cancel_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Iterator<Map<String, String>> iterator = mMultiCheck.iterator();
				if (iterator.hasNext()) {
					new AlertDialog.Builder(AlarmclockListView.this)
							.setTitle("提示")
							.setMessage(
									"您已经选择了"
											+ mMultiCheck.size()
											+ " 个插座，"
											+ " 确定删除这些插座的定时任务？")
							.setNegativeButton("否",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,	int which) {
											// TODO Auto-generated method stub
										}
									})
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {
										public void onClick( DialogInterface dialog, int whichButton) {
											Count2ndFormatChosedJacks();
											SocketOutputTask.getHandler().sendEmptyMessage(Const.DELE);//发�?�删除报�?
											SocketOutputTask.getHandler().sendEmptyMessageDelayed(Const.DELE, 200);
											AlarmclockListView.sAlarmListHandler.sendEmptyMessageDelayed(Const.UI_REFRESH, 800);
											ClearMultiCheck();
										}
										
									}).show();
				} else {
					ToastUtil.TextToastShort(AlarmclockListView.this, "删除定时任务，请至少选择一个插座");
				}
				
			}
		});
	}
	
	
	
	public void Count2ndFormatChosedJacks() {
		Iterator<Map<String, String>> iterator = mMultiCheck.iterator();
		String extractJackId = "";
		
		Timer.chosedJackGroupHex = null;
		
		for(int i=0; i<48; i++){
			Timer.chosedJackGroup[i] = 0;
		}
		
		while(iterator.hasNext()) {
			Object object = iterator.next();
			extractJackId = object.toString();			
			int startIndex = extractJackId.indexOf("=") + 1;
			int endIndex = extractJackId.indexOf("}");
			int chosedJackId = Integer.parseInt(extractJackId.substring(startIndex, endIndex));
			Timer.chosedJackGroup[chosedJackId - 1] = 1;	
		}		
			
		Log.i(TAG,DataFormatConversion.Int2String(Timer.chosedJackGroup));
		Log.i(TAG,DataFormatConversion.BinStr48ToHexStr12(DataFormatConversion.Int2String(Timer.chosedJackGroup)));	
		
		//f000000000000000000-111100000000...
		Timer.chosedJackGroupHex = DataFormatConversion.BinStr48ToHexStr12(DataFormatConversion.Int2String(Timer.chosedJackGroup));
	}
	
	/**
	 * 怎样清理ArrayList即高效又能防止内存泄漏，待定
	 */
	public void ClearMultiCheck() {
		mMultiCheck.clear();
		mMultiCheck = new ArrayList<Map<String, String>>();
	}
	


	public void onRestart() {
		Log.d(TAG, "[onRestart]");
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub	
		unregisterReceiver(myReceiver);
		super.onDestroy();	
	}

	public static SparseBooleanArray isSelected = new SparseBooleanArray();
	
	public class AlarmClockListAdapter extends BaseAdapter {
		
		public AlarmClockListAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			for (int i = 0; i < jackTasks.size(); i++) {
				isSelected.put(i, false);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return jackTasks.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.alarmclock_item, null);
				holder.name = (TextView) convertView.findViewById(R.id.jack_num);
				holder.start = (TextView) convertView.findViewById(R.id.start_time);
				holder.poweron = (TextView) convertView.findViewById(R.id.ac_one);
				holder.poweroff = (TextView) convertView.findViewById(R.id.ac_two);
				holder.cycle = (TextView) convertView.findViewById(R.id.ac_three);
				holder.cBox = (CheckBox) convertView.findViewById(R.id.check_box);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(jackTasks.get(position).getName());
			holder.start.setText(jackTasks.get(position).getStart());
			holder.poweron.setText(jackTasks.get(position).getPoweron());
			holder.poweroff.setText(jackTasks.get(position).getPoweroff());
			if(jackTasks.get(position).getCycle().toString().equals("99")){
				holder.cycle.setText("infinite");
			}else{				
				holder.cycle.setText(jackTasks.get(position).getCycle().toString());
			}
			holder.cBox.setChecked(isSelected.get(position));
			return convertView;
		}

		public final class ViewHolder {
			public TextView name;
			public TextView start;
			public TextView poweron;
			public TextView poweroff;
			public TextView cycle;
			public CheckBox cBox;
		}
	}

	

}
