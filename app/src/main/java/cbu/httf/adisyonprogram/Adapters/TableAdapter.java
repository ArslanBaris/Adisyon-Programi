package cbu.httf.adisyonprogram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TablesHolder> {

    private Context mContext;
    private ArrayList<TablesModel> mTableList;


    public TableAdapter(ArrayList<TablesModel> mTableList,Context mContext) {
        this.mTableList=mTableList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public TablesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table,parent,false);
        return new TablesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TablesHolder holder, int position) {
        holder.txtTableId.setText(String.valueOf(mTableList.get(position).getID()));
        holder.txtTableNumber.setText(String.valueOf(mTableList.get(position).getTableNo()));
        holder.txtTableName.setText(mTableList.get(position).getAd());
    }

    @Override
    public int getItemCount() {
        return mTableList.size();
    }


    public class TablesHolder extends RecyclerView.ViewHolder{
        TextView txtTableId;
        TextView txtTableNumber;
        TextView txtTableName;

        public TablesHolder(@NonNull View itemView) {
            super(itemView);
            txtTableId=itemView.findViewById(R.id.table_item_txtTableId);
            txtTableName= itemView.findViewById(R.id.table_item_txtTableName);
            txtTableNumber= itemView.findViewById(R.id.table_item_txtTableNumber);
        }
    }
}
