package android.guide.simplesheetapp;
//  todo 0 app show simple inventory data base
//      activity 1 show all items and in the button there is a button to add an item,
//      which will open a new activity has submit and cancel buttons, and ofc the edit texts
//      any item in the list clickable to be delete, edited.
//      each row hase name, category, number, price.
//

// todo - app specification :
//    1- database {Product Entity, DAO, ProductDB}
//    2- list view {custom Array adapter}
//    3- send through Activity {from ProductAdapter to EditProduct,
//                          and from EditProduct to ProductAdapter}
//    4-
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "android.guide.simplesheetapp.MESSAGE";

    Button button;
    ListView listView;
    List<Product> productList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.addBtn);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        Bundle save_message = intent.getBundleExtra(EditProduct.SAVE_PRODUCT_MSG);
        Bundle delete_message = intent.getBundleExtra(EditProduct.DELETE_PRODUCT_MSG);
        Bundle update_message = intent.getBundleExtra(EditProduct.UPDATE_PRODUCT_MSG);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditProduct.class);
                String activityType = "add";
                intent.putExtra(EXTRA_MESSAGE,activityType);
                startActivity(intent);
            }
        });

        ProductDB productDB = Room.databaseBuilder(getApplicationContext(),
                ProductDB.class, "product").allowMainThreadQueries().build();

        ProductDAO productDAO = productDB.productDAO();

        if (save_message!= null) {
            Log.d("TAG", "onCreate: SAVE msg "+save_message);
            Product p = new Product();
            p.name = save_message.getString("name");
            p.price = save_message.getInt("price");
            p.stock = save_message.getInt("stock");
            productDAO.insertProduct(p);
        }
        if (delete_message!= null) {
            Log.d("TAG", "onCreate: DELETE msg "+delete_message);
            Product p = new Product();
            p.name = delete_message.getString("name");
            p.price = delete_message.getInt("price");
            p.stock = delete_message.getInt("stock");
            int msg = productDAO.deleteById(delete_message.getInt("id"));
            if(msg == 0){
                Toast.makeText(MainActivity.this,"sorry fail",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"success delete ",Toast.LENGTH_SHORT).show();
            }
        }

        if (update_message!= null) {
            Product p = new Product();

            p.name = update_message.getString("name");
            p.price = update_message.getInt("price");
            p.stock = update_message.getInt("stock");

            int msg = productDAO.editById(update_message.getInt("id"),p.name,p.price,p.stock);
            if(msg == 0){
                Toast.makeText(MainActivity.this,"sorry fail",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"success update ",Toast.LENGTH_SHORT).show();
            }
        }

        if(productDAO.getAll()!=null){
            productList = new ArrayList<Product>();
            productList = productDAO.getAll();
            productAdapter = new ProductAdapter(MainActivity.this,productList);
            listView.setAdapter(productAdapter);
        }
    }

}