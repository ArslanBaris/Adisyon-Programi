package cbu.httf.adisyonprogram.Fragment.User;

import android.app.Notification;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import cbu.httf.adisyonprogram.Activity.UserTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class UserUpdateFragment extends BottomSheetDialogFragment {
    private ImageView imgClose;
    private Button putUser;
    private EditText editTextId;
    private EditText editTextUserName;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private RadioButton rbAdmin;
    private  RadioButton rbUser;

    private int id;
    private String userName;
    private String name;
    private String surname;
    private String eMail;
    private String password;
    private String userTypeName;
    private String token;

    private NotificationManagerCompat notificationManager;

    public UserUpdateFragment(String token,int id) {
        this.id=id;
        this.token=token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user,container,false);
        return view;
    }

    public void sendOnChannel1(int id,String name,String surname) {
        String title = "Updated User";
        String message = String.valueOf(id)+" | "+ name+" "+surname;
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_person_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        editTextId=(EditText)view.findViewById(R.id.editTextUpdateUserID);
        editTextUserName=(EditText)view.findViewById(R.id.editTextUpdateUsername);
        editTextName=(EditText)view.findViewById(R.id.editTextUpdateName);
        editTextSurname=(EditText)view.findViewById(R.id.editTextUpdateSurname);
        editTextEmail=(EditText)view.findViewById(R.id.editTextUpdateEmail);
        editTextPassword=(EditText)view.findViewById(R.id.editTextUpdatePassword);
        rbAdmin=(RadioButton)view.findViewById(R.id.rbUpdateAdmin);
        rbUser=(RadioButton)view.findViewById(R.id.rbUpdateUser);
        notificationManager = NotificationManagerCompat.from(getContext());

        if(id!=0)
            editTextId.setText(String.valueOf(id));

        imgClose = view.findViewById(R.id.update_user_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        putUser = (Button)view.findViewById(R.id.putUser);

        putUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextId.getText().toString())&&
                        !TextUtils.isEmpty(editTextUserName.getText().toString())&&
                        !TextUtils.isEmpty(editTextName.getText().toString())&&
                        !TextUtils.isEmpty(editTextSurname.getText().toString())&&
                        !TextUtils.isEmpty(editTextEmail.getText().toString())&&
                        !TextUtils.isEmpty(editTextPassword.getText().toString())&&
                        (rbAdmin.isChecked() || (rbUser.isChecked()))){

                    id = Integer.parseInt(editTextId.getText().toString());
                    userName = editTextUserName.getText().toString();
                    name = editTextName.getText().toString();
                    surname = editTextSurname.getText().toString();
                    eMail = editTextEmail.getText().toString();
                    password=editTextPassword.getText().toString();

                    if (rbAdmin.isChecked()){
                        userTypeName=rbAdmin.getText().toString();
                    }else{
                        userTypeName=rbUser.getText().toString();
                    }

                    UserModel userModel = new UserModel(id,userName,name,surname,eMail,password,userTypeName);

                    Call<ResultModel> userModelCall = Service.getServiceApi().putUser(token,userModel);

                    userModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
                                sendOnChannel1(id,name,surname);
                                ((UserTransactActivity)getActivity()).recreate();
                                dismiss();

                            } else {
                                Toast.makeText(getContext(), "Request failed. ("+response.code()+")" , Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {
                            Toast.makeText(getContext(), "Failure: "+t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Boş alanları doldurunuz.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
