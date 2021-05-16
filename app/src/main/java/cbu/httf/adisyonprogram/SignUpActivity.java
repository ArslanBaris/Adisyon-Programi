package cbu.httf.adisyonprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {

    private EditText userName;
    private EditText name;
    private EditText surname;
    private EditText eMail;
    private EditText firstPassword;
    private EditText secondPassword;
    private RadioButton rbAdmin;
    private  RadioButton rbUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName=(EditText)findViewById(R.id.etUsername);
        name=(EditText)findViewById(R.id.etName);
        surname=(EditText)findViewById(R.id.etSurname);
        eMail=(EditText)findViewById(R.id.etEmail);
        firstPassword=(EditText)findViewById(R.id.etStPasword);
        secondPassword=(EditText)findViewById(R.id.etNdPasword);
        rbAdmin=(RadioButton)findViewById(R.id.rbAdmin);
        rbUser=(RadioButton)findViewById(R.id.rbUser);


        ((Button)findViewById(R.id.btnSignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            isEmpty(userName,name,surname,eMail,firstPassword,secondPassword,rbAdmin,rbUser);


                //Intent i = new Intent(SignUpActivity.this,SignIn.class);
                //startActivity(i);
            }
        });

    }

    public void isEmpty(EditText etUserName,EditText etName,EditText etSurname,EditText etEmail,EditText etFirstPassword,
                        EditText etSecondPassword,RadioButton rbAdmin,RadioButton rbUser){
        String deneme;
        String userName = etUserName.getText().toString();
        String name = etName.getText().toString();
        String surName = etSurname.getText().toString();
        String eMail = etEmail.getText().toString();
        String firstPassword=etFirstPassword.getText().toString();
        String secondPassword=etSecondPassword.getText().toString();
        boolean admin = false;
        boolean user = false;
        deneme=String.valueOf(admin);

        Toast.makeText(SignUpActivity.this, deneme,Toast.LENGTH_LONG).show();

        if (TextUtils.isEmpty(userName)||TextUtils.isEmpty(name)||TextUtils.isEmpty(surName)||
                TextUtils.isEmpty(eMail)||TextUtils.isEmpty(firstPassword)||TextUtils.isEmpty(secondPassword)|| (admin==false)|| (user==false)){

            Toast.makeText(SignUpActivity.this, "Boş değerleri doldurum",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(SignUpActivity.this, "tamam",Toast.LENGTH_LONG).show();



    }

}