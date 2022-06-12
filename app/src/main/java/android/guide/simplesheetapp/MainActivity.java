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

    Button button;
    ListView listView;
    List<Product> productList;
    ProductAdapter productAdapter;
    Bundle saveMesBundle, deleteMsgBundle, updateMsgBundle;
    ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.addBtn);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        saveMesBundle = intent.getBundleExtra(EditProduct.SAVE_PRODUCT_MSG);
        deleteMsgBundle = intent.getBundleExtra(EditProduct.DELETE_PRODUCT_MSG);
        updateMsgBundle = intent.getBundleExtra(EditProduct.UPDATE_PRODUCT_MSG);

// TODO 6.1 initialize the database
        ProductDB productDB = Room.databaseBuilder(getApplicationContext(),
                ProductDB.class, "product").allowMainThreadQueries().build();
// TODO 6.2 declare the dao to interact with the database
        productDAO = productDB.productDAO();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditProduct.class);
                startActivity(intent);
            }
        });
//  TODO 8.1 IF RECEIVE A BUNDLE FROM EditProduct Activity TO INSERT A ROW IN DATABASE
        if (saveMesBundle!= null) {
            insertRow();
        }
        //  TODO 8.2 IF RECEIVE A BUNDLE FROM EditProduct Activity TO DELETE A ROW IN DATABASE
        if (deleteMsgBundle!= null) {
            deleteRow();
        }
        //  TODO 8.3 IF RECEIVE A BUNDLE FROM EditProduct Activity TO UPDATE A ROW IN DATABASE
        if (updateMsgBundle!= null) {
            updateRow();
        }

        // TODO 7 CHECK IF THE DATABASE IS EMPTY OR NOT TO DISPLAY THE ITEM IN LISTVIEW
//            this must be the bottom of all code above to be updated everytime
//            that database has delete, insert or update.
        if(productDAO.getAll()!=null){
            setListView();
        }

    }

    public void setListView(){
        productList = new ArrayList<Product>();
//        get all item of products as an arraylist
        productList = productDAO.getAll();
//        declare adapter
        productAdapter = new ProductAdapter(MainActivity.this,productList);
//        set adapter to list view
        listView.setAdapter(productAdapter);
    }

    public void insertRow(){
        Product p = new Product();
        p.name = saveMesBundle.getString("name");
        p.price = saveMesBundle.getInt("price");
        p.stock = saveMesBundle.getInt("stock");
        productDAO.insertProduct(p);
        Toast.makeText(MainActivity.this,String.format("inserting %s success", p.name),Toast.LENGTH_SHORT).show();

    }

    public void updateRow(){
        Product p = new Product();
        p.name = updateMsgBundle.getString("name");
        p.price = updateMsgBundle.getInt("price");
        p.stock = updateMsgBundle.getInt("stock");

        int msg = productDAO.editById(updateMsgBundle.getInt("id"),p.name,p.price,p.stock);
        if(msg == 0){

            Toast.makeText(MainActivity.this,String.format("updating %s fail", p.name),Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this,String.format("updating %s success", p.name),Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRow(){
        int msg = productDAO.deleteById(deleteMsgBundle.getInt("id"));
        String name = deleteMsgBundle.getString("name");
        if(msg == 0){
            Toast.makeText(MainActivity.this,String.format("deleting %s fail", name),Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this,String.format("deleting %s success", name),Toast.LENGTH_SHORT).show();
        }
    }

}