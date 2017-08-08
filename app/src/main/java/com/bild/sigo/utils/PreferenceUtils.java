package com.bild.sigo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public class PreferenceUtils {

    private static final String TAG_INICIAR_SESION = "login_iniciar_sesion";

    private static String LOGIN_SESION = "sesion_login";

    private static boolean loginSesion;

    private static Context context;

    public static boolean isIniciarSesion(Context actividad) {
        try {
            if (actividad != null) {
                context = actividad;
            }
            SharedPreferences prefs = actividad.getSharedPreferences(TAG_INICIAR_SESION, Context.MODE_PRIVATE);
            loginSesion = prefs.getBoolean(LOGIN_SESION, false);
            return loginSesion;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setIniciarSesion(boolean loginSesion, Context actividad) {
        if (actividad != null) {
            context = actividad;
        }
        PreferenceUtils.loginSesion = loginSesion;
        SharedPreferences prefs = actividad.getSharedPreferences(TAG_INICIAR_SESION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(LOGIN_SESION, loginSesion);
        editor.apply();
    }
}
