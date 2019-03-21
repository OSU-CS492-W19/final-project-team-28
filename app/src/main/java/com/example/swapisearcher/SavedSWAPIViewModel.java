package com.example.swapisearcher;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.swapisearcher.data.SWAPI_Repo;
import com.example.swapisearcher.data.Saved_Repo;

import java.util.List;

public class SavedSWAPIViewModel extends AndroidViewModel {
    private Saved_Repo mRepo;

    public SavedSWAPIViewModel(Application application) {
        super(application);
        mRepo = new Saved_Repo(application);
    }

    public void insertRepo(SWAPI_Repo repo) {
        mRepo.insertCity(repo);
    }

    public void deleteRepo(SWAPI_Repo repo) {
        mRepo.deleteCity(repo);
    }

    public LiveData<List<SWAPI_Repo>> getAllRepos() {
        return mRepo.getAllCities();
    }

    public LiveData<SWAPI_Repo> getRepoByName(String fullName) {
        return mRepo.getCityByName(fullName);
    }/**/
}
