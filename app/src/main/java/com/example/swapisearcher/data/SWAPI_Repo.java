package com.example.swapisearcher.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "repos")
public class SWAPI_Repo implements Serializable{
    @NonNull
    @PrimaryKey
    public String name;

    public SWAPI_Repo(){}
    public SWAPI_Repo(String n){
        name = n;
    }

}
