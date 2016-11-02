package pansong291.Vcut.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;
import java.io.File;
import pansong291.Vcut.BL;
import pansong291.Vcut.R;
import pansong291.Vcut.Utils;
import android.net.Uri;
import android.provider.MediaStore;

public class MainActivity extends Zactivity 
{
 //main界面视图
 View rLayout;
 VideoView videoView;
 TextView txt_now_time,txt_all_time,txt_select_pic;
 SeekBar skb;
 Button btnSelectVideo;
 //对话框里的视图
 TextView txtCurrentPath;
 View viewDialog,viewGoBack,viewOther;
 AlertDialog listDialog;
 ListView listvPath;
 ListFolderAdapter listAdapter;
 MyOnClickListener myOnClickListener;
 
 private float screenH,screenW;
 String currentVideoPath;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);

  init();
//  listAdapter.setFolderInfo(Utils.getAllSonFolder(BL.getBL().getCurrentPath()));
 }
 
 int int_video_all_time=0;
 
 private void init()
 {
  txt_now_time=(TextView)findViewById(R.id.textview_now_time);
  txt_all_time=(TextView)findViewById(R.id.textview_all_time);
  txt_select_pic=(TextView)findViewById(R.id.textview_selected);
  skb=(SeekBar)findViewById(R.id.seekbar);
  skb.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
   {
    @Override
    public void onProgressChanged(SeekBar p1, int p2, boolean p3)
    {
     if(p3)//如果是用户改变SeekBar
     {
      txt_all_time.setText(Utils.getVideoTime(p2));
     }
    }
    @Override
    public void onStartTrackingTouch(SeekBar p1)
    {
     txt_all_time.setText(Utils.getVideoTime(p1.getProgress()));
     skb_not_user_change=false;
    }
    @Override
    public void onStopTrackingTouch(SeekBar p1)
    {
     if(videoView.getDuration()>0)
     {
      //设置当前播放的位置
      videoView.seekTo(p1.getProgress());
      txt_all_time.setText(Utils.getVideoTime(int_video_all_time));
      skb_not_user_change=true;
     }
    }
  });
  
  screenH=getWindowManager().getDefaultDisplay().getHeight()*2/5;
  screenW=getWindowManager().getDefaultDisplay().getWidth();
  rLayout=findViewById(R.id.rlayout);
  LayoutParams lp1=rLayout.getLayoutParams();//取控件当前的布局参数
  lp1.height=(int)screenH;//强制设置控件高度
  lp1.width=(int)screenW;
  rLayout.setLayoutParams(lp1);//使设置好的布局参数应用到控件
  
  videoView=(VideoView)findViewById(R.id.videoview);
  videoView.setOnPreparedListener(new OnPreparedListener()
  {
   @Override
   public void onPrepared(MediaPlayer mp)
   {
    int_video_all_time=videoView.getDuration();
    skb.setMax(videoView.getDuration());
    txt_all_time.setText(Utils.getVideoTime(int_video_all_time));
   }
  });
  videoView.setOnCompletionListener(new OnCompletionListener()
   {
    @Override
    public void onCompletion(MediaPlayer p1)
    {
     skb.setProgress(0);
     videoView.seekTo(0);
    }
  });
  
  btnSelectVideo=(Button)findViewById(R.id.button_select_video);
  
  viewDialog=LayoutInflater.from(getApplication()).inflate(R.layout.dialog_choose_folder,null);
  listvPath=(ListView)viewDialog.findViewById(R.id.listview_dialog_folder);
  txtCurrentPath=(TextView)viewDialog.findViewById(R.id.textview_dialog_current_folder);
  viewGoBack=viewDialog.findViewById(R.id.linearlayout_dialog_goback);
  viewOther=viewDialog.findViewById(R.id.button_dialog_other);
  
  myOnClickListener=new MyOnClickListener(this);
  viewGoBack.setOnClickListener(myOnClickListener);
  viewOther.setOnClickListener(myOnClickListener);
  
  listAdapter=new ListFolderAdapter(this);
  listvPath.setAdapter(listAdapter);
  listvPath.setOnItemClickListener(new AdapterView.OnItemClickListener()
   {
    @Override
    public void onItemClick(AdapterView<?> p1,View p2,int p3,long p4)
    {
     String fn=listAdapter.getFolderInfo(p3);
     File f=new File(BL.getBL().getCurrentPath()+"/"+fn);
     if(f.isDirectory())
     {
      BL.getBL().addToCurrentPath(fn);
      setListDataChange();
     }else
     {
      initVideo(f.getAbsolutePath());
      listDialog.dismiss();
     }
    }
   });

  listDialog=new AlertDialog.Builder(this)
   .setTitle("选择视频文件")
   .setView(viewDialog)
   .setNegativeButton("取消",null)
   .create();
 }
 
 private final static int NONE=0,PROGRESS_CHANGED=1;
 private boolean skb_not_user_change=true;
 Handler myHandler=new Handler()
 {
  @Override
  public void handleMessage(Message msg)
  {
   int i=videoView.getCurrentPosition();
   if(skb_not_user_change)
    skb.setProgress(i);
   txt_now_time.setText(Utils.getVideoTime(i));
   sendEmptyMessage(PROGRESS_CHANGED);
  }   
 };
 
 public void onChooseVideoClick(View v)
 {
  setListDataChange();
  listDialog.show();
 }

 public void onSelectedClick(View v)
 {
  if(videoView.getDuration()<0)return;
  BL.getBL().addToPics(new MyBitmap(Utils.getVideoPicture(currentVideoPath,videoView.getCurrentPosition())));
  txt_select_pic.setText("已选取"+BL.getBL().getPicsNum()+"张");
 }
 
 public void onCompletionClick(View v)
 {
  if(BL.getBL().getPicsNum()>0)
   startActivity(new Intent(MainActivity.this,EditPicActivity.class));
 }
 
 public void onPlayPauseClick(View v)
 {
  if(videoView.getDuration()<0)return;
  if(videoView.isPlaying())
   videoView.pause();
  else
   videoView.start();
 }
 
 public void onGoForwardClick(View v)
 {
  if(videoView.getDuration()<0)return;
  if(int_video_all_time-videoView.getCurrentPosition()>5000)
   videoView.seekTo(videoView.getCurrentPosition()+5000);
  else
   videoView.seekTo(int_video_all_time);
 }
 
 public void onGoBackClick(View v)
 {
  if(videoView.getDuration()<0)return;
  if(videoView.getCurrentPosition()>5000)
   videoView.seekTo(videoView.getCurrentPosition()-5000);
  else
   videoView.seekTo(0);
 }
 
 public void onBehindClick(View v)
 {
  if(videoView.getDuration()<0)return;
  if(int_video_all_time-videoView.getCurrentPosition()>1000)
   videoView.seekTo(videoView.getCurrentPosition()+1000);
  else
   videoView.seekTo(int_video_all_time);
 }
 
 public void onBeforeClick(View v)
 {
  if(videoView.getDuration()<0)return;
  if(videoView.getCurrentPosition()>1000)
   videoView.seekTo(videoView.getCurrentPosition()-1000);
  else
   videoView.seekTo(0);
 }
 
 //载入视频
 private void initVideo(String fp)
 {
  //toast("你选的视频是："+fp);
  currentVideoPath=fp;
  videoView.setVideoPath(fp);
  //获取视频宽高
  Bitmap bp1=Utils.getVideoPicture(fp,1);
  float vh=bp1.getHeight();//视频高度
  float vw=bp1.getWidth();//视频宽度
  LayoutParams lp2=videoView.getLayoutParams();
  if((vw/vh)>(screenW/screenH))
  {
   lp2.width=(int)screenW;
   lp2.height=(int)(vh*(screenW/vw));
  }else
  {
   lp2.width=(int)(vw*(screenH/vh));
   lp2.height=(int)screenH;
  }
//  toast("屏幕：\n宽"+screenW+"\n高"+screenH+"\n视频：\n宽"+vw+"\n高"+vh+"\n播放：\n宽"+lp2.width+"\n高"+lp2.height,1);
  videoView.setLayoutParams(lp2);
  videoView.start();
  videoView.requestFocus();
  myHandler.sendEmptyMessage(NONE);
 }
 
 //重写onActivityResult以获得你需要的信息
 @Override
 protected void onActivityResult(int requestCode,int resultCode,Intent data)
 {
  super.onActivityResult(requestCode,resultCode,data);
  //此处的requestCode用于判断接收的Activity是不是你想要的那个
  if(resultCode==RESULT_OK&&requestCode==0)
  {
   //toast("path="+data.getData().getPath()+"\ntoString="+data.getData().toString()+"\nEncodedPath="+data.getData().getEncodedPath(),1);
   //获得视频的路径并加载视频
   initVideo(Utils.getAbsolutePath(this,data.getData(),MediaStore.Video.Media.DATA));
   listDialog.dismiss();
  }else if(resultCode==RESULT_OK)
  {
   toast("请重新选择视频");
  }
 }
 
 public void setListDataChange()
 {
  listAdapter.setFolderInfo(Utils.getAllSonFolder(BL.getBL().getCurrentPath()));
  listAdapter.notifyDataSetChanged();
  txtCurrentPath.setText(BL.getBL().getCurrentPath());
 }

 @Override
 protected void onResume()
 {
  super.onResume();
  txt_select_pic.setText("已选取"+BL.getBL().getPicsNum()+"张");
 }
 
 @Override
 public void onBackPressed()
 {
  super.onBackPressed();
  videoView.stopPlayback();
  System.exit(0);
 }


 
 
}
