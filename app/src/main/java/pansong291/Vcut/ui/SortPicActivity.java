package pansong291.Vcut.ui;
import android.os.Bundle;
import pansong291.Vcut.R;
import android.widget.LinearLayout;
import pansong291.Vcut.BL;
import android.widget.ImageView;
import pansong291.Vcut.Utils;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SortPicActivity extends Zactivity
{
 //界面视图
 LinearLayout llSort;
 RadioGroup rdgp;
 ImageView[]img;
 RadioButton[]rdb;
 
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
  llSort=(LinearLayout)findViewById(R.id.sortpic_LinearLayout);
  rdgp=(RadioGroup)findViewById(R.id.sort_picRadioGroup);
  
  int num=BL.getBL().getPicsNum();
  img=new ImageView[num];
  rdb=new RadioButton[num];
  
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
   rdb[i].setHeight(bp.getHeight());
   rdb[i].setWidth(bp.getWidth());
   rdb[i].setBackgroundResource(R.drawable.radio_btn);
   rdgp.addView(rdb[i]);
  }
 }
 
}
