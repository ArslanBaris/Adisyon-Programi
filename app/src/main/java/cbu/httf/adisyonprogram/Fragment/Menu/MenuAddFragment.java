package cbu.httf.adisyonprogram.Fragment.Menu;

import android.app.Notification;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import cbu.httf.adisyonprogram.Activity.MenuTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class MenuAddFragment extends BottomSheetDialogFragment {

    private ImageView imgClose;
    private Button postMenu;
    private Spinner spinnerAddCategory;
    private EditText editTextProductName;
    private EditText editTextUnitPrice;
    private int category;
    private String productName;
    private float unitPrice;
    private String token;

    ArrayList<Integer> categories;
    ArrayAdapter<Integer> arrayAdapter;
    private NotificationManagerCompat notificationManager;

    public MenuAddFragment(String token,  ArrayList<Integer> categories,int category) {
        this.categories=categories;
        this.token=token;
        this.category=category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_menu,container,false);
        return view;
    }

    public void sendOnChannel1(String productName) {
        String title = "Added Product";
        String message = productName;
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_product_2)
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

        spinnerAddCategory=(Spinner)view.findViewById(R.id.spinnerAddMenu);
        editTextProductName=(EditText)view.findViewById(R.id.editTextAddProductName);
        editTextUnitPrice=(EditText)view.findViewById(R.id.editTextAddUnitPrice);

        notificationManager = NotificationManagerCompat.from(getContext());

        arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item,categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddCategory.setAdapter(arrayAdapter);

        if(category!=0)
            spinnerAddCategory.setSelection(categories.indexOf(category));


        imgClose = view.findViewById(R.id.add_menu_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        postMenu = (Button)view.findViewById(R.id.postMenu);

        postMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextProductName.getText().toString())&&
                        !TextUtils.isEmpty(editTextUnitPrice.getText().toString())&&
                        spinnerAddCategory.getSelectedItem()!=null){

                    category=Integer.parseInt(spinnerAddCategory.getSelectedItem().toString());
                    productName= editTextProductName.getText().toString();
                    unitPrice= Float.parseFloat(editTextUnitPrice.getText().toString());

                    Call<ResultModel> menuModelCall = Service.getServiceApi().postMenu(token, category,productName,unitPrice);
                    menuModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
                                sendOnChannel1(productName);
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
