package com.bild.sigo.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.bild.sigo.BaseActivity;
import com.bild.sigo.R;
import com.bild.sigo.adapters.NavDrawerListAdapter;
import com.bild.sigo.core.models.menu.NavDrawerItem;
import com.bild.sigo.fragments.ViewPagerFragment;
import com.bild.sigo.utils.PreferenceUtils;
import com.bild.sigo.utils.SystemUtils;
import com.bild.sigo.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Daniel Contreras on 07-08-17.
 * '
 */

public class HomeActivity extends BaseActivity {

    private Fragment currentFragment = null;
    private DrawerLayout mDrawerLayout;
    private RecyclerView left_drawer;
    private NavDrawerListAdapter adapter;

    private String titleMenu = "";

    @Override
    public void initApp() {

        setContentView(R.layout.activity_home);
        initVariables();

        configMenu();
        initHome();
        createActionBarHome();
        openCloseMenu();
    }

    private void initVariables() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        left_drawer = (RecyclerView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.START);

    }

    private void configMenu() {

        LinearLayoutManager llm = new LinearLayoutManager(HomeActivity.this);
        left_drawer.setHasFixedSize(true);
        left_drawer.setLayoutManager(llm);

        String url = "";
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<>();

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        try {

            String name = "Usuario Prueba";
            url = "photo.das";

            /*if (!"".equals(idDevice) && idDevice != null) {

                if (idDevice.matches("[0-9]+") && idDevice.length() > 2)
                    url = "https://graph.facebook.com/" + idDevice + "/picture?width=200&height=200";
                else if (user.getFavorite() != null)
                    url = user.getFavorite().getIdTeam_Team().getShieldImage();*/

            navDrawerItems.add(new NavDrawerItem(
                    name,
                    NavDrawerItem.USER,
                    url));

        } catch (Exception e) {
            navDrawerItems.add(new NavDrawerItem("Nuevo usuario", NavDrawerItem.USER, url));
            e.printStackTrace();
        }

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], NavDrawerItem.ROW_SECCION));      // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], NavDrawerItem.ROW_SECCION));      // Aplicaciones
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], NavDrawerItem.ROW_SECCION));      // Indicadores
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], NavDrawerItem.ROW_SECCION));      // Inbox
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], NavDrawerItem.ROW_SECCION));      // Logout

        adapter = new NavDrawerListAdapter(navDrawerItems, new NavDrawerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String data, int position, View view) {
                if (data != null) {

                    RelativeLayout relativeLayout_menu = view.findViewById(R.id.relativeLayout_menu);

                    titleMenu = adapter.selected;

                    if (!adapter.selected.equalsIgnoreCase(adapter.getItem(position).getTitle())) {
                        relativeLayout_menu.setBackgroundColor(Color.parseColor("#005C84"));
                        relativeLayout_menu.setBackgroundColor(Color.parseColor("#0072A4"));
                        adapter.setSelected(adapter.getItem(position).getTitle());
                        adapter.notifyDataSetChanged();
                    }

                    changeContentByMenu(data);

                }

            }
        });

        adapter.setSelected(titleMenu);
        left_drawer.setAdapter(adapter);

    }

    private void initHome() {

        ViewTreeObserver vto = left_drawer.getViewTreeObserver();
        if (vto.isAlive()) {

            currentFragment = ViewPagerFragment.newInstance(ViewPagerFragment.TYPE_HOME);

            SystemUtils.replaceFragment(HomeActivity.this,
                    R.id.container,
                    "MainHomeFragment",
                    true,
                    null,
                    currentFragment);

        }

    }

    public void changeContentByMenu(String title) {

        if (title.equalsIgnoreCase("Home")) {

            closeMenu();
            //startActivity(new Intent(HomeActivity.this, LoginPremiumActivity.class));

        } else if (title.equalsIgnoreCase("Aplicaciones")) {

            closeMenu();
            //startActivity(new Intent(HomeActivity.this, LoginPremiumActivity.class));

        } else if (title.equalsIgnoreCase("Indicadores")) {

            closeMenu();
            //initTrivias();

        } else if (title.equalsIgnoreCase("Inbox")) {

            closeMenu();

           /* currentFragment = ViewPagerFragment.newInstance(ViewPagerFragment.TYPE_HOME);
            clearBackStack(true);
            changeFragment("MainHomeFragment", currentFragment);
            resetActionbar();*/

        } else if (title.equalsIgnoreCase("Logout")) {

            cerrarSesion();

        }

        Utils.releaseMemory();

    }

    private void cerrarSesion() {

        closeMenu();
        Intent intent2 = new Intent(HomeActivity.this, LoginActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PreferenceUtils.setIniciarSesion(false, HomeActivity.this);

        //LoginManager.getInstance().logOut();
        startActivity(intent2);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.anim_bottom);

        clearBackStack(false);
        Utils.releaseMemory();
    }

    private void openCloseMenu() {
        actionBar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(left_drawer)) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(left_drawer)) {
            closeMenu();
        } else {

            super.onBackPressed();
            adapter.setSelected("Home");
            adapter.notifyDataSetChanged();

        }

    }

    private void closeMenu() {

        mDrawerLayout.closeDrawer(left_drawer);

    }

    private void openMenu() {

        mDrawerLayout.openDrawer(left_drawer);

    }

    private void clearBackStack(boolean condition) {
        SystemUtils.cleanBackStack(HomeActivity.this, condition);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.setSelected(titleMenu);
            adapter.notifyDataSetChanged();
        }
    }


}
