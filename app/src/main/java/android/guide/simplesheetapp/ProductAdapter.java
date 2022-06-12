package android.guide.simplesheetapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
// TODO 5 CREATE THE ADAPTER CLASS
public class ProductAdapter extends ArrayAdapter<Product> {
    String TAG;
//    use this message to know that we click to an existing row, to show the delete button in edit layout
    public static final String ADAPTER_MSG = "android.guide.simplesheetapp.ADAPTER_MSG";
//constructor of the adapter receive a context and list
    public ProductAdapter(@NonNull Context context, List<Product> productList) {
        // pass the context and productList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, productList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_layput, parent, false);

        }

// get the position of the view from the ArrayAdapter
        Product currentProduct = getItem(position);

        // then according to the position of the view assign the desired View
        TextView nameTV = currentItemView.findViewById(R.id.name);
        TextView priceTV = currentItemView.findViewById(R.id.price);
        TextView stockTV = currentItemView.findViewById(R.id.stock);

        nameTV.setText(currentProduct.name);
        priceTV.setText(currentProduct.price+"RS");
        stockTV.setText(String.valueOf(currentProduct.stock));

        currentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),EditProduct.class);
                String activityType = "edit";

                Bundle bundle = new Bundle();
                bundle.putInt("id",currentProduct.id);
                bundle.putString("name",currentProduct.name);
                bundle.putInt("price",currentProduct.price);
                bundle.putInt("stock",currentProduct.stock);
                // send tow msg, a bundle that contain each row data,
//                and a msg for showing the delete button
                intent.putExtra("edited_data",bundle);
                intent.putExtra(ADAPTER_MSG,activityType);

                getContext().startActivity(intent);
            }
        });
        return currentItemView;
    }
}
