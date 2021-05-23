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

    @SerializedName("tableNo")
    @Expose
    private int tableNo;

    public TablesModel() {
    }

    public TablesModel(int ID, String ad,int tableNo) {
        this.ID = ID;
        Ad = ad;
        this.tableNo=tableNo;
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

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }
}
