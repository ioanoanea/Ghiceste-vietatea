package com.ioanoanea.gicestevietateaclona.objects;

import android.content.Context;
import android.content.SharedPreferences;

public class InformationManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    public static boolean ON = true;
    public static boolean OFF = false;

    public InformationManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("INFO", 0);
        editor = sharedPreferences.edit();
    }

    public void setLevel(int level){
        editor.putInt("LEVEL",level);
        editor.putBoolean("SET", true);
        editor.apply();
    }

    public void nextLevel(){
        if(getLastUnlockedLevel() < getLevel() + 1){
            setLastUnlockedLevel(getLevel() + 1);
        }
        setLevel(getLevel() + 1);
    }

    public int getLevel(){
        return sharedPreferences.getInt("LEVEL", 0);
    }

    public void setCoins(int coins){
        editor.putInt("COINS",coins);
        editor.putBoolean("SET", true);
        editor.apply();
    }

    public void addCoins(int coins){
        setCoins(getCoins() + coins);
    }

    public void removeCoins(int coins){
        setCoins(getCoins() - coins);
    }

    public int getCoins(){
        return sharedPreferences.getInt("COINS",0);
    }

    public boolean isSet(){
        return sharedPreferences.getBoolean("SET", false);
    }

    public void setLastUnlockedLevel(int level){
        editor.putInt("LAST_UNLOKED_LEVEL", level);
    }

    public int getLastUnlockedLevel(){
        return sharedPreferences.getInt("LAST_UNLOKED_LEVEL",1);
    }

    public void setSound(boolean sound){
        editor.putBoolean("SOUND", sound);
        editor.apply();
    }

    public boolean isSoundOn(){
        return sharedPreferences.getBoolean("SOUND",true);
    }
}
