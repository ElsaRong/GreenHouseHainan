package com.greenhouse.viewconfig;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.greenhouse.R;
import com.greenhouse.mvadpater.GridItemSwitchTestAdapter;
import com.greenhouse.ui.Launcher;

public class DragGrid extends GridView {

	private int dragPosition;
	private int dropPosition;
	
	//private ImageView dragImageView;
	
	ViewGroup fromView;
	Animation AtoB,BtoA,DelDone;
	int stopCount=0;
	private G_PageListener pageListener;
	int moveNum;
	private G_ItemChangeListener itemListener;
	
	private int mLastX,xtox;
	boolean isCountXY=false;
	private int mLastY,ytoy;
	
	// 用来做item的悬浮窗口和删除之类
	private WindowManager windowManager;
	private WindowManager.LayoutParams windowParams;

//	private int itemHeight, itemWidth;
	private ImageView iv_drag;

	public DragGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DragGrid(Context context) {
		super(context);
	}

	boolean flag = false;

	public void setLongFlag(boolean temp) {
		flag = temp;
	}

	public boolean setOnItemLongClickListener(final MotionEvent ev) {
			
			this.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					LauncherViewConfig.isMove = true;
					int x = (int) ev.getX();
					int y = (int) ev.getY();
					mLastX=x;
					mLastY=y;
					dragPosition = dropPosition = arg2;
					
					if (dragPosition == AdapterView.INVALID_POSITION) {
						return false;
					}
					fromView = (ViewGroup) getChildAt(dragPosition
							- getFirstVisiblePosition());
					//	itemHeight = fromView.getHeight();
					//	itemWidth = fromView.getWidth();
					fromView.destroyDrawingCache();
					fromView.setDrawingCacheEnabled(true);
					// 长按item后选定区域的颜色设置（背景渲染）
//					fromView.setDrawingCacheBackgroundColor(R.color.title_bar_background);
					// android位图：让item可以移动的功能
					Bitmap bm = Bitmap.createBitmap(fromView.getDrawingCache());//				
					Bitmap bitmap = Bitmap.createBitmap(bm, 8, 8, bm.getWidth()-16, bm.getHeight()-8);
					startDrag(bitmap, x, y);
					return false;
				};
			});
		
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			return setOnItemLongClickListener(ev);
		}
		return super.onInterceptTouchEvent(ev);
	}

	private void startDrag(final Bitmap bm, final int x, final int y) {
		if(Launcher.isClickable){
		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);// "window"
		Animation disappear= AnimationUtils.loadAnimation(getContext(), R.anim.out);
		disappear.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				fromView.setVisibility(8);
				stopDrag();
				windowParams = new WindowManager.LayoutParams();
				windowParams.gravity = Gravity.TOP | Gravity.LEFT;
				// 长按item后的悬浮距离28
				windowParams.x = fromView.getLeft()+28;
//				windowParams.y = fromView.getTop()+(int)(40*Configure.screenDensity)+8;
				//长按后向下移动
				windowParams.y = fromView.getTop()+(int)(40*LauncherViewConfig.screenDensity)+25;
				windowParams.alpha = 0.8f;
				windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
				windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

				iv_drag = new ImageView(getContext());
				iv_drag.setImageBitmap(bm);
				windowManager.addView(iv_drag, windowParams);
				iv_drag.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.del_done));
				//dragImageView = iv_drag;
			}
		});
		fromView.startAnimation(disappear);
		pageListener.page(1,-100);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (iv_drag != null
				&& dragPosition != AdapterView.INVALID_POSITION) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if(!isCountXY) {xtox = x-mLastX;ytoy = y-mLastY;isCountXY= true;}
				onDrag(x, y);
				break;
			case MotionEvent.ACTION_UP:
				stopDrag();
				onDrop(x, y);
//				onDrop(mLastX, mLastY);
				break;
			}
		//	mLastX=x;mLastY=y;
		}
		return super.onTouchEvent(ev);
	}

	private void onDrag(int x, int y) {
		if (iv_drag != null) {
			// 长按item后选定区域到触摸点的距离
			windowParams.alpha = 0.8f;
			windowParams.x = (x-mLastX-xtox)+fromView.getLeft()+28- moveNum*LauncherViewConfig.screenWidth;
			windowParams.y = (y-mLastY-ytoy)+fromView.getTop()+(int)(40*LauncherViewConfig.screenDensity)+25;
			//windowParams.x = x - itemWidth / 2 - moveNum*Configure.screenWidth;
			//windowParams.y = y - itemHeight / 2;
			windowManager.updateViewLayout(iv_drag, windowParams);
			//iv_drag.setPadding((x-mLastX-xtox)+fromView.getLeft()+28- moveNum*Configure.screenWidth, (y-mLastY-ytoy)+fromView.getTop()+(int)(40*Configure.screenDensity)+8, 
			//		Configure.screenWidth-(x-mLastX-xtox)+fromView.getLeft()+28- moveNum*Configure.screenWidth+16, Configure.screenHeight-((y-mLastY-ytoy)+fromView.getTop()+(int)(40*Configure.screenDensity)+8)-itemHeight+16);
		}
		if((x>(LauncherViewConfig.screenWidth/2 - 100) && x<(LauncherViewConfig.screenWidth/2+100)) && (y>LauncherViewConfig.screenHeight-200)){
			pageListener.page(2,-100);
			return;
		}
		if(LauncherViewConfig.isDelDark){
			pageListener.page(3,-200);
		}
		if(moveNum>0){
			if((x>=(moveNum+1)*LauncherViewConfig.screenWidth-20*LauncherViewConfig.screenDensity || x<=moveNum*LauncherViewConfig.screenWidth)&&
					!LauncherViewConfig.isChangingPage) stopCount++;
			else	stopCount=0;
			if(stopCount>10){
				stopCount=0;
				if(x>=(moveNum+1)*LauncherViewConfig.screenWidth-20*LauncherViewConfig.screenDensity && LauncherViewConfig.curentPage<LauncherViewConfig.countPages-1){
					LauncherViewConfig.isChangingPage=true;pageListener.page(0,++LauncherViewConfig.curentPage);
					moveNum++;
				}
				else if(x<=moveNum*LauncherViewConfig.screenWidth && LauncherViewConfig.curentPage>0) {
					LauncherViewConfig.isChangingPage=true;pageListener.page(0,--LauncherViewConfig.curentPage);
					moveNum--;
				}
			}
		}else {
			if((x>=(moveNum+1)*LauncherViewConfig.screenWidth-20*LauncherViewConfig.screenDensity || x<=moveNum*LauncherViewConfig.screenWidth)&&
					!LauncherViewConfig.isChangingPage) stopCount++;
			else	stopCount=0;
			if(stopCount>10){
				stopCount=0;
				if(x>=(moveNum+1)*LauncherViewConfig.screenWidth-20*LauncherViewConfig.screenDensity && LauncherViewConfig.curentPage<LauncherViewConfig.countPages-1){
					LauncherViewConfig.isChangingPage=true;pageListener.page(0,++LauncherViewConfig.curentPage);
					moveNum++;
				}
				else if(x<=moveNum*LauncherViewConfig.screenWidth && LauncherViewConfig.curentPage>0) {
					LauncherViewConfig.isChangingPage=true;pageListener.page(0,--LauncherViewConfig.curentPage);
					moveNum--;
				}
			}
		}
	}

	public void setPageListener(G_PageListener pageListener) {
		this.pageListener = pageListener;
	}

	public interface G_PageListener {
		void page(int cases,int page);
	}
	
	public void setOnItemChangeListener(G_ItemChangeListener pageListener) {
		this.itemListener = pageListener;
	}

	public interface G_ItemChangeListener {
		void change(int from,int to,int count);
	}

	// item间交换位置的动画效果
	private void onDrop(int x, int y) {
		fromView.setDrawingCacheBackgroundColor(0);
		LauncherViewConfig.isMove = false;
		if(LauncherViewConfig.isDelDark){
			DelDone = getDelAnimation(x,y);//AnimationUtils.loadAnimation(getContext(), R.anim.del_done);
			DelDone.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					LauncherViewConfig.removeItem = dragPosition;
					pageListener.page(5,-300);
				}
			});
			fromView.setVisibility(0);
			fromView.startAnimation(DelDone);
			return;
		}
		pageListener.page(4,-300);
		int tempPosition = pointToPosition(x- moveNum*LauncherViewConfig.screenWidth, y);
		if (tempPosition != AdapterView.INVALID_POSITION) {
			dropPosition = tempPosition;
		}
		if(moveNum!=0){
			itemListener.change(dragPosition,dropPosition,moveNum);
			moveNum=0;
			return;
		}
		moveNum=0;
		ViewGroup toView = (ViewGroup) getChildAt(dropPosition	- getFirstVisiblePosition());
		final GridItemSwitchTestAdapter adapter = (GridItemSwitchTestAdapter) this.getAdapter();
		if(dragPosition%2 == 0){
			AtoB = getDownAnimation((dropPosition%2==dragPosition%2)?0:1,(dropPosition/2-dragPosition/2));
			if (dropPosition != dragPosition) toView.startAnimation(getMyAnimation((dragPosition%2==dropPosition%2)?0:-1,(dragPosition/2-dropPosition/2)));
		}	else{
			AtoB= getDownAnimation((dropPosition%2==dragPosition%2)?0:-1,(dropPosition/2-dragPosition/2));
			if (dropPosition != dragPosition) toView.startAnimation(getMyAnimation((dragPosition%2==dropPosition%2)?0:1,(dragPosition/2-dropPosition/2)));
		}
		fromView.startAnimation(AtoB);
		AtoB.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {}
			@Override
			public void onAnimationRepeat(Animation arg0) {}
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
//				adapter.exchange(dragPosition, dropPosition);
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void stopDrag() {
		if (iv_drag != null) {
			windowManager.removeView(iv_drag);
			iv_drag = null;
		}
	}
	
	// 设置item渲染的？
	public Animation getMyAnimation(float x,float y){
		TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, x, 
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, y);
		go.setFillAfter(true);
		go.setDuration(550);
		return go;
	}
	
	// 设置item渲染的？	
	public Animation getDownAnimation(float x,float y){
		AnimationSet set = new AnimationSet(true);
		TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, x, 
				Animation.RELATIVE_TO_SELF, y, Animation.RELATIVE_TO_SELF, y);
		go.setFillAfter(true);go.setDuration(550);
		
		AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);alpha.setFillAfter(true);alpha.setDuration(550);
		
		ScaleAnimation scale = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f);
		scale.setFillAfter(true);scale.setDuration(550);
		
		set.addAnimation(go);set.addAnimation(alpha);set.addAnimation(scale);
		return set;
	}
	
	// 设置item渲染的？
	public Animation getDelAnimation(int x,int y){
		AnimationSet set = new AnimationSet(true);
		//TranslateAnimation go = new TranslateAnimation(Animation.ABSOLUTE, x-itemWidth/2, Animation.ABSOLUTE, x-itemWidth/2, 
		//		Animation.ABSOLUTE, y-itemHeight/2, Animation.ABSOLUTE, y-itemHeight/2);
		//go.setFillAfter(true);go.setDuration(1550);
		RotateAnimation rotate = new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		rotate.setFillAfter(true);rotate.setDuration(550);
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);alpha.setFillAfter(true);alpha.setDuration(550);
		
	//	ScaleAnimation scale = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
	//	scale.setFillAfter(true);scale.setDuration(550);
		
		//set.addAnimation(rotate);
		set.addAnimation(alpha);
		set.addAnimation(rotate);
		return set;
	}
}















