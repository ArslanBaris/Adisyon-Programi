package cbu.httf.adisyonprogram.Fragment.Table;

import android.content.Context;
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

import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableAddFragment extends BottomSheetDialogFragment {

    private ImageView imgClose;
    private Button postTable;
    private EditText editTextTableName;
    private String tableName;
    private String token;


    public TableAddFragment(String token) {

        this.token=token;
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
        editTextTableName=(EditText)view.findViewById(R.id.editTextAddTableName);

        imgClose = view.findViewById(R.id.add_table_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        postTable = (Button)view.findViewById(R.id.postTable);

        postTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextTableName.getText().toString())) {
                    tableName = editTextTableName.getText().toString();

                    Call<TablesModel> tablesModelCall = Service.getServiceApi().postTable(token, tableName);
                    tablesModelCall.enqueue(new Callback<TablesModel>() {
                        @Override
                        public void onResponse(Call<TablesModel> call, Response<TablesModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Request Successful.: " + token, Toast.LENGTH_LONG).show();
                                dismiss();

                            } else {
                                Toast.makeText(getContext(), "Request failed.: " + token, Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<TablesModel> call, Throwable t) {

                        }
                    });


                }
            }
        });

    }

}
