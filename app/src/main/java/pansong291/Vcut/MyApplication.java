package pansong291.Vcut;

import pansong291.crash.CrashApplication;
import android.util.Log;

public class MyApplication extends CrashApplication
{
 @Override
 public Class<?> getPackageActivity()
 {
  setShouldShowDeviceInfo(true);
  Log.d("崩溃测试","已运行");
  return ErrorActivity.class;
 }

}
