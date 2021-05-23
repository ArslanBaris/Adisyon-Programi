package cbu.httf.adisyonprogram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuModel {
    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("Kategori")
    @Expose
    private String Kategori;

    @SerializedName("Ad")
    @Expose
    private  String Ad;

    @SerializedName("Fiyat")
    @Expose
    private String Fiyat;

    public MenuModel() {
    }

    public MenuModel(int ID, String kategori, String ad, String fiyat) {
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

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getFiyat() {
        return Fiyat;
    }

    public void setFiyat(String fiyat) {
        Fiyat = fiyat;
    }
}

