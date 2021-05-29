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
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static cbu.httf.adisyonprogram.Notification.App.CHANNEL_1_ID;

public class MenuUpdateFragment extends BottomSheetDialogFragment {

    private ImageView imgClose;
    private Button putMenu;
    private Spinner spinnerUpdateCategory;
    private EditText editTextID;
    private EditText editTextProductName;
    private EditText editTextUnitPrice;

    private int productId;
    private int category;
    private String productName;
    private float unitPrice;
    private String token;

    private NotificationManagerCompat notificationManager;
    ArrayList<Integer> categories;
    ArrayAdapter<Integer> arrayAdapter;

    public MenuUpdateFragment(String token,  ArrayList<Integer> categories,int category,int productId) {
        this.categories=categories;
        this.token=token;
        this.category=category;
        this.productId=productId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_menu,container,false);
        return view;
    }

    public void sendOnChannel1(int productId,String productName) {
        String title = "Updated Product";
        String message = String.valueOf(productId)+" | "+ productName;
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_product)
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

        editTextID=(EditText)view.findViewById(R.id.editTextUpdateMenuID);
        spinnerUpdateCategory=(Spinner)view.findViewById(R.id.spinnerUpdateMenu);
        editTextProductName=(EditText)view.findViewById(R.id.editTextUpdateProductName);
        editTextUnitPrice=(EditText)view.findViewById(R.id.editTextUpdateUnitPrice);

        notificationManager = NotificationManagerCompat.from(getContext());

        arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item,categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpdateCategory.setAdapter(arrayAdapter);

        if(productId!=0 && category!=0 ){
            editTextID.setText(String.valueOf(productId));
            spinnerUpdateCategory.setSelection( categories.indexOf(category));
        }

        imgClose = view.findViewById(R.id.update_menu_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        putMenu = (Button)view.findViewById(R.id.putMenu);

        putMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextID.getText().toString())||
                        !TextUtils.isEmpty(editTextProductName.getText().toString())||
                        !TextUtils.isEmpty(editTextUnitPrice.getText().toString())||
                        spinnerUpdateCategory.getSelectedItem()!=null){

                    productId= Integer.parseInt(editTextID.getText().toString());
                    category=Integer.parseInt(spinnerUpdateCategory.getSelectedItem().toString());
                    productName= editTextProductName.getText().toString();
                    unitPrice= Float.parseFloat(editTextUnitPrice.getText().toString());

                    MenuModel menuModel = new MenuModel(productId,category,productName,unitPrice);

                    Call<ResultModel> resultModelCall = Service.getServiceApi().putMenu(token, menuModel);
                    resultModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
                                sendOnChannel1(productId,productName);
                                ((MenuTransactActivity)getActivity()).recreate();
                                dismiss();

                            }else{
                                Toast.makeText(getContext(), "Request failed. ("+response.code()+")" , Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {
                            Toast.makeText(getContext(), "Failure: "+t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Boş alanları doldurunuz." , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
