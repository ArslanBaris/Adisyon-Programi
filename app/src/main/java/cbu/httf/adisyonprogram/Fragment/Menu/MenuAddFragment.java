package cbu.httf.adisyonprogram.Fragment.Menu;

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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import cbu.httf.adisyonprogram.Activity.MenuTransactActivity;
import cbu.httf.adisyonprogram.Activity.TableTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAddFragment extends BottomSheetDialogFragment {

    private ImageView imgClose;
    private Button postMenu;
    private EditText editTextCategory;
    private EditText editTextProductName;
    private EditText editTextUnitPrice;
    private String category;
    private String productName;
    private float unitPrice;
    private String token;

    public MenuAddFragment(String token) {

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
        editTextCategory=(EditText)view.findViewById(R.id.editTextAddCategory);
        editTextProductName=(EditText)view.findViewById(R.id.editTextAddProductName);
        editTextUnitPrice=(EditText)view.findViewById(R.id.editTextAddUnitPrice);

        imgClose = view.findViewById(R.id.add_menu_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        postMenu = (Button)view.findViewById(R.id.postTable);

        postMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextCategory.getText().toString())||
                        !TextUtils.isEmpty(editTextProductName.getText().toString())||
                        !TextUtils.isEmpty(editTextUnitPrice.getText().toString())){

                    category= editTextCategory.getText().toString();
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
