package cbu.httf.adisyonprogram.Fragment.User;

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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import cbu.httf.adisyonprogram.Activity.TableTransactActivity;
import cbu.httf.adisyonprogram.Activity.UserTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import cbu.httf.adisyonprogram.data.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateFragment extends BottomSheetDialogFragment {
    private ImageView imgClose;
    private Button putUser;
    private EditText editTextId;
    private EditText editTextUserName;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextEmail;

    private RadioButton rbAdmin;
    private  RadioButton rbUser;

    private int id;
    private String userName;
    private String name;
    private String surname;
    private String eMail;
    private String userTypeName;
    private String token;

    public UserUpdateFragment(String token) {
        this.token=token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user,container,false);
        return view;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        editTextId=(EditText)view.findViewById(R.id.editTextUpdateUserID);
        editTextUserName=(EditText)view.findViewById(R.id.editTextUpdateUsername);
        editTextName=(EditText)view.findViewById(R.id.editTextAddName);
        editTextSurname=(EditText)view.findViewById(R.id.editTextUpdateSurname);
        editTextEmail=(EditText)view.findViewById(R.id.editTextUpdateEmail);
        rbAdmin=(RadioButton)view.findViewById(R.id.rbUpdateAdmin);
        rbUser=(RadioButton)view.findViewById(R.id.rbUpdateUser);

        imgClose = view.findViewById(R.id.update_user_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        putUser = (Button)view.findViewById(R.id.postUser);

        putUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextId.getText().toString())||
                        !TextUtils.isEmpty(editTextUserName.getText().toString())||
                        !TextUtils.isEmpty(editTextName.getText().toString())||
                        !TextUtils.isEmpty(editTextSurname.getText().toString())||
                        !TextUtils.isEmpty(editTextEmail.getText().toString())||
                        (!rbAdmin.isChecked()&& (!rbUser.isChecked()))){

                    id = Integer.parseInt(editTextId.getText().toString());
                    userName = editTextUserName.getText().toString();
                    name = editTextName.getText().toString();
                    surname = editTextSurname.getText().toString();
                    eMail = editTextEmail.getText().toString();

                    if (rbAdmin.isChecked()){
                        userTypeName=rbAdmin.getText().toString();
                    }else{
                        userTypeName=rbUser.getText().toString();
                    }

                    UserModel userModel = new UserModel(id,userName,name,surname,eMail,userTypeName);

                    Call<ResultModel> userModelCall = Service.getServiceApi().putUser(token,userModel);

                    userModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
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
