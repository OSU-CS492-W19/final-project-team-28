package com.example.swapisearcher.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface SWAPI_Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SWAPI_Repo repo);

    @Delete
    void delete(SWAPI_Repo repo);

    @Query("SELECT * FROM repos")
    LiveData<List<SWAPI_Repo>> getAllRepos();


    @Query("SELECT * FROM repos WHERE name = :fullname LIMIT 1")
    LiveData<SWAPI_Repo> getRepoByName(String fullname);
}
