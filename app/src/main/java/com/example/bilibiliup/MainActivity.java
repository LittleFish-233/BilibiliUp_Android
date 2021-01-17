package com.example.bilibiliup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    Timer timer = null;
    int jishu = 0;
    String linshi_up = "";

    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        //初始化
        yonghu yonghu_ = (yonghu) this.getApplication();
        if(yonghu_.shifou_chushihua==false)
        {
            try {
                yonghu_.peizhi_.duqu_jiben(this);
                yonghu_.shifou_chushihua=true;
            } catch (FileNotFoundException | JSONException e) {
                e.printStackTrace();
            }
        }



        //判断是否登入
        if(Objects.equals(yonghu_.peizhi_.dedeUserID, ""))
        {
            AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
            alertdialogbuilder.setMessage("未登入Bilibili账号，可能会导致API调用受阻，点击“是的”将打开二维码登入界面，点击“不”将继续获取信息。后续可以点击设置按钮进行登录和其他高级设置");
            alertdialogbuilder.setPositiveButton("是的",this::onClick_1);
            alertdialogbuilder.setNeutralButton("不", this::onClick_2);
            alertdialogbuilder.setTitle("错误");
            final AlertDialog alertdialog1 = alertdialogbuilder.create();
            alertdialog1.show();
        }
        else
        {
            if(Objects.equals(yonghu_.peizhi_.up, ""))
            {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                alertdialogbuilder.setMessage("绑定的UP主的uuid为空，将为你打开设置，请输入正确的uuid");
                alertdialogbuilder.setPositiveButton("是的",this::onClick_3);
                alertdialogbuilder.setNeutralButton("不", this::onClick_4);
                alertdialogbuilder.setTitle("错误");
                final AlertDialog alertdialog1 = alertdialogbuilder.create();
                alertdialog1.show();
            }
        }
        //广播
        /*
        Intent intent = new Intent();
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("data","Notice me senpai!");
        sendBroadcast(intent);*/
        //刷新数据
        shuju shuju_=new shuju();
        //初始化计时器
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            jishu++;
                            //判断是否改变目标up主
                            if(!Objects.equals(yonghu_.peizhi_.up, linshi_up) && !Objects.equals(yonghu_.peizhi_.up, ""))
                            {
                                jishu=0;
                                linshi_up=yonghu_.peizhi_.up;
                                shuju_.shuaxin_shuju(yonghu_.peizhi_.up,yonghu_.peizhi_.huoqu_cook());
                                //刷新缓存
                                shuaxin_shuju(shuju_);
                            }
                            if(jishu>yonghu_.peizhi_.huoqu_jiange*2&& !Objects.equals(yonghu_.peizhi_.up, ""))
                            {
                                jishu=0;
                                linshi_up=yonghu_.peizhi_.up;
                                shuju_.shuaxin_shuju(yonghu_.peizhi_.up,yonghu_.peizhi_.huoqu_cook());
                                //播放动画
                                shuaxin_shuju(shuju_);
                            }

                        }
                        catch (Exception exc)
                        {
                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, "获取信息失败，详细信息<" + exc.getMessage() + ">", duration);
                            toast.show();
                            exc.printStackTrace();
                        }
                    }
                });
            }
        }, 0,500);
    }

    private void  shuaxin_shuju(shuju shuju_)
    {
        ((TextView)findViewById(R.id.textView3)).setText(shuju_.mingzi);
        ((TextView)findViewById(R.id.textView)).setText(shuju_.fenshishu);
        ((TextView)findViewById(R.id.textView4)).setText(shuju_.shuju_dan_.shiping_mingzi_shou);
        ((TextView)findViewById(R.id.textView5)).setText(shuju_.shuju_dan_.bofenshu);
        ((TextView)findViewById(R.id.textView7)).setText(shuju_.shuju_dan_.dianzan);
        ((TextView)findViewById(R.id.textView16)).setText(shuju_.shuju_dan_.toubi);
        ((TextView)findViewById(R.id.textView14)).setText(shuju_.shuju_dan_.shoucang);
        ((TextView)findViewById(R.id.textView8)).setText(shuju_.shuju_dan_.pinglun);
        ((TextView)findViewById(R.id.textView12)).setText(shuju_.shuju_dan_.danmu);
        ((TextView)findViewById(R.id.textView10)).setText(shuju_.shuju_dan_.fenxiang);
    }

    public void onClick_1(DialogInterface dialog, int id)
    {
        Intent intent = new Intent(this, DengruActivity.class);
        startActivity(intent);
    }
    public void onClick_2(DialogInterface dialog, int id)
    {
        yonghu yonghu_ = (yonghu) this.getApplication();
        if(Objects.equals(yonghu_.peizhi_.up, ""))
        {
            AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
            alertdialogbuilder.setMessage("绑定的UP主的uuid为空，将为你打开设置，请输入正确的uuid");
            alertdialogbuilder.setPositiveButton("是的",this::onClick_3);
            alertdialogbuilder.setNeutralButton("不", this::onClick_4);
            alertdialogbuilder.setTitle("错误");
            final AlertDialog alertdialog1 = alertdialogbuilder.create();
            alertdialog1.show();
        }
    }
    public void onClick_3(DialogInterface dialog, int id)
    {
        Intent intent = new Intent(this, ShezhiActivity.class);
        startActivity(intent);
    }
    public void onClick_4(DialogInterface dialog, int id)
    {
        finish();
    }

    public void GuanyU(View view)
    {
        Intent intent = new Intent(this, GuanyuActivity.class);
        startActivity(intent);
    }

    public void Shezhi(View view)
    {
        Intent intent = new Intent(this, ShezhiActivity.class);
        startActivity(intent);
    }
    public void Ceshi(View view)
    {
        Intent localIntent = new Intent();
        localIntent.setClass(this, WidgetService.class); // 销毁时重新启动Service
        this.startService(localIntent);
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "你好";
            String description = "我只是测试";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123456789", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
