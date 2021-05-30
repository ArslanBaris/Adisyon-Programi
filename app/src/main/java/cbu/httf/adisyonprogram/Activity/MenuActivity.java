package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.CategoryAdapter;
import cbu.httf.adisyonprogram.Adapters.ItemAdapter;
import cbu.httf.adisyonprogram.Adapters.ItemListAdapter;
import cbu.httf.adisyonprogram.Adapters.ProductAdapter;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.Item;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.OrderList;
import cbu.httf.adisyonprogram.data.model.OrderModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import kotlin.time.FormatToDecimalsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class MenuActivity extends AppCompatActivity {
    LinearLayout layout;
    RecyclerView rcv;
    RecyclerView rcv2;
    List<OrderList> ItemAdapter_list = new ArrayList<>();
    ItemListAdapter adapter_itemList;
    public int Table_ID;
    public static String takentoken;
    public String name,number;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        notificationManager = NotificationManagerCompat.from(this);
        layout = findViewById(R.id.category);
        Intent takenIntent = getIntent();
        takentoken=takenIntent.getStringExtra("token");
        name=takenIntent.getStringExtra("tableName");
        number=takenIntent.getStringExtra("tableNumber");
        Table_ID = Integer.parseInt(takenIntent.getStringExtra("masaId"));
        getCategories();


    }

    void  abc(ArrayList<CategoryModel> categoryModels){
        for (CategoryModel categoryModel:categoryModels) {
            final Button button = new Button(getApplicationContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(370, 150));
            button.setId(categoryModel.getCategoryId());
            button.setText(categoryModel.getCategoryName());
            button.setOnClickListener(this::btnCategory_Click);
            layout.addView(button);
        }

    }

    public void getProducts(int id){
        Call<List<MenuModel>> listCall=Service.getServiceApi().getMenu(takentoken);
        listCall.enqueue(new Callback<List<MenuModel>>() {
            @Override
            public void onResponse(Call<List<MenuModel>> call, Response<List<MenuModel>> response) {
                if(response.isSuccessful()){
                    ArrayList<MenuModel> productsModelBody = new ArrayList<>();
                    ArrayList<OrderList> productsModel = new ArrayList<>();
                    productsModelBody =(ArrayList<MenuModel>) response.body();
                    for(MenuModel Item : productsModelBody)
                    {
                        if (Item.getKategori() == id)
                            productsModel.add(new OrderList(Item.getAd(), "0", Item.getID(), Table_ID));
                    }
                    adapter_itemList = new ItemListAdapter(productsModel);
                    rcv = findViewById(R.id.Items);
                    rcv.setHasFixedSize(true);
                    rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rcv.setAdapter(adapter_itemList);

                }else{
                    Toast.makeText(MenuActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuModel>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Request failed. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
    public void getCategories(){


        Call<List<CategoryModel>> categoriesModelCall = Service.getServiceApi().getCategories(takentoken);

        categoriesModelCall.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if(response.isSuccessful()){
                    ArrayList<CategoryModel> categoriesModels = new ArrayList<>();
                    categoriesModels =(ArrayList<CategoryModel>) response.body();
                    abc(categoriesModels);

                }else{
                    Toast.makeText(MenuActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Request failed. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
    public void btnCategory_Click(View view) {
        int btn_id = view.getId();
        getProducts(btn_id);

    }
    public void sendOnChannel1Product() {
        String title = "Add Product";
        String message =name+" "+number+" "+"products added" ;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_product_2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }
    public void btnOkClick(View view) {
        startActivity(new Intent(MenuActivity.this,TableItemActivity.class).
                putExtra("token",takentoken).
                putExtra("tableId",String.valueOf(Table_ID)).
                putExtra("tableName",name).
                putExtra("tableNumber",number));
        List<OrderModel> orderModels_list = new ArrayList<>();
        for (OrderList Item : ItemAdapter_list) {
            orderModels_list.add(new OrderModel(Item.getTable_ID(),Integer.valueOf(Item.getItem_Piece()),Item.getItem_ID()));
        }
        postOrder(orderModels_list);
        sendOnChannel1Product();
        this.finish();
    }

    public void postOrder(List<OrderModel> orderModelList){

        for (OrderModel item : orderModelList) {
            Call<ResultModel> postOrderCall = Service.getServiceApi().postOrder(takentoken,item.getMasaID(),item.getUrun_Adet(),item.getMenuID());
            postOrderCall.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if(response.isSuccessful())
                    Toast.makeText(MenuActivity.this, "Request success. "+response.code() , Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MenuActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResultModel> call, Throwable t) {

                }
            });
        }
    }

    public void btnAdd_Click(View view) {

        rcv2 = findViewById(R.id.Rcv);
        rcv2.setHasFixedSize(true);
        rcv2.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter_list = adapter_itemList.getItem_List();
        for (int i = ItemAdapter_list.size() - 1; i >= 0; i--) {
            if (Integer.valueOf(ItemAdapter_list.get(i).getItem_Piece()) == 0)
                ItemAdapter_list.remove(i);
            System.out.println(ItemAdapter_list.get(i).getItem_ID()+" "+ItemAdapter_list.get(i).getTable_ID());
        }
        rcv2.setAdapter(new ItemAdapter(ItemAdapter_list));

    }


}