package com.bild.sigo.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;

import com.bild.sigo.BaseActivity;
import com.bild.sigo.R;
import com.bild.sigo.dialog.SplashDialog;
import com.bild.sigo.utils.PreferenceUtils;
import com.bild.sigo.utils.Utils;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public class SplashActivity extends BaseActivity {

    private SplashDialog splashDialog;
    private Handler handler = new Handler();

    private static final int REQUEST_STATE_PERMISSION = 225;

    private String[] mPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public void initApp() {

        setContentView(R.layout.dialog_splash);
        startApp();

    }

    private void startApp() {
        if (checkPermission())
            getDetails(true);
        else
            requestPermission();
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(this, mPermission[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, mPermission[1]) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, mPermission, REQUEST_STATE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STATE_PERMISSION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length == 2) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                        getDetails(true);

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {

                        //Show permission explanation dialog...
                        new AlertDialog.Builder(new ContextThemeWrapper(SplashActivity.this, android.R.style.Theme_Dialog))
                                .setTitle(getResources().getString(R.string.app_name))
                                .setMessage("Para ingresar a la app debes permitir este permiso. ¿Intentar nuevamente?")
                                .setPositiveButton("Dar permisos", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        if (ContextCompat.checkSelfPermission(SplashActivity.this, mPermission[0]) == PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(SplashActivity.this, mPermission[1]) == PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(SplashActivity.this, mPermission[2]) == PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(SplashActivity.this, mPermission[3]) == PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(SplashActivity.this, mPermission[4]) == PackageManager.PERMISSION_GRANTED) {
                                            getDetails(true);

                                        } else {
                                            //Do the stuff that requires permission...
                                            ActivityCompat.requestPermissions(SplashActivity.this, mPermission, REQUEST_STATE_PERMISSION);
                                        }

                                    }
                                })
                                .setNegativeButton("Salir de la App", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Utils.showToast("Puede cambiar los permisos de SIGO desde el menú Configuración en tu dispositivo", SplashActivity.this);
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                } else {
                    getDetails(false);
                }
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getDetails(boolean accepted) {

        if (accepted) {

            showProgress();
            checkValues();

            /*if ("SI DATA ES..." == null) {

                //SE LLAMA A SERVICIO
                core.championship.getChampionshipDefault(callbackDefault);

            } else {

                showProgress();
                checkValues();
            }*/

        } else {
            if (checkPermission())
                getDetails(true);
            else
                requestPermission();
        }
    }

    private void checkValues() {

        if (!PreferenceUtils.isIniciarSesion(SplashActivity.this)) {
            dismiss(true);
        } else {

            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.anim_bottom);
            finish();

        }

    }

    public void dismiss(boolean wait) {

        if (wait) {
            handler.postDelayed(splash, 4 * 1000);
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.anim_bottom);
            finish();
        }

    }

    private Runnable splash = new Runnable() {
        @Override
        public void run() {

            dismissProgress();
            //startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            handler.removeCallbacksAndMessages(null);

            finish();

        }
    };

    private void showProgress() {
        splashDialog = new SplashDialog(this);
        splashDialog.show();
        splashDialog.setCancelable(false);
    }

    private void dismissProgress() {

        if (splashDialog != null && splashDialog.isShowing())
            splashDialog.animateDismiss();

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            dismissProgress();
        } catch (Exception e) {
            e.printStackTrace();
            splash = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dismissProgress();
        } catch (Exception e) {
            e.printStackTrace();
            splash = null;
        }
    }

}
