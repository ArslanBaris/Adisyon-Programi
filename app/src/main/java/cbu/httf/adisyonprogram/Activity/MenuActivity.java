package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.CategoryAdapter;
import cbu.httf.adisyonprogram.Adapters.ItemAdapter;
import cbu.httf.adisyonprogram.Adapters.ItemListAdapter;
import cbu.httf.adisyonprogram.Adapters.ProductAdapter;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.Item;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    LinearLayout layout;
    RecyclerView rcv;
    RecyclerView rcv2;
    List<Item> ItemAdapter_list = new ArrayList<>();
    ItemListAdapter adapter_itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        layout = findViewById(R.id.category);

        for (int i = 1; i <= 15; i++) {
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(370, 150));
            button.setId(i);
            button.setText("B " + i);
            button.setOnClickListener(this::btnCategory_Click);
            layout.addView(button);
        }
    }
    public void btnCategory_Click(View view) {
        int btn_id = view.getId();
        List<Item> ItemList = new ArrayList<>();
        for (int i = 1; i <= btn_id; i++) {
            ItemList.add(new Item(btn_id + "test" + i, "0"));
        }
        adapter_itemList = new ItemListAdapter(ItemList);
        rcv = findViewById(R.id.Items);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter_itemList);
    }

    public void btnOkClick(View view) {
        Intent intent = new Intent(getApplicationContext(), TableItemActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void btnAdd_Click(View view) {

        rcv2 = findViewById(R.id.Rcv);
        rcv2.setHasFixedSize(true);
        rcv2.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter_list = adapter_itemList.getItem_List();
        for (int i = ItemAdapter_list.size() - 1; i >= 0; i--) {
            if (Integer.valueOf(ItemAdapter_list.get(i).getItem_Piece()) == 0)
                ItemAdapter_list.remove(i);
        }
        rcv2.setAdapter(new ItemAdapter(ItemAdapter_list));
    }


}