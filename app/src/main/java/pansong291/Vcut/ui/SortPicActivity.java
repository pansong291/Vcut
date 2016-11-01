package pansong291.Vcut.ui;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import pansong291.Vcut.BL;
import pansong291.Vcut.R;
import pansong291.Vcut.Utils;
import android.widget.Button;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;

public class SortPicActivity extends Zactivity
{
 //界面视图
 LinearLayout llSort;
 RadioGroup rdgp;
 ImageView[]img;
 RadioButton[]rdb;
 Button btn;
 
 //对话框视图
 AlertDialog adg;
 ImageView dimg;
 View prog;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  // TODO: Implement this method
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_sort_pic);
  
  init();
  
 }
 
 private void init()
 {
  View dv=getLayoutInflater().inflate(R.layout.dialogview,null);
  dimg=(ImageView)dv.findViewById(R.id.dialog_img);
  prog=dv.findViewById(R.id.dialog_progressbar);
  adg=new AlertDialog.Builder(this)
   .setView(dv)
   .setNegativeButton("返回",null)
   .create();
  
  llSort=(LinearLayout)findViewById(R.id.sortpic_LinearLayout);
  rdgp=(RadioGroup)findViewById(R.id.sort_picRadioGroup);
  
  int num=BL.getBL().getPicsNum();
  img=new ImageView[num];
  rdb=new RadioButton[num];
  
  if(BL.getBL().getPicCsNum()>0)BL.getBL().setPicCs(null);
  for(int i=0;i<num;i++)
  {
   MyBitmap mbp=BL.getBL().getPic(i);
   Bitmap bp=mbp.bitmap;
   int y=(int)mbp.getTY();
   //toast("race="+mbp.getRace()+"\ny="+y+"\nbp.h="+bp.getHeight(),1);
   switch(mbp.getS())
   {
    case MyBitmap.SAVE_ALL:
    break;
    case MyBitmap.SAVE_UP:
     bp=Utils.ImageCrop(bp,0,y);
    break;
    case MyBitmap.SAVE_DOWN:
     bp=Utils.ImageCrop(bp,y,bp.getHeight()-y);
     //toast("y="+y+"\nH="+bp.getHeight()+"\nH-y="+(bp.getHeight()-y));
    break;
   }
   BL.getBL().addToPicCs(bp);
   img[i]=new ImageView(this);
   img[i].setImageBitmap(bp);
   //img[i].setMaxHeight(bp.getHeight());
   //img[i].setMaxWidth(bp.getWidth());
   llSort.addView(img[i]);
   rdb[i]=new RadioButton(this);
   rdb[i].setId(i);
   rdb[i].setHeight(bp.getHeight());
   rdb[i].setWidth(bp.getWidth());
   rdb[i].setBackgroundResource(R.drawable.radio_btn);
   rdgp.addView(rdb[i]);
  }
 }
 
 public void onMoveUpClick(View v)
 {
  int c=rdgp.getCheckedRadioButtonId();
  if(c<=0)return;
  exchangeSequence(c,c-1);
 }

 public void onMoveDownClick(View v)
 {
  int c=rdgp.getCheckedRadioButtonId();
  if(c<0||c==BL.getBL().getPicCsNum()-1)return;
  exchangeSequence(c,c+1);
 }
 
 private void exchangeSequence(int a,int b)
 {
  Bitmap bpa=BL.getBL().getPicC(a),
   bpb=BL.getBL().getPicC(b);
  img[a].setImageBitmap(bpb);
  img[b].setImageBitmap(bpa);

  rdb[a].setHeight(bpb.getHeight());
  rdb[a].setWidth(bpb.getWidth());
  rdb[b].setHeight(bpa.getHeight());
  rdb[b].setWidth(bpa.getWidth());
  rdb[b].setChecked(true);
  BL.getBL().exchangePicC(a,b);
 }
 
 public void onCompletionClick(View v)
 {
  /**
   Bitmap bitmap1;
   Bitmap bitmap2;
   Bitmap bitmap3 = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());
   Canvas canvas = new Canvas(bitmap3);
   canvas.drawBitmap(bitmap1, new Matrix(), null);
   canvas.drawBitmap(bitmap2, 120, 350, null);
   //120、350为bitmap2写入点的x、y坐标
  */
  adg.show();
  new Thread(new myRunnable()).start();
 }
 
 Handler myhandler=new Handler()
 {
  @Override
  public void handleMessage(Message msg)
  {
   prog.setVisibility(8);
   dimg.setImageBitmap((Bitmap)msg.obj);
   if(msg.arg1!=0)
    toast("图片已保存至/storage/sdcard0/Pictures",1);
   else
    toast("保存出错",1);
  }
 };
 
 class myRunnable implements Runnable
 {
  @Override
  public void run()
  {
   int h=0;
   for(Bitmap bp:BL.getBL().getPicCs())
   {
    h+=bp.getHeight();
   }
   Bitmap bmp=BL.getBL().getPicC(0),rbmp;
   rbmp=Bitmap.createBitmap(bmp.getWidth(),h,bmp.getConfig());
   Canvas canvas=new Canvas(rbmp);
   canvas.drawBitmap(bmp,new Matrix(),null);
   h=0;
   for(int i=1;i<BL.getBL().getPicCsNum();i++)
   {
    h+=BL.getBL().getPicC(i-1).getHeight();
    canvas.drawBitmap(BL.getBL().getPicC(i),0,h,null);
   }
   
   Message msg=new Message();
   msg.obj=rbmp;
   msg.arg1=Utils.createImageFile(SortPicActivity.this,rbmp)?1:0;
   myhandler.sendMessage(msg);
  }
 }
 
}
