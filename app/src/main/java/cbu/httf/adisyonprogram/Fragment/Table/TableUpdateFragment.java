package cbu.httf.adisyonprogram.Fragment.Table;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import org.json.JSONObject;

import cbu.httf.adisyonprogram.Activity.TableTransactActivity;
import cbu.httf.adisyonprogram.Network.Service;
import cbu.httf.adisyonprogram.R;
import cbu.httf.adisyonprogram.data.model.ResultModel;
import cbu.httf.adisyonprogram.data.model.TablesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableUpdateFragment extends BottomSheetDialogFragment {
    private ImageView imgClose;
    private Button putTable;
    private EditText editTextTableId;
    private EditText editTextTableName;
    private EditText editTextTableNumber;
    private int tableId;
    private String tableName;
    private int tableNumber;
    private String token;


    public TableUpdateFragment(String token,int tableId) {
        this.token=token;
        this.tableId=tableId;
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
        editTextTableId=(EditText)view.findViewById(R.id.editTextUpdateTableId);
        editTextTableName=(EditText)view.findViewById(R.id.editTextUpdateTableName);
        editTextTableNumber=(EditText)view.findViewById(R.id.editTextUpdateTableNumber);

        if(tableId!=0)
            editTextTableId.setText(String.valueOf(tableId));

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
                if (!TextUtils.isEmpty(editTextTableId.getText().toString())||!TextUtils.isEmpty(editTextTableName.getText().toString())||
                        !TextUtils.isEmpty(editTextTableNumber.getText().toString())) {
                    tableId= Integer.parseInt(editTextTableId.getText().toString());
                    tableName = editTextTableName.getText().toString();
                    tableNumber = Integer.parseInt(editTextTableNumber.getText().toString());

                    TablesModel tablesModel = new TablesModel(tableId,tableName,tableNumber);

                    Call<ResultModel> resultModelCall = Service.getServiceApi().putTable(token, tablesModel);


                        resultModelCall.enqueue(new Callback<ResultModel>() {

                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(getContext(), "Request Successful." , Toast.LENGTH_LONG).show();
                                    ((TableTransactActivity)getActivity()).recreate();
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
