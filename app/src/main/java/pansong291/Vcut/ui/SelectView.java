package pansong291.Vcut.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SelectView extends ImageView
{
 private Context mct;
 private Paint mPaint;
 private int color;
 private boolean FirstCr=true;
 //线条的位置
 private float csy;
 //记录初始高宽
 private float h,w;
 //默认构造函数
 public SelectView(Context context)
 {
  super(context);
  mct=context;
  mPaint=new Paint();
 }
 //该构造方法在静态引入XML文件中是必须的
 public SelectView(Context context,AttributeSet paramAttributeSet)
 {
  super(context,paramAttributeSet);
  mct=context;
  mPaint=new Paint();
 }

 @Override
 protected void onDraw(Canvas canvas)
 {
  if(FirstCr)
  {
   w=getWidth();
   h=getHeight();
   csy=h*3/4;
   color=Color.rgb(255,0,0);
   mPaint.setColor(color);
   //设置抗锯齿
   mPaint.setAntiAlias(true);
   mPaint.setStrokeWidth(3f);
   FirstCr=false;
  }
  
  canvas.drawLine(0,csy,w,csy,mPaint);
 }
 
 //设置笔刷颜色
 public void setLineaColor(int r,int g,int b)
 {
  color=Color.rgb(r,g,b);
 }

 //设置位置
 public void setPosition(float y)
 {
  //将位置限定在屏幕内
  if(y>=0&&y<=h)csy=y;
  invalidate();
 }
 //移动位置
 public void postPosition(float y)
 {
  csy+=y;
  //将位置限定在屏幕内
  if(csy<0||csy>h)csy-=y;
  invalidate();
 }

 public void resetH(int i)
 {
  h=i;
 }
 
 public float get_y()
 {
  return csy;
 }

}
