package android.guide.simplesheetapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;
// TODO 3 CREATE THE DAO INTERFACE TO INTERACT WITH THE DATABASE
//    YOU CAN CREATE ANY METHOD THAT SERVE THE REQUIREMENT
@Dao
public interface ProductDAO {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id IN (:pIds)")
    List<Product> getByID(int[] pIds);

    @Query("SELECT * FROM product WHERE name LIKE :name")
    Product getByName(String name);

    @Insert
    void insertProduct(Product... product);

    @Delete
    void delete(Product product);

    @Update
    void edit(Product... product);


    @Query("UPDATE product SET name = :name, price= :price, stock= :stock WHERE id =:id")
    int editById(int id, String name, int price, int stock);

    @Query("DELETE FROM product WHERE id =:id")
    int deleteById(int id);






}
