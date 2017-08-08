package com.bild.sigo.core.models.menu;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class NavDrawerItem {

    public static final int USER = 1;
    public static final int ROW_SECCION = 2;

    private int type;
    private String title;
    private int iconRes;
    private String urlImage;

    public NavDrawerItem(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public NavDrawerItem(String title, int iconRes, int type) {
        this.type = type;
        this.title = title;
        this.iconRes = iconRes;
    }

    public NavDrawerItem(String title, int type, String urlImage) {
        this.type = type;
        this.title = title;
        this.urlImage = urlImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
