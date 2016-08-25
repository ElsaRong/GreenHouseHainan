package com.greenhouse.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.greenhouse.R;
import com.greenhouse.util.BitmapManager;
import com.greenhouse.util.ToastUtil;
import com.greenhouse.widget.ShowPhotoDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChangeJackImage extends Activity implements OnClickListener {

	// 拍照和选择图片
	public static final int CHOOSEPHOTO_CODE = 10003;
	public static final int TAKEPHOTO_CODE = 10002;
	public static final int TAKEPHOTO_CROP = 10004;
	GridView gridView;
	private List<String> image_list = new ArrayList<String>();
	private Dialog dialog;
	private File img_crop;
	// 定义路径
	private String mImgPaths;
	String report[] = new String[] { "替换", "删除" };
	public static boolean image = false;// 控制替换照片
	private String image_path;// 替换的路径
	private int index_image = 0;// 提交位置
	int index = -1;
	int inte = 0;
	JackChangeImageAdapter adapter;
	Options options;
	
	int[] btmp78 = { R.drawable.default_jack, R.drawable.gray_air_conditioning,
			R.drawable.gray_co2, R.drawable.gray_fan, R.drawable.gray_fanner,
			R.drawable.gray_hps, R.drawable.gray_led, R.drawable.gray_mh,
			R.drawable.gray_water_pump, R.drawable.green_bulb };
	int[] btmp9 = { R.drawable.gray_air_conditioning,
			R.drawable.gray_co2, R.drawable.gray_fan, R.drawable.gray_fanner,
			R.drawable.gray_hps, R.drawable.gray_led, R.drawable.gray_mh,
			R.drawable.gray_water_pump, R.drawable.green_bulb };
	int[] btmp10 = { R.drawable.default_jack, R.drawable.gray_air_conditioning,
			R.drawable.gray_co2, R.drawable.gray_fan, R.drawable.gray_fanner,
			R.drawable.gray_hps, R.drawable.gray_led, R.drawable.gray_mh,
			R.drawable.gray_water_pump, R.drawable.green_bulb };
	String[] jackChangeImageTxt = {"220V插座","240V插座","空调","CO2发生器","大风扇","小风扇","HPS灯","LED灯","MH灯","风机","灯"};
	int btmp[] ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jackimage_gridview);		
		gridView = (GridView) findViewById(R.id.gr_ter);		
		btmp = btmp78;	
		
//		adapter = new JackChangeImageAdapter();
//		gridView.setAdapter(adapter);
		
		ImageView localImageView = (ImageView)findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back );
		localImageView.setOnClickListener(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKEPHOTO_CODE) {
			if (ToastUtil.getFileSizes(img_crop) > 0) {
				options = new Options();
				options.inJustDecodeBounds = true;
				System.out.println("这个路径 = " + img_crop.toString());
				BitmapFactory.decodeFile(img_crop.getAbsolutePath(), options);
				options.inJustDecodeBounds = false;
				int be = (int) (options.outHeight / (float) 200);
				if (be <= 0) {
					be = 1;
				}
				options.inSampleSize = be;
				if (image) {
					image = false;
					image_path = img_crop.getAbsolutePath();
					image_list.remove(index_image);
					image_list.add(index_image, image_path);
				} else {
					image_list.add(img_crop.getAbsolutePath());
					// 显示在控件上
				}
				adapter.notifyDataSetChanged();
			}
		} else if (requestCode == CHOOSEPHOTO_CODE && data != null) {
			inte++;
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = ChangeJackImage.this.getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			mImgPaths = cursor.getString(columnIndex);
			img_crop = new File(mImgPaths);
			System.out.println("下面这个路径 = " + img_crop.getPath());
			options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(mImgPaths, options);
			options.inJustDecodeBounds = false;
			int be = (int) (options.outHeight / (float) 200);
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			if (image) {
				// 得到路径
				image = false;
				image_path = img_crop.getAbsolutePath();
				image_list.remove(index_image);
				image_list.add(index_image, image_path);
			} else {
				image_list.add(mImgPaths);
			}
			adapter.notifyDataSetChanged();
		}
	}


	private class JackChangeImageAdapter extends BaseAdapter {
		LayoutInflater infater = null;
		int in;

		public JackChangeImageAdapter() {
			infater = (LayoutInflater) ChangeJackImage.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return image_list.size() + btmp.length+1;
		}

		@Override
		public Object getItem(int position) {
			return image_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertview, ViewGroup parent) {
			ViewHolder holder;
			View view;
			if (convertview == null) {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				LayoutInflater inflater = LayoutInflater.from(ChangeJackImage.this);
				view = infater.inflate(R.layout.jackimage_gridview_item, null);
				view.setLayoutParams(lp);
				holder = new ViewHolder(view);
				holder.jackChangeImage = (ImageView)view.findViewById(R.id.jack_change_image);
				holder.jackChangeImageTxt = (TextView)view.findViewById(R.id.jack_change_image_txt);
				view.setTag(holder);
				
				
				view = infater.inflate(R.layout.jackimage_gridview_item, null);
				holder = new ViewHolder(view);
				view.setTag(holder);

			} else {
				view = convertview;
				holder = (ViewHolder) convertview.getTag();
			}
			
			
//			if (position >= btmp.length) {
//				int index = position - btmp.length;// 得到大于出来的下标。就是集合的索引
//				if (index == image_list.size()) {
//					holder.jackChangeImage.setScaleType(ScaleType.FIT_XY);
//				} else {
//					holder.jackChangeImage.setScaleType(ScaleType.CENTER_CROP);
//					String imag = image_list.get(index);
//					BitmapManager.getFinalBitmap(ChangeJackImage.this).display(holder.jackChangeImage, imag);
//				}
//			} else {
//				holder.jackChangeImage.setImageResource(btmp[position]);
//			}
		
			holder.jackChangeImageTxt.setText(jackChangeImageTxt[position]);
			holder.jackChangeImage.setTag(position);
			holder.jackChangeImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					in = (Integer) v.getTag()-btmp.length;
					if (in>= image_list.size()) {
						index = in;
					} else 
						if(position<=btmp.length){
						Intent intent = new Intent();
						intent.setClass(ChangeJackImage.this, EditJack.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);

					}

				}
			});
			return view;
		}
	}

	class ViewHolder {
		ImageView jackChangeImage;
		TextView jackChangeImageTxt;

		@SuppressLint("CutPasteId")
		public ViewHolder(View view) {
			jackChangeImage = (ImageView)view.findViewById(R.id.jack_change_image);
			jackChangeImageTxt = (TextView)view.findViewById(R.id.jack_change_image_txt);
		}
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.title_btn:
			this.finish();
			onBackPressed();
			return;
		default:
			return;
		}
	}
	
	
}





















