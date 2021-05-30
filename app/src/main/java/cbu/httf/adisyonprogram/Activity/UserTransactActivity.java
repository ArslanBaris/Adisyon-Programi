package cbu.httf.adisyonprogram.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
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
import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class UserTransactActivity extends AppCompatActivity {

    private Button btnAddUser,btnUpdateUser,btnDeleteUser;

    private UserUpdateFragment userUpdateFragment;

    public   static String takentoken;
    public   String takenUserName;
    public  int userId=0;
    public String userName;
    public String name;
    public String surname;
    public String eMail;
    public String userTypeName;
    private RecyclerView recyclerView;
    private NotificationManagerCompat notificationManager;


    private void init(){
        getSupportActionBar().setTitle("User Transact");
        getSupportActionBar().setIcon(R.drawable.ic_users);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAddUser=(Button)findViewById(R.id.btnUserAdd);
        btnUpdateUser=(Button)findViewById(R.id.btnUserUpdate);
        btnDeleteUser=(Button)findViewById(R.id.btnUserDelete);
        notificationManager = NotificationManagerCompat.from(this);
        userUpdateFragment =  new UserUpdateFragment(takentoken,userId,userName,name,surname,eMail,userTypeName);

        recyclerView=(RecyclerView)findViewById(R.id.user_recyclerView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transact);

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
                userName=userModel.getUserName();
                name=userModel.getName();
                surname=userModel.getSurname();
                eMail=userModel.geteMail();
                userTypeName=userModel.getUserTypeName();
                userUpdateFragment =  new UserUpdateFragment(takentoken,userId,userName,name,surname,eMail,userTypeName);
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

    public void sendOnChannel1() {
        String title = "Deleted User";
        String message = userId+" | "+name+" "+surname;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_person_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    public void UserDelete(View v){
        Call<ResultModel> userDeleteCall = Service.getServiceApi().deleteUser(takentoken,userId);
        userDeleteCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UserTransactActivity.this,"Transaction is successful.", Toast.LENGTH_LONG).show();
                    sendOnChannel1();
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