package cbu.httf.adisyonprogram.data.model;

import java.io.Serializable;

public class OrderList implements Serializable {
    private String Item_Name;
    private String Item_Piece;
    private int Item_ID;
    private int Table_ID;

    public OrderList(String item_Name, String item_Piece,int item_ID,int table_ID) {
        Item_Name = item_Name;
        Item_Piece = item_Piece;
        Item_ID = item_ID;
        Table_ID = table_ID;
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

    public void setItem_Piece(String item_Piece) { Item_Piece = item_Piece; }

    public int getItem_ID() {
        return Item_ID;
    }

    public void setItem_ID(int item_ID) {
        Item_ID = item_ID;
    }

    public int getTable_ID() {
        return Table_ID;
    }

    public void setTable_ID(int table_ID) {
        Table_ID = table_ID;
    }
}
