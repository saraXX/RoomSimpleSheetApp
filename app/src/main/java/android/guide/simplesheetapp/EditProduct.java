package android.guide.simplesheetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProduct extends AppCompatActivity {
    public static final String SAVE_PRODUCT_MSG = "android.guide.simplesheetapp.SAVE_PRODUCT";
    public static final String DELETE_PRODUCT_MSG = "android.guide.simplesheetapp.DELETE_PRODUCT";
    public static final String UPDATE_PRODUCT_MSG = "android.guide.simplesheetapp.UPDATE_PRODUCT";

    EditText nameET, priceET, stockET;
    int price, stock;
    String name;
    Button saveBtn, cancelBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        nameET = findViewById(R.id.nameET);
        priceET = findViewById(R.id.priceET);
        stockET = findViewById(R.id.stockET);

        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("edited_data");
        String adapterMsg = intent.getStringExtra(ProductAdapter.ADAPTER_MSG);



        if(adapterMsg!=null && adapterMsg.equals("edit")){
            deleteBtn.setVisibility(View.VISIBLE);
            nameET.setText(bundle.getString("name"));
            priceET.setText(String.valueOf(bundle.getInt("price")));
            stockET.setText(String.valueOf(bundle.getInt("stock")));
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameET.getText().toString();
                price = Integer.valueOf(priceET.getText().toString());
                stock = Integer.valueOf(stockET.getText().toString());

                Intent intent = new Intent(EditProduct.this, MainActivity.class);
                Bundle b = new Bundle();

                b.putString("name",name);
                b.putInt("price", price);
                b.putInt("stock",stock);
                if(adapterMsg==null){
                    intent.putExtra(SAVE_PRODUCT_MSG,b);
                    startActivity(intent);
                }
                else{

                    b.putInt("id",bundle.getInt("id"));
                    intent.putExtra(UPDATE_PRODUCT_MSG,b);
                    Log.d("TAG", "onClick: else statment "+b.getString("name"));
                    startActivity(intent);
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameET.getText().toString();
                price = Integer.valueOf(priceET.getText().toString());
                stock = Integer.valueOf(stockET.getText().toString());

                Intent intent = new Intent(EditProduct.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putInt("id",bundle.getInt("id"));
                b.putString("name",name);
                b.putInt("price", price);
                b.putInt("stock",stock);
                intent.putExtra(DELETE_PRODUCT_MSG,b);

                startActivity(intent);
            }
        });



    }

}
