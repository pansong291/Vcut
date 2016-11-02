package pansong291.Vcut;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import android.app.Activity;

public class Utils
{
 
 //将图片以文件写出
 public static boolean createImageFile(Context c,Bitmap bmp)
 {
  String path="/storage/sdcard0/Pictures";
  // 首先保存图片
  File appDir=new File(path);
  if(!appDir.exists())
  {
   appDir.mkdir();
  }

  String fileName=System.currentTimeMillis()+"_Vcut.jpg";
  File file=new File(appDir,fileName);
  /***/
  try{
   FileOutputStream fos=new FileOutputStream(file);
   bmp.compress(CompressFormat.JPEG,100,fos);
   fos.flush();
   fos.close();
  }catch(Exception e)
  {
   return false;
  }
/***
  //其次把文件插入到系统图库
  try {
   MediaStore.Images.Media.insertImage(
   c.getContentResolver(),file.getAbsolutePath(),fileName,null);
  }catch(Exception e){}
  // 最后通知图库更新
  //c.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+path)));
 /***/
  return true;
 }
 
 //裁剪图片
 public static Bitmap ImageCrop(Bitmap bitmap,int y1,int y2)
 {
  int w=bitmap.getWidth();//得到图片的宽
  return Bitmap.createBitmap(bitmap,0,y1,w,y2);
 }
 
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
 
 //从Uri中获取文件路径
 public static String getAbsolutePath(Activity c,Uri uri,String s) 
 {
  try{
   String [] proj={s};
   Cursor cursor=c.managedQuery(uri,
                                proj,  // Which columns to return
                                null,  // WHERE clause; which rows to return (all rows)
                                null,  // WHERE clause selection arguments (none)
                                null); // Order-by clause (ascending by name)
   int column_index=cursor.getColumnIndexOrThrow(proj[0]);
   cursor.moveToFirst();
   return cursor.getString(column_index);
  }catch(Exception e)
  {
   return uri.getPath();
  }
 }
 
 
 
}
