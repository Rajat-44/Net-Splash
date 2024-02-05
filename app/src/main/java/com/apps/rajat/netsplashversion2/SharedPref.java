package com.apps.rajat.netsplashversion2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HP on 12/23/2017.
 */

public class SharedPref {
    SharedPreferences mySharePref;
    public SharedPref(Context context){
        mySharePref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);


    }

    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mySharePref.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }
    public boolean loadNightModeState(){
        Boolean state = mySharePref.getBoolean("NightMode", false);
        return state;
    }
}
