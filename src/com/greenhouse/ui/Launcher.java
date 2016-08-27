package com.greenhouse.ui;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.greenhouse.R;
import com.greenhouse.animation.HeavenAnimateView;
import com.greenhouse.database.ControllerService;
import com.greenhouse.database.JackService;
import com.greenhouse.database.SensorService;
import com.greenhouse.model.SocketClient;
import com.greenhouse.model.SocketServer;
import com.greenhouse.mvadpater.GridItemDataAdapter;
import com.greenhouse.networkservice.NetworkManager;
import com.greenhouse.networkservice.SocketHeartbeatTask;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.networkservice.AsyncSocketReq;
import com.greenhouse.networkservice.ThreadPoolManager;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.SlideMenuAdapter;
import com.greenhouse.util.ToastUtil;
import com.greenhouse.viewconfig.DragGrid;
import com.greenhouse.viewconfig.LauncherViewConfig;
import com.greenhouse.viewconfig.ScrollLayout;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;

public class Launcher extends Activity {

	private static final String TAG = "Launcher.java";

	/*界面相关标志位*/
	public static boolean isClickable;            //控制器图标是否可点击，如果已经点击且正在连接，则不能再点击
	public static boolean IsFirstConnect = true;  //
	public static int IsStartActivity;			  //
	public static String selectMac = null;		  //已点击选中的控制器MAC
	public static String selectIp = null;         //已点击选中的控制器IP
	
	/*主界面布局视图相关*/
	private LinearLayout linearLayout;       //单个滑动布局
	private LinearLayout.LayoutParams param; //单个滑动布局	
	private ScrollLayout scrollLayout;       //滑动布局
	private DragGrid dragGridView;			 //网格视图
	private TextView pageNumber;			 //滑动页面的页码
	private DrawerLayout drawerLayuout;      //左侧滑菜单布局
	private ListView listView;               //左侧滑菜单视图
	
	/*主界面控件相关*/
	private ImageView runImage, deleteBtn;         //删除button
	private static ProgressBar controller_waiting; //等待菊花
	private static TextView txt_controllerwaiting; //等待文字
	private static ProgressBar title_waiting;      //title栏等待小菊花
	
	
	/*主界面布局－数据绑定相关*/
	public static List<String> controllersList = new ArrayList<String>();                             //所有控制器信息
	private ArrayList<ArrayList<String>> scrollControllersList = new ArrayList<ArrayList<String>>();  //分页显示控制器信息
	private ArrayList<DragGrid> dragGridArray = new ArrayList<DragGrid>();	                          //分页网格布局Adapter
	
	
	private ActionBarDrawerToggle mDrawerToggle;
	
	
	public static boolean BACK_TO_LAUNCHER = false;
	

	public BroadcastReceiver myReceiver;

	/*数据库表对象*/
	private ControllerService controllerService = new ControllerService(this); //数据库Controller表对象
	private JackService jackService = new JackService(this);				   //数据库Jack表对象
	private SensorService sensorService = new SensorService(this);			   //数据库Sensor表对象
	
	
	
	public static SocketClient client;
	public static SocketServer server;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		setContentView(R.layout.main);	
		
		initControllerList();              //初始化所有mac信息到ArrayList<String>
		initClassifiedControllers();       //初始化分页mac信息到ArrayList<ArrayList<String>>
		initScrollLayoutAndGridViewItem(); //分页绘制界面
		initSlideMenu();
		initSensor();
		runAnimation();
	}
	
	public void initControllerList() {
		if(!controllersList.isEmpty()){
			controllersList.clear();
		}
		if(controllerService.getCount() == 0) {
			controllersList.add("add");
		} else {
			controllersList = controllerService.getAllMac();
			controllersList.add("add");
		}
		
	}

	private void initClassifiedControllers() {
		if(!scrollControllersList.isEmpty()) {
			scrollControllersList.clear();
		}
		LauncherViewConfig.init(Launcher.this);            //初始化界面配
		LauncherViewConfig.countPages = (int) Math.ceil(controllersList.size() / (float) LauncherViewConfig.PAGE_SIZE);
		scrollControllersList.clear();;
		for (int i = 0; i < LauncherViewConfig.countPages; i++) {
			scrollControllersList.add(new ArrayList<String>());
			controllersList.remove(controllersList.size() - 1);
			for (int j = LauncherViewConfig.PAGE_SIZE * i; j < (LauncherViewConfig.PAGE_SIZE * (i + 1) > controllersList
					.size() ? controllersList.size() : LauncherViewConfig.PAGE_SIZE * (i + 1)); j++)
				scrollControllersList.get(i).add(controllersList.get(j));
		}
		boolean isLast = true;
		for (int i = scrollControllersList.get(LauncherViewConfig.countPages - 1).size(); i < LauncherViewConfig.PAGE_SIZE; i++) {
			if (isLast) {
				scrollControllersList.get(LauncherViewConfig.countPages - 1).add("add");
				isLast = false;
			} else
				scrollControllersList.get(LauncherViewConfig.countPages - 1).add("null");
		}
	}
	
	private void initScrollLayoutAndGridViewItem() {
		scrollLayout = (ScrollLayout) findViewById(R.id.views);
		deleteBtn = (ImageView) findViewById(R.id.dels); 
		controller_waiting = (ProgressBar) findViewById(R.id.controller_waiting); 
		txt_controllerwaiting = (TextView) findViewById(R.id.txt_controllerwaiting);
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting); 
		pageNumber = (TextView) findViewById(R.id.tv_page);
		pageNumber.setText("1");
		for (int i = 0; i < LauncherViewConfig.countPages; i++) {
			scrollLayout.addView(initGridViewItem(i));
		}
		scrollLayout.setPageListener(new ScrollLayout.PageListener() {
			@Override
			public void page(int page) {
				setCurPage(page);
			}
		});
	}



    Handler mainHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);  
			switch (msg.what) {
			case Const.SOCKET_CONNECTING:
				controller_waiting.setVisibility(View.VISIBLE);
				txt_controllerwaiting.setVisibility(View.VISIBLE);		
				break;
			case Const.SOCKET_CONNECTED:
				IsStartActivity = Const.TIME_SERVICE_FAILED;
				if (Launcher.client != null) {
					Launcher.client.setRunningState(false);
				}
				threadPoolManager.startSocketClient();//线程池管理对象在这里生成，永远单例并且不会被kill
				txt_controllerwaiting.setText("连接成功，APP正在为控制器授时...");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SocketOutputTask.getHandler().sendEmptyMessage(Const.TIME);//发两遍会在另一个时间段收到time的一串回执
				SocketOutputTask.getHandler().sendEmptyMessageDelayed(Const.TIME, 500);
				SocketInputTask.getHandler().sendEmptyMessage(Const.SOCKET_CONNECTED);
				SocketHeartbeatTask.getHandler().sendEmptyMessageDelayed(Const.HEARTBEAT, 300000);
				mainHandler.sendEmptyMessageDelayed(Const.TIMEOUT, 8000);
				break;
			case Const.TIME_SERVICE_FINISHED:
				isClickable = true;
				if (Launcher.client != null) {
					Launcher.client.setRunningState(true);
				}
				controller_waiting.setVisibility(View.GONE);
				txt_controllerwaiting.setVisibility(View.GONE);	
				threadPoolManager.startSocketServerAccept();
				IsStartActivity = Const.TIME_SERVICE_FINISHED;
				// 20160417bug修复－每次跳转前都会清空数据库中�?有sensor：设置为offline
				sensorService.modifyAllSensorOffline();
				startActivity(new Intent(Launcher.this, JackFragmentMaster.class));//跳转前等待是否收到stop报文（服务器－APP�?
				break;	
			case Const.TIMEOUT:
				if (Launcher.IsStartActivity == Const.TIME_SERVICE_FAILED) {
					isClickable = true;
					Launcher.client.destroy();
					Launcher.server.destroy();
					/* add by Elsa, 2016/8/27 多次连接失败后再也无法连接的原因:socket已连接，InputTask不能GET到TIME回执
					threadPoolManager.stopSocketClient(); 
					*/
					controller_waiting.setVisibility(View.GONE);
					txt_controllerwaiting.setVisibility(View.GONE);	
					ToastUtil.TextToastShort(Launcher.this, "授时失败，请重新连接");
					Log.d(TAG, "Time Out 连接超时");
				}
				break;	
			case Const.SOCKET_DISCONNECTED:
				isClickable = true;
				IsStartActivity = Const.TIME_SERVICE_FAILED;
				Launcher.client.destroy();
				Launcher.server.destroy();
				controller_waiting.setVisibility(View.GONE);
				txt_controllerwaiting.setVisibility(View.GONE);
				title_waiting.setVisibility(View.GONE);
				ToastUtil.TextToastShort(Launcher.this, "连接失败，请重新尝试");
				break;
			case Const.UI_REFRESH:
				scrollLayout.removeAllViews();
				initControllerList();
				initClassifiedControllers();
				for (int i = 0; i < LauncherViewConfig.countPages; i++) {
					scrollLayout.addView(initGridViewItem(i));
				}
				break;
			case Const.BACK_TO_LAUNCHER:
				Intent intent = new Intent();
				intent.setAction("com.greenhosue.backtolauncheraction");
				sendBroadcast(intent);
				break;
			default:
				break;
	        }
		}
	};
	
	//线程池对象，持有对主线程的引用
	private ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance(mainHandler);
	
	private LinearLayout initGridViewItem(int i) {
		isClickable = true;
		final int ii = i;	
		linearLayout = new LinearLayout(this);		
		dragGridView = new DragGrid(Launcher.this);
		dragGridArray.clear();
		GridItemDataAdapter adapter = new GridItemDataAdapter(Launcher.this, scrollControllersList.get(i));
		dragGridView.setAdapter(adapter);
		dragGridView.setNumColumns(4);
		dragGridView.setHorizontalSpacing(0);
		dragGridView.setVerticalSpacing(0);		
		dragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,	final int arg2, long arg3) {
				if (isClickable) {	
					if(scrollControllersList.get(ii).get(arg2).equals("add")) {
						if (NetworkManager.getNetworkState(Launcher.this) != NetworkManager.NETWORK_WIFI) {								
							ToastUtil.TextToastShort(Launcher.this, "请确认手机wifi是否打开");								
						}else{					
							startActivity(new Intent(Launcher.this, Smartlink.class));
						}
					} else if (scrollControllersList.get(ii).get(arg2).equals("null")){
						
					} else {	
						if (NetworkManager.getNetworkState(Launcher.this) == NetworkManager.NETWORK_NONE) {								
							ToastUtil.TextToastShort(Launcher.this, "请打开手机网络");								
						}else{
							isClickable = false;
							BACK_TO_LAUNCHER = false;
							Integer selectId = ii * LauncherViewConfig.PAGE_SIZE + arg2 + 1;
							selectMac = controllerService.queryMac(selectId);
							client = new SocketClient();
							server = new SocketServer();
							selectIp = controllerService.queryIp(Launcher.selectMac);
							Log.e(TAG, "ip" + selectIp);
							new AsyncSocketReq(mainHandler).execute();
					}
				}					
			}
		}
	});
		
		dragGridView.setSelector(R.anim.grid_light);
		
		dragGridView.setPageListener(new DragGrid.G_PageListener() {
			@Override
			public void page(int cases, int page) {
				switch (cases) {
				case 0:
					scrollLayout.snapToScreen(page);
					setCurPage(page);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							LauncherViewConfig.isChangingPage = false;
						}
					}, 800);
					break;
				case 1:
					deleteBtn.setBackgroundResource(R.drawable.del);
					deleteBtn.setVisibility(0);
					try {
						deleteBtn.startAnimation(up);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					deleteBtn.setBackgroundResource(R.drawable.del_check);
					LauncherViewConfig.isDelDark = true;
					break;
				case 3: 
					deleteBtn.setBackgroundResource(R.drawable.del);
					LauncherViewConfig.isDelDark = false;
					break;
				case 4:
					deleteBtn.startAnimation(down);
					break;
				case 5:
					if(LauncherViewConfig.removeItem <= controllersList.size() - 1){
						int del_count = 0;
						controllerService.deleteController(controllersList.get(LauncherViewConfig.removeItem));
						jackService.deleteAllJack(controllersList.get(LauncherViewConfig.removeItem));
						sensorService.deleteAllSensor(controllersList.get(LauncherViewConfig.removeItem));
						for (del_count = LauncherViewConfig.removeItem; del_count < controllersList.size() - 1; del_count++) {
							controllersList.set(del_count, controllersList.get(del_count + 1));
							scrollControllersList.get(LauncherViewConfig.curentPage).set(del_count,scrollControllersList.get(LauncherViewConfig.curentPage).get(del_count + 1));
						}
						scrollControllersList.get(LauncherViewConfig.curentPage).set(del_count, "add");
						scrollControllersList.get(LauncherViewConfig.curentPage).set(del_count + 1, null);
						controllersList.remove(LauncherViewConfig.removeItem);
						
						mainHandler.sendEmptyMessage(Const.UI_REFRESH);
					}
					deleteBtn.startAnimation(down);
					break;
				}
			}
		});
	
		dragGridView.setOnItemChangeListener(new DragGrid.G_ItemChangeListener() {
			@Override
			public void change(int from, int to, int count) {
				String controller =  scrollControllersList.get(LauncherViewConfig.curentPage - count).get(from);	
				scrollControllersList.get(LauncherViewConfig.curentPage - count).add(from,scrollControllersList.get(LauncherViewConfig.curentPage).get(to));
				scrollControllersList.get(LauncherViewConfig.curentPage - count).remove(from + 1);
				scrollControllersList.get(LauncherViewConfig.curentPage).add(to, controller);
				scrollControllersList.get(LauncherViewConfig.curentPage).remove(to + 1);	
				((GridItemDataAdapter) ((dragGridArray.get(LauncherViewConfig.curentPage - count)).getAdapter())).notifyDataSetChanged();
				((GridItemDataAdapter) ((dragGridArray.get(LauncherViewConfig.curentPage)).getAdapter())).notifyDataSetChanged();
			}
		});
		
		param = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		param.rightMargin = 100;
		param.leftMargin = 50;
				
		if (dragGridArray.size() != 0) { //mGridViewArray第二遍没有初始化
			ViewGroup parent = (ViewGroup) dragGridArray.get(i).getParent();			
			if (parent != null) {
				parent.removeAllViews();
			} 
		}
		
		dragGridArray.add(dragGridView);
		linearLayout.addView(dragGridArray.get(i), param);		
		
		return linearLayout;
	}

	public void initSlideMenu() {
		drawerLayuout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerLayuout.setDrawerListener(mDrawerToggle);
		this.listView = ((ListView) findViewById(R.id.slide_menu));
		this.listView.setAdapter(new SlideMenuAdapter(this));
		this.listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					startActivity(new Intent(Launcher.this, Smartlink.class));
					break;
				// Test UI entry
				case 1:
					client = new SocketClient();
					Launcher.client.setTerminalType(2);
					selectMac = controllerService.queryMac(1);
					startActivity(new Intent(Launcher.this, JackFragmentMaster.class));			
					break;
				case 2:
					startActivity(new Intent(Launcher.this, About.class));
					break;
				case 3:
					break;
				}			
			}
		});

		ImageView localImageView = (ImageView) findViewById(R.id.title_btn);
		localImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				drawerLayuout.openDrawer(Gravity.LEFT);
			}
		});
	}

	
	SensorManager sm;
	
	SensorEventListener lsn;
	
	boolean isClean = false;
	
	Vibrator vibrator;
	
	int rockCount = 0;
	
	private void initSensor() {
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {
				if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					if (!isClean && rockCount >= 10) {
						isClean = true;
						rockCount = 0;
						vibrator.vibrate(100);
						return;
					}
					float newX = e.values[SensorManager.DATA_X];
					float newY = e.values[SensorManager.DATA_Y];
					float newZ = e.values[SensorManager.DATA_Z];
					if ((newX >= 18 || newY >= 20 || newZ >= 20) && rockCount % 2 == 0) {
						rockCount++;
						return;
					}
					if ((newX <= -18 || newY <= -20 || newZ <= -20) && rockCount % 2 == 1) {
						rockCount++;
						return;
					}
				}
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
			}
		};
		sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);		
	}

	private TranslateAnimation left, right;
	
	private Animation up, down;
	
	public void runAnimation() {
		down = AnimationUtils.loadAnimation(Launcher.this, R.anim.del_down);
		up = AnimationUtils.loadAnimation(Launcher.this, R.anim.del_up);
		down.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				deleteBtn.setVisibility(8);
			}
		});

		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(25000);
		left.setDuration(25000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(left);
			}
		});
		
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(right);
			}
		});
	}

	public void setCurPage(final int page) {
		Animation a = AnimationUtils.loadAnimation(Launcher.this, R.anim.scale_in);
		a.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				pageNumber.setText((page + 1) + "");
				pageNumber.startAnimation(AnimationUtils.loadAnimation(Launcher.this, R.anim.scale_out));
			}
		});
		pageNumber.startAnimation(a);
	}

	// 20160305 有时间把刷新界面时间的部分加上，比如用while ture �? handler �? msg
	private void refreshHeavenView() {
		HeavenAnimateView localHeavenAnimateView = (HeavenAnimateView) findViewById(R.id.heaven);
		if (localHeavenAnimateView != null) {
			localHeavenAnimateView.update();
			localHeavenAnimateView.postInvalidate();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(GreenHouseApplication.getSocketConnectionFlag() != 0){
				
				new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("确认退出应用？")
				.setNegativeButton("否",	new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
					}
				})
				.setPositiveButton("是",	new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog,
							int whichButton) {						
					}
				}).show();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	protected void onRestart() {
		// TODO Auto-generated method stub
		if (Launcher.client != null) {
			Launcher.client.setRunningState(false);
		}
		mainHandler.sendEmptyMessage(Const.UI_REFRESH);
		Log.e(TAG,"[onRestart]");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG,"[onResume]");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.e(TAG, "[onPause]");
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.e(TAG, "[onStop]");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub	
		threadPoolManager.releaseInstance();
		threadPoolManager = null;
		mainHandler.removeCallbacksAndMessages(null);
		super.onDestroy();	
	}

	public final static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
