package com.bild.sigo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bild.sigo.BaseActivity;
import com.bild.sigo.R;
import com.bild.sigo.adapters.FragmentViewPagerAdapter;
import com.bild.sigo.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Daniel Contreras on 08-08-17.
 * '
 */

public class ViewPagerFragment extends Fragment {

    private ArrayList<Fragment> allItems;
    private ArrayList<String> titles;

    private BaseActivity activity;
    public ViewPager mViewPager;
    public static int type;

    public static final int TYPE_HOME = 0;
    public static final int TYPE_VIDEO = 2;

    public static ViewPagerFragment newInstance(int type) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle b = new Bundle();
        b.putInt("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseActivity) activity;

        titles = new ArrayList<>();
        allItems = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mViewPager = view.findViewById(R.id.pager);
        ((ViewPager.LayoutParams) (view.findViewById(R.id.pager_header)).getLayoutParams()).isDecor = true;

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = getArguments();
        if (b != null && mViewPager != null) {

            if (titles.size() != 0)
                titles.clear();

            if (allItems.size() != 0)
                allItems.clear();

            if (type != -1) {
                type = b.getInt("type");
                switch (type) {
                    case TYPE_HOME:

                        titles.add("INDICADORES");
                        titles.add("APLICACIONES");

                        allItems.add(IndicatorsFragment.newInstance());
                        allItems.add(ApplicationsFragment.newInstance());

                        initFragments(allItems.size());

                        break;

                }

                Log.e("MainFragment", "position: " + b.getInt("position", 0));

                mViewPager.setCurrentItem(b.getInt("position", 0), true);
            }

        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initFragments(int size) {
        //we need to check if there are live matches
        try {
            Utils.releaseMemory();

            mViewPager.setOffscreenPageLimit(size);
            final FragmentViewPagerAdapter mFragmentViewPagerAdapter = new FragmentViewPagerAdapter(getChildFragmentManager(), allItems, titles);
            mViewPager.setAdapter(mFragmentViewPagerAdapter);
            mFragmentViewPagerAdapter.notifyDataSetChanged();

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {
                }

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    //String title = (String) mFragmentViewPagerAdapter.getPageTitle(position);

                }

                public void onPageSelected(int position) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
