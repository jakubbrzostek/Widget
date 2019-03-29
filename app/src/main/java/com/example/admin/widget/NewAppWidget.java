package com.example.admin.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.Delayed;


/**
 *
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    public static  boolean flag,flag2,flag3,flag4;
    private static final String WEB    = "automaticWidgetSyncButtonClick";
    private static final String MUSIC    = "automaticWidget1";
    private static final String IMAGE    = "automaticWidget2";
    private static final String PLAY    = "automaticWidget3";
    private static final String NEXT    = "automaticWidget4";
    private static final String STOP    = "automaticWidget5";
    private static final String PAUSE    = "automaticWidget6";
    private static final String IMAGE_CHANGE    = "automaticWidget7";
    private static final String SHOP_LIST    = "automaticWidget8";

    public static MediaPlayer mp;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setOnClickPendingIntent(R.id.button, getPendingSelfIntent(context, WEB));
        views.setOnClickPendingIntent(R.id.button4, getPendingSelfIntent(context, SHOP_LIST));
        views.setOnClickPendingIntent(R.id.button3, getPendingSelfIntent(context, MUSIC));
        views.setOnClickPendingIntent(R.id.button2, getPendingSelfIntent(context, IMAGE));
        views.setOnClickPendingIntent(R.id.imageButton, getPendingSelfIntent(context, PLAY));
        views.setOnClickPendingIntent(R.id.imageButton2, getPendingSelfIntent(context, NEXT));
        views.setOnClickPendingIntent(R.id.imageButton3, getPendingSelfIntent(context, PAUSE));
        views.setOnClickPendingIntent(R.id.imageButton4, getPendingSelfIntent(context, STOP));
        views.setOnClickPendingIntent(R.id.imageView4, getPendingSelfIntent(context, IMAGE_CHANGE));

        //   views.setTextViewText(R.id.appwidget_text, widgetText); ------ Shoplist


        //--- Shoplist

        appWidgetManager.updateAppWidget(appWidgetId, views);


    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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
    public void onReceive(Context context, Intent intent){

        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
        RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);

        if (WEB.equals(intent.getAction())) {

            Intent webIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.google.com"));
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(webIntent);
            //Toast.makeText(context.getApplicationContext(), "STRING MESSAGE", Toast.LENGTH_LONG).show();
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }
        if (MUSIC.equals(intent.getAction())) {
            mp = MediaPlayer.create(context, R.raw.budzik);
            views.setViewVisibility(R.id.imageView4, View.INVISIBLE);

            if(flag==true) {
                views.setViewVisibility(R.id.imageButton, View.INVISIBLE);
                views.setViewVisibility(R.id.imageButton2, View.INVISIBLE);
                views.setViewVisibility(R.id.imageButton3, View.INVISIBLE);
                views.setViewVisibility(R.id.imageButton4, View.INVISIBLE);
                flag = false;
            }
            else if (flag==false) {
                    views.setViewVisibility(R.id.imageButton, View.VISIBLE);
                    views.setViewVisibility(R.id.imageButton2, View.VISIBLE);
                    views.setViewVisibility(R.id.imageButton3, View.VISIBLE);
                    views.setViewVisibility(R.id.imageButton4, View.VISIBLE);
                flag=true;
            }

            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }

        if (IMAGE.equals(intent.getAction())) {
            views.setViewVisibility(R.id.imageButton, View.INVISIBLE);
            views.setViewVisibility(R.id.imageButton2, View.INVISIBLE);
            views.setViewVisibility(R.id.imageButton3, View.INVISIBLE);
            views.setViewVisibility(R.id.imageButton4, View.INVISIBLE);

            if(flag3==true) {
                views.setViewVisibility(R.id.imageView4, View.VISIBLE);
                flag3 = false;
            }
            else if (flag3==false) {
                views.setViewVisibility(R.id.imageView4, View.INVISIBLE);
                flag3=true;
            }

            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }


        if (PLAY.equals(intent.getAction())) {

            mp.start();
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }
        if (PAUSE.equals(intent.getAction())) {
            mp.pause();
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }
        if (STOP.equals(intent.getAction())) {
            mp.reset();
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }
        if (NEXT.equals(intent.getAction())) {

            mp.reset();

            if(flag2==true) {
                mp = MediaPlayer.create(context, R.raw.samsung);
                flag2 = false;
            }
            else if (flag2==false) {
                mp = MediaPlayer.create(context, R.raw.budzik);
                flag2=true;
            }
            mp.start();
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }

        if (IMAGE_CHANGE.equals(intent.getAction())) {
         // Toast.makeText(context.getApplicationContext(), "image change", Toast.LENGTH_LONG).show();

            if(flag4==true) {
                views.setImageViewResource(R.id.imageView4, R.drawable.audi);
                flag4 = false;
            }
            else if (flag4==false) {
                views.setImageViewResource(R.id.imageView4, R.drawable.apple);
                flag4=true;
            }


            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);

        }

        if ( SHOP_LIST.equals(intent.getAction()) ) {
            setRemoteAdapter(context,views);
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class),views);
        }

    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    //-----Shoplist
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter( R.id.widget_list, new Intent( context, WidgetService.class ) );


    }//---- Shoplist

}

