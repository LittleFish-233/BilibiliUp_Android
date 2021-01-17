package com.example.bilibiliup;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.json.JSONException;

import java.io.FileNotFoundException;

public class RSSPullService extends IntentService {



    public RSSPullService() {
        super("132");

    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Context context= getBaseContext();
        int appWidgetId=workIntent.getIntExtra("id",0);


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
}
