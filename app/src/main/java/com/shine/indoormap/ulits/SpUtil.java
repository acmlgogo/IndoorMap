package com.shine.indoormap.ulits;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Preference 集成类
 *
 * @author ml
 */
public class SpUtil {

    /**
     * 屏保json
     * */
    public  static final String SCREEN_JSON="SCREEN_JSON";

    //id
    public static final String ID = "id";

    /**
     * 窗口名字
     */
    public static final String WINDDOWNAME = "windowsname";
    /**
     * 设备ID
     */
    public static final String DEVICE = "device";

    /**
     * 操作员 ID
     */
    public static String PERSONNELID = "PersonnelId";

    /**
     * 服务器IP
     **/
    public static final String SERVERIP = "serverip";
    /**
     * 记住密码
     */
    public static final String REMEMBERPWD = "rememberpwd";
    /**
     * 自动登录
     */
    public static final String AUTOLOGIN = "autlogin";
    /**
     * 服务器IP
     */
    public static final String IP = "ip";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";
    /**
     * 密码
     */
    public static final String MATERIALJSON = "materialjson";

    /***
     * 开启屏保状态
     * */
    public static final String ISSTOP = "isstop";
    /**
     * 已登录账号
     */
    public static final String LOGIN = "login";
    /**
     * 语音状态
     */
    public static final String VOICEON = "voiceOn";

    /****
     *
     *项目名称
     *
     * */
    public static final String PROJECT = "project";

    private static SharedPreferences settings;
    private static SpUtil spUtil;

    public static SpUtil init(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        if (spUtil == null) {
            spUtil = new SpUtil();
        }
        return spUtil;
    }

    public SpUtil saveString(String key, String value) {
        if (settings == null) {
            throw new RuntimeException("必须先初始化");
        }
        settings.edit().putString(key, value).commit();
        return spUtil;
    }


    public String getString(String key) {
        return settings.getString(key, "");
    }

    public String getString(String key, String defaultStr) {
        return settings.getString(key, defaultStr);
    }

    public int getInt(String key) {
        return settings.getInt(key, 0);
    }

    public void setInt(String key, int value) {
        settings.edit().putInt(key, value).commit();
    }

    public long getLong(String key) {
        return settings.getLong(key, 0);
    }

    public void setLong(String key, long value) {
        settings.edit().putLong(key, value).commit();
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(key, "");
    }

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getInt(key, 0);
    }

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void setInt(Context context, String key, int value) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor edit = defaultSharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean(Context context, String kay) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean aBoolean = defaultSharedPreferences.getBoolean(kay, false);
        return aBoolean;
    }

    /**
     * 清除默认的Preference中的内容
     *
     * @param context
     */
    public static void clearPreference(Context context) {
        SharedPreferences p = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    public static void clearPreferenceContent(Context context, String key) {
        SharedPreferences p = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = p.edit();
        editor.remove(key);
        editor.commit();
    }
}