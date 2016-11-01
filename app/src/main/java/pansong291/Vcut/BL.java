package pansong291.Vcut;

import android.graphics.Bitmap;
import java.util.ArrayList;
import pansong291.Vcut.ui.MyBitmap;

public class BL
{
 //单例模式
 private static BL bl=new BL();
 private BL()
 {}

 public static BL getBL()
 {
  return bl;
 }
 
 //当前目录
 private String strCurrentPath="/storage/emulated/0/DCIM/Camera";//"/storage";
 public String getCurrentPath(){return strCurrentPath;}
 public void addToCurrentPath(String s)
 {
  strCurrentPath=strCurrentPath+"/"+s;
 }
 public void removeLastFromCurrentPath()
 {
  if(strCurrentPath.length()>9)
   strCurrentPath=strCurrentPath.substring(0,strCurrentPath.lastIndexOf("/"));
 }
 public void setCurrentPath(String s)
 {
  strCurrentPath=s;
 }
 
 //已截取图片
 private ArrayList<MyBitmap>pics=new ArrayList<MyBitmap>();
 private int currentPic;
 public void setCurrentPic(MyBitmap b)
 {
  currentPic=pics.indexOf(b);
 }
 public void setCurrentPic(int i)
 {
  currentPic=i;
 }
 public int getCurrentPic()
 {
  return currentPic;
 }
 public void setPics(ArrayList<MyBitmap>pics)
 {
  this.pics=pics;
 }
 public void addToPics(MyBitmap bp)
 {
  pics.add(bp);
 }
 public int getPicsNum()
 {
  return pics.size();
 }
 public MyBitmap getPic(int i)
 {
  return pics.get(i);
 }
 public void removePic(int i)
 {
  pics.remove(i);
 }
 public void remvePic(MyBitmap bp)
 {
  pics.remove(bp);
 }
 
 //已编辑图片
 private ArrayList<Bitmap>picCs=new ArrayList<Bitmap>();
 public void setPicCs(ArrayList<Bitmap>p)
 {
  picCs=p;
 }
 public void addToPicCs(Bitmap bp)
 {
  picCs.add(bp);
 }
 public int getPicCsNum()
 {
  return picCs.size();
 }
 public Bitmap getPicC(int i)
 {
  return picCs.get(i);
 }
 public void removePicC(int i)
 {
  picCs.remove(i);
 }
 public void remvePicC(Bitmap bp)
 {
  picCs.remove(bp);
 }
 
}
