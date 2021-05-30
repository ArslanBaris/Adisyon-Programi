package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.LoginRequest;
import cbu.httf.adisyonprogram.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText editTextLoginEmail;
    private EditText editTextLoginPassword;

    private String eMail;
    private String password;
    private  static  String token;

    public void init(){
        getSupportActionBar().setTitle("Sign In");  //ActionBar Text
        getSupportActionBar().setIcon(R.drawable.ic_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextLoginEmail=(EditText)findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword=(EditText)findViewById(R.id.editTextLoginPassword);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();
    }

    public void btnSignIn(View v){

        eMail = editTextLoginEmail.getText().toString();
        password = editTextLoginPassword.getText().toString();
        login(eMail,password);

    }

    public void login(String eMail,String password){

        LoginRequest loginRequest = new LoginRequest();     //login information
        loginRequest.seteMail(eMail);
        loginRequest.setPassword(password);

        Call<LoginResponse> loginResponseCall = Service.getServiceApi().login(loginRequest);

       loginResponseCall.enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

               if(response.isSuccessful()){
                   Toast.makeText(SignInActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();

                   LoginResponse loginResponse = response.body();
                   token = response.body().getToken();

                   Toast.makeText(SignInActivity.this,"Welcome "+loginResponse.getResult().get("name"),Toast.LENGTH_SHORT).show();

                   if(loginResponse.getResult().get("UserTypeName").equals("Root")||loginResponse.getResult().get("UserTypeName").equals("Administrator")) {

                       startActivity(new Intent(SignInActivity.this, AdminActivity.class).
                               putExtra("token", token).
                               putExtra("userName",loginResponse.getResult().get("userName")));


                   }else if(loginResponse.getResult().get("UserTypeName").equals("Manager")) {      //Yönlendirme

                       startActivity(new Intent(SignInActivity.this,TablesActivity.class).
                               putExtra("token",token).
                               putExtra("userName",loginResponse.getResult().get("userName")));
                   }
               }else{
                   Toast.makeText(SignInActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<LoginResponse> call, Throwable t) {
               Toast.makeText(SignInActivity.this,"Failure: "+t.getMessage(),Toast.LENGTH_LONG).show();
           }
       });

    }

}