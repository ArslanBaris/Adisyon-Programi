package cbu.httf.adisyonprogram.Network;

import java.util.List;

import cbu.httf.adisyonprogram.data.model.LoginRequest;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import cbu.httf.adisyonprogram.data.model.LoginResponse;
import cbu.httf.adisyonprogram.data.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ServiceApi {

    //Login Request
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //GET Requests
    @GET("user")
    Call<List<UserModel>> getUsers(@Header("token") String token);

    @GET("user")
    Call<UserModel> getUser(@Header("token") String token);


    @GET("masalar")
    Call<List<TablesModel>> getTables(@Header("token") String token);

    @GET("menu")
    Call<List<MenuModel>> getMenu(@Header("token") String token);


    //PUT Requests

    @PUT("user")
    Call<ResultModel> putUser(@Header("token") String token,
                              @Body UserModel userModel);

    @PUT("masalar")
    Call<ResultModel> putTable(@Header("token") String token,
                               @Body TablesModel tablesModel);

    @PUT("menu")
    Call<ResultModel> putMenu(@Header("token") String token,
                              @Body MenuModel menuModel);


    //POST Requests

    @FormUrlEncoded
    @POST("user")
    Call<ResultModel> postUser(@Header("token") String token,
                               @Field("userName") String userName,
                               @Field("name") String name,
                               @Field("surname") String surname,
                               @Field("eMail") String eMail,
                               @Field("userTypeName") String userTypeName);

    @FormUrlEncoded
    @POST("masalar")
    Call<ResultModel> postTable(@Header("token") String token,
                                @Field("Ad") String ad,
                                @Field("tableNo") int tableNo);

    @FormUrlEncoded
    @POST("menu")
    Call<ResultModel> postMenu(@Header("token") String token,
                               @Field("Kategori") String kategori,
                               @Field("Ad") String ad,
                               @Field("Fiyat") float fiyat);



}
