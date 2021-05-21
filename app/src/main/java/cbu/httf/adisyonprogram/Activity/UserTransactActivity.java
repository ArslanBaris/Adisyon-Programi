package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import cbu.httf.adisyonprogram.Network.ServiceApi;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import cbu.httf.adisyonprogram.data.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserTransactActivity extends AppCompatActivity {

    private Button btnAddUser,btnUpdateUser,btnDeleteUser;

    private EditText userName;
    private EditText name;
    private EditText surname;
    private EditText eMail;
    private RadioButton rbAdmin;
    private  RadioButton rbUser;
    private Retrofit retrofit;

    private String BASE_URL = "https://mobiles-app-api.herokuapp.com/";
    private ServiceApi serviceApi;

    private Call<List<UserModel>> call;
    private Call<List<TablesModel>> callTables;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transact);

        btnAddUser=findViewById(R.id.btnUserAdd);
        btnUpdateUser=findViewById(R.id.btnUserUpdate);
        btnDeleteUser=findViewById(R.id.btnUserDelete);


/*
        userName=(EditText)findViewById(R.id.editTextUsername);
        name=(EditText)findViewById(R.id.editTextProductName);
        surname=(EditText)findViewById(R.id.editTextSurname);
        eMail=(EditText)findViewById(R.id.editTextEmail);
        rbAdmin=(RadioButton)findViewById(R.id.rbAdminUpdate);
        rbUser=(RadioButton)findViewById(R.id.rbUserUpdate);*/



        //getUserList();

    }


}