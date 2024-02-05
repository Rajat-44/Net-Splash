package com.apps.rajat.netsplashversion2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HP on 12/23/2017.
 */

public class SharedPrefLoadGoogleAuto {
    SharedPreferences mySharePref;
    public SharedPrefLoadGoogleAuto(Context context){
        mySharePref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);


    }

    public void setGoogleIsOnState(Boolean state){
        SharedPreferences.Editor editor = mySharePref.edit();
        editor.putBoolean("GoogleIsOn", state);
        editor.commit();
    }
    public boolean loadGoogleIsOnState(){
        Boolean state = mySharePref.getBoolean("GoogleIsOn", false);
        return state;
    }
}
