package com.bild.sigo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bild.sigo.BaseActivity;
import com.bild.sigo.R;
import com.bild.sigo.adapters.ApplicationsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class ApplicationsFragment extends Fragment {

    private BaseActivity activity;
    private ApplicationsAdapter adapter;

    private RecyclerView listApps;

    private List<String> applications;

    public static ApplicationsFragment newInstance() {

        //ApplicationsFragment fragment = new ApplicationsFragment();
        //Bundle b = new Bundle();
        //fragment.setArguments(b);

        return new ApplicationsFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseActivity) activity;

        if (this.activity == null) {
            this.activity = (BaseActivity) getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bundle b = getArguments();
        //estado = b.getString("estado", "");

        applications = new ArrayList<>();

        applications.add("Application 1");
        applications.add("Application 2");
        applications.add("Application 3");
        applications.add("Application 4");
        applications.add("Application 5");
        applications.add("Application 6");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listview_applications, container, false);
        listApps = view.findViewById(R.id.listApps);

        listApps.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listApps.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ApplicationsAdapter(activity, applications, listener);
        listApps.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private ApplicationsAdapter.OnItemClickListener listener = new ApplicationsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String data, int position, View view) {
            if (data != null) {

                Toast.makeText(activity, "hola mundo", Toast.LENGTH_SHORT).show();

            }

        }
    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
