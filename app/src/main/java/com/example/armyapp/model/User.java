package com.example.armyapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public final int id;

    @ColumnInfo(name = "name")
    public final String name;

    @ColumnInfo(name = "date")
    public final String date;

    public User(int id, String name,String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
