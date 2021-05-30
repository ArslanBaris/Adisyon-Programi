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

import cbu.httf.adisyonprogram.Adapters.TableAdapter;
import cbu.httf.adisyonprogram.Fragment.Table.TableAddFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableUpdateFragment;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class TableTransactActivity extends AppCompatActivity  {

    private Button btnAddTable,btnUpdateTable,btnDeleteTable;
    private TableAddFragment tableAddFragment;
    private TableUpdateFragment tableUpdateFragment;

    private RecyclerView recyclerView;

    private String takenUserName;
    public   static String takentoken;
    private NotificationManagerCompat notificationManager;
    public  int tableId=0;
    public String tableName;
    public int tableNumber;

    private void init(){
        getSupportActionBar().setTitle("Table Transact");  //ActionBar Text
        getSupportActionBar().setIcon(R.drawable.ic_table_4);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent takenIntent = getIntent();
        takenUserName = takenIntent.getStringExtra("userName");
        takentoken=takenIntent.getStringExtra("token");

        btnAddTable=(Button)findViewById(R.id.btnTableAdd);
        btnUpdateTable=(Button)findViewById(R.id.btnTableUpdate);
        btnDeleteTable=(Button)findViewById(R.id.btnTableDelete);
        notificationManager = NotificationManagerCompat.from(this);
        recyclerView=(RecyclerView)findViewById(R.id.table_recyclerView);

        tableAddFragment =  new TableAddFragment(takentoken);       // If not item selected, default values are sent.
        tableUpdateFragment =  new TableUpdateFragment(takentoken,tableId,tableName,tableNumber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_transact);

        init();
        getTables();
    }

    public void getTables(){
        Call<List<TablesModel>> tablesModelCall = Service.getServiceApi().getTables(takentoken);
        tablesModelCall.enqueue(new Callback<List<TablesModel>>() {
            @Override
            public void onResponse(Call<List<TablesModel>> call, Response<List<TablesModel>> response) {
                if(response.isSuccessful()){
                    ArrayList<TablesModel> tablesModels = new ArrayList<>();  // for response body
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

    public void initTables(ArrayList<TablesModel> tablesModels){

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        TableAdapter tableAdapter = new TableAdapter(tablesModels,getApplicationContext());
        recyclerView.setAdapter(tableAdapter);

        tableAdapter.setOnItemClickListener(new TableAdapter.OnTableItemClickListener() {
            @Override
            public void onTableItemClick(TablesModel tablesModel, int position) {  // selected item informations
                tableId=tablesModel.getID();
                tableName=tablesModel.getAd();
                tableNumber=tablesModel.getTableNo();
                tableUpdateFragment =  new TableUpdateFragment(takentoken,tableId,tableName,tableNumber);  // This informations sent
            }
        });
    }

    public void FragmentTableAdd(View v){
        tableAddFragment.show(getSupportFragmentManager(),"ADD TABLE");
    }

    public void FragmentTableUpdate(View v){
        tableUpdateFragment.show(getSupportFragmentManager(),"UPDATE TABLE");
    }

    private void sendOnChannel1() {     // Create notification
        String title = "Deleted Table";
        String message = String.valueOf(tableId)+" | "+tableName+": "+String.valueOf(tableNumber);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_table_1)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Priorty
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    public void TableDelete(View v){
        if(tableId==0)                   //if not selected, cannot delete
            Toast.makeText(TableTransactActivity.this,"Choose table", Toast.LENGTH_LONG).show();
        else {
            Call<ResultModel> tableDeleteCall = Service.getServiceApi().deleteTable(takentoken, tableId);  // Submitted ID to be deleted.
            tableDeleteCall.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(TableTransactActivity.this, "Transaction is successful.", Toast.LENGTH_LONG).show();
                        sendOnChannel1();   // Call Notification
                        recreate();     // Rebuild activity
                    } else {
                        Toast.makeText(TableTransactActivity.this, "Request failed. " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResultModel> call, Throwable t) {
                    Toast.makeText(TableTransactActivity.this, "Request failed. " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}