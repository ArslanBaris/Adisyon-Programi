package cbu.httf.adisyonprogram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TablesModel {
    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("Ad")
    @Expose
    private String Ad;

    public TablesModel() {
    }

    public TablesModel(int ID, String ad) {
        this.ID = ID;
        Ad = ad;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }
}
