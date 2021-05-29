package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextEmail;
    private EditText editTextfirstPassword;
    private EditText editTextsecondPassword;

    private RadioButton rbAdmin;
    private  RadioButton rbUser;

    private String takentoken;

    private String userName;
    private String name;
    private String surname;
    private String eMail;
    private String password;
    private String userTypeName;
    private NotificationManagerCompat notificationManager;

    public  void init(){
        editTextUserName=(EditText)findViewById(R.id.etUsername);
        editTextName=(EditText)findViewById(R.id.etName);
        editTextSurname=(EditText)findViewById(R.id.etSurname);
        editTextEmail=(EditText)findViewById(R.id.etEmail);
        editTextfirstPassword=(EditText)findViewById(R.id.etStPasword);
        editTextsecondPassword=(EditText)findViewById(R.id.etNdPasword);
        rbAdmin=(RadioButton)findViewById(R.id.rbAdmin);
        rbUser=(RadioButton)findViewById(R.id.rbUser);
        notificationManager = NotificationManagerCompat.from(this);
    }

    public void sendOnChannel1(String name,String surname) {
        String title = "Added User";
        String message = name+" "+surname;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_person_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent takenIntent = getIntent();
        takentoken=takenIntent.getStringExtra("token");
        init();

        ((Button)findViewById(R.id.btnSignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(     !TextUtils.isEmpty(editTextUserName.getText().toString())&&
                        !TextUtils.isEmpty(editTextName.getText().toString())&&
                        !TextUtils.isEmpty(editTextSurname.getText().toString())&&
                        !TextUtils.isEmpty(editTextEmail.getText().toString())&&
                        !TextUtils.isEmpty(editTextfirstPassword.getText().toString())&&
                        !TextUtils.isEmpty(editTextsecondPassword.getText().toString())&&
                        (!rbAdmin.isChecked() || (!rbUser.isChecked()))){

                        if(editTextsecondPassword.getText().toString().equals(editTextfirstPassword.getText().toString())) {

                            userName = editTextUserName.getText().toString();
                            name = editTextName.getText().toString();
                            surname = editTextSurname.getText().toString();
                            eMail = editTextEmail.getText().toString();
                            password=editTextfirstPassword.getText().toString();

                           if (rbAdmin.isChecked()){
                               userTypeName=rbAdmin.getText().toString();
                           }else{
                                userTypeName=rbUser.getText().toString();
                           }

                            Call<ResultModel> addUserCall = Service.getServiceApi().postUser(takentoken,userName,name,surname,eMail,password,userTypeName);
                            addUserCall.enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Request Successful." , Toast.LENGTH_LONG).show();
                                    sendOnChannel1(name,surname);
                                    startActivity(new Intent(SignUpActivity.this,UserTransactActivity.class).putExtra("token",takentoken));

                                }else {
                                    Toast.makeText(SignUpActivity.this, "Request failed. ("+response.code()+")" , Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResultModel> call, Throwable t) {
                                Toast.makeText(SignUpActivity.this, "Failure: "+t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        }else{
                        Toast.makeText(SignUpActivity.this, "Eşleşmeyen şifre.",Toast.LENGTH_LONG).show();
                        }

                }else {
                    Toast.makeText(SignUpActivity.this, "Boş alanları doldurunuz.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}