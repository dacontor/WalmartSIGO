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
import com.bild.sigo.adapters.IndicatorsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class IndicatorsFragment extends Fragment {

    private BaseActivity activity;
    private IndicatorsAdapter adapter;

    private RecyclerView listIndicadores;

    private List<String> indicators;

    public static IndicatorsFragment newInstance() {

        //IndicatorsFragment fragment = new IndicatorsFragment();
        //Bundle b = new Bundle();
        //fragment.setArguments(b);

        return new IndicatorsFragment();
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

        indicators = new ArrayList<>();

        indicators.add("Indicators 1");
        indicators.add("indicators 2");
        indicators.add("indicators 3");
        indicators.add("indicators 4");
        indicators.add("indicators 5");
        indicators.add("indicators 6");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listview_indicadores, container, false);
        listIndicadores = view.findViewById(R.id.listIndicadores);

        listIndicadores.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listIndicadores.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new IndicatorsAdapter(activity, indicators, listener);
        listIndicadores.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private IndicatorsAdapter.OnItemClickListener listener = new IndicatorsAdapter.OnItemClickListener() {
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
