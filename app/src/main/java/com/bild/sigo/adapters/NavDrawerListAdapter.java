package com.bild.sigo.adapters;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bild.sigo.R;
import com.bild.sigo.core.models.menu.NavDrawerItem;
import com.bild.sigo.core.views.TextViewCustom;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class NavDrawerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NavDrawerItem> navDrawerItems;
    public String selected = "";
    private OnItemClickListener listener;

    public NavDrawerListAdapter(ArrayList<NavDrawerItem> navDrawerItems, OnItemClickListener listener) {
        this.navDrawerItems = navDrawerItems;
        this.listener = listener;
    }

    private static class ViewHolderUser extends RecyclerView.ViewHolder {

        TextViewCustom name;
        SimpleDraweeView userProfile;

        ViewHolderUser(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtNameUser);
            userProfile = itemView.findViewById(R.id.user_image);

        }
    }

    private static class ViewHolderSeccion extends RecyclerView.ViewHolder {

        TextViewCustom title;
        RelativeLayout relativeLayout_menu;

        ViewHolderSeccion(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            relativeLayout_menu = itemView.findViewById(R.id.relativeLayout_menu);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case NavDrawerItem.USER:
                return new ViewHolderUser(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawerlist_item_user, viewGroup, false));
            case NavDrawerItem.ROW_SECCION:
                return new ViewHolderSeccion(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawerlist_item_row, viewGroup, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        NavDrawerItem item = getItem(position);

        switch (holder.getItemViewType()) {

            case NavDrawerItem.USER:

                ViewHolderUser mHolderuser = (ViewHolderUser) holder;

                mHolderuser.name.setText(item.getTitle());
                mHolderuser.name.setType(TextViewCustom.REGULAR);

                if (!item.getUrlImage().isEmpty())
                    mHolderuser.userProfile.setImageURI(Uri.parse(item.getUrlImage()));

                break;

            case NavDrawerItem.ROW_SECCION:
                final ViewHolderSeccion mHolderSeccion = (ViewHolderSeccion) holder;

                mHolderSeccion.title.setText(item.getTitle());
                mHolderSeccion.title.setType(TextViewCustom.REGULAR);

                if (selected.equalsIgnoreCase(item.getTitle())) {
                    mHolderSeccion.relativeLayout_menu.setBackgroundColor(Color.parseColor("#0072A4"));
                } else {
                    mHolderSeccion.relativeLayout_menu.setBackgroundColor(Color.parseColor("#005C84"));
                }

                mHolderSeccion.relativeLayout_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(mHolderSeccion.title.getText().toString(), position, v);
                    }
                });

                break;
        }

    }

    public NavDrawerItem getItem(int position) {
        return navDrawerItems.get(position);
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    @Override
    public int getItemViewType(int position) {
        return navDrawerItems.get(position).getType();
    }

    public interface OnItemClickListener {
        void onItemClick(String item, int position, View view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return navDrawerItems.size();
    }

}