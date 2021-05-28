package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Adapters.UserAdapter;
import cbu.httf.adisyonprogram.Fragment.User.UserUpdateFragment;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTransactActivity extends AppCompatActivity {

    private Button btnAddUser,btnUpdateUser,btnDeleteUser;

    private UserUpdateFragment userUpdateFragment;

    private RecyclerView recyclerView;
    public   static String takentoken;
    public   String takenUserName;
    public  int userId=0;

    private void init(){
        btnAddUser=(Button)findViewById(R.id.btnUserAdd);
        btnUpdateUser=(Button)findViewById(R.id.btnUserUpdate);
        btnDeleteUser=(Button)findViewById(R.id.btnUserDelete);

        userUpdateFragment =  new UserUpdateFragment(takentoken,userId);

        recyclerView=(RecyclerView)findViewById(R.id.user_recyclerView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transact);
        getSupportActionBar().setTitle("User Transact");

        Intent takenIntent = getIntent();
        takenUserName = takenIntent.getStringExtra("userName");
        takentoken=takenIntent.getStringExtra("token");

        init();

        getUsers();

    }

    public void initUsers(ArrayList<UserModel> usersModels){

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        UserAdapter userAdapter = new UserAdapter(usersModels,getApplicationContext());
        recyclerView.setAdapter(userAdapter);

        userAdapter.setOnItemClickListener(new UserAdapter.OnUserItemClickListener() {
            @Override
            public void onUserItemClick(UserModel userModel, int position) {
                userId=userModel.getID();
                userUpdateFragment =  new UserUpdateFragment(takentoken,userId);
            }
        });
    }

    public void getUsers() {
        Call<List<UserModel>> userModelCall = Service.getServiceApi().getUsers(takentoken);

        userModelCall.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()){
                    ArrayList<UserModel> usersModels = new ArrayList<>();
                    usersModels =(ArrayList<UserModel>) response.body();

                    initUsers(usersModels);
                }else{
                    Toast.makeText(UserTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(UserTransactActivity.this, "Request failed. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void FragmentUserAdd(View v){
        startActivity(new Intent(UserTransactActivity.this,SignUpActivity.class).putExtra("token",takentoken));

    }

    public void FragmentUserUpdate(View v){
        userUpdateFragment.show(getSupportFragmentManager(),"UPDATE USER");
    }

    public void UserDelete(View v){
        Call<ResultModel> userDeleteCall = Service.getServiceApi().deleteUser(takentoken,userId);
        userDeleteCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UserTransactActivity.this,"Transaction is successful.", Toast.LENGTH_LONG).show();
                    recreate();
                }else{
                    Toast.makeText(UserTransactActivity.this, "Request failed. "+response.code() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(UserTransactActivity.this, "Request failed. "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }




}