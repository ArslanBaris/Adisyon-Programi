package cbu.httf.adisyonprogram.data.model;

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
