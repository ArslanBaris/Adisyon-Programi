package cbu.httf.adisyonprogram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.MenuModel;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsHolder>{

    private Context mContext;
    private ArrayList<MenuModel> mMenuList;
    private OnProductItemClickListener listener;
    private int categoryId;


    public ProductAdapter(ArrayList<MenuModel> mMenuList, Context mContext,int categoryId) {
        this.categoryId=categoryId;
        this.mMenuList=mMenuList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsHolder holder, int position) {
        /*if(mMenuList.get(position).getKategori()==1){
            holder.imageView.setImageResource(R.drawable.);
        }
        else if(mMenuList.get(position).getKategori()==1){
            holder.imageView.setImageResource(R.drawable.);
        }
        else if(mMenuList.get(position).getKategori()==1){
            holder.imageView.setImageResource(R.drawable.);
        }*/
        holder.txtProductName.setText(String.valueOf(mMenuList.get(position).getAd()));
        holder.txtMenuPrice.setText(String.valueOf(mMenuList.get(position).getFiyat()));

    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }


    public class ProductsHolder extends RecyclerView.ViewHolder{
        TextView txtProductName;
        TextView txtMenuPrice;
        ImageView imageView;


        public ProductsHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName=itemView.findViewById(R.id.product_item_txtName);
            txtMenuPrice=itemView.findViewById(R.id.product_item_txtPrice);
            imageView=itemView.findViewById(R.id.tableImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener!= null && position!=RecyclerView.NO_POSITION){
                        listener.onProductItemClick(mMenuList.get(position),position);
                    }
                }
            });
        }
    }

    public interface OnProductItemClickListener{
        void onProductItemClick(MenuModel menuModel, int position);
    }

    public void setOnItemClickListener(OnProductItemClickListener listener){
        this.listener=listener;
    }

}
