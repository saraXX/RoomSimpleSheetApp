package android.guide.simplesheetapp;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
// TODO 4 CREATE DATABASE CLASS and DO NOT CHANGE ANY THING AFTER RUN, OR DELETE THE APP FROM EMULATOR
@Database(
//        entities = table
        entities = {Product.class},
        version = 1

//        comment out only if you change the database, and don't forget the additional dependencies
//        autoMigrations = {
//        @AutoMigration(from = 1, to = 2)
//        }
        )
// extend the RoomDatabase
public abstract class ProductDB extends RoomDatabase {
//    static class MyAutoMigration implements AutoMigrationSpec { }
//    create a dao object
    public abstract ProductDAO productDAO();
}
