package com.bild.sigo.core.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bild.sigo.R;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class CustomPagerStrip extends PagerTabStrip {

    public CustomPagerStrip(Context context) {
        super(context);
    }

    public CustomPagerStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomPagerStrip);

        int indicatorColor = a.getColor(R.styleable.CustomPagerStrip_indicatorColor, Color.BLACK);

        if (indicatorColor == Color.TRANSPARENT) {
            setDrawFullUnderline(false);
        } else {
            setTabIndicatorColor(indicatorColor);
            setDrawFullUnderline(a.getBoolean(R.styleable.CustomPagerStrip_isUnderlined, false));
        }

        if (!isInEditMode()) {
            //Typeface regular = Typeface.createFromAsset(context.getAssets(), ".ttf");

            for (int i = 0; i < getChildCount(); ++i) {
                View nextChild = getChildAt(i);
                if (nextChild instanceof TextView) {
                    TextView textViewToConvert = (TextView) nextChild;
                    //textViewToConvert.setTypeface(regular);
                    textViewToConvert.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.CustomPagerStrip_fontSize, 12));
                    textViewToConvert.setGravity(Gravity.CENTER);
                }
            }
        }

        setTextColor(a.getColor(R.styleable.CustomPagerStrip_fontColor, getResources().getColor(R.color.colorAccent)));

        a.recycle();
    }
}