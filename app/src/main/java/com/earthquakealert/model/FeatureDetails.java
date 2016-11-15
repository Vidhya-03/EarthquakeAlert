package com.earthquakealert.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by vidhyasri on 10/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureDetails {
    public PropertyDetails properties;
    public GeometryDetails geometry;

    public PropertyDetails getProperties() {
        return properties;
    }

    public void setProperties(PropertyDetails properties) {
        this.properties = properties;
    }

    public GeometryDetails getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDetails geometry) {
        this.geometry = geometry;
    }
}
