package cbu.httf.adisyonprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TableTransactActivity extends AppCompatActivity {

    private LinearLayout tableInfo;
    private EditText editTextTableName;
    private EditText editTextTableNumber;
    private String tableName;
    private int tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_transact);

        editTextTableName=(EditText)findViewById(R.id.editTextTblName);
        editTextTableNumber=(EditText)findViewById(R.id.editTextTblNumber);
        tableInfo=(LinearLayout)findViewById(R.id.tableInfo);
    }

    public void ChangeInfoTbl(View v){
        tableInfo.setVisibility(View.VISIBLE);
    }

}