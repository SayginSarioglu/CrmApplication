package com.example2.diablove.yakamozrehberi.HelperClasses;


@SuppressWarnings("ALL")
public class Contact {

    public String adSoyad, mobil,is,faks, email,sirket,unvan;


    public Contact(String adSoyad, String mobil, String is, String faks, String email, String sirket, String unvan) {
        this.adSoyad = adSoyad;
        this.mobil = mobil;
        this.is = is;
        this.faks = faks;
        this.email = email;
        this.sirket = sirket;
        this.unvan = unvan;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getFaks() {
        return faks;
    }

    public void setFaks(String faks) {
        this.faks = faks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSirket() {
        return sirket;
    }

    public void setSirket(String sirket) {
        this.sirket = sirket;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }
}
