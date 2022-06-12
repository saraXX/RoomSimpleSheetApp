package android.guide.simplesheetapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
// TODO 2 CREATE AN ENTITY CLASS WITH A PROPER ANNOTATION
@Entity
public class Product {
//    autoGenerate mean no need to assign the id, it will be assign as an integer (1,2,3, ...).
    @PrimaryKey(autoGenerate = true)
    public int id;
//    annotation not important the var name will be the column name by default.
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "price")
    public int price;
    @ColumnInfo(name = "stock")
    public int stock;


//    i can use setter and getter also, both are correct

//    public Product(String name, int price, int stock) {
//        this.name = name;
//        this.price = price;
//        this.stock = stock;
//    }
//    public Product() {
//    }

//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public int getStock() {
//        return stock;
//    }
//
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public void setStock(int stock) {
//        this.stock = stock;
//    }
}
