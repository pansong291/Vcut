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
import android.widget.RadioButton;
import android.content.Intent;
import android.widget.TextView;

public class EditPicActivity extends Zactivity
{
 //界面视图
 View relayout;
 TextView txtPicNum;
 ImageView imgv;
 SelectView sectv;
 RadioButton radio_all,radio_up,radio_down;
 
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
  screenH=getWindowManager().getDefaultDisplay().getHeight()*2/5;
  screenW=getWindowManager().getDefaultDisplay().getWidth();
  
  txtPicNum=(TextView)findViewById(R.id.textview_pic_num);
  
  relayout=findViewById(R.id.reLayout);
  LayoutParams lp1=relayout.getLayoutParams();
  lp1.height=(int)screenH;
  relayout.setLayoutParams(lp1);
  
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
       BL.getBL().getPic(BL.getBL().getCurrentPic()).setFY(sectv.get_y());
       //toast("Ty="+BL.getBL().getPic(BL.getBL().getCurrentPic()).getTY()+"\nRy="+sectv.get_y()+"\nPicH="+BL.getBL().getPic(BL.getBL().getCurrentPic()).bitmap.getHeight());
       break;
      case MotionEvent.ACTION_MOVE:
       sectv.postPosition(me.getY()-down_y);
       down_y=me.getY();
       break;
     }
     return true;
    }
  });
  
  radio_all=(RadioButton)findViewById(R.id.btn_save_all);
  radio_up=(RadioButton)findViewById(R.id.btn_save_up);
  radio_down=(RadioButton)findViewById(R.id.btn_save_down);
  
  imgv=(ImageView)findViewById(R.id.imageview);
  setCurrentPic(BL.getBL().getPic(0));//这句放在最后！！
  
  
 }
  
 private void setCurrentPic(MyBitmap bp1)
 {
  txtPicNum.setText("当前第"+(BL.getBL().getPicPosition(bp1)+1)+"张");
  imgv.setImageBitmap(bp1.bitmap);
  //获取图片宽高
  float vh=bp1.bitmap.getHeight();
  float vw=bp1.bitmap.getWidth();
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
  
  if(bp1.getRace()<0)bp1.setRace(((float)bp1.bitmap.getHeight())/((float)lp2.height));
  if(bp1.getFY()<0)bp1.setFY(lp2.height*3/4);//这句必须在race赋值之后
//  toast("race="+bp1.getRace());
  if(bp1.getS()<0)bp1.setS(MyBitmap.SAVE_ALL);

  //切换图片时，高度发生变化，重新设置高度并调整红线位置
  sectv.resetH(lp2.height);
  sectv.setPosition(bp1.getFY());
  
  setRadioButtonChecked(bp1.getS());
  
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
  if(BL.getBL().getPicCsNum()>0)
   BL.getBL().removePicC(BL.getBL().getCurrentPic());
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
  BL.getBL().getPic(BL.getBL().getCurrentPic()).setFY(sectv.get_y());
 }

 public void onDownLineClick(View v)
 {
  sectv.setPosition(sectv.get_y()+1);
  BL.getBL().getPic(BL.getBL().getCurrentPic()).setFY(sectv.get_y());
 }

 public void onSaveAllClick(View v)
 {
  BL.getBL().getPic(BL.getBL().getCurrentPic()).setS(MyBitmap.SAVE_ALL);
 }

 public void onSaveUpClick(View v)
 {
  BL.getBL().getPic(BL.getBL().getCurrentPic()).setS(MyBitmap.SAVE_UP);
 }

 public void onSaveDownClick(View v)
 {
  BL.getBL().getPic(BL.getBL().getCurrentPic()).setS(MyBitmap.SAVE_DOWN);
 }
 
 public void onCompletionClick(View v)
 {
  if(BL.getBL().getPic(BL.getBL().getPicsNum()-1).getS()<0)
  {
   toast("你未完成所有图片的编辑");
   return;
  }
  startActivity(new Intent(EditPicActivity.this,SortPicActivity.class));
 }
 
 private void setRadioButtonChecked(int i)
 {
  switch(i)
  {
   case MyBitmap.SAVE_ALL:
    radio_all.setChecked(true);
   break;
   case MyBitmap.SAVE_UP:
    radio_up.setChecked(true);
   break;
   case MyBitmap.SAVE_DOWN:
    radio_down.setChecked(true);
   break;
  }
 }
 
}
