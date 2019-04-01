package com.kaida.apple.kaidacode;

import android.content.Context;
import android.content.SharedPreferences;

public class CodeSettins {


    private static final String IP_Adress = "ip";

    private static final String SB_ID = "id";

    private static final String CODE_Setting = "setting";

    private static CodeSettins instance;

    private Context context;

    public CodeSettins(Context context) {
        this.context = context;

    }

    public synchronized static CodeSettins getInstance(Context context) {
        if (instance == null) {
            instance = new CodeSettins(context);
        }
        return instance;
    }

    public SharedPreferences load(Context context) {
        return context.getSharedPreferences(CODE_Setting, Context.MODE_PRIVATE);
    }

    public void putString(Context context, String key, String value) {
        SharedPreferences preferences = load(context);
        if (null != preferences) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key,value);
            editor.commit();
        }
    }

    public String getString(Context context,String key) {
        SharedPreferences preferences = load(context);
        if (null != preferences) {
            return preferences.getString(key,null);
        }
        return null;
    }


    public void setIP_Adress(String code_ip) {putString(context, IP_Adress,code_ip);}

    public String getIP_Adress() {return getString(context,IP_Adress);}

    public void setSbId (String sb_id) {putString(context,SB_ID,sb_id);}

    public String getSbId() {
        return getString(context,SB_ID);
    }
}
