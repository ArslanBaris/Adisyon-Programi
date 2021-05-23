package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cbu.httf.adisyonprogram.Fragment.Menu.MenuAddFragment;
import cbu.httf.adisyonprogram.Fragment.Menu.MenuUpdateFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableAddFragment;
import cbu.httf.adisyonprogram.Fragment.Table.TableUpdateFragment;
import cbu.httf.adisyonprogram.R;

public class MenuTransactActivity extends AppCompatActivity {

    private Button btnAddMenu,btnUpdateMenu,btnDeleteMenu;
    private MenuAddFragment menuAddFragment;
    private MenuUpdateFragment menuUpdateFragment;

    private String takenUserName;
    public   static String takentoken;

    private void init(){
        btnAddMenu=findViewById(R.id.btnMenuAdd);
        btnUpdateMenu=findViewById(R.id.btnMenuUpdate);
        btnDeleteMenu=findViewById(R.id.btnMenuDelete);

        menuAddFragment =  new MenuAddFragment(takentoken);
        menuUpdateFragment =  new MenuUpdateFragment(takentoken);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_transact);

        getSupportActionBar().setTitle("Menu Transact");

        Intent takenIntent = getIntent();
        takenUserName = takenIntent.getStringExtra("userName");
        takentoken=takenIntent.getStringExtra("token");

        init();

    }

    public void FragmentMenuAdd(View v){
        menuAddFragment.show(getSupportFragmentManager(),"ADD MENU");
    }

    public void FragmentMenuUpdate(View v){
        menuUpdateFragment.show(getSupportFragmentManager(),"UPDATE MENU");
    }

    public void FragmentMenuDelete(View v){
        //tableAddFragment.show(getSupportFragmentManager(),"DELETE TABLE");
    }

}