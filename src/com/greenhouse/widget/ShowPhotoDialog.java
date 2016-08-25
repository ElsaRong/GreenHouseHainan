package com.greenhouse.widget;

import java.io.File;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.greenhouse.R;
import com.greenhouse.util.ToastUtil;



/**
 * 弹出选择照片dialog
 * 
 * @author 弹出选取照片dialog 基本步骤---
 * 
 * 
 *         1.照相:首先调用ToastUtil.getPicFile()获取到图片文件保存路�????, new
 *         �????个dialog(这个dialog的取消不用管�????,点击各个部位能自动消�????);
 *         然后在onActivityResult中code值为TAKEPHOTO_CODE ,并用getFileSizes(File
 *         f)方法判断file的大小是否大�????0,如果符合条件,这个file就是图片文件
 * 
 *         2.取图:在onActivityResult中判断data不为空code=CHOOSEPHOTO_CODE ,调用
 *         ToastUtil.getPicPath(Intent data, Context mcontext)方法,获取到图片保存路�???? // *
 */
public class ShowPhotoDialog extends Dialog implements android.view.View.OnClickListener {
    // Context context;
    Activity context;
    File file;
    public static final int CHOOSEPHOTO_CODE = 10003;
    public static final int TAKEPHOTO_CODE = 10002;
    public static final int TAKEPHOTO_CROP = 10004;

    public ShowPhotoDialog(Context context) {
        super(context);
    }

    public ShowPhotoDialog(int style, File file, Activity context) {
        super(context, style);
        this.context = context;
        this.file = file;
        // this.act=act;
        init();
    }

    private void init() {
        View contentView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.jackimage_dialog, null);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.x = 0;
        localLayoutParams.y = -500;
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.horizontalMargin = 0;
        contentView.setMinimumWidth(10000);
        setContentView(contentView);
        Button album_get = (Button) contentView.findViewById(R.id.albumget_btn);
        Button camera_get = (Button) contentView.findViewById(R.id.cameraget_btn);
        Button info_btn = (Button) contentView.findViewById(R.id.info_btn);
        info_btn.setVisibility(View.VISIBLE);
        info_btn.setText("取消");
        album_get.setText("从手机相册�?�择");
        camera_get.setText("拍照");
        camera_get.setTag(file);
        // 调用相册
        album_get.setOnClickListener(this);
        // 调用照相�?????
        camera_get.setOnClickListener(this);
        // 取消按钮
        info_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
        case R.id.albumget_btn:
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            context.startActivityForResult(intent, CHOOSEPHOTO_CODE);
            dismiss();
            break;
        case R.id.cameraget_btn:
            if (hasSdCard()) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = (File) v.getTag();
                Uri mOutPutFileUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
                context.startActivityForResult(intent,TAKEPHOTO_CODE);
                dismiss();
            } else {
            	ToastUtil.TextToastShort(context, "手机内没有sd�??");
            }
            break;  
        case R.id.info_btn:
            dismiss();
            break;
        default:
            break;
        }
    }

    public void crop(Bitmap data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.putExtra("data", data);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        context.startActivityForResult(intent, TAKEPHOTO_CROP);
    }
    // SD卡是否存�????
    public static boolean hasSdCard() {
        // System.out.println(android.os.Build.MODEL);
        String status = Environment.getExternalStorageState();
        // && !"Nexus S".equals(android.os.Build.MODEL
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
