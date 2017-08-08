package com.bild.sigo.dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.bild.sigo.R;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public class SplashDialog extends Dialog {

    private RelativeLayout relativeLayout_preloader;

    public SplashDialog(Context context) {
        super(context, R.style.Theme_FullScreenDialog);
        setCancelable(true);
        setContentView(R.layout.dialog_splash);
        relativeLayout_preloader = findViewById(R.id.relativeLayout_preloader);
    }

    @Override
    public void show() {
        try {
            if (relativeLayout_preloader.getVisibility() == View.GONE)
                relativeLayout_preloader.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animateDismiss() {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(relativeLayout_preloader, "alpha", 1.0f, 0f);
        fadeOut.setDuration(400);
        fadeOut.start();

        fadeOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    relativeLayout_preloader.setVisibility(View.GONE);
                    dismiss();
                    cancel();
                } catch (Exception e) {
                    Log.d("", e.toString());
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}