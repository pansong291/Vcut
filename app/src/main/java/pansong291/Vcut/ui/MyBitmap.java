package pansong291.Vcut.ui;
import android.graphics.Bitmap;

public class MyBitmap
{
 Bitmap bitmap;
 float race=-1,fy=-1,ty=-1;
 int save=-1;
 public static final int SAVE_ALL=0,SAVE_UP=1,SAVE_DOWN=2;
 
 public MyBitmap(Bitmap b,int s)
 {
  bitmap=b;
  save=s;
 }
 
 public MyBitmap(Bitmap b)
 {
  bitmap=b;
 }

 public void setRace(float r)
 {
  race=r;
 }

 public float getRace()
 {
  return race;
 }

 public float getFY()
 {
  return fy;
 }

 public void setFY(float y)
 {
  fy=y;
  if(race>0)
   ty=fy*race;
 }
 public float getTY()
 {
  return ty;
 }

 public void setTY(float y)
 {
  ty=y;
 }
 
 public int getS()
 {
  return save;
 }
 
 public void setS(int s)
 {
  save=s;
 }
 
}
