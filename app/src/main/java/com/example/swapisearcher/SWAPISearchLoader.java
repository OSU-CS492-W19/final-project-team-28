package com.example.swapisearcher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.swapisearcher.utils.NetworkUtils;

import java.io.IOException;


public class SWAPISearchLoader extends android.support.v4.content.AsyncTaskLoader<String>{

    private static final String TAG = SWAPISearchLoader.class.getSimpleName();

    private String mSWAPIAdapter;
    private String mSWAPISearchURL;
    private static final int GITHUB_SEARCH_LOADER_ID = 0;



    public SWAPISearchLoader(@NonNull Context context , String url) {
        super(context);
        mSWAPISearchURL = url;
    }

    @Override
    protected void onStartLoading() {
        if (mSWAPISearchURL != null) {
            //forceLoad();
            forceLoad();

            if (mSWAPIAdapter != null) {
                Log.d(TAG, "Delivering cached results");
                deliverResult(mSWAPIAdapter);
            } else {
                forceLoad();
            }
        }
    }



    @Override
    public String loadInBackground() {
        if (mSWAPISearchURL != null) {
            String searchResults = null;
            try {
                searchResults =
                        NetworkUtils.doHTTPGet(mSWAPISearchURL);
                Log.d(TAG, "loading results from SWAPI with URL: " + mSWAPISearchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        } else {
            return null;
        }
    }

    @Override
    public void deliverResult(@Nullable String data) {
        mSWAPIAdapter = data;
        super.deliverResult(data);
    }

}
