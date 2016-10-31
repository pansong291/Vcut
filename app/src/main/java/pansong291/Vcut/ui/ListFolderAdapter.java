package pansong291.Vcut.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import pansong291.Vcut.R;
import pansong291.Vcut.Utils;
import java.io.File;
import pansong291.Vcut.BL;

public class ListFolderAdapter extends BaseAdapter
{
 private MainActivity context;
 private LayoutInflater vinflate;
 private ArrayList<String>dataList=new ArrayList<String>();
 public ListFolderAdapter(MainActivity c)
 {
  context=c;
  vinflate=LayoutInflater.from(context);
 }

 public void clearFolderInfo()
 {
  dataList.clear();
 }
 
 public void addFolderInfo(String a)
 {
  dataList.add(a);
 }
 
 public String getFolderInfo(int i)
 {
  return dataList.get(i);
 }
 
 public void setFolderInfo(ArrayList<String>l)
 {
  dataList=l;
 }
 
 @Override
 public int getCount()
 {
  return dataList.size();
 }
 public Object getItem(int p1)
 {
  return dataList.get(p1);
 }
 public long getItemId(int p1)
 {
  return p1;
 }
 public View getView(int i,View v,ViewGroup p)
 {
  FolderItemView liv;
  if(v==null)
  {
   v=vinflate.inflate(R.layout.list_item_folder,null);
   liv=new FolderItemView();
   //这句要注释掉，否则item不能点击
   //v.setClickable(false);
   liv.txtFolderName=(TextView)v.findViewById(R.id.textview_list_item);
   liv.imgFolderPic=(ImageView)v.findViewById(R.id.imageview_list_item_folder);
   v.setTag(liv);
  }else
  {
   liv=(FolderItemView)v.getTag();
  }
  String ss=dataList.get(i);
  liv.txtFolderName.setText(ss);
  if(!new File(BL.getBL().getCurrentPath()+"/"+ss).isDirectory())
   liv.imgFolderPic.setImageResource(R.drawable.file);
  else if(ss.startsWith("."))
   liv.imgFolderPic.setImageResource(R.drawable.folder_hidden);
  else
   liv.imgFolderPic.setImageResource(R.drawable.folder);
  return v;
 }
 
 private class FolderItemView
 {
  ImageView imgFolderPic;
  TextView txtFolderName;
 }
 
// public class FolderItemValue
// {
//  public String strFolderName;
//  public FolderItemValue(String a)
//  {
//   strFolderName=a;
//  }
// }
 
}
