package com.example2.diablove.yakamozrehberi.HelperClasses;

/**
 * Created by Diablove on 8/4/2016.
 */
@SuppressWarnings("ALL")
public class Company {

    public String compName, compCode, latitude,longitude;

    public Company(String compCode, String compName, String latitude, String longitude) {
        this.compCode = compCode;
        this.compName = compName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getcompCode() {
        return compCode;
    }

    public void setAddress(String address) {
        this.compCode = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
