package cbu.httf.adisyonprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MenuTransactActivity extends AppCompatActivity {

    private LinearLayout menuInfo;
    private EditText editTextCategory;
    private EditText editTextProductName;
    private EditText editTextUnitPrice;
    private String category;
    private String productName;
    private float unitPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_transact);

        editTextCategory=(EditText)findViewById(R.id.editTextTblName);
        editTextProductName=(EditText)findViewById(R.id.editTextTblNumber);
        editTextUnitPrice=(EditText)findViewById(R.id.editTextTblNumber);
        menuInfo=(LinearLayout)findViewById(R.id.menuInfo);
    }

    public void ChangeInfoTbl(View v){
        menuInfo.setVisibility(View.VISIBLE);
    }
}