package com.bild.sigo.core.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class TextViewCustom extends TextView {

    public static final int REGULAR = 1;
    public static final int BOLD = 2;

    public Typeface regular;
    private Typeface bold;

    private int type = 1;

    public TextViewCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            init(context);
    }

    public TextViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context);
    }

    public TextViewCustom(Context context) {
        super(context);
        if (!isInEditMode())
            init(context);
    }

    private void init(Context context) {
        //regular = Typeface.createFromAsset(context.getAssets(), ".ttf");
        //bold = Typeface.createFromAsset(context.getAssets(), ".ttf");

        switch (type) {
            case REGULAR:
                this.setTypeface(regular);
                break;
            case BOLD:
                this.setTypeface(bold);
                break;
            default:
                this.setTypeface(regular);
                break;
        }
    }

    public void setType(int type) {
        this.type = type;
        switch (type) {

            case REGULAR:
                this.setTypeface(regular);
                break;

            case BOLD:
                this.setTypeface(bold);
                break;
        }
    }
}