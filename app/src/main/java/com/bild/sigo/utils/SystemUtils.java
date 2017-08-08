package com.bild.sigo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public class SystemUtils {

    /**
     * Gets the screen size on a array. 0 is the "width" coordinate, 1 is the "height".
     *
     * @param context : the current context on the application
     * @return an array containing the width (0) and the height (1)
     */
    public static int[] getScreenSize(Context context) {
        int[] coordinates = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        coordinates[0] = display.getWidth();
        coordinates[1] = display.getHeight();
        return coordinates;
    }

    public static boolean isTablet(Context context) {

        int size = 0;
        try {

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            size = (int) Math.sqrt(Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2) + Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2));

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return size >= 7;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void replaceFragment(FragmentActivity activity,
                                       final int container,
                                       final String fragmentName,
                                       final boolean addToBackStack,
                                       final Bundle b,
                                       Fragment currentFragment) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (b != null)
            currentFragment.setArguments(b);

        //if the fragment is already there
        if (fragmentManager.findFragmentByTag(fragmentName) != null) {
            Log.e("SystemUtils", fragmentName + " fragment is already there");
            //fragmentTransaction.remove(fragmentManager.findFragmentByTag(fragmentName));
            fragmentTransaction.replace(container, currentFragment, fragmentName);
        } else {
            Log.e("SystemUtils", fragmentName + " new fragment");
            fragmentTransaction.add(container, currentFragment, fragmentName);
        }

        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void cleanBackStack(FragmentActivity activity, Boolean isHome) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        int length = fragmentManager.getBackStackEntryCount() - 1;

        for (int i = length; i >= 1; i--) {
            Log.e("Largo Pila", "->" + i);
            Log.e("Fragment ", "->" + fragmentManager.getFragments().get(i));
            fragmentManager.popBackStack();
        }

        if (isHome)
            fragmentManager.popBackStack();
    }

}
