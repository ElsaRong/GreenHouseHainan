package com.greenhouse.animation;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {


	public static Animation inFromRight() {
		Animation inFRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFRight.setDuration(500);
		inFRight.setInterpolator(new AccelerateInterpolator());
		return inFRight;
	}

	public static Animation inFromLeft() {
		Animation inFLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFLeft.setDuration(500);
		inFLeft.setInterpolator(new AccelerateInterpolator());
		return inFLeft;
	}


	public static Animation outToLeft() {
		Animation outToLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outToLeft.setDuration(500);
		outToLeft.setInterpolator(new AccelerateInterpolator());
		return outToLeft;
	}


	public static Animation outToRight() {
		Animation outTRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outTRight.setDuration(500);
		outTRight.setInterpolator(new AccelerateInterpolator());
		return outTRight;
	}

}