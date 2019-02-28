package com.example.swapisearcher;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.preference.PreferenceManager;
//import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.example.android.lifecycleweather.data.WeatherPreferences;
import com.example.swapisearcher.utils.NetworkUtils;
import com.example.swapisearcher.utils.SWAPIUtils;
//import com.example.android.lifecycleweather.utils.OpenWeatherMapUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SWAPIAdapter.OnSWAPIItemClickListener, LoaderManager.LoaderCallbacks<String>{

    //display stuff
    private TextView mSWAPIMainTV;
    private RecyclerView mForecastItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private SWAPIAdapter swapiAdapter;


    //log
    private static final String TAG = MainActivity.class.getSimpleName();




    //refresh stuff
    private SharedPreferences settings;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;




    //arraylist helper
    private ArrayList<SWAPIUtils.SWAPIItem> mSWAPIStuff;
    private static final String SWAPI_URL_KEY = "urlKey";
    private static final int LOADER_ID = 0;
    private static final int SWAPI_SEARCH_LOADER_ID = 0;
    ArrayList<SWAPIUtils.SWAPIItem> SWAPIItems;
    private static final String LIFECYCLE_EVENTS_TEXT_KEY =
            "lifecycleEvents";
    private static final String REPOS_ARRAY_KEY = "WeatherRepos";




    //on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //refresh listener for preferences
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        /*
        listener = new SharedPreferences.OnSharedPreferenceChangeListener(){

        }
        */

        getSupportActionBar().setElevation(0);

        //set header text
        mSWAPIMainTV = findViewById(R.id.tv_forecast_location);
        mSWAPIMainTV.setText("Characters");


        //error checks
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);

        //recycle viewer
        mForecastItemsRV = findViewById(R.id.rv_forecast_items);
        swapiAdapter = new SWAPIAdapter(this);
        mForecastItemsRV.setAdapter(swapiAdapter);

        //lifecycle events key
        if(savedInstanceState != null && savedInstanceState.containsKey(LIFECYCLE_EVENTS_TEXT_KEY)){
            SWAPIItems = (ArrayList<SWAPIUtils.SWAPIItem>) savedInstanceState.getSerializable(REPOS_ARRAY_KEY);
            swapiAdapter.updateSWAPIITems(SWAPIItems);
        }

        //loader manager
        getSupportLoaderManager().initLoader(SWAPI_SEARCH_LOADER_ID , null, this);


        //
        mForecastItemsRV.setLayoutManager(new LinearLayoutManager(this ));
        mForecastItemsRV.setHasFixedSize(true);

        //PreferenceManager.setDefaultValues(this, R.xml.prefs, true);


        //load SWAPI
        //load
        loadSWAPI();
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);


        Log.d("refreshDisplay", "Refresh Display was called");
        loadSWAPI();

    }



    //load SWAPI method
    public void loadSWAPI(){
        //get prefs


        //build URL
        String SWAPIURL = SWAPIUtils.buildSWAPIURL();
        Log.d(TAG, "built the url for SWAPI " + SWAPIURL); //implement tag

        //place in loader and initalize
        Bundle SwapiURL = new Bundle();
        SwapiURL.putString(SWAPI_URL_KEY, SWAPIURL);
        mLoadingIndicatorPB.setVisibility(View.VISIBLE);

        getSupportLoaderManager().restartLoader(LOADER_ID, SwapiURL, this);

    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle bundle) {
        String url = null;
        if (bundle != null) {
            url = bundle.getString(SWAPI_URL_KEY);
        }
        //make weather search loader for SWAPI
        return new SWAPISearchLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {


        //on load finished
        Log.d("onLoadFinished", "-- Got cached JSON data from loader. --");
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        if (s != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mForecastItemsRV.setVisibility(View.VISIBLE);
            mSWAPIStuff = SWAPIUtils.parseSWAPIJSON(s);
            swapiAdapter.updateSWAPIITems(mSWAPIStuff);
        } else {
            mForecastItemsRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
        }
        //
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        //dont need to do anything here
    }

    @Override
    public void onSWAPIItemClick(SWAPIUtils.SWAPIItem swapiItem) {

        //need to make SWAPI item detail Activity

        Intent intent = new Intent(this, SWAPIDetailActivity.class);

        intent.putExtra(SWAPIUtils.SWAPIItem.EXTRA_SWAPI_ITEM, swapiItem);
        startActivity(intent);


    }
}
