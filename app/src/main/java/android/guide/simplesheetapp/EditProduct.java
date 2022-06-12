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
    int id, price, stock;
    String name;
    Button saveBtn, cancelBtn, deleteBtn;
    boolean isEditingState;

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



        if(bundle!=null){
            isEditingState = true;
            deleteBtn.setVisibility(View.VISIBLE);
            id = bundle.getInt("id");
            name = bundle.getString("name");
            price = bundle.getInt("price");
            stock = bundle.getInt("stock");
        }
        if (isEditingState==true){
            nameET.setText(name);
            priceET.setText(String.valueOf(price));
            stockET.setText(String.valueOf(stock));
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
//                this mean this activity started through add button
                if(isEditingState==false){
                    intent.putExtra(SAVE_PRODUCT_MSG,b);
                    startActivity(intent);
                }
                else{
//                    this mean this activity started through clicking a listView(row)
//                    and the save button here will update the row instead of insert a new row
//                      and in the update case, send the id of the current product that received from adapter bundle
                    b.putInt("id",id);
                    intent.putExtra(UPDATE_PRODUCT_MSG,b);
                    Log.d("TAG", "onClick: else statment "+b.getString("name"));
                    startActivity(intent);
                }

            }
        });
// click on cancel button will finish the activity
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditProduct.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putInt("id",id);
                b.putString("name",name);
                b.putInt("price", price);
                b.putInt("stock",stock);
                intent.putExtra(DELETE_PRODUCT_MSG,b);

                startActivity(intent);
            }
        });
    }
}
