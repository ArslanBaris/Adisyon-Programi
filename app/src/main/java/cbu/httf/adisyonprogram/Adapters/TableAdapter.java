package cbu.httf.adisyonprogram.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TablesHolder> {

    private Context mContext;
    private ArrayList<TablesModel> mTableList;

    private OnTableItemClickListener listener;
    private int selected_position=-1;

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


        if (selected_position == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#A1A1A1"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onTableItemClick(mTableList.get(position),position);
                            selected_position = position;
                            notifyDataSetChanged();
                        }
                    }
                }
            });

        }
    }

    public interface OnTableItemClickListener{
        void onTableItemClick(TablesModel tablesModel, int position);
    }

    public void setOnItemClickListener(OnTableItemClickListener listener){
        this.listener=listener;
    }

}
