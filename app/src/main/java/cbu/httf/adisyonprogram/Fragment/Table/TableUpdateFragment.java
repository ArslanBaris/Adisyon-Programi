package cbu.httf.adisyonprogram.Fragment.Table;

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

public class TableUpdateFragment extends BottomSheetDialogFragment {
    private ImageView imgClose;
    private Button putTable;
    private EditText editTextTableName;
    private EditText editTextTableNumber;
    private String tableName;
    private int tableNumber;
    private String token;


    public TableUpdateFragment(String token) {

        this.token=token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_table,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextTableName=(EditText)view.findViewById(R.id.editTextUpdateTableName);
        editTextTableNumber=(EditText)view.findViewById(R.id.editTextUpdateTableNumber);

        imgClose = view.findViewById(R.id.update_table_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        putTable = (Button)view.findViewById(R.id.putTable);

        putTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextTableName.getText().toString())||!TextUtils.isEmpty(editTextTableNumber.getText().toString())) {
                    tableName = editTextTableName.getText().toString();
                    tableNumber= Integer.parseInt(editTextTableNumber.getText().toString());

                    TablesModel tablesModel = new TablesModel(tableNumber,tableName);

                    Call<TablesModel> tablesModelCall = Service.getServiceApi().putTable(token, tablesModel);
                    tablesModelCall.enqueue(new Callback<TablesModel>() {
                        @Override
                        public void onResponse(Call<TablesModel> call, Response<TablesModel> response) {
                            Toast.makeText(getContext(), "Request Successful.: " + token, Toast.LENGTH_LONG).show();

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
