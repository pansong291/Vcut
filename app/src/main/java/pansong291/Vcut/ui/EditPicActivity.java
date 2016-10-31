package pansong291.Vcut.ui;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import pansong291.Vcut.BL;
import pansong291.Vcut.R;
import android.graphics.Bitmap;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;

public class EditPicActivity extends Zactivity
{
 //界面视图
 ImageView imgv;
 SelectView sectv;
 
 private float screenH,screenW;
 private float down_y;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  // TODO: Implement this method
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_edit_pic);
  
  init();
  
 }
 
 private void init()
 {
  screenH=getWindowManager().getDefaultDisplay().getHeight()/2;
  screenW=getWindowManager().getDefaultDisplay().getWidth();
   
  sectv=(SelectView)findViewById(R.id.sectview);
  sectv.setOnTouchListener(new OnTouchListener()
   {
    @Override
    public boolean onTouch(View p1, MotionEvent me)
    {
     switch(me.getAction())
     {
      case MotionEvent.ACTION_DOWN:
       down_y=me.getY();
       break;
      case MotionEvent.ACTION_UP:
       break;
      case MotionEvent.ACTION_MOVE:
       sectv.postPosition(me.getY()-down_y);
       down_y=me.getY();
       break;
     }
     return true;
    }
  });
  
  imgv=(ImageView)findViewById(R.id.imageview);
  setCurrentPic(BL.getBL().getPic(0));
  
 }
  
 private void setCurrentPic(Bitmap bp1)
 {
  imgv.setImageBitmap(bp1);
  //获取图片宽高
  float vh=bp1.getHeight();
  float vw=bp1.getWidth();
  LayoutParams lp2=imgv.getLayoutParams();
  if((vw/vh)>(screenW/screenH))
  {
   lp2.width=(int)screenW;
   lp2.height=(int)(vh*(screenW/vw));
  }else
  {
   lp2.width=(int)(vw*(screenH/vh));
   lp2.height=(int)(screenH);
  }
//  toast("屏幕：\n宽"+screenW+"\n高"+screenH+"\n视频：\n宽"+vw+"\n高"+vh+"\n播放：\n宽"+lp2.width+"\n高"+lp2.height,1);
  imgv.setLayoutParams(lp2);
  //切换图片时，高度发生变化，重新设置高度并调整红线位置
  sectv.resetH(lp2.height);
  if(lp2.height<sectv.get_y())
   sectv.setPosition(lp2.height);
  BL.getBL().setCurrentPic(bp1);
 }
 
 public void onBeforePicClick(View v)
 {
  if(BL.getBL().getCurrentPic()==0)
   return;
  setCurrentPic(BL.getBL().getPic(BL.getBL().getCurrentPic()-1));
 }
 
 public void onDeletePicClick(View v)
 {
  if(BL.getBL().getPicsNum()==1)return;
  BL.getBL().removePic(BL.getBL().getCurrentPic());
  if(BL.getBL().getCurrentPic()==0)
   setCurrentPic(BL.getBL().getPic(0));
  else
   setCurrentPic(BL.getBL().getPic(BL.getBL().getCurrentPic()-1));
   
 }
 
 public void onBehindPicClick(View v)
 {
  if(BL.getBL().getCurrentPic()==BL.getBL().getPicsNum()-1)
   return;
  setCurrentPic(BL.getBL().getPic(BL.getBL().getCurrentPic()+1));
 }

 public void onUpLineClick(View v)
 {
  sectv.setPosition(sectv.get_y()-1);
 }

 public void onDownLineClick(View v)
 {
  sectv.setPosition(sectv.get_y()+1);
 }

 public void onSaveAllClick(View v)
 {
  
 }

 public void onSaveUpClick(View v)
 {
  
 }

 public void onSaveDownClick(View v)
 {
  
 }
 
}
