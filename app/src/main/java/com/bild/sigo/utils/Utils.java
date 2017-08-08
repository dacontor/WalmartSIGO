package com.bild.sigo.utils;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public class Utils {

    public static void releaseMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("Libera memoria", "");
    }

    public static void ocultarTeclado(EditText fieldText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fieldText.getWindowToken(), 0);
    }

    public static void showToast(String msg, Context mContext) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

}
