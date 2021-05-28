package cbu.httf.adisyonprogram.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.TablesModel;

public class TablesMenuAdapter extends ArrayAdapter<TablesModel> {

    private ArrayList<TablesModel> mTableList;
    private Context mContext;
    private ImageView imageView;
    private TextView textView;

    public TablesMenuAdapter(ArrayList<TablesModel> mTableList,Context mContext) {
        super(mContext,R.layout.item_table_menu,mTableList);
        this.mTableList=mTableList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_table_menu,null);


        if(view!=null){
            imageView=view.findViewById(R.id.table_menu_image);
            textView=view.findViewById(R.id.txt_menu_tableName);

            textView.setText(mTableList.get(position).getAd());
            //imageView.setBackgroundResource(R.drawable.ic_table);
        }
        return view;
    }


}
