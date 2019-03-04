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

    //make a pref for people, vehicles, films, species, starships, and planets
    private final static String SWAPI_People = "people";

    private final static String SWAPI_WOOKIE_FORMAT = "format";
    private final static String SWAPI_WOOKIE_WOOKIE = "wookiee";
    //private final static Boolean wookiepref= true;

    public static class SWAPIItem implements Serializable{
        public static final String EXTRA_SWAPI_ITEM = "com.example.swapisearcher.utils.SWAPIItem.SearchResult";

        //person arguments
        public String name;
        public double height;
        public double mass;
        public String hair_color;
        public String skin_color;
        public String eye_color;
        public String birth_year;
        public String gender;

        //planet arguments
        public String PlanName;
        public String RotPer; //hours to rotate
        public String OrbitPer; //days to rotate?
        public String diameter; //km?
        public String climate;
        public String gravity;
        public String terrain;
        public String SurWat;
        public String Popul;


        //film arguments
        public String title;
        public String ep_id;
        public String op_crawl;
        public String director;
        public String producer;
        public String releasedt;

        //starship arguments
        public String ShipName;
        public String ShipModel;
        public String ShipManu;
        public String ShipCost;
        public String ShipLength; //The length of this starship in meters
        public String ShipAtmospherespeed;
        public String ShipCrew;
        public String ShipPass;
        public String ShipCargo;
        public String ShipConsum;
        public String ShipHyperdrive;
        public String ShipMGLT; //megalights, speed i guess?
        public String ShipClass;

        //species arguments

        public String SpecName;
        public String SpecClass;
        public String SpecDesig;
        public double SpecHeight;
        public String SpecSkin;
        public String SpecHair;
        public String SpecEye;
        public String SpecLife;

        //vehicles arguments
        public String VehiName;
        public String VehiModel;
        public String VehiManu;
        public String VehiCost;
        public String VehiLength;//meters
        public String VehiAtmos;
        public String VehiCrew;
        public String VehiPass;
        public String VehiCargo;
        public String VehiConsum;
        public String VehiClass;

        //public String name;
    }

    public static String buildSWAPIURL(String lang, String categ){
        String SEARCH_CATEG = "starships";

        if(categ.equals("People")){
            SEARCH_CATEG = "people";
        } else if(categ.equals("Films")){
            SEARCH_CATEG = "films";
        }else if(categ.equals("Planets")){
            SEARCH_CATEG = "planets";
        }else if(categ.equals("Species")){
            SEARCH_CATEG = "species";
        }else if(categ.equals("Vehicles")){
            SEARCH_CATEG = "vehicles";
        }else if(categ.equals("Starships")){
            SEARCH_CATEG = "starships";
        }


        if (lang.equals("Wookiee")) {
            return Uri.parse(SWAPI_Base_URL).buildUpon()
                    .appendPath(SEARCH_CATEG)
                    .appendQueryParameter(SWAPI_WOOKIE_FORMAT, SWAPI_WOOKIE_WOOKIE)
                    .toString();
        }else{
            return Uri.parse(SWAPI_Base_URL).buildUpon()
                    .appendPath(SEARCH_CATEG)
                    .toString();
        }
    }

    public static ArrayList<SWAPIItem> parseSWAPIJSON(String SWAPIJSON, String lang, String cata){
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
                //hooo boy, 12 things are incoming to display each subject, and then each subject's wookiee category


                //people
                if(cata.equals("People")) {
                    if (lang.equals("Wookiee")) {
                        //String CharName = SWAPIListElem.getString("name");
                        swapiItem.name = SWAPIListElem.getString("whrascwo");
                        swapiItem.height = SWAPIListElem.getInt("acwoahrracao");
                        swapiItem.mass = SWAPIListElem.getInt("scracc");
                        swapiItem.hair_color = SWAPIListElem.getString("acraahrc_oaooanoorc");
                        swapiItem.skin_color = SWAPIListElem.getString("corahwh_oaooanoorc");
                        swapiItem.eye_color = SWAPIListElem.getString("worowo_oaooanoorc");
                        swapiItem.birth_year = SWAPIListElem.getString("rhahrcaoac_roworarc");
                        swapiItem.gender = SWAPIListElem.getString("rrwowhwaworc");
                    } else {
                        swapiItem.name = SWAPIListElem.getString("name");
                        swapiItem.height = SWAPIListElem.getInt("height");
                        swapiItem.mass = SWAPIListElem.getInt("mass");
                        swapiItem.hair_color = SWAPIListElem.getString("hair_color");
                        swapiItem.skin_color = SWAPIListElem.getString("skin_color");
                        swapiItem.eye_color = SWAPIListElem.getString("eye_color");
                        swapiItem.birth_year = SWAPIListElem.getString("birth_year");
                        swapiItem.gender = SWAPIListElem.getString("gender");
                    }
                }
                //films
                else if(cata.equals("Films")) {

                    //wookie
                    if (lang.equals("Wookiee")) {
                        swapiItem.name = SWAPIListElem.getString("aoahaoanwo");
                        swapiItem.ep_id = SWAPIListElem.getString("woakahcoowawo_ahwa");
                        swapiItem.op_crawl = SWAPIListElem.getString("ooakwowhahwhrr_oarcraohan");
                        swapiItem.director = SWAPIListElem.getString("waahrcwooaaooorc");
                        swapiItem.producer = SWAPIListElem.getString("akrcoowahuoaworc");
                        swapiItem.releasedt = SWAPIListElem.getString("rcwoanworacwo_waraaowo");



                    }
                    //non wookie
                    else{
                        swapiItem.name = SWAPIListElem.getString("title");
                        swapiItem.ep_id = SWAPIListElem.getString("episode_id");
                        swapiItem.op_crawl = SWAPIListElem.getString("opening_crawl");
                        swapiItem.director = SWAPIListElem.getString("director");
                        swapiItem.producer = SWAPIListElem.getString("producer");
                        swapiItem.releasedt = SWAPIListElem.getString("release_date");
                    }

                }

                else if(cata.equals("Species")) {

                    //wookie
                    if (lang.equals("Wookiee")) {
                        swapiItem.name = SWAPIListElem.getString("whrascwo");
                        swapiItem.SpecClass = SWAPIListElem.getString("oaanraccahwwahoaraaoahoowh");
                        swapiItem.SpecDesig = SWAPIListElem.getString("wawocahrrwhraaoahoowh");
                        swapiItem.SpecHeight = SWAPIListElem.getInt("rahoworcrarrwo_acwoahrracao");
                        swapiItem.SpecSkin = SWAPIListElem.getString("corahwh_oaooanoorcc");
                        swapiItem.SpecHair = SWAPIListElem.getString("acraahrc_oaooanoorcc");
                        swapiItem.SpecEye = SWAPIListElem.getString("worowo_oaooanoorcc");
                        swapiItem.SpecLife = SWAPIListElem.getString("rahoworcrarrwo_anahwwwocakrawh");

                    }
                    //non wookie
                    else{
                        swapiItem.name = SWAPIListElem.getString("name");
                        swapiItem.SpecClass = SWAPIListElem.getString("classification");
                        swapiItem.SpecDesig = SWAPIListElem.getString("designation");
                        swapiItem.SpecHeight = SWAPIListElem.getInt("average_height");
                        swapiItem.SpecSkin = SWAPIListElem.getString("skin_colors");
                        swapiItem.SpecHair = SWAPIListElem.getString("hair_colors");
                        swapiItem.SpecEye = SWAPIListElem.getString("eye_colors");
                        swapiItem.SpecLife = SWAPIListElem.getString("average_lifespan");

                    }

                }

                else if(cata.equals("Vehicles")) {

                    //wookie
                    if (lang.equals("Wookiee")) {
                        swapiItem.name = SWAPIListElem.getString("whrascwo");
                        swapiItem.VehiModel = SWAPIListElem.getString("scoowawoan");
                        swapiItem.VehiManu = SWAPIListElem.getString("scrawhhuwwraoaaohurcworc");
                        swapiItem.VehiCost = SWAPIListElem.getString("oaoocao_ahwh_oarcwowaahaoc");
                        swapiItem.VehiLength = SWAPIListElem.getString("anwowhrraoac");
                        swapiItem.VehiAtmos = SWAPIListElem.getString("scrak_raaoscoocakacworcahwhrr_cakwowowa");
                        swapiItem.VehiCrew = SWAPIListElem.getString("oarcwooh");
                        swapiItem.VehiPass = SWAPIListElem.getString("akraccwowhrrworcc");
                        swapiItem.VehiCargo = SWAPIListElem.getString("oararcrroo_oaraakraoaahaoro");
                        swapiItem.VehiConsum = SWAPIListElem.getString("oaoowhchuscrarhanwoc");
                        swapiItem.VehiClass = SWAPIListElem.getString("howoacahoaanwo_oaanracc");

                    }
                    //non wookie
                    else{
                        swapiItem.name = SWAPIListElem.getString("name");
                        swapiItem.VehiModel = SWAPIListElem.getString("model");
                        swapiItem.VehiManu = SWAPIListElem.getString("manufacturer");
                        swapiItem.VehiCost = SWAPIListElem.getString("cost_in_credits");
                        swapiItem.VehiLength = SWAPIListElem.getString("length");
                        swapiItem.VehiAtmos = SWAPIListElem.getString("max_atmosphering_speed");
                        swapiItem.VehiCrew = SWAPIListElem.getString("crew");
                        swapiItem.VehiPass = SWAPIListElem.getString("passengers");
                        swapiItem.VehiCargo = SWAPIListElem.getString("cargo_capacity");
                        swapiItem.VehiConsum = SWAPIListElem.getString("consumables");
                        swapiItem.VehiClass = SWAPIListElem.getString("vehicle_class");

                    }
                }

                else if(cata.equals("Starships")) {

                    //wookiee
                    if (lang.equals("Wookiee")) {
                        swapiItem.name = SWAPIListElem.getString("whrascwo");
                        swapiItem.ShipModel = SWAPIListElem.getString("scoowawoan");
                        swapiItem.ShipManu = SWAPIListElem.getString("scrawhhuwwraoaaohurcworc");
                        swapiItem.ShipCost = SWAPIListElem.getString("oaoocao_ahwh_oarcwowaahaoc");
                        swapiItem.ShipLength = SWAPIListElem.getString("anwowhrraoac");
                        swapiItem.ShipAtmospherespeed = SWAPIListElem.getString("scrak_raaoscoocakacworcahwhrr_cakwowowa");
                        swapiItem.ShipCrew = SWAPIListElem.getString("oarcwooh");
                        swapiItem.ShipPass = SWAPIListElem.getString("akraccwowhrrworcc");
                        swapiItem.ShipCargo = SWAPIListElem.getString("oararcrroo_oaraakraoaahaoro");
                        swapiItem.ShipConsum = SWAPIListElem.getString("oaoowhchuscrarhanwoc");
                        swapiItem.ShipHyperdrive = SWAPIListElem.getString("acroakworcwarcahhowo_rcraaoahwhrr");
                        swapiItem.ShipMGLT = SWAPIListElem.getString("MGLT");//caorarccacahak_oaanracc
                        swapiItem.ShipClass = SWAPIListElem.getString("caorarccacahak_oaanracc");//caorarccacahak_oaanracc

                    }
                    //non wookie
                    else{
                        swapiItem.name = SWAPIListElem.getString("name");
                        swapiItem.ShipModel = SWAPIListElem.getString("model");
                        swapiItem.ShipManu = SWAPIListElem.getString("manufacturer");
                        swapiItem.ShipCost = SWAPIListElem.getString("cost_in_credits");
                        swapiItem.ShipLength = SWAPIListElem.getString("length");
                        swapiItem.ShipAtmospherespeed = SWAPIListElem.getString("max_atmosphering_speed");
                        swapiItem.ShipCrew = SWAPIListElem.getString("crew");
                        swapiItem.ShipPass = SWAPIListElem.getString("passengers");
                        swapiItem.ShipCargo = SWAPIListElem.getString("cargo_capacity");
                        swapiItem.ShipConsum = SWAPIListElem.getString("consumables");
                        swapiItem.ShipHyperdrive = SWAPIListElem.getString("hyperdrive_rating");
                        swapiItem.ShipMGLT = SWAPIListElem.getString("MGLT");
                        swapiItem.ShipClass = SWAPIListElem.getString("starship_class");


                    }

                }

                else if(cata.equals("Planets")) {

                    //wookie
                    if (lang.equals("Wookiee")) {
                        swapiItem.name = SWAPIListElem.getString("whrascwo");
                        swapiItem.RotPer = SWAPIListElem.getString("rcooaoraaoahoowh_akworcahoowa");
                        swapiItem.OrbitPer = SWAPIListElem.getString("oorcrhahaoraan_akworcahoowa");
                        swapiItem.diameter = SWAPIListElem.getString("waahrascwoaoworc");
                        swapiItem.climate = SWAPIListElem.getString("oaanahscraaowo");
                        swapiItem.gravity = SWAPIListElem.getString("rrrcrahoahaoro");
                        swapiItem.terrain = SWAPIListElem.getString("aoworcrcraahwh");
                        swapiItem.SurWat = SWAPIListElem.getString("churcwwraoawo_ohraaoworc");
                        swapiItem.Popul = SWAPIListElem.getString("akooakhuanraaoahoowh");

                    }
                    //non wookie
                    else{
                        swapiItem.name = SWAPIListElem.getString("name");
                        swapiItem.RotPer = SWAPIListElem.getString("rotation_period");
                        swapiItem.OrbitPer = SWAPIListElem.getString("orbital_period");
                        swapiItem.diameter = SWAPIListElem.getString("diameter");
                        swapiItem.climate = SWAPIListElem.getString("climate");
                        swapiItem.gravity = SWAPIListElem.getString("gravity");
                        swapiItem.terrain = SWAPIListElem.getString("terrain");
                        swapiItem.SurWat = SWAPIListElem.getString("surface_water");
                        swapiItem.Popul = SWAPIListElem.getString("population");
                    }

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
