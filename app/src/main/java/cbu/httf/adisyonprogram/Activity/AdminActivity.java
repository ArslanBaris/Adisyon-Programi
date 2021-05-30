package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cbu.httf.adisyonprogram.R;

public class AdminActivity extends AppCompatActivity {

    private TextView txtUser;
    private  String takenUserName;
    private  static String takentoken;

    public void init(){
        getSupportActionBar().setTitle("Admin Page");   //ActionBar Text
        txtUser=(TextView)findViewById(R.id.txtAdminUserName);

        Intent takenIntent = getIntent();
        takenUserName = takenIntent.getStringExtra("userName");
        takentoken=takenIntent.getStringExtra("token");

        txtUser.setText(takenUserName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
    }

    public void btnUser(View v){
        Intent intent = new Intent(AdminActivity.this,UserTransactActivity.class);
        intent.putExtra("userName",takenUserName);
        intent.putExtra("token",takentoken);
        startActivity(intent);
    }

    public void btnTable(View v){
        Intent intent = new Intent(AdminActivity.this,TableTransactActivity.class);
        intent.putExtra("userName",takenUserName);
        intent.putExtra("token",takentoken);
        startActivityForResult(intent,1);
    }

    public void btnMenu(View v){
        Intent intent = new Intent(AdminActivity.this,MenuTransactActivity.class);
        intent.putExtra("userName",takenUserName);
        intent.putExtra("token",takentoken);
        startActivity(intent);
    }

}