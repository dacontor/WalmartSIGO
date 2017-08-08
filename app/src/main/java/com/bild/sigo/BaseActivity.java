package com.bild.sigo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bild.sigo.core.ServiceCore;
import com.bild.sigo.core.views.TextViewCustom;
import com.bild.sigo.utils.SystemUtils;
import com.bild.sigo.utils.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public abstract class BaseActivity extends AppCompatActivity {

    private int ACTIVITY_INTERNET = 6783;

    private int width, height;

    public boolean IS_TABLET;

    private ActionBar actionBar;
    public ServiceCore core;

    public ImageButton actionBar_menu, actionBar_back;
    private View view_menu, view_back;

    public static final int ERROR_NO_INTERNET = 0;
    public static final int ERROR_TIMEOUT = 1;
    public static final int ERROR_GENERAL = 2;
    public static final int ERROR_SERVICE = 3;
    public static final int ERROR_NO_PLAY = 4;
    public static final int ERROR_CMD_LIVE = 5;

    public View actionBarView, actionBar_logo;

    private Rect actionBarLogoRect = new Rect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(getApplicationContext());

        core = ServiceCore.getInstance(ServiceCore.baseURL);

        width = SystemUtils.getScreenSize(BaseActivity.this)[0];
        height = SystemUtils.getScreenSize(BaseActivity.this)[1];

        IS_TABLET = SystemUtils.isTablet(BaseActivity.this);

        if (IS_TABLET)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        checkInternet();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (core == null)
            core = ServiceCore.getInstance();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void checkInternet() {
        if (!SystemUtils.isNetworkAvailable(BaseActivity.this)) {
            showErrorDialog(ERROR_NO_INTERNET);
        } else {
            getSupportActionBar().hide();
            initApp();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_INTERNET) {
            checkInternet();
        }

    }

    public abstract void initApp();

    protected void createActionBarHome() {

        actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        final LayoutInflater inflater = getLayoutInflater();

        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        layout.setMargins(0, 0, 0, 0);

        actionBarView = inflater.inflate(R.layout.actionbar, null);

        actionBar_logo = actionBarView.findViewById(R.id.actionBar_logo);
        actionBar_menu = actionBarView.findViewById(R.id.actionBar_menu);
        actionBar_back = actionBarView.findViewById(R.id.actionBar_back);

        view_back = actionBarView.findViewById(R.id.view_back);
        view_menu = actionBarView.findViewById(R.id.view_menu);

        actionBar_back.setOnClickListener(backListener);
        view_back.setOnClickListener(backListener);

        toggleNavIcon(true);

        actionBar.setCustomView(actionBarView, layout);
        actionBar.setDisplayShowCustomEnabled(true);

        Toolbar toolbar = (Toolbar) actionBarView.getParent();
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setPadding(0, 0, 0, 0);

        if (!actionBar.isShowing())
            actionBar.show();

    }

    /**
     * Toggles the menu icon
     *
     * @param isMenu : <b>true</b> to show the menu icon, <b>false</b> to show the back button
     */
    public void toggleNavIcon(boolean isMenu) {

        try {
            if (actionBar_back != null)
                actionBar_back.setVisibility(isMenu ? View.GONE : View.VISIBLE);

            if (view_back != null)
                view_back.setVisibility(isMenu ? View.GONE : View.VISIBLE);

            if (actionBar_menu != null)
                actionBar_menu.setVisibility(isMenu ? View.VISIBLE : View.GONE);

            if (view_menu != null)
                view_menu.setVisibility(isMenu ? View.VISIBLE : View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int calcSize(double percentage) {
        return (int) (percentage * width / 100);
    }

    public int calcSizeH(double percentage) {
        return (int) (percentage * height / 100);
    }

    private View.OnClickListener backListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            setResult(RESULT_OK, new Intent());
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {

        try {
            FragmentManager fm = getSupportFragmentManager();

            int backCount = fm.getBackStackEntryCount();

            Log.e("BaseActivity", "backstack: " + backCount);

            if (backCount > 1) {
                Log.e("BaseActivity", "popping backstack");
                fm.popBackStack();
            } else {
                fm.popBackStack();
                backCount = fm.getBackStackEntryCount();

                Log.e("BaseActivity", "last one: " + backCount);
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.onBackPressed();
        }

        Utils.releaseMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public void showErrorDialog(int type) {

        /*String message;
        AlertDialog.Builder builder;

        switch (type) {
            case ERROR_NO_INTERNET:
                message = getString(R.string.error_no_internet);

                builder = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Sin conexión")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                //startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), ACTIVITY_INTERNET);
                                try {
                                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    finish();
                                }

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                onBackPressed();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);

                break;
            case ERROR_TIMEOUT:
                message = getString(R.string.error_timeout);

                builder = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Error")
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                onBackPressed();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);

                break;
            case ERROR_GENERAL:
                message = getString(R.string.error_general);

                builder = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Error")
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                onBackPressed();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                break;

            case ERROR_SERVICE:
                message = getString(R.string.error_service);

                builder = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Error")
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                onBackPressed();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                break;

            case ERROR_CMD_LIVE:
                message = getString(R.string.error_cmd_live);

                builder = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Error")
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                break;

            case ERROR_NO_PLAY:
                message = getString(R.string.error_no_play);
                builder = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Reproductor")
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                onBackPressed();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                break;
            default:
                message = getString(R.string.error_no_internet);

                builder = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Sin conexión")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), ACTIVITY_INTERNET);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                onBackPressed();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                break;
        }

        builder.show();
        AlertDialog errorDialog = builder.show();
        if (!errorDialog.isShowing())
            errorDialog.show();*/
    }

    @Override
    public void onLowMemory() {
        Utils.releaseMemory();
    }

}
