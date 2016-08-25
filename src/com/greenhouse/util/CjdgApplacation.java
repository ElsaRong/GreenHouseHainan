package com.greenhouse.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;




public class CjdgApplacation extends Application {

	public static String rootPath;
	private static CjdgApplacation application;

	private static List<Activity> list = new ArrayList<Activity>();

	public static final String APP_SECRET = "X0h0XUMJD6gCN";

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}


	public static String getRootPath() {
		return rootPath;
	}

	private void init() {
		File rootDir = Environment.getExternalStorageDirectory();
		rootPath = rootDir.getAbsolutePath() + "/SuperShoper/";
		File f = new File(rootPath + "video");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "db");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "temp");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "cache");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "photos");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "cache/pic");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "cache/video");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "cache/audio");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "log");
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(rootPath + "headPic");
		if (!f.exists()) {
			f.mkdirs();
		}

	}

	public static String getLogPath() {
		return rootPath + "log/";
	}

	public static String getCache() {
		return rootPath + "cache/";
	}

	public static String getHeadCache() {
		return rootPath + "headPic/";
	}

	/**
	 * 
	 * @return
	 */
	public static CjdgApplacation getApplication() {
		return application;
	}

	/**
	 * 
	 * @return
	 */
	public static String getAudioCache() {
		return rootPath + "cache/audio";
	}

	/**
	 * 
	 * @return
	 */
	public static String getVideoCache() {
		return rootPath + "cache/video";
	}

	/**
	 * 
	 * @return
	 */
	public static String getPicCache() {
		return rootPath + "cache/pic";
	}

	/**
	 * 
	 * @return
	 */
	public static String getPhotos() {
		return rootPath + "photos";
	}

	public static String getDB() {
		return rootPath + "db";
	}

	public static void addActivity(Activity activity) {
		list.add(activity);
	}

	public static void exitActivity(Activity activity) {
		list.remove(activity);
	}

}
