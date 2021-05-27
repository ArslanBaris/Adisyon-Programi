package cbu.httf.adisyonprogram.data.model;

import java.io.Serializable;

public class Item implements Serializable {

    private  String Item_Name;
    private String Item_Piece;

    public Item(String item_Name, String item_Piece) {
        Item_Name = item_Name;
        Item_Piece = item_Piece;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getItem_Piece() {
        return Item_Piece;
    }

    public void setItem_Piece(String item_Piece) {
        Item_Piece = item_Piece;
    }
}
