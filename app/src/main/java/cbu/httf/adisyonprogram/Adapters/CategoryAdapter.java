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
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoriesHolder> {

    private Context mContext;
    private ArrayList<CategoryModel> mCategoryList;
    private OnCategoryItemClickListener listener;


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
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        holder.txtMenuCategory.setText(String.valueOf(mCategoryList.get(position).getCategoryName()));

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }


    public class CategoriesHolder extends RecyclerView.ViewHolder{
        TextView txtMenuCategory;


        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            txtMenuCategory=itemView.findViewById(R.id.category_item_txtCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener!= null && position!=RecyclerView.NO_POSITION){
                        listener.onCategoryItemClick(mCategoryList.get(position),position);
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
