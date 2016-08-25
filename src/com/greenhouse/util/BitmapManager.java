package com.greenhouse.util;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import com.greenhouse.R;

import android.content.Context;

public class BitmapManager {
    private static FinalBitmap fb;
    private static FinalDb taskDb;
    // private FinalDb coursekDb ;
    private static FinalDb messageDb;
    private static FinalDb userDb;


    public static FinalBitmap getFinalBitmap(Context context) {
        if (fb == null) {
            fb = FinalBitmap.create(context);
            fb.configBitmapLoadThreadSize(5);
            fb.configDiskCachePath(CjdgApplacation.getPicCache());
            fb.configDiskCacheSize(1024 * 1024 * 60);//60
            fb.configLoadfailImage(R.drawable.common_image_load_faild);
            fb.configLoadingImage(R.drawable.common_image_loading);
        }
        return fb;
    }
}
