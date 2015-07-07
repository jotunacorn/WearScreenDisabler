package com.afsandeberg.joakim.wearscreendisabler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by joakim on 2015-07-06.
 */
public class InterceptService extends Service {

    WindowManager mang;
    WindowManager.LayoutParams params;
    View mViw;
    public void onCteqwer(int i) {

        Context context = null;
        Class<InterceptService> context1 = InterceptService.class;

        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        mang = ((WindowManager) getSystemService(WINDOW_SERVICE));

        System.out.println("Done with params");

        LayoutInflater inflater = (LayoutInflater) getSystemService
                (LAYOUT_INFLATER_SERVICE);
        mViw = inflater.inflate(R.layout.overlay, null);

        new View.OnDragListener(){
            @Override
            public boolean onDrag(View v, DragEvent event) {

                return false;
            }
        };
        mViw.setOnTouchListener(new View.OnTouchListener() {
            boolean doublePress = false;
            final Handler handler = new Handler();
            Runnable mLongPressed = new Runnable() {
                public void run() {
                    System.out.println("Long press!");
                    if(!doublePress) {
                        handler.removeCallbacks(mLongPressed2);
                        handler.postDelayed(mLongPressed2, 5000);
                        Toast.makeText(getApplicationContext(), "Long press again to stop interception",
                                Toast.LENGTH_SHORT).show();
                        doublePress = true;
                    }
                    else{
                        mang.removeView(mViw);
                        Toast.makeText(getApplicationContext(), "Touch interception stopped",
                                Toast.LENGTH_LONG).show();
                    }


                }
            };
            Runnable mLongPressed2 = new Runnable() {
                public void run() {
                    doublePress = false;
                }
            };
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    handler.postDelayed(mLongPressed, 1000);
                if((event.getAction() == MotionEvent.ACTION_MOVE)||(event.getAction() == MotionEvent.ACTION_UP))
                    handler.removeCallbacks(mLongPressed);
                return true;
            }

        });

        mang.addView(mViw, params);

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        System.out.println("STARTING SERVICE");
        Bundle i = intent.getExtras();
        int userName = 0;
        onCteqwer(userName);
        return START_NOT_STICKY;
    }
}
