package com.greenhouse.animation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.greenhouse.R;


public class HeavenAnimateView extends View
{
  private static final int DEFAULT_PADDING = 4;
  private static final int TEXT_SIZE = 16;
  private static final String TIME_FORMAT = "hh:mma";
  private static final int TRACE_WIDTH = 1;
  private boolean isDaytime;
  private int mDrawablePadding;
  private Drawable mMoonDrawable;
  private RectF mOval = new RectF();
  private int mStartDegree;
  private Drawable mSunDrawable;
  private int mSweepDegree;
  private Paint mTextPaint = new Paint();
  private String mTimeText;
  private Paint mTracePaint = new Paint();
  private int mTraceRadius;

  public HeavenAnimateView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public HeavenAnimateView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private void init()
  {
    Resources localResources = getResources();
    float f = localResources.getDisplayMetrics().density;
    this.mSunDrawable = localResources.getDrawable(R.drawable.sun);
    this.mMoonDrawable = localResources.getDrawable(R.drawable.moon);
    this.mDrawablePadding = (int)(4.0F * f);
    this.mTracePaint.setStyle(Paint.Style.STROKE);
    this.mTracePaint.setAntiAlias(true);
    this.mTracePaint.setColor(-1);
    this.mTracePaint.setStrokeWidth(1.0F * f);
    this.mTextPaint.setTextAlign(Paint.Align.CENTER);
    this.mTextPaint.setAntiAlias(true);
    this.mTextPaint.setTypeface(Typeface.SERIF);
    this.mTextPaint.setColor(-1);
    this.mTextPaint.setTextSize(16.0F * f);
    update();
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    update();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.isDaytime);
    for (Drawable localDrawable = this.mSunDrawable; ; localDrawable = this.mMoonDrawable)
    {
      localDrawable.draw(paramCanvas);
      paramCanvas.drawArc(this.mOval, this.mStartDegree, this.mSweepDegree, false, this.mTracePaint);
      int i = getWidth() / 2;
      int j = getHeight() - getPaddingBottom() - this.mTextPaint.getFontMetricsInt().bottom;
      paramCanvas.drawText(this.mTimeText, i, j, this.mTextPaint);
      return;
    }
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    int i = (paramInt1 - this.mSunDrawable.getIntrinsicWidth()) / 2;
    int j = getPaddingTop();
    this.mSunDrawable.setBounds(i, j, i + this.mSunDrawable.getIntrinsicWidth(), j + this.mSunDrawable.getIntrinsicHeight());
    int k = (paramInt1 - this.mMoonDrawable.getIntrinsicWidth()) / 2;
    this.mMoonDrawable.setBounds(k, j, k + this.mMoonDrawable.getIntrinsicWidth(), j + this.mMoonDrawable.getIntrinsicHeight());
    int l = j + Math.max(this.mSunDrawable.getIntrinsicHeight(), this.mMoonDrawable.getIntrinsicHeight()) + this.mDrawablePadding;
    if (l < paramInt2 / 2)
      l = paramInt2 / 2;
    int i1 = paramInt2 - getPaddingBottom() - l;
    int i2 = (paramInt1 - getPaddingLeft() - getPaddingRight()) / 2;
    int i3 = (i2 * i2 + i1 * i1) / i1 / 2;
    this.mOval.set(i2 + getPaddingLeft() - i3, l, i3 + (i2 + getPaddingLeft()), l + i3 * 2);
    int i4 = (int)(180.0D * (Math.asin(1.0D * i2 / i3) / 3.141592653589793D));
    this.mStartDegree = (270 - i4);
    this.mSweepDegree = (i4 * 2);
    this.mTraceRadius = i3;
  }

  public void update()
  {
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(11);
    if ((i >= 6) && (i < 18));
    for (boolean j = true; ; j = false)
    {
      this.isDaytime = false;
      this.mTimeText = new SimpleDateFormat("hh:mm:ss", Locale.US).format(localCalendar.getTime());
      return;
    }
  }
}
