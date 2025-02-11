package cbu.httf.adisyonprogram.Fragment.Table;

import android.app.Notification;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import cbu.httf.adisyonprogram.Activity.TableTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class TableAddFragment extends BottomSheetDialogFragment {


    private ImageView imgClose;
    private Button postTable;
    private EditText editTextTableName;
    private EditText editTextTableNumber;
    private String tableName;
    private int tableNumber;
    private String token;

    private NotificationManagerCompat notificationManager;

    public TableAddFragment(String token) {

        this.token=token;
    }

    private  void init(View view){
        editTextTableName=(EditText)view.findViewById(R.id.editTextAddTableName);
        editTextTableNumber=(EditText)view.findViewById(R.id.editTextAddTableNumber);
        imgClose = view.findViewById(R.id.add_table_imgClose);
        postTable = (Button)view.findViewById(R.id.postTable);
        notificationManager = NotificationManagerCompat.from(getContext());

    }

    public void sendOnChannel1(int tableNumber,String tableName) {
        String title = "Added Table";
        String message = tableName+" : "+String.valueOf(tableNumber) ;
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_table_1)
                .setContentTitle(title)
                .setContentText(message)

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_table,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });


        postTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (    !TextUtils.isEmpty(editTextTableName.getText().toString()) &&
                        !TextUtils.isEmpty(editTextTableNumber.getText().toString())) {

                    tableName = editTextTableName.getText().toString();
                    tableNumber = Integer.parseInt(editTextTableNumber.getText().toString());

                    Call<ResultModel> tableModelCall = Service.getServiceApi().postTable(token, tableName,tableNumber);

                    tableModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if (response.isSuccessful()) {
                                                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
                                sendOnChannel1(tableNumber,tableName);

                                ((TableTransactActivity)getActivity()).recreate();
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
