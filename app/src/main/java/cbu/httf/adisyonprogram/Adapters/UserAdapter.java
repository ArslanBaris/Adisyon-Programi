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
import cbu.httf.adisyonprogram.data.model.UserModel;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersHolder>  {

    private Context mContext;
    private ArrayList<UserModel> mUserList;


    public UserAdapter(ArrayList<UserModel> mUserList,Context mContext) {
        this.mUserList=mUserList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.txtUserId.setText(String.valueOf(mUserList.get(position).getID()));
        holder.txtUserName.setText(String.valueOf(mUserList.get(position).getUserName()));
        holder.txtName.setText(mUserList.get(position).getName());
        holder.txtSurname.setText(mUserList.get(position).getSurname());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UsersHolder extends RecyclerView.ViewHolder{
        TextView txtUserId;
        TextView txtUserName;
        TextView txtName;
        TextView txtSurname;

        public UsersHolder(@NonNull View itemView) {
            super(itemView);
            txtUserId=itemView.findViewById(R.id.user_item_txtUserId);
            txtUserName= itemView.findViewById(R.id.user_item_txtUserName);
            txtName= itemView.findViewById(R.id.user_item_txtName);
            txtSurname= itemView.findViewById(R.id.user_item_txtSurname);
        }
    }
}
