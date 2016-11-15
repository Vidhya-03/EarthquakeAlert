package com.earthquakealert.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.earthquakealert.EarthquakeActivity;
import com.earthquakealert.R;
import com.earthquakealert.util.ApplicationGlobalDataManager;

/**
 * Created by vidhyasri on 10/11/2016.
 */
public class EarthquakeTypeFragment extends Fragment{
    View _view;
    Context _context;
    Button _significant;
    Button _all;
    Button _4_5;
    Button _2_5;
    Button _1_0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_earthquake_type,
                container, false);
        _significant = (Button) _view.findViewById(R.id.significant);
        _1_0 = (Button) _view.findViewById(R.id.mag1_0);
        _2_5 = (Button) _view.findViewById(R.id.mag2_5);
        _4_5 = (Button) _view.findViewById(R.id.mag4_5);
        _all = (Button) _view.findViewById(R.id.all);
        return _view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((EarthquakeActivity)getActivity()).getSupportActionBar().setTitle("Earthquake Alert");
        ((EarthquakeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        _significant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to store the magnitude in shared preference
                storeMagnitude("significant");
                ((EarthquakeActivity) getActivity()).getSupportActionBar().setTitle("Significant Earthquakes");
                ((EarthquakeActivity) getActivity()).showFragment(new EarthquakeDetailsFragment(), EarthquakeDetailsFragment.class.getName(), true);
            }
        });

        _1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to store the magnitude in shared preference\
                storeMagnitude("1.0");
                ((EarthquakeActivity) getActivity()).getSupportActionBar().setTitle("M1.0+ Earthquakes");
                ((EarthquakeActivity) getActivity()).showFragment(new EarthquakeDetailsFragment(), EarthquakeDetailsFragment.class.getName(), true);
            }
        });

        _2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to store the magnitude in shared preference
                storeMagnitude("2.5");
                ((EarthquakeActivity) getActivity()).getSupportActionBar().setTitle("M2.5+ Earthquakes");
                ((EarthquakeActivity) getActivity()).showFragment(new EarthquakeDetailsFragment(), EarthquakeDetailsFragment.class.getName(), true);
            }
        });

        _4_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to store the magnitude in shared preference
                storeMagnitude("4.5");
                ((EarthquakeActivity) getActivity()).getSupportActionBar().setTitle("M4.5+ Earthquakes");
                ((EarthquakeActivity) getActivity()).showFragment(new EarthquakeDetailsFragment(), EarthquakeDetailsFragment.class.getName(), true);
            }
        });

        _all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to store the magnitude in shared preference
                storeMagnitude("all");
                ((EarthquakeActivity) getActivity()).getSupportActionBar().setTitle("All Earthquakes");
                ((EarthquakeActivity) getActivity()).showFragment(new EarthquakeDetailsFragment(), EarthquakeDetailsFragment.class.getName(), true);
            }
        });
    }

    private void storeMagnitude(String magnitude) {
        ApplicationGlobalDataManager.getInstace().setMagnitude(magnitude);
    }
}
