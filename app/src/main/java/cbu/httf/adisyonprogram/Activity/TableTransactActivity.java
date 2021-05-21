package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import cbu.httf.adisyonprogram.Fragment.Table.TableAddFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableUpdateFragment;
import cbu.httf.adisyonprogram.R;


public class TableTransactActivity extends AppCompatActivity  {

    private Button btnAddTable,btnUpdateTable,btnDeleteTable;
    private TableAddFragment tableAddFragment;
    private TableUpdateFragment tableUpdateFragment;




    private EditText editTextTableNumber;

    private int tableNumber;
    private String takenUserName;
    public   static String takentoken;



    private void init(){
        btnAddTable=findViewById(R.id.btnTableAdd);
        btnUpdateTable=findViewById(R.id.btnTableUpdate);
        btnDeleteTable=findViewById(R.id.btnTableDelete);

        tableAddFragment =  new TableAddFragment(takentoken);
        tableUpdateFragment =  new TableUpdateFragment(takentoken);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_transact);



        //editTextTableName=(EditText)findViewById(R.id.editTextTblName);
        //editTextTableNumber=(EditText)findViewById(R.id.editTextTblNumber);
        //tableInfo=(LinearLayout)findViewById(R.id.tableInfo);

        Intent takenIntent = getIntent();
        takenUserName = takenIntent.getStringExtra("userName"); // SignIn den username alınıp buraya gönderilecek
        takentoken=takenIntent.getStringExtra("token");


        init();

    }



    public void FragmentTableAdd(View v){
        tableAddFragment.show(getSupportFragmentManager(),"ADD TABLE");
    }

    public void FragmentTableUpdate(View v){
        tableUpdateFragment.show(getSupportFragmentManager(),"UPDATE TABLE");
    }

    public void FragmentTableDelete(View v){
        //tableAddFragment.show(getSupportFragmentManager(),"DELETE TABLE");
    }


    /*public void UpdateTable(View v){
        tableInfo.setVisibility(View.VISIBLE);
        Toast.makeText(TableTransactActivity.this,"token: "+takentoken,Toast.LENGTH_LONG).show();
    }*/

   /* public void btnTableSave(View v){
        //
        tableNumber=Integer.valueOf(editTextTableNumber.getText().toString());
        TablesModel tablesModel = new TablesModel();
        tablesModel.setAd(tableName);
        tablesModel.setID(tableNumber);
        Call<TablesModel> tablesModelCall = Service.getServiceApi().putTable(takentoken,tablesModel);
        //Call<TablesModel> tablesModelCall = Service.getServiceApi().putTable(takentoken,tableNumber,tableName);
        tablesModelCall.enqueue(new Callback<TablesModel>() {
            @Override
            public void onResponse(Call<TablesModel> call, Response<TablesModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TableTransactActivity.this,"Request Successful.: "+takentoken,Toast.LENGTH_LONG).show();
                    //Toast.makeText(TableTransactActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    //Toast.makeText(TableTransactActivity.this,"token: "+takentoken,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(TableTransactActivity.this,"Request failed.: "+takentoken,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TablesModel> call, Throwable t) {

            }
        });


    }

    */
}