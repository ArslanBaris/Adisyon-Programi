package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import cbu.httf.adisyonprogram.R;

public class MenuTransactActivity extends AppCompatActivity {

    private Button btnAddMenu,btnUpdateMenu,btnDeleteMenu;
    //private TableAddFragment tableAddFragment;


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

        //editTextCategory=(EditText)findViewById(R.id.edit);
        //editTextProductName=(EditText)findViewById(R.id.editTextTblNumber);
       // editTextUnitPrice=(EditText)findViewById(R.id.editTextTblNumber);

    }

}