package cbu.httf.adisyonprogram.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoriesHolder> {



    private Context mContext;
    private ArrayList<CategoryModel> mCategoryList;
    private OnCategoryItemClickListener listener;

    private int selected_position=-1;
    public CategoryAdapter(ArrayList<CategoryModel> mCategoryList, Context mContext) {
        this.mCategoryList=mCategoryList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CategoriesHolder holder, int position) {
        holder.txtMenuCategory.setText(String.valueOf(mCategoryList.get(position).getCategoryName()));

        if (selected_position == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#A1A1A1"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }


    public class CategoriesHolder extends RecyclerView.ViewHolder{
        TextView txtMenuCategory;
        LinearLayout linearLayout;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            txtMenuCategory=itemView.findViewById(R.id.category_item_txtCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onCategoryItemClick(mCategoryList.get(position),position);
                            selected_position = position;
                            notifyDataSetChanged();
                        }
                    }

                }
            });
        }
    }

    public interface OnCategoryItemClickListener{
        void onCategoryItemClick(CategoryModel categoryModel,int position);
    }

    public void setOnItemClickListener(OnCategoryItemClickListener listener){
        this.listener=listener;
    }


}
