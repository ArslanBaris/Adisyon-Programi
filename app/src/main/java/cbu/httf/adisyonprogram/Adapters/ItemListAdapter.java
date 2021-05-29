package cbu.httf.adisyonprogram.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.Item;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.OrderList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.CardHolder>{

    private List<OrderList> Item_List;
    public int Item_Piece;
    public int Item_ID,Table_ID;
    public String Item_Name;
    List<OrderList> ItemEdit_list = new ArrayList<>();

    public ItemListAdapter(List<OrderList> item_List) {
        this.Item_List = item_List;
    }

    @NonNull
    @Override
    public ItemListAdapter.CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView;
        cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog, parent, false);
        return new CardHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.CardHolder holder, int position) {

        holder.tv_Name.setText(Item_List.get(position).getItem_Name());
        holder.tv_Piece.setText(Item_List.get(position).getItem_Piece());

        holder.btn_pls.setOnClickListener(View -> {
            Item_Name = Item_List.get(position).getItem_Name();
            Item_ID = Item_List.get(position).getItem_ID();
            Table_ID = Item_List.get(position).getTable_ID();
            Item_Piece = Integer.parseInt(Item_List.get(position).getItem_Piece());
            Item_Piece++;
            Item_List.set(position, new OrderList(Item_List.get(position).getItem_Name(), String.valueOf(Item_Piece),Item_List.get(position).getItem_ID(),Item_List.get(position).getTable_ID()));
            holder.tv_Piece.setText(Item_List.get(position).getItem_Piece());
            getItem_List();
        });

        holder.btn_min.setOnClickListener(View -> {
            Item_Name = Item_List.get(position).getItem_Name();
            Item_ID = Item_List.get(position).getItem_ID();
            Table_ID = Item_List.get(position).getTable_ID();
            Item_Piece = Integer.parseInt(Item_List.get(position).getItem_Piece());
            if (Item_Piece > 0)
                Item_Piece--;
            else
                Item_Piece = 0;
            Item_List.set(position, new OrderList(Item_List.get(position).getItem_Name(), String.valueOf(Item_Piece),Item_List.get(position).getItem_ID(),Item_List.get(position).getTable_ID()));
            holder.tv_Piece.setText(Item_List.get(position).getItem_Piece());
            getItem_List();
        });
    }
    boolean control;
    public List<OrderList> getItem_List(){
        List<OrderList> Item_List = new ArrayList<>();
        control=true;
        Item_List.add(new OrderList(Item_Name,String.valueOf(Item_Piece),Item_ID,Table_ID));
        for (int i = 0; i<ItemEdit_list.size();i++){
            if (Item_List.get(0).getItem_Name()==ItemEdit_list.get(i).getItem_Name()) {
                ItemEdit_list.set(i, new OrderList(ItemEdit_list.get(i).getItem_Name(), Item_List.get(0).getItem_Piece(), Item_ID, Table_ID));
                control=false;
            }
        }
        if (control)
        {
            ItemEdit_list.add(new OrderList(Item_List.get(0).getItem_Name(),Item_List.get(0).getItem_Piece(), Item_ID, Table_ID));
        }
        return ItemEdit_list;
    }
    @Override
    public int getItemCount() {
        return Item_List.size();
    }
    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView tv_Name, tv_Piece, ItemPiece, ItemName;
        public Button btn_pls, btn_min;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            tv_Name = itemView.findViewById(R.id.tv_Name);
            tv_Piece = itemView.findViewById(R.id.tv_Piece);
            btn_min = itemView.findViewById(R.id.btn_min);
            btn_pls = itemView.findViewById(R.id.btn_pls);
            ItemName = itemView.findViewById(R.id.ItemName);
            ItemPiece = itemView.findViewById(R.id.ItemPiece);
        }
    }
}
