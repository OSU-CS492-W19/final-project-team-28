package com.example.swapisearcher;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.preference.ListPreference;

//import com.example.android.lifecycleweather.data.WeatherPreferences;
import com.example.swapisearcher.data.SWAPI_Repo;
import com.example.swapisearcher.utils.NetworkUtils;
import com.example.swapisearcher.utils.SWAPIUtils;
//import com.example.android.lifecycleweather.utils.OpenWeatherMapUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SWAPIAdapter.OnSWAPIItemClickListener, LoaderManager.LoaderCallbacks<String>, Saved_SWAPI_Adapter.OnSavedItemClickListener{

    //display stuff
    private TextView mSWAPIMainTV;
    private RecyclerView mForecastItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private SWAPIAdapter swapiAdapter;

    private DrawerLayout mDrawerLayout;
    private  NavigationView mNavigationView;
    private Boolean bDrawerIsOpen = false;

    private RecyclerView mSavedRV;
    private Saved_SWAPI_Adapter mSavedAdapter;
    private SavedSWAPIViewModel mSavedViewModel;


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

        listener = new SharedPreferences.OnSharedPreferenceChangeListener(){

            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                refreshDisplay();
            }
        };
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //String Lang = sharedPreferences.getString(getString(R.string.pref_Lang_key), getString(R.string.pref_default));
        String cata = sharedPreferences.getString(getString(R.string.pref_cata_key), getString(R.string.pref_default));

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        // Remove shadow under action bar.
        getSupportActionBar().setElevation(0);

        mDrawerLayout = findViewById(R.id.drawer_layout);


        //set header text
        mSWAPIMainTV = findViewById(R.id.tv_forecast_location);
        mSWAPIMainTV.setText(cata);


        //error checks
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);

        //recycle viewer
        mForecastItemsRV = findViewById(R.id.rv_forecast_items);
        swapiAdapter = new SWAPIAdapter(this);
        mForecastItemsRV.setAdapter(swapiAdapter);

        /*
        mSavedRV = findViewById(R.id.rv_saved_SWAPI);
        mSavedAdapter = new Saved_SWAPI_Adapter(this);
        mSavedRV.setAdapter(mSavedAdapter);
        mSavedRV.setLayoutManager(new LinearLayoutManager(this));
        mSavedRV.setHasFixedSize(true);

        mSavedViewModel = ViewModelProviders.of(this).get(SavedSWAPIViewModel.class);
        mSavedViewModel.getAllRepos().observe(this, new Observer<List<SWAPI_Repo>>(){
            @Override
            public void onChanged(@Nullable List<SWAPI_Repo> repos){
                mSavedAdapter.updateRepoList(repos);
            }
        });
        /**/


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

        //load default prefs
        //PreferenceManager.setDefaultValues(this, R.xml.prefs, true);

        mNavigationView = findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        changeCataPref(menuItem.getTitle().toString());
                        return true;
                    }
                });

        //load SWAPI
        //load
        loadSWAPI();
        //getSupportLoaderManager().initLoader(LOADER_ID, null, this);


        Log.d("refreshDisplay", "Refresh Display was called");
        //loadSWAPI();

    }

    public void refreshDisplay(){

        //refresh

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

        //loader manager
        getSupportLoaderManager().initLoader(SWAPI_SEARCH_LOADER_ID , null, this);


        //
        mForecastItemsRV.setLayoutManager(new LinearLayoutManager(this ));
        mForecastItemsRV.setHasFixedSize(true);

        //load default prefs
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

        SharedPreferences sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String Lang = sharedPreferences.getString(getString(R.string.pref_Lang_key), getString(R.string.pref_default));
        String cata = sharedPreferences.getString(getString(R.string.pref_cata_key), getString(R.string.pref_default));


        //build URL
        String SWAPIURL = SWAPIUtils.buildSWAPIURL(Lang, cata);
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
        //load pref
        SharedPreferences sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String Lang = sharedPreferences.getString(getString(R.string.pref_Lang_key), getString(R.string.pref_default));
        String cata = sharedPreferences.getString(getString(R.string.pref_cata_key), getString(R.string.pref_default));
        //on load finished
        Log.d("onLoadFinished", "-- Got cached JSON data from loader. --");
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        if (s != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mForecastItemsRV.setVisibility(View.VISIBLE);
            mSWAPIStuff = SWAPIUtils.parseSWAPIJSON(s, Lang, cata);
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

    @Override
    public void onSavedItemClick(SWAPI_Repo repo){

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showPreferences();
                return true;
            case android.R.id.home:
                    mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//nav to pref bar
    public void showPreferences(){
        //navigate to prefs page
        Intent preferences = new Intent(this, SettingsActivity.class);
        startActivity(preferences);
    }
    public void changeCataPref(String t){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getString(R.string.pref_cata_key), t);
        editor.apply();
    };
}
