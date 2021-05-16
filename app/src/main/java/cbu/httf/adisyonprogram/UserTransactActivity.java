package cbu.httf.adisyonprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class UserTransactActivity extends AppCompatActivity {

    private EditText userName;
    private EditText name;
    private EditText surname;
    private EditText eMail;
    private RadioButton rbAdmin;
    private  RadioButton rbUser;
    LinearLayout userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transact);

        userName=(EditText)findViewById(R.id.editTextUsername);
        name=(EditText)findViewById(R.id.editTextName);
        surname=(EditText)findViewById(R.id.editTextSurname);
        eMail=(EditText)findViewById(R.id.editTextEmail);
        rbAdmin=(RadioButton)findViewById(R.id.rdBtnAdmin);
        rbUser=(RadioButton)findViewById(R.id.rdBtnUser);
        userInfo=(LinearLayout)findViewById(R.id.userInfo);

    }

    public void ChangeInfo(View v){
        userInfo.setVisibility(View.VISIBLE);
    }
}