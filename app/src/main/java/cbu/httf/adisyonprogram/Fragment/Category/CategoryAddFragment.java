package cbu.httf.adisyonprogram.Fragment.Category;

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

import cbu.httf.adisyonprogram.Activity.MenuTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class CategoryAddFragment extends BottomSheetDialogFragment {

    private ImageView imgClose;
    private Button postCategory;

    private EditText editTextCategoryName;
    private String categoryName;
    private NotificationManagerCompat notificationManager;

    private String token;

    public CategoryAddFragment(String token) {
        this.token=token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category,container,false);
        return view;
    }

    public void sendOnChannel1(String categoryName) {
        String title = "Added Category";
        String message = categoryName;
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_category)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        editTextCategoryName=(EditText)view.findViewById(R.id.editTextAddCategoryName);
        notificationManager = NotificationManagerCompat.from(getContext());

        imgClose = view.findViewById(R.id.add_category_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        postCategory = (Button)view.findViewById(R.id.postCategory);

        postCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextCategoryName.getText().toString())){

                    categoryName= editTextCategoryName.getText().toString();

                    Call<ResultModel> categoryModelCall = Service.getServiceApi().postCategory(token,categoryName);
                    categoryModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
                                sendOnChannel1(categoryName);
                                ((MenuTransactActivity)getActivity()).recreate();
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
