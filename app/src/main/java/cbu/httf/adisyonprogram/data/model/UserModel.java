package cbu.httf.adisyonprogram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("eMail")
    @Expose
    private String eMail;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("UserTypeName")
    @Expose
    private String UserTypeName;

    public UserModel() {
    }

    public UserModel(int ID, String userName, String name, String surname, String eMail, String password, String userTypeName) {
        this.ID = ID;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
        this.password = password;
        UserTypeName = userTypeName;
    }

    public UserModel(int ID, String userName, String name, String surname, String eMail, String userTypeName) {
        this.ID = ID;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
        UserTypeName = userTypeName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserTypeName() {
        return UserTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        UserTypeName = userTypeName;
    }

}
