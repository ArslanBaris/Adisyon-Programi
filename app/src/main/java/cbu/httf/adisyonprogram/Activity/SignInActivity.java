package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.LoginRequest;
import cbu.httf.adisyonprogram.data.model.LoginResponse;
import cbu.httf.adisyonprogram.data.model.UserModel;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    public void test(View view){
        Intent intent = new Intent(getApplicationContext(), TableItemActivity.class);
        startActivity(intent);
        this.finish();
    }

    private EditText editTextLoginEmail;
    private EditText editTextLoginPassword;
    private TextView textView;
    private String eMail;
    private String password;
    private  static  String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().setTitle("Sign In");


        editTextLoginEmail=(EditText)findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword=(EditText)findViewById(R.id.editTextLoginPassword);
        textView=(TextView)findViewById(R.id.textView);

    }

   /* public void btnGoToSignUp(View v){
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }*/
    public void btnSignIn(View v){

        eMail = editTextLoginEmail.getText().toString();
        password = editTextLoginPassword.getText().toString();
        login(eMail,password);

    }

    public void login(String eMail,String password){


        LoginRequest loginRequest = new LoginRequest();
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

                   if(loginResponse.getResult().get("UserTypeName").equals("Root")||loginResponse.getResult().get("UserTypeName").equals("Administrator")){

                       startActivity(new Intent(SignInActivity.this,AdminActivity.class).putExtra("token",token).
                               putExtra("userName",loginResponse.getResult().get("userName")));

                   }else if(loginResponse.getResult().get("UserTypeName").equals("User")) {
                       Toast.makeText(SignInActivity.this,"Main Activity",Toast.LENGTH_LONG).show();
                   }

               }else{
                   Toast.makeText(SignInActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<LoginResponse> call, Throwable t) {

           }
       });

    }


    public void getUsers(){
        Call<UserModel> userModelCall = Service.getServiceApi().getUser(token);

        userModelCall.enqueue(new Callback<UserModel>() {

            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Başarılı", Toast.LENGTH_LONG).show();
                    UserModel users = response.body();

                    eMail = "";
                    eMail += "Ad: "+users.getName()+"\n";
                    eMail += "UseType : "+users.getUserTypeName();
                    textView.setText(eMail);
                    Toast.makeText(SignInActivity.this, eMail, Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(SignInActivity.this,"Veri alındı",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
}