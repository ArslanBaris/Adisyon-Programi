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
import cbu.httf.adisyonprogram.data.model.MenuModel;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuUpdateFragment extends BottomSheetDialogFragment {

    private ImageView imgClose;
    private Button putMenu;
    private EditText editTextID;
    private EditText editTextCategory;
    private EditText editTextProductName;
    private EditText editTextUnitPrice;

    private int id;
    private String category;
    private String productName;
    private float unitPrice;
    private String token;

    public MenuUpdateFragment(String token) {
        this.token=token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_menu,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        editTextID=(EditText)view.findViewById(R.id.editTextUpdateMenuID);
        editTextCategory=(EditText)view.findViewById(R.id.editTextUpdateCategory);
        editTextProductName=(EditText)view.findViewById(R.id.editTextUpdateProductName);
        editTextUnitPrice=(EditText)view.findViewById(R.id.editTextUpdateUnitPrice);

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
                        !TextUtils.isEmpty(editTextCategory.getText().toString())||
                        !TextUtils.isEmpty(editTextProductName.getText().toString())||
                        !TextUtils.isEmpty(editTextUnitPrice.getText().toString())){

                    id= Integer.parseInt(editTextID.getText().toString());
                    category= editTextCategory.getText().toString();
                    productName= editTextProductName.getText().toString();
                    unitPrice= Float.parseFloat(editTextUnitPrice.getText().toString());

                    MenuModel menuModel = new MenuModel(id,category,productName,unitPrice);

                    Call<ResultModel> resultModelCall = Service.getServiceApi().putMenu(token, menuModel);
                    resultModelCall.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
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
