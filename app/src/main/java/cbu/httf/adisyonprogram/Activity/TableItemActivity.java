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

public class TableItemActivity extends AppCompatActivity {
    RecyclerView rcv;
    List<Item> ItemList = new ArrayList<>();
    ItemAdapter adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_item);
        rcv = findViewById(R.id.Rcv_Items);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 1; i <= 10; i++) {
            ItemList.add(new Item("test" + i, "10"));
        }
        adapterItem = new ItemAdapter(ItemList);
        rcv.setAdapter(adapterItem);
    }

    public void btnManuClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void btnPayClick(View view) {

    }
}