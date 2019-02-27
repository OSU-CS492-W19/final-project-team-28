package com.example.swapisearcher.utils;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static class SWAPIItem implements Serializable{
        public static final String EXTRA_SWAPI_ITEM = "com.example.swapisearcher.utils.SWAPItem.SearchResult";
        public String name;
        //public String name;
    }

    public static String buildSWAPIURL(){
        return Uri.parse(SWAPI_Base_URL).buildUpon()
                .appendPath(SWAPI_People)
                .toString();
    }

    public static ArrayList<SWAPIItem> parseSWAPIJSON(String SWAPIJSON){
        try{
            JSONObject SWAPIOBJ = new JSONObject(SWAPIJSON);
            JSONArray SWAPIList =  SWAPIOBJ.getJSONArray("results");

            ArrayList<SWAPIItem> SWAPIItemsList = new ArrayList<SWAPIItem>();
            for ( int i = 0; i< SWAPIList.length(); i++){
                SWAPIItem swapiItem = new SWAPIItem();

                JSONObject SWAPIListElem = SWAPIList.getJSONObject(i);

                //String CharName = SWAPIListElem.getString("name");
                swapiItem.name = SWAPIListElem.getString("name");


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
