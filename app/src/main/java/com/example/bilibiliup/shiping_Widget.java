package com.example.bilibiliup;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link shiping_WidgetConfigureActivity shiping_WidgetConfigureActivity}
 */
public class shiping_Widget extends AppWidgetProvider {

     static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) throws IOException, JSONException {
         Intent intent = new Intent(context, RSSPullService.class);
         intent.putExtra("id",appWidgetId);
        try
        {
            context.startService(intent);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            try {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            try {
                shiping_WidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}


