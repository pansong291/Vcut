package pansong291.Vcut;

import android.graphics.Bitmap;
import java.util.ArrayList;

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
 private ArrayList<Bitmap>pics=new ArrayList<Bitmap>();
 public void setPics(ArrayList<Bitmap>pics)
 {
  this.pics=pics;
 }
 public void addToPics(Bitmap bp)
 {
  pics.add(bp);
 }
 public int getPicsNum()
 {
  return pics.size();
 }
 public Bitmap getPic(int i)
 {
  return pics.get(i);
 }
 public void removePic(int i)
 {
  pics.remove(i);
 }
 public void remvePic(Bitmap bp)
 {
  pics.remove(bp);
 }
 
}
