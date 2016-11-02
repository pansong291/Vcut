package pansong291.Vcut.ui;

import android.content.Intent;
import android.view.View;
import pansong291.Vcut.BL;
import pansong291.Vcut.R;
import android.provider.MediaStore;

public class MyOnClickListener implements View.OnClickListener
{
 MainActivity m;
 public MyOnClickListener(MainActivity n)
 {
  m=n;
 }

 @Override
 public void onClick(View p1)
 {
  if(p1.getId()==R.id.button_dialog_other)
  {
   //调用其它文件选择器选择视频
   //使用startActivityForResult是为了获取用户选择的文件
   Intent it=new Intent(Intent.ACTION_PICK, 
                      MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
   it.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,"video/*");
   //Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
   //intent.addCategory(Intent.CATEGORY_OPENABLE);
   //intent.setType("video/*");
   m.startActivityForResult(Intent.createChooser(it,"选择视频"),0);
  }else
  {
   BL.getBL().removeLastFromCurrentPath();
   m.setListDataChange();
  }
 }

}
