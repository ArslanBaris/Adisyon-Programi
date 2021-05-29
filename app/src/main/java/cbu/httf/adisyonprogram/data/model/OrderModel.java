package cbu.httf.adisyonprogram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderModel {
    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("masaID")
    @Expose
    private int masaID;

    @SerializedName("urun_Adet")
    @Expose
    private int urun_Adet;

    @SerializedName("menuID")
    @Expose
    private int menuID;


    public OrderModel() {
    }

    public OrderModel(int ID, int masaID, int urun_Adet, int menuID) {
        this.ID = ID;
        this.masaID = masaID;
        this.urun_Adet = urun_Adet;
        this.menuID = menuID;
    }

    public OrderModel( int masaID, int urun_Adet, int menuID) {

        this.masaID = masaID;
        this.urun_Adet = urun_Adet;
        this.menuID = menuID;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMasaID() {
        return masaID;
    }

    public void setMasaID(int masaID) {
        this.masaID = masaID;
    }

    public int getUrun_Adet() {
        return urun_Adet;
    }

    public void setUrun_Adet(int urun_Adet) {
        this.urun_Adet = urun_Adet;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

}
