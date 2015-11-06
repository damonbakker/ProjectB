package mobile_development.damon.projectb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by damon on 11/4/2015.
 */
public class SharedPreference
{
    static final String PREF_EMAIL = "email";
    static final String PREF_LOGIN_CHECK = "login";
    static final String PREF_ID = "id";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static boolean checklogin(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_LOGIN_CHECK, false);
    }

    public static void setLogin(Context ctx, String email,int id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EMAIL, email);
        editor.putBoolean(PREF_LOGIN_CHECK, true);
        editor.putInt(PREF_ID,id);
        editor.apply();

    }

    public static void unsetLogin(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_EMAIL);
        editor.remove(PREF_ID);
        editor.putBoolean(PREF_LOGIN_CHECK, false);
        editor.apply();
    }

    public static String getEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "");
    }

    public static int getId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_ID, 0);
    }


}
