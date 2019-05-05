package com.gimus.sensor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import com.gimus.gimus_lib.audio.SoundPlayer;

public class A extends Application {
    public static A a;
    public static Client client;
    public MainActivity mainActivity;
    protected SoundPlayer sp;

    public static Application getApplication() {
        return (Application) A.a;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }
    public  boolean clickOnNewServerUpdate=false;

    @Override
    public void onCreate() {
        super.onCreate();
        a=this;
        handler.sendMessageDelayed(handler.obtainMessage(), 2*R.integer.tick);
        sp = new SoundPlayer(getContext());
        clickOnNewServerUpdate=A.getResourceBoolean(R.bool.beepOnNewServerUpdate);
    }

    public static String getResourceString(int id) {
        return getContext().getResources().getText(id).toString();
    }
    public static boolean getResourceBoolean(int id) {
        return  getContext().getResources().getBoolean(id);
    }

    public static void click() {
        a.sp.play(3);
    }

    public static void sensorValueSentClick(){
        if (a.clickOnNewServerUpdate) click();
    }


    public static void savePreferenceString(String preferenceName, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preferenceName, value); // value to store
        editor.commit();
    }

    public static String getPreferenceString(String preferenceName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString(preferenceName, "");
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mainActivity !=null ) mainActivity.tick();
            handler.sendMessageDelayed( handler.obtainMessage(), 3000);
            return false;
        }
    });

}