package com.greenhouse.viewconfig;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class LauncherViewConfig{
	
	public static int PAGE_SIZE = 8;
	
	public static boolean isMove=false;
	public static boolean isChangingPage=false;
	public static boolean isDelDark = false;
	public static int screenHeight=0;
	public static int screenWidth=0;
	public static float screenDensity=0;
	
	public static int curentPage=0;
	public static int countPages=0;
	public static int removeItem=0;
	
	public static void init(Activity context) {
		if(screenDensity==0||screenWidth==0||screenHeight==0){				
			DisplayMetrics dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			LauncherViewConfig.screenDensity = dm.density;
			LauncherViewConfig.screenHeight = dm.heightPixels;
			LauncherViewConfig.screenWidth = dm.widthPixels;
		}
		curentPage=0;
		countPages=0;
	}
	

	public int[] ret(int[] intArray) {
		int size = intArray.length;
		for (int i =size - 1; i >= 0; i--)
			for (int j = 0; j < i; j++)
				if (intArray[j] > intArray[j + 1]) {
					int t = intArray[j];
					intArray[j] = intArray[j + 1];
					intArray[j + 1] = t;
				}
		return intArray;
	}
}