package com.greenhouse.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

public class OptionButton
  extends CompoundButton
{
  private boolean mCancelable = true;
  
  public OptionButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public OptionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public OptionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
//  public void setCancelable(boolean paramBoolean)
//  {
//    this.mCancelable = paramBoolean;
//  }
  
//  public void toggle()
//  {
//    if ((!this.mCancelable) && (isChecked())) {
//      return;
//    }
//    super.toggle();
//  }
  
  
}
