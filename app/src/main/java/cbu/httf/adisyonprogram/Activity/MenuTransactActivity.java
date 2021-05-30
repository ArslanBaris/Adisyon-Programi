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
import android.widget.Button;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.CategoryAdapter;
import cbu.httf.adisyonprogram.Adapters.ProductAdapter;
import cbu.httf.adisyonprogram.Fragment.Category.CategoryAddFragment;
import cbu.httf.adisyonprogram.Fragment.Category.CategoryUpdateFragment;
import cbu.httf.adisyonprogram.Fragment.Menu.MenuAddFragment;
import cbu.httf.adisyonprogram.Fragment.Menu.MenuUpdateFragment;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class MenuTransactActivity extends AppCompatActivity {

    private Button btnAddMenu,btnUpdateMenu,btnDeleteMenu;

    private MenuAddFragment menuAddFragment;
    private MenuUpdateFragment menuUpdateFragment;
    private CategoryAddFragment categoryAddFragment;
    private CategoryUpdateFragment categoryUpdateFragment;

    private String takenUserName;

    public static String takentoken;
    public  int categoryId=0;
    public  int productId=0;
    public String productName,categoryName;

    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewProduct;

    private NotificationManagerCompat notificationManager;

    static ArrayList<Integer> categories;

    private void init(){
        getSupportActionBar().setIcon(R.drawable.ic_menu_transact);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAddMenu=findViewById(R.id.btnMenuAdd);
        btnUpdateMenu=findViewById(R.id.btnMenuUpdate);
        btnDeleteMenu=findViewById(R.id.btnMenuDelete);
        notificationManager = NotificationManagerCompat.from(this);
        categories=new ArrayList<>();

        categoryUpdateFragment =  new CategoryUpdateFragment(takentoken,categoryId,categoryName);
        recyclerViewCategory=(RecyclerView)findViewById(R.id.category_recyclerView);
        recyclerViewProduct=(RecyclerView)findViewById(R.id.product_recyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_transact);

        getSupportActionBar().setTitle("Menu Transact");

        Intent takenIntent = getIntent();
        takenUserName = takenIntent.getStringExtra("userName");
        takentoken=takenIntent.getStringExtra("token");

        init();

        getCategories();

        menuAddFragment =  new MenuAddFragment(takentoken,categories,categoryId);
        menuUpdateFragment =  new MenuUpdateFragment(takentoken,categories,categoryId,productId);
        categoryAddFragment =  new CategoryAddFragment(takentoken);

    }

    public void initCategories(ArrayList<CategoryModel> categoryModels){
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewCategory.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModels,getApplicationContext());
        recyclerViewCategory.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnCategoryItemClickListener() {
            @Override
            public void onCategoryItemClick(CategoryModel categoryModel, int position) {
                categoryId=categoryModel.getCategoryId();
                categoryName=categoryModel.getCategoryName();
                productId=0;
                categoryUpdateFragment =  new CategoryUpdateFragment(takentoken,categoryId,categoryName);
                menuAddFragment =  new MenuAddFragment(takentoken,categories,categoryId);
                getProducts();
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

                    for(CategoryModel categoryModel: categoriesModels){
                        categories.add(categoryModel.getCategoryId());
                    }

                    initCategories(categoriesModels);

                }else{
                    Toast.makeText(MenuTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(MenuTransactActivity.this, "Request failed. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getProducts(){

        Call<List<MenuModel>> productsModelCall = Service.getServiceApi().getMenu(takentoken);

        productsModelCall.enqueue(new Callback<List<MenuModel>>() {
            @Override
            public void onResponse(Call<List<MenuModel>> call, Response<List<MenuModel>> response) {
                if(response.isSuccessful()){
                    ArrayList<MenuModel> productsModelsBody = new ArrayList<>();
                    ArrayList<MenuModel> productsModels = new ArrayList<>();
                    productsModelsBody =(ArrayList<MenuModel>) response.body();
                    for(MenuModel productsModel : productsModelsBody )
                    {
                        if(productsModel.getKategori()==categoryId){
                            productsModels.add(productsModel);
                        }
                    }

                    initProducts(productsModels);

                }else{
                    Toast.makeText(MenuTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuModel>> call, Throwable t) {
                Toast.makeText(MenuTransactActivity.this, "Failure. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initProducts(ArrayList<MenuModel> productModels){

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter productAdapter = new ProductAdapter(productModels,getApplicationContext(),categoryId);
        recyclerViewProduct.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new ProductAdapter.OnProductItemClickListener() {
            @Override
            public void onProductItemClick(MenuModel menuModel, int position) {
                productId= menuModel.getID();
                productName=menuModel.getAd();
                menuUpdateFragment =  new MenuUpdateFragment(takentoken,categories,categoryId,productId);

            }
        });
    }


    public void FragmentCategoryAdd(View v){
        categoryAddFragment.show(getSupportFragmentManager(),"ADD CATEGORY");
    }

    public void FragmentCategoryUpdate(View v){
        categoryUpdateFragment.show(getSupportFragmentManager(),"UPDATE CATEGORY");
    }

    public void sendOnChannel1Category() {
        String title = "Deleted Category";
        String message = categoryId+" | "+categoryName;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_category)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    public void CategoryDelete(View v){

        Call<ResultModel> deleteCall = Service.getServiceApi().deleteCategory(takentoken,categoryId);
        deleteCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MenuTransactActivity.this,"Transaction is successful.", Toast.LENGTH_LONG).show();
                    sendOnChannel1Category();
                    recreate();
                }else{
                    Toast.makeText(MenuTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(MenuTransactActivity.this, "Failure. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void FragmentMenuAdd(View v){
        menuAddFragment.show(getSupportFragmentManager(),"ADD MENU");
    }

    public void FragmentMenuUpdate(View v){
        if(productId==0)
            Toast.makeText(MenuTransactActivity.this,"Choose product", Toast.LENGTH_LONG).show();
        else
            menuUpdateFragment.show(getSupportFragmentManager(),"UPDATE MENU");
    }

    public void sendOnChannel1Product() {
        String title = "Deleted Product";
        String message = productId+" | "+productName;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_product_2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    public void MenuDelete(View v){

        Call<ResultModel> deleteCall = Service.getServiceApi().deleteMenu(takentoken,productId);
        deleteCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MenuTransactActivity.this,"Transaction is successful.", Toast.LENGTH_LONG).show();
                    sendOnChannel1Product();
                    recreate();
                }else{
                    Toast.makeText(MenuTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(MenuTransactActivity.this, "Failure. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

}