package com.example.bilibiliup;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;

import java.io.FileNotFoundException;

/**
 * The configuration screen for the {@link shiping_Widget shiping_Widget} AppWidget.
 */
public class shiping_WidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.example.bilibiliup.shiping_Widget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Context context=this;
    EditText mAppWidgetText;
    TextView textView;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = shiping_WidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();
            try {
                saveTitlePref(context, mAppWidgetId, widgetText);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            try {
                shiping_Widget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
            } catch (Exception e) {
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "请输入正确的UUID", duration);
                toast.show();

                e.printStackTrace();
                return;
            }



            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

            setResult(RESULT_OK, resultValue);
            finish();

        }
    };

    public shiping_WidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) throws FileNotFoundException, JSONException {
        yonghu yonghu_ = (yonghu) context.getApplicationContext();
        yonghu_.widgets_id.baocun_widgets_id(context,appWidgetId,text);
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) throws FileNotFoundException, JSONException {
        yonghu yonghu_ = (yonghu) context.getApplicationContext();
        return yonghu_.widgets_id.huoqu_up(context,appWidgetId);
    }

    static void deleteTitlePref(Context context, int appWidgetId) throws FileNotFoundException, JSONException {
        yonghu yonghu_ = (yonghu) context.getApplicationContext();
        yonghu_.widgets_id.shanchu(context,appWidgetId);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.shiping__widget_configure);
        mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        findViewById(R.id.button5).setOnClickListener(onclicklistener_1);
        textView = (TextView) findViewById(R.id.textView20);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

    }
    View.OnClickListener onclicklistener_1=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String widgetText = mAppWidgetText.getText().toString();
            //获取基类
            yonghu yonghu_ = (yonghu) (context.getApplicationContext());
            if(yonghu_.shifou_chushihua==false)
            {
                try {
                    yonghu_.peizhi_.duqu_jiben(context);
                    yonghu_.shifou_chushihua=true;
                } catch (FileNotFoundException | JSONException e) {
                    e.printStackTrace();
                }
            }
            //刷新数据
            shuju shuju_=new shuju();
            try {
                shuju_.shuaxin_shuju((String) widgetText,yonghu_.peizhi_.huoqu_cook());
            } catch (Exception e) {
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "请输入正确的UUID", duration);
                toast.show();
                e.printStackTrace();
            }

            ((TextView)findViewById(R.id.textView20)).setText(shuju_.mingzi);
            ((TextView)findViewById(R.id.textView21)).setText(shuju_.fenshishu);
            ((TextView)findViewById(R.id.textView22)).setText(shuju_.shuju_dan_.shiping_mingzi_shou);
            ((TextView)findViewById(R.id.textView23)).setText(shuju_.shuju_dan_.bofenshu);
            ((TextView)findViewById(R.id.textView24)).setText(shuju_.shuju_dan_.dianzan);
            ((TextView)findViewById(R.id.textView25)).setText(shuju_.shuju_dan_.toubi);
            ((TextView)findViewById(R.id.textView26)).setText(shuju_.shuju_dan_.shoucang);
            ((TextView)findViewById(R.id.textView29)).setText(shuju_.shuju_dan_.pinglun);
            ((TextView)findViewById(R.id.textView31)).setText(shuju_.shuju_dan_.danmu);
            ((TextView)findViewById(R.id.textView33)).setText(shuju_.shuju_dan_.fenxiang);

        }
    };
}