package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.ItemAdapter;
import cbu.httf.adisyonprogram.Adapters.ItemListAdapter;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.Item;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.OrderList;
import cbu.httf.adisyonprogram.data.model.OrderModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class TableItemActivity extends AppCompatActivity {
    RecyclerView rcv;

    ItemAdapter adapterItem;
    String token;
    String name,number;
    int table_ID ;

    TextView tv_Name;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_item);
        notificationManager = NotificationManagerCompat.from(this);
        Intent takenIntent = getIntent();
        token=takenIntent.getStringExtra("token");
        name = takenIntent.getStringExtra("tableName");
        number = takenIntent.getStringExtra("tableNumber");
        table_ID = Integer.parseInt(takenIntent.getStringExtra("tableId"));
        tv_Name = findViewById(R.id.tv_TableNamee);
        tv_Name.setText(name+" "+number);

        getOrders(true);

    }

    public void getOrders(Boolean requestType ){
        Call<List<OrderModel>> orderModelCall= Service.getServiceApi().getOrders(token);
        orderModelCall.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if(response.isSuccessful()){
                    ArrayList<OrderModel> productsModelBody = new ArrayList<>();
                    ArrayList<OrderList> orderList = new ArrayList<>();
                    productsModelBody =(ArrayList<OrderModel>) response.body();
                    ArrayList<Integer> orderId = new ArrayList<>();
                    for (OrderModel item : productsModelBody){
                        if(item.getMasaID()== table_ID){
                            orderId.add(item.getID());
                            if(requestType)
                                getMenu(item,orderList);
                            else
                                deleteOrders(orderId);
                        }

                    }

                }else{
                    Toast.makeText(TableItemActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {

            }
        });
    }

    public void getMenu(OrderModel orderModel,ArrayList<OrderList> orderList ){

            Call<MenuModel> menuModelCall=Service.getServiceApi().getProduct(token,orderModel.getMenuID());
            menuModelCall.enqueue(new Callback<MenuModel>() {
                @Override
                public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {

                    orderList.add(new OrderList(response.body().getAd(),String.valueOf(orderModel.getUrun_Adet()),orderModel.getMenuID(),orderModel.getMasaID()));
                    initOrder(orderList);
                }

                @Override
                public void onFailure(Call<MenuModel> call, Throwable t) {

                }
            });
    }


    public void initOrder(ArrayList<OrderList> ItemList){

        rcv = findViewById(R.id.Rcv_Items);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        adapterItem = new ItemAdapter(ItemList);
        rcv.setAdapter(adapterItem);
    }


    public void btnManuClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("token",token);
        intent.putExtra("masaId",String.valueOf(table_ID));
        intent.putExtra("tableName",name);
        intent.putExtra("tableNumber",number);
        startActivity(intent);
        this.finish();

    }

    public void sendOnChannel1Product() {
        String title = "Pay Product";
        String message = name+" "+number+" "+"All products pay" ;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_product_2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    public void btnPayClick(View view) {
        getOrders(false);
        sendOnChannel1Product();
        this.finish();
    }

    public  void deleteOrders(ArrayList<Integer> orderId){
        for(Integer id:orderId){
            Call<ResultModel> resultModelCall=Service.getServiceApi().deleteOrder(token,id);
            resultModelCall.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                    recreate();
                }

                @Override
                public void onFailure(Call<ResultModel> call, Throwable t) {

                }
            });
        }
    }
}