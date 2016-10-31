package pansong291.Vcut;

import android.util.Log;
import pansong291.Vcut.ui.ErrorActivity;
import pansong291.crash.CrashApplication;

public class MyApplication extends CrashApplication
{
 @Override
 public Class<?> getPackageActivity()
 {
  setShouldShowDeviceInfo(false);
  return ErrorActivity.class;
 }

}
