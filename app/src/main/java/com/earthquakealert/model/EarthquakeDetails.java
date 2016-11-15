package com.earthquakealert.model;

/**
 * Created by vidhyasri on 10/11/2016.
 */

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EarthquakeDetails {
    public List<FeatureDetails> features;

    public List<FeatureDetails> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDetails> features) {
        this.features = features;
    }
}
