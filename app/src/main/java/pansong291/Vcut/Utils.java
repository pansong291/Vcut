package pansong291.Vcut;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;

public class Utils
{
 
 //转换视频时间
 public static String getVideoTime(int ti)
 {
  int i=ti/1000;
  int minute=i/60;
  int hour=minute/60;
  int second=i%60;
  minute%=60;
  return String.format("%02d:%02d:%02d",hour,minute,second);
 }
 
 //获取视频中某一帧
 public static Bitmap getVideoPicture(String videoPath,int ms)
 {
  MediaMetadataRetriever retriever=new MediaMetadataRetriever();
  retriever.setDataSource(videoPath);
  //得到每一秒时刻的bitmap比如第一秒,第二秒
  return retriever.getFrameAtTime(ms*1000,MediaMetadataRetriever.OPTION_CLOSEST_SYNC); 
 }
 
 //获取所有子目录
 public static ArrayList<String> getAllSonFolder(String s)
 {
  File f=new File(s);
  //文件过滤器，只有当其不是文件夹或视频文件时才被过滤掉
  FileFilter myFileFliter=new FileFilter()
  {
   @Override
   public boolean accept(File p1)
   {
    if(p1.isDirectory())return true;
    return isVideoFile(p1.getName());
   }
  };
  ArrayList<String>arrayFiles=new ArrayList<String>();
  File[]files=f.listFiles(myFileFliter);
  if(files!=null)
  {
   for(int d=0;d<files.length;d++)
   {
    arrayFiles.add(files[d].getName());
   }
   //排序
   Collections.sort(arrayFiles);
  }
  return arrayFiles;
 }
 
 public static boolean isVideoFile(String fn)
 {
  boolean isVideo=false;
  String fnes=fn.substring(fn.lastIndexOf(".")+1,fn.length());
  switch(fnes)
  {
   case "mp4":
   case "avi":
   case "rmvb":
   case "3gp":
   case "mkv":
   case "webm":
    isVideo=true;
   break;
  }
  return isVideo;
 }
 
 
 
 
 
}
