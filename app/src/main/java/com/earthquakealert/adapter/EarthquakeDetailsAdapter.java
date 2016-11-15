package com.earthquakealert.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.earthquakealert.R;
import com.earthquakealert.interfaces.RecyclerViewClickListener;
import com.earthquakealert.model.EarthquakeDetails;
import com.earthquakealert.model.FeatureDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vidhyasri on 10/11/2016.
 */
public class EarthquakeDetailsAdapter  extends RecyclerView.Adapter<EarthquakeDetailsAdapter.CustomViewHolder> {

    EarthquakeDetails _earthquakeDetails;
    RecyclerViewClickListener _recyclerViewClickListener;
    private Map<String,String> featureMap;

    public EarthquakeDetailsAdapter(EarthquakeDetails earthquakeDetails,RecyclerViewClickListener recyclerViewClickListener) {
        this._earthquakeDetails = earthquakeDetails;
        this._recyclerViewClickListener = recyclerViewClickListener;
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView _mag;
        TextView _date;
        TextView _link;
        TextView _latlong;
        ImageView _location;

        public CustomViewHolder(View view)
        {
            super(view);
            _mag = (TextView) view.findViewById(R.id.mag);
            _date = (TextView) view.findViewById(R.id.date);
            _link = (TextView) view.findViewById(R.id.link);
            _latlong = (TextView) view.findViewById(R.id.latlong);
            _location = (ImageView) view.findViewById(R.id.location);
        }
    }
    @Override
    public EarthquakeDetailsAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_earthquake_details, parent, false);

        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(EarthquakeDetailsAdapter.CustomViewHolder holder, final int position) {
        final FeatureDetails featureDetails = _earthquakeDetails.getFeatures().get(position);
        featureMap = new HashMap<String,String>();
        holder._mag.setText(""+featureDetails.getProperties().getMag());
        holder._latlong.setText(""+featureDetails.getGeometry().getCoordinates()[0]+", "+featureDetails.getGeometry().getCoordinates()[1]);
        holder._link.setText(featureDetails.getProperties().getUrl());
        holder._date.setText(""+featureDetails.getProperties().getTime());
        holder._location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featureMap.put("long",""+featureDetails.getGeometry().getCoordinates()[0]);
                featureMap.put("lat",""+featureDetails.getGeometry().getCoordinates()[1]);
                featureMap.put("url",null);
                _recyclerViewClickListener.onClick(featureMap);
            }
        });
        holder._link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featureMap.put("lat",null);
                featureMap.put("long",null);
                featureMap.put("url",featureDetails.getProperties().getUrl());
                _recyclerViewClickListener.onClick(featureMap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _earthquakeDetails.getFeatures().size();
    }
}
