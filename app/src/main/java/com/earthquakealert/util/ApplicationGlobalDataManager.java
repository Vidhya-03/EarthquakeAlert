package com.earthquakealert.util;

import com.earthquakealert.model.EarthquakeDetails;

/**
 * Created by vidhyasri on 10/11/2016.
 */
public class ApplicationGlobalDataManager {

    public static ApplicationGlobalDataManager _applicationGlobal = null;
    public EarthquakeDetails _earthquakeDetails;
    public String mag;

    public static ApplicationGlobalDataManager getInstace() {
        if(_applicationGlobal == null) {
            _applicationGlobal = new ApplicationGlobalDataManager();
        }
        return _applicationGlobal;
    }

    public void set_earthquakeDetails(EarthquakeDetails details) {
        _earthquakeDetails = details;
    }

    public EarthquakeDetails getEathquakeDetails() {
        return _earthquakeDetails;
    }

    public void setMagnitude(String magnitude) {
        mag = magnitude;
    }

    public String getMagnitude() {
        return mag;
    }
}
