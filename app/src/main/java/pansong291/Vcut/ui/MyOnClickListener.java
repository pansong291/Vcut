package pansong291.Vcut.ui;

import android.content.Intent;
import android.view.View;
import pansong291.Vcut.BL;
import pansong291.Vcut.R;

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
   //使用startActivityForResult是为了获取用户选择的图片
   Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
   intent.addCategory(Intent.CATEGORY_OPENABLE);
   intent.setType("video/*");
   m.startActivityForResult(Intent.createChooser(intent,"选择视频"),0);
  }else
  {
   BL.getBL().removeLastFromCurrentPath();
   m.setListDataChange();
  }
 }

}
