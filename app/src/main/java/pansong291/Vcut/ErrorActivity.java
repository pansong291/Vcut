package pansong291.Vcut;

import android.os.Bundle;
import android.widget.TextView;
import pansong291.crash.CrashApplication;

public class ErrorActivity extends Zactivity
{
 TextView merrorlogtxt;
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_error);
  merrorlogtxt=(TextView)findViewById(R.id.activityerrorTextView1);
  merrorlogtxt.setText(getIntent().getStringExtra(CrashApplication.ERROR_MESSAGE_TAG));
 }

 @Override
 public void onBackPressed()
 {
  super.onBackPressed();
  System.exit(0);
 }
 
 
 
}
