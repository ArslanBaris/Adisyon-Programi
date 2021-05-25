package cbu.httf.adisyonprogram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuModel {
    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("Kategori")
    @Expose
    private int Kategori;

    @SerializedName("Ad")
    @Expose
    private  String Ad;

    @SerializedName("Fiyat")
    @Expose
    private float Fiyat;

    public MenuModel() {
    }

    public MenuModel(int ID, int kategori, String ad, float fiyat) {
        this.ID = ID;
        Kategori = kategori;
        Ad = ad;
        Fiyat = fiyat;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getKategori() {
        return Kategori;
    }

    public void setKategori(int kategori) {
        Kategori = kategori;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public float getFiyat() {
        return Fiyat;
    }

    public void setFiyat(float fiyat) {
        Fiyat = fiyat;
    }
}

