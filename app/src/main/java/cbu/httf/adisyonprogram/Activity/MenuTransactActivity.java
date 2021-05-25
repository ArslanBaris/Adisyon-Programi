package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.CategoryAdapter;
import cbu.httf.adisyonprogram.Adapters.ProductAdapter;
import cbu.httf.adisyonprogram.Adapters.TableAdapter;
import cbu.httf.adisyonprogram.Fragment.Menu.MenuAddFragment;
import cbu.httf.adisyonprogram.Fragment.Menu.MenuUpdateFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableAddFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableUpdateFragment;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuTransactActivity extends AppCompatActivity {

    private Button btnAddMenu,btnUpdateMenu,btnDeleteMenu;
    private MenuAddFragment menuAddFragment;
    private MenuUpdateFragment menuUpdateFragment;

    private String takenUserName;
    public   static String takentoken;

    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewProduct;

    private int categoryId;
    private static int productId;

    static ArrayList<Integer> categories;

    private void init(){
        btnAddMenu=findViewById(R.id.btnMenuAdd);
        btnUpdateMenu=findViewById(R.id.btnMenuUpdate);
        btnDeleteMenu=findViewById(R.id.btnMenuDelete);

        categories=new ArrayList<>();

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

        menuAddFragment =  new MenuAddFragment(takentoken,categories);
        menuUpdateFragment =  new MenuUpdateFragment(takentoken,categories);
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
                //Toast.makeText(MenuTransactActivity.this, "C ID: "+categoryId , Toast.LENGTH_LONG).show();
                getProducts(categoryId);
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

    public void getProducts(int categoryId){
        Toast.makeText(MenuTransactActivity.this, "C ID: "+categoryId , Toast.LENGTH_LONG).show();
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
                productId=menuModel.getID();
            }
        });
    }


    public void FragmentMenuAdd(View v){
        menuAddFragment.show(getSupportFragmentManager(),"ADD MENU");
    }

    public void FragmentMenuUpdate(View v){
        menuUpdateFragment.show(getSupportFragmentManager(),"UPDATE MENU");
    }

    public void FragmentMenuDelete(View v){
        //tableAddFragment.show(getSupportFragmentManager(),"DELETE TABLE");
        Call<ResultModel> deleteCall = Service.getServiceApi().deleteMenu(takentoken,productId);
        deleteCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MenuTransactActivity.this,"Transaction is successful.", Toast.LENGTH_LONG).show();
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