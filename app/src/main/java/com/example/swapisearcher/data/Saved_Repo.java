package com.example.swapisearcher.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Saved_Repo {
    private SWAPI_Dao mDao;

    public Saved_Repo(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDao = db.SWAPI_Dao();
    }

    public void insertCity(final SWAPI_Repo repo) {
        new InsertAsyncTask(mDao).execute(repo);
    }

    public void deleteCity(SWAPI_Repo repo) {
        new DeleteAsyncTask(mDao).execute(repo);
    }

    public LiveData<List<SWAPI_Repo>> getAllCities() {
        return mDao.getAllRepos();
    }

    public LiveData<SWAPI_Repo> getCityByName(String fullName) {
        return mDao.getRepoByName(fullName);
    }

    private static class InsertAsyncTask extends AsyncTask<SWAPI_Repo, Void, Void> {
        private SWAPI_Dao mAsyncTaskDao;
        InsertAsyncTask(SWAPI_Dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SWAPI_Repo... gitHubRepos) {
            mAsyncTaskDao.insert(gitHubRepos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<SWAPI_Repo, Void, Void> {
        private SWAPI_Dao mAsyncTaskDao;
        DeleteAsyncTask(SWAPI_Dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SWAPI_Repo... gitHubRepos) {
            mAsyncTaskDao.delete(gitHubRepos[0]);
            return null;
        }
    }
}
