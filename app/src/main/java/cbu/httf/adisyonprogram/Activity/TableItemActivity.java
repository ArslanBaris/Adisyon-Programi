package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.ItemAdapter;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.Item;
import cbu.httf.adisyonprogram.data.model.OrderList;

public class TableItemActivity extends AppCompatActivity {
    RecyclerView rcv;
    List<OrderList> ItemList = new ArrayList<>();
    ItemAdapter adapterItem;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent takenIntent = getIntent();
        token=takenIntent.getStringExtra("token");

        setContentView(R.layout.activity_table_item);
        rcv = findViewById(R.id.Rcv_Items);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        adapterItem = new ItemAdapter(ItemList);
        rcv.setAdapter(adapterItem);
    }

    public void btnManuClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
        this.finish();

    }

    public void btnPayClick(View view) {

    }
}