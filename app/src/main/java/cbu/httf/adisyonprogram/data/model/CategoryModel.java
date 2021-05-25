package cbu.httf.adisyonprogram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("CategoryName")
    @Expose
    private String CategoryName;

    public CategoryModel() {
    }

    public CategoryModel(int ID, String CategoryName) {
        this.ID = ID;
        this.CategoryName = CategoryName;
    }

    public int getCategoryId() {
        return ID;
    }

    public void setCategoryId(int ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.CategoryName = CategoryName;
    }
}
