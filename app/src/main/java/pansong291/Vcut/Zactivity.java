package pansong291.Vcut;

import android.app.Activity;
import android.os.Bundle;
import pansong291.crash.ActivityControl;

public class Zactivity extends Activity
{

 @Override
 protected void onResume()
 {
  super.onResume();
  
 }
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  ActivityControl.getActivityControl().addActivity(this);
 }

 @Override
 protected void onDestroy()
 {
  super.onDestroy();
  ActivityControl.getActivityControl().removeActivity(this);
 }
 
 
 
}
