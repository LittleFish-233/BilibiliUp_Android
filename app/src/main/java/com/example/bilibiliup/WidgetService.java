package com.example.bilibiliup;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WidgetService extends Service {
    private Timer timer = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建一个通知管理器
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //        获取Notification实例
        Notification notification = new NotificationCompat.Builder(this, "123456789")
                .setContentTitle("Bilibili挂件")
                .setContentText("此通知确保后台更新正常启动")
                .setWhen(System.currentTimeMillis())
                //             .setAutoCancel(false) 点击通知栏后是否消失
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bilibili_ico))
                // .setCustomContentView(remoteView) // 设置自定义的RemoteView，需要API最低为24
                .setSmallIcon(R.drawable.ic_stat_name)
                // 设置点击通知栏后跳转地址
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
                .build();
//        添加渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123456789", "subscribeName", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("description");
            notificationManager.createNotificationChannel(channel);
        }
        // 设置常驻 Flag
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        //展示通知栏
        notificationManager.notify(123, notification);
        startForeground(123, notification);

        IntentFilter localIntentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
        localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
        myReceiver searchReceiver = new myReceiver();
        registerReceiver(searchReceiver, localIntentFilter);
        return START_STICKY;
    }
    @Override
    public void onStart(Intent intent, int startId)
    {
// 再次动态注册广播

        super.onStart(intent, startId);
    }
    static int jishu=0;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "定时器服务启动");
        timer = new Timer();//更新时间
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //获取基类
                yonghu yonghu_ = (yonghu) (getApplicationContext());
                if (yonghu_.shifou_chushihua == false) {
                    try {
                        yonghu_.peizhi_.duqu_jiben(getApplicationContext());
                        yonghu_.shifou_chushihua = true;
                    } catch (FileNotFoundException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(jishu>yonghu_.peizhi_.huoqu_jiange*2)
                {
                    jishu=0;
                    //获得组件的服务类
                    AppWidgetManager manager =AppWidgetManager.getInstance(getApplicationContext());

                    ComponentName name = new ComponentName(getApplicationContext(), shiping_Widget.class);
                    int[] appwidgets_ids=  manager.getAppWidgetIds(name);
                    for (int i=0;i<appwidgets_ids.length;i++)
                    {
                        update(appwidgets_ids[i],getApplicationContext());
                    }
                }
                else
                {
                    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String current=format.format(new Date());
                    RemoteViews views=new RemoteViews(getPackageName(), R.layout.shiping__widget);
                    views.setTextViewText(R.id.textView35, current);
                    //获得组件的服务类
                    AppWidgetManager manager=AppWidgetManager.getInstance(getApplicationContext());
                    ComponentName name=new ComponentName(getApplicationContext(), shiping_Widget.class);
                    manager.updateAppWidget(name, views);
                    jishu++;
                }

            }
        }, 0, 500);
    }

    public void update(int appWidgetId,Context context)
    {
        CharSequence widgetText = null;
        try {
            widgetText = shiping_WidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //获取基类
        yonghu yonghu_ = (yonghu) (context.getApplicationContext());
        if (yonghu_.shifou_chushihua == false) {
            try {
                yonghu_.peizhi_.duqu_jiben(context);
                yonghu_.shifou_chushihua = true;
            } catch (FileNotFoundException | JSONException e) {
                e.printStackTrace();
            }
        }
        //刷新数据
        shuju shuju_ = new shuju();
        try
        {
            shuju_.shuaxin_shuju((String) widgetText, yonghu_.peizhi_.huoqu_cook());
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }

        final AppWidgetManager instance = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shiping__widget);
        views.setTextViewText(R.id.textView20, shuju_.mingzi);
        views.setTextViewText(R.id.textView21, shuju_.fenshishu);
        views.setTextViewText(R.id.textView22, shuju_.shuju_dan_.shiping_mingzi_shou);
        views.setTextViewText(R.id.textView23, shuju_.shuju_dan_.bofenshu);
        views.setTextViewText(R.id.textView24, shuju_.shuju_dan_.dianzan);
        views.setTextViewText(R.id.textView25, shuju_.shuju_dan_.toubi);
        views.setTextViewText(R.id.textView26, shuju_.shuju_dan_.shoucang);
        views.setTextViewText(R.id.textView29, shuju_.shuju_dan_.pinglun);
        views.setTextViewText(R.id.textView31, shuju_.shuju_dan_.danmu);
        views.setTextViewText(R.id.textView33, shuju_.shuju_dan_.fenxiang);
        instance.updateAppWidget(appWidgetId, views);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        Intent intent=new Intent("com.my.learn.code.BaseService");
        startService(intent);
    }
}

