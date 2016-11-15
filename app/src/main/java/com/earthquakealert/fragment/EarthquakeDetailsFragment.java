package com.earthquakealert.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.earthquakealert.EarthquakeActivity;
import com.earthquakealert.R;
import com.earthquakealert.adapter.EarthquakeDetailsAdapter;
import com.earthquakealert.interfaces.RecyclerViewClickListener;
import com.earthquakealert.interfaces.ServerRequestListener;
import com.earthquakealert.model.EarthquakeDetails;

import java.util.Locale;
import java.util.Map;

/**
 * Created by vidhyasri on 10/11/2016.
 */
public class EarthquakeDetailsFragment extends Fragment implements RecyclerViewClickListener{

    View _view;
    LinearLayout _listLayout;
    RecyclerView _details;
    TextView _errorText;
    ServerRequestListener _serverRequestListener;
    EarthquakeDetailsAdapter _earthquakeDetailsAdapter;
    RecyclerView.LayoutManager recyclerView_LayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _serverRequestListener = (EarthquakeActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_earthquake_details,
                container, false);
        _listLayout = (LinearLayout) _view.findViewById(R.id.listLayout);
        _details = (RecyclerView) _view.findViewById(R.id.recycler_view);
        _details.setHasFixedSize(true);
        recyclerView_LayoutManager = new LinearLayoutManager(getActivity());
        _details.setLayoutManager(recyclerView_LayoutManager);
        _errorText = (TextView) _view.findViewById(R.id.errorText);
        return _view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((EarthquakeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        _serverRequestListener.fetchInfo();
    }

    public void refresh(EarthquakeDetails earthquakeDetails) {
        if(earthquakeDetails.getFeatures().size() > 0) {
            _earthquakeDetailsAdapter = new EarthquakeDetailsAdapter(earthquakeDetails,this);
            _details.setAdapter(_earthquakeDetailsAdapter);

        } else {
            _errorText.setText(getString(R.string.data_not_found));
        }
    }

    @Override
    public void onClick(Map<String, String> featureMap) {
        if(featureMap.get("url") != null) {
            Uri uri = Uri.parse(featureMap.get("url")); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Float.parseFloat(featureMap.get("lat")),Float.parseFloat(featureMap.get("long")));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }

    }
}
