package cbu.httf.adisyonprogram.Network;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cbu.httf.adisyonprogram.data.model.Cons;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Service {

    public static ServiceApi serviceApi;
    //public static Gson gson = new GsonBuilder().setLenient().create();

    public  static Retrofit retrofit;

    private static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Cons.BASE_URL)
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ServiceApi getServiceApi() {

        serviceApi = getInstance().create(ServiceApi.class);
        return serviceApi;
    }
}
