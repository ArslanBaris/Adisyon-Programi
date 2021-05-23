package cbu.httf.adisyonprogram.data.model;

import android.util.Base64;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginResponse {

    private String token;
    private Map<String,String> result;

    public Map<String, String> getResult() {
        return result;
    }

    public String getToken() {
        return token;
    }

}
