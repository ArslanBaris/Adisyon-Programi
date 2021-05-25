package cbu.httf.adisyonprogram.Fragment.Menu;

import android.app.NotificationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import cbu.httf.adisyonprogram.Activity.MenuTransactActivity;
import cbu.httf.adisyonprogram.Activity.TableTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.CategoryModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public MenuAddFragment(String token,  ArrayList<Integer> categories) {
        this.categories=categories;
        this.token=token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_menu,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        spinnerAddCategory=(Spinner)view.findViewById(R.id.spinnerAddMenu);
        editTextProductName=(EditText)view.findViewById(R.id.editTextAddProductName);
        editTextUnitPrice=(EditText)view.findViewById(R.id.editTextAddUnitPrice);

        arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item,categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddCategory.setAdapter(arrayAdapter);


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
                if (!TextUtils.isEmpty(editTextProductName.getText().toString())||
                        !TextUtils.isEmpty(editTextUnitPrice.getText().toString())||
                        spinnerAddCategory.getSelectedItem()!=null){

                    spinnerAddCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            category=Integer.parseInt(spinnerAddCategory.getSelectedItem().toString());
                            Toast.makeText(getContext(), "id: "+category , Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    productName= editTextProductName.getText().toString();
                    unitPrice= Float.parseFloat(editTextUnitPrice.getText().toString());

                    Call<ResultModel> menuModelCall = Service.getServiceApi().postMenu(token, category,productName,unitPrice);
                    menuModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();

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
