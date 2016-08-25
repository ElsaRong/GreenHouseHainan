package com.greenhouse.util;

import java.io.File;
import java.io.FileInputStream;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


/**
 *  toast��ʾ��͹�����
 */
public class ToastUtil {

    private static Toast mToast = null;
    private static int LENGTH_LONG = Toast.LENGTH_LONG;
    private static int LENGTH_SHORT = Toast.LENGTH_SHORT;    
    

    public static void toast(Context context, String text, int duration) {
        // ��ֹtoast�Ŷ���ʾ
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
   
	
	/**
	 * ��ͨ�ı���Ϣ��ʱ����ʾ
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void TextToastLong(Context context,CharSequence text){
		mToast = Toast.makeText(context, text, LENGTH_LONG);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
	}
	
	/**
	 * ��ͨ�ı���Ϣ��ʱ����ʾ
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void TextToastShort(Context context,CharSequence text){
		mToast = Toast.makeText(context, text, LENGTH_SHORT);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
	}
    
    
    
    
    
    
    // ��������ͼ��ַ,��ȡͼƬ·��
    public static String getPicPath(Intent data, Context mcontext) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = mcontext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    // ��ȡ����ʱ��Ƭ����·��
    public static File getPicFile() {
        String path = CjdgApplacation.getPhotos();
        File path1 = new File(path);
        if (!path1.exists()) {
            path1.mkdirs();
        }
        File file = new File(path1, System.currentTimeMillis() + ".jpg");
        return file;
    }

    // ��ȡ�ļ���С
    public static long getFileSizes(File f) {// ȡ���ļ���С
        long s = 0;
        try {
            if (f.exists()) {
                FileInputStream fis = null;

                fis = new FileInputStream(f);

                s = fis.available();
            } else {
                f.createNewFile();
                System.out.println("�ļ�������");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    // ��ͼƬ�������Ŵ���,��ֹ�ڴ����
    public static Bitmap getBitmap(String path) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = 2;//2
        // options.inSampleSize = computeSampleSize(options, -1, 128 * 128);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile(path, options);
    }

//    // ����ͼƬ���ű���
//    private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
//        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
//        int roundedSize;
//        if (initialSize <= 8) {
//            roundedSize = 1;
//            while (roundedSize < initialSize) {
//                roundedSize <<= 1;
//            }
//        } else {
//            roundedSize = (initialSize + 7) / 8 * 8;
//        }
//        return roundedSize;
//    }
//
//    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
//        double w = options.outWidth;
//        double h = options.outHeight;
//
//        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
//        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
//                Math.floor(h / minSideLength));
//        if (upperBound < lowerBound) {
//            // return the larger one when there is no overlapping zone.
//            return lowerBound;
//        }
//        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
//            return 1;
//        } else if (minSideLength == -1) {
//            return lowerBound;
//        } else {
//            return upperBound;
//        }
//    }
}
