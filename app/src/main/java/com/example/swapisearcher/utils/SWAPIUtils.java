package com.example.swapisearcher.utils;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.swapisearcher.R;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class SWAPIUtils {
    private final static String SWAPI_Base_URL = "https://swapi.co/api";
    private final static String SWAPI_People = "people";
    private final static String SWAPI_WOOKIE_FORMAT = "format";
    private final static String SWAPI_WOOKIE_WOOKIE = "wookiee";
    private final static Boolean wookiepref= true;

    public static class SWAPIItem implements Serializable{
        public static final String EXTRA_SWAPI_ITEM = "com.example.swapisearcher.utils.SWAPIItem.SearchResult";
        public String name;
        public double height;
        public String mass;
        public String hair_color;
        public String skin_color;
        public String eye_color;
        public String birth_year;
        public String gender;

        //public String name;
    }

    public static String buildSWAPIURL(String lang){


        if (lang.equals("Wookiee")) {
            return Uri.parse(SWAPI_Base_URL).buildUpon()
                    .appendPath(SWAPI_People)
                    .appendQueryParameter(SWAPI_WOOKIE_FORMAT, SWAPI_WOOKIE_WOOKIE)
                    .toString();
        }else{
            return Uri.parse(SWAPI_Base_URL).buildUpon()
                    .appendPath(SWAPI_People)
                    .toString();
        }
    }

    public static ArrayList<SWAPIItem> parseSWAPIJSON(String SWAPIJSON, String lang){
        try{
            JSONObject SWAPIOBJ = new JSONObject(SWAPIJSON);
            JSONArray SWAPIList;
            if(lang.equals("Wookiee")) {
                SWAPIList = SWAPIOBJ.getJSONArray("rcwochuanaoc");
            }else{
                SWAPIList = SWAPIOBJ.getJSONArray("results");
            }

            ArrayList<SWAPIItem> SWAPIItemsList = new ArrayList<SWAPIItem>();
            for ( int i = 0; i< SWAPIList.length(); i++){
                SWAPIItem swapiItem = new SWAPIItem();

                JSONObject SWAPIListElem = SWAPIList.getJSONObject(i);
                if (lang.equals("Wookiee")) {
                    //String CharName = SWAPIListElem.getString("name");
                    swapiItem.name = SWAPIListElem.getString("whrascwo");
                    swapiItem.height = SWAPIListElem.getInt("acwoahrracao");
                    swapiItem.mass = SWAPIListElem.getString("scracc");
                    swapiItem.hair_color = SWAPIListElem.getString("acraahrc_oaooanoorc");
                    swapiItem.skin_color = SWAPIListElem.getString("corahwh_oaooanoorc");
                    swapiItem.eye_color = SWAPIListElem.getString("worowo_oaooanoorc");
                    swapiItem.birth_year = SWAPIListElem.getString("rhahrcaoac_roworarc");
                    swapiItem.gender = SWAPIListElem.getString("rrwowhwaworc");
                }else{
                    swapiItem.name = SWAPIListElem.getString("name");
                    swapiItem.height = SWAPIListElem.getInt("height");
                    swapiItem.mass = SWAPIListElem.getString("mass");
                    swapiItem.hair_color = SWAPIListElem.getString("hair_color");
                    swapiItem.skin_color = SWAPIListElem.getString("skin_color");
                    swapiItem.eye_color = SWAPIListElem.getString("eye_color");
                    swapiItem.birth_year = SWAPIListElem.getString("birth_year");
                    swapiItem.gender = SWAPIListElem.getString("gender");
                }
                SWAPIItemsList.add(swapiItem);
            }
            return SWAPIItemsList;
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        /*
        catch (ParseException e){
            e.printStackTrace();
            return null;
        }
*/

    }

}
