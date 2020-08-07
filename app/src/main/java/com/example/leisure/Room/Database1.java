package com.example.leisure.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = User.class,exportSchema = false,version = 1)
public abstract class Database1 extends RoomDatabase {

    private static final String DB_NAME ="person_db";
    private static Database1 instance;
    public static synchronized Database1 getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),Database1.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;

    }


    public abstract Dao1 dao1();

}
