package com.bild.sigo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bild.sigo.BaseActivity;
import com.bild.sigo.R;
import com.bild.sigo.core.views.TextViewCustom;

import java.util.List;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class ApplicationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> listApps;
    private BaseActivity activity;
    private OnItemClickListener listener;

    public ApplicationsAdapter(BaseActivity activity, List<String> listApps, OnItemClickListener listener) {
        this.listApps = listApps;
        this.activity = activity;
        this.listener = listener;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextViewCustom title;
        RelativeLayout rlApplication;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rlApplication = itemView.findViewById(R.id.rlApplication);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        return new ApplicationsAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_application, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        String item = getItem(position);

        final ApplicationsAdapter.ViewHolder mHolderSeccion = (ApplicationsAdapter.ViewHolder) holder;

        mHolderSeccion.title.setText(item);
        mHolderSeccion.title.setType(TextViewCustom.REGULAR);

        mHolderSeccion.rlApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mHolderSeccion.title.getText().toString(), position, v);
            }
        });

    }

    public String getItem(int position) {
        return listApps.get(position);
    }

    /*@Override
    public int getItemViewType(int position) {
        return navDrawerItems.get(position).getType();
    }*/

    public interface OnItemClickListener {
        void onItemClick(String item, int position, View view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return listApps.size();
    }

}