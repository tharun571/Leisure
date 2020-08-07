package com.example.leisure.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "nameof")
    public String nameof;

    @ColumnInfo(name = "loca")
    public String location;

    @ColumnInfo(name = "rate")
    public String rate;

    @ColumnInfo(name = "max")
    public String max;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "longi")
    public String longi;

    public User(int uid, String nameof, String location, String rate, String max, String lat, String longi) {
        this.uid = uid;
        this.nameof = nameof;
        this.location = location;
        this.rate = rate;
        this.max = max;
        this.lat = lat;
        this.longi = longi;
    }

    @Ignore
    public User(String nameof, String location, String rate, String max, String lat, String longi) {
        this.nameof = nameof;
        this.location = location;
        this.rate = rate;
        this.max = max;
        this.lat = lat;
        this.longi = longi;
    }
}
