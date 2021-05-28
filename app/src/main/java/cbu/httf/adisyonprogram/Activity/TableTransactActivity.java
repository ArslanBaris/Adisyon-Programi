package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.CategoryAdapter;
import cbu.httf.adisyonprogram.Adapters.TableAdapter;
import cbu.httf.adisyonprogram.Fragment.Category.CategoryUpdateFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableAddFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableUpdateFragment;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TableTransactActivity extends AppCompatActivity  {

    private Button btnAddTable,btnUpdateTable,btnDeleteTable;
    private TableAddFragment tableAddFragment;
    private TableUpdateFragment tableUpdateFragment;

    private RecyclerView recyclerView;

    private String takenUserName;
    public   static String takentoken;

    public  int tableId=0;

    private void init(){
        btnAddTable=(Button)findViewById(R.id.btnTableAdd);
        btnUpdateTable=(Button)findViewById(R.id.btnTableUpdate);
        btnDeleteTable=(Button)findViewById(R.id.btnTableDelete);

        tableAddFragment =  new TableAddFragment(takentoken);
        tableUpdateFragment =  new TableUpdateFragment(takentoken,tableId);
        recyclerView=(RecyclerView)findViewById(R.id.table_recyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Table Transact");
       // getSupportActionBar().setIcon(R.drawable.ic_table);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_transact);

        Intent takenIntent = getIntent();
        takenUserName = takenIntent.getStringExtra("userName");
        takentoken=takenIntent.getStringExtra("token");

        init();

        getTables();


    }

    public void initTables(ArrayList<TablesModel> tablesModels){

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        TableAdapter tableAdapter = new TableAdapter(tablesModels,getApplicationContext());
        recyclerView.setAdapter(tableAdapter);

        tableAdapter.setOnItemClickListener(new TableAdapter.OnTableItemClickListener() {
            @Override
            public void onTableItemClick(TablesModel tablesModel, int position) {
                tableId=tablesModel.getID();
                tableUpdateFragment =  new TableUpdateFragment(takentoken,tableId);
            }
        });
    }

    public void getTables(){
        Call<List<TablesModel>> tablesModelCall = Service.getServiceApi().getTables(takentoken);
        tablesModelCall.enqueue(new Callback<List<TablesModel>>() {
            @Override
            public void onResponse(Call<List<TablesModel>> call, Response<List<TablesModel>> response) {
                if(response.isSuccessful()){
                    ArrayList<TablesModel> tablesModels = new ArrayList<>();
                    tablesModels =(ArrayList<TablesModel>) response.body();

                    initTables(tablesModels);

                }else{
                    Toast.makeText(TableTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TablesModel>> call, Throwable t) {
                Toast.makeText(TableTransactActivity.this, "Request failed. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void FragmentTableAdd(View v){
        tableAddFragment.show(getSupportFragmentManager(),"ADD TABLE");
    }

    public void FragmentTableUpdate(View v){
        tableUpdateFragment.show(getSupportFragmentManager(),"UPDATE TABLE");
    }

    public void TableDelete(View v){
        Call<ResultModel> tableDeleteCall = Service.getServiceApi().deleteTable(takentoken,tableId);
        tableDeleteCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TableTransactActivity.this,"Transaction is successful.", Toast.LENGTH_LONG).show();
                    recreate();
                }else{
                    Toast.makeText(TableTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(TableTransactActivity.this, "Request failed. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

}