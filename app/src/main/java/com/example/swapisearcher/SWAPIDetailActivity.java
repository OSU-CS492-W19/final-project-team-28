package com.example.swapisearcher;



//normals
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//package speceific
import com.example.swapisearcher.utils.SWAPIUtils;

public class SWAPIDetailActivity extends AppCompatActivity {

    private TextView Name;
    private TextView Detail1;
    private TextView Detail2;
    private TextView Detail3;
    private TextView Detail4;
    private TextView Detail5;
    private TextView Detail6;
    private TextView Detail7;
    private TextView Detail8;
    private TextView Detail9;
    private TextView Detail10;
    private TextView Detail11;
    private TextView Detail12;
    private TextView Detail13;

    //units for cm, m, and km
    private String CMLengthunits;
    private String MLengthunits;
    private String KMLengthunits;

    //divisors for conversion to inches, feet, and yards
    private double CMdivisor;
    private double Mdivisor;
    private double KMdivisor;

    //weight designations
    private String KGunits;

    //weight conversion
    private double KGdivisor;

    private SWAPIUtils.SWAPIItem mswapiItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_item_detail);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String forecastUnits = sharedPreferences.getString(getString(R.string.pref_key), getString(R.string.pref_default));
        String cata = sharedPreferences.getString(getString(R.string.pref_cata_key), getString(R.string.pref_cata_default));

        if (forecastUnits.equals("Metric")){
            CMLengthunits = " cm";
            MLengthunits = " m";
            KMLengthunits = " km";

            CMdivisor = 1;
            Mdivisor = 1;
            KMdivisor = 1;

            KGunits = " kg";

            KGdivisor = 1;
        } else {


            CMLengthunits = " in";
            MLengthunits = " Yards";
            KMLengthunits = " Miles";

            CMdivisor = 2.5;
            Mdivisor = 1.094;
            KMdivisor = 1.60934;

            KGunits = " lbs";
            KGdivisor = 2.205;
        }


        //textview findings

        //people
        Name = (TextView) findViewById(R.id.tv_name);
        Detail1 = (TextView) findViewById(R.id.tv_Detail1);
        Detail2 = (TextView) findViewById(R.id.tv_Detail2);
        Detail3 = (TextView) findViewById(R.id.tv_Detail3);
        Detail4 = (TextView) findViewById(R.id.tv_Detail4);
        Detail5 = (TextView) findViewById(R.id.tv_Detail5);
        Detail6 = (TextView) findViewById(R.id.tv_Detail6);
        Detail7 = (TextView) findViewById(R.id.tv_Detail7);
        Detail8 = (TextView) findViewById(R.id.tv_Detail8);
        Detail9 = (TextView) findViewById(R.id.tv_Detail9);
        Detail10 = (TextView) findViewById(R.id.tv_Detail10);
        Detail11 = (TextView) findViewById(R.id.tv_Detail11);
        Detail12 = (TextView) findViewById(R.id.tv_Detail12);
        Detail13 = (TextView) findViewById(R.id.tv_Detail13);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(SWAPIUtils.SWAPIItem.EXTRA_SWAPI_ITEM)) {
            mswapiItem = (SWAPIUtils.SWAPIItem) intent.getSerializableExtra(SWAPIUtils.SWAPIItem.EXTRA_SWAPI_ITEM);
            fillInLayoutText(mswapiItem);
        }

    }


//options menu

//on options item selected
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.swapi_detail, menu);
    return true;
}

//get item selected (share character)
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_share:
            shareRepo();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}

//share character
public void shareRepo() {
    if (mswapiItem != null) {
        //String wow = R.string.share_chooser_title;
        String shareText = getString(R.string.share_repo_text, mswapiItem.name, "On the SWAPISearcher");
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(shareText)
                .setChooserTitle(R.string.share_chooser_title)
                .startChooser();
    }
}



    //fill in layout
    private void fillInLayoutText(SWAPIUtils.SWAPIItem swapitem) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //String forecastUnits = sharedPreferences.getString(getString(R.string.pref_key), getString(R.string.pref_default));
        String cata = sharedPreferences.getString(getString(R.string.pref_cata_key), getString(R.string.pref_cata_default));
        if(cata.equals("People")) {
            String Name2 = "<b>Character: </b>" + swapitem.name;
            String CharHeihgt = "<b>Height: </b>" + swapitem.height / CMdivisor + CMLengthunits;
            String CharMsas = "<b>Mass: </b>" + swapitem.mass * KGdivisor + KGunits;
            String CharHair_coolr = "<b>Hair Color: </b>" + swapitem.hair_color;
            String CharSkin_coolr = "<b>Skin Color: </b>" + swapitem.skin_color;
            String CharEye_coolr = "<b>Eye Color: </b>" + swapitem.eye_color;
            String CharBirth_yaer = "<b>Birth Year: </b>" + swapitem.birth_year;
            String Genedr = "<b>Gender: </b>" + swapitem.gender;

            Name.setText(Html.fromHtml(Name2));
            Detail1.setText(Html.fromHtml(CharHeihgt));
            Detail2.setText(Html.fromHtml(CharMsas));
            Detail3.setText(Html.fromHtml(CharHair_coolr));
            Detail4.setText(Html.fromHtml(CharSkin_coolr));
            Detail5.setText(Html.fromHtml(CharEye_coolr));
            Detail6.setText(Html.fromHtml(CharBirth_yaer));
            Detail7.setText(Html.fromHtml(Genedr));
        }

        if (cata.equals("Planets")){
            String Name2 = "<b>Planet: </b>" + swapitem.name;
            String RotPer = "<b>Rotation Period: </b>" + swapitem.RotPer + "day(s)";
            String OrbitPer = "<b>Orbit Period: </b>" + swapitem.OrbitPer + "day(s)";
            String Diam = "<b>Diameter: </b>" + swapitem.diameter; //in metric, remeber to convert
            String Clim = "<b>Climate: </b>" + swapitem.climate;
            String Grav = "<b>Gravity: </b>" + swapitem.gravity; // get units
            String Terra = "<b>terrain: </b>" + swapitem.terrain;
            String SurWat = "<b>Surface Water: </b>" + swapitem.SurWat;//get units
            String Pop = "<b>Population: </b>" + swapitem.Popul;


            //how the heck is this null
            Name.setText(Html.fromHtml(Name2));
            Detail1.setText(Html.fromHtml(RotPer));
            Detail2.setText(Html.fromHtml(OrbitPer));
            Detail3.setText(Html.fromHtml(Diam));
            Detail4.setText(Html.fromHtml(Clim));
            Detail5.setText(Html.fromHtml(Grav));
            Detail6.setText(Html.fromHtml(Terra));
            Detail7.setText(Html.fromHtml(SurWat));
            Detail8.setText(Html.fromHtml(Pop));
        }
        if (cata.equals("Films")){
            String Name2 = "<b>Title: </b>" + swapitem.name;
            String epid = "<b>Episode ID: </b>" + swapitem.ep_id;
            String dir = "<b>Director: </b>" + swapitem.director;
            String prod = "<b>Producer: </b>" + swapitem.producer; //in metric, remeber to convert
            String crawl = "<b>Opening Crawl: </b>" + swapitem.op_crawl;



            //how the heck is this null
            Name.setText(Html.fromHtml(Name2));
            Detail1.setText(Html.fromHtml(epid));
            Detail2.setText(Html.fromHtml(dir));
            Detail3.setText(Html.fromHtml(prod));
            Detail4.setText(Html.fromHtml(crawl));

        }
        if (cata.equals("Species")){
            String specName = "<b>Species Name: </b>" + swapitem.name;
            String specClass = "<b>Class: </b>" + swapitem.SpecClass;
            String specDesig = "<b>Designation: </b>" + swapitem.SpecDesig;
            String specHeight = "<b>Height: </b>" + swapitem.SpecHeight / CMdivisor + CMLengthunits; //in metric, remeber to convert
            String specSkin = "<b>Skin Color: </b>" + swapitem.SpecSkin;
            String specHair = "<b>Hair Color: </b>" + swapitem.SpecHair;
            String specEye = "<b>Eye Color: </b>" + swapitem.SpecEye;
            String specLife = "<b>Life Span: </b>" + swapitem.SpecLife +" Years";



            //how the heck is this null
            Name.setText(Html.fromHtml(specName));
            Detail1.setText(Html.fromHtml(specClass));
            Detail2.setText(Html.fromHtml(specDesig));
            Detail3.setText(Html.fromHtml(specHeight));
            Detail4.setText(Html.fromHtml(specSkin));
            Detail5.setText(Html.fromHtml(specHair));
            Detail6.setText(Html.fromHtml(specEye));
            Detail7.setText(Html.fromHtml(specLife));

        }
        if (cata.equals("Vehicles")){
            String vehiName = "<b>Vehicle Name: </b>" + swapitem.name;
            String vehiClass = "<b>Classification: </b>" + swapitem.VehiClass;
            String vehiModel = "<b>Model: </b>" + swapitem.VehiModel;
            String vehiManu = "<b>Manufacturer: </b>" + swapitem.VehiManu;
            String vehiCost = "<b>Cost: </b>" + swapitem.VehiCost + " Credits"; //remember units, credits
            String vehiLength = "<b>Vehicle Length: </b>" + swapitem.VehiLength + " meters"; //metric, remember units and conversions
            String vehiAtmos = "<b>Atmospheric Speed: </b>" + swapitem.VehiAtmos;
            String vehiCrew = "<b>Crew Total: </b>" + swapitem.VehiCrew;
            String vehiPass = "<b>Passenger Total: </b>" + swapitem.VehiPass;
            String vehiCargo = "<b>Cargo Amount: </b>" + swapitem.VehiCargo +" kg"; // get units, allow unit conversion
            String vehiConsum = "<b>Consumables: </b>" + swapitem.VehiConsum;




            //how the heck is this null
            Name.setText(Html.fromHtml(vehiName));
            Detail1.setText(Html.fromHtml(vehiClass));
            Detail2.setText(Html.fromHtml(vehiModel));
            Detail3.setText(Html.fromHtml(vehiManu));
            Detail4.setText(Html.fromHtml(vehiCost));
            Detail5.setText(Html.fromHtml(vehiLength));
            Detail6.setText(Html.fromHtml(vehiAtmos));
            Detail7.setText(Html.fromHtml(vehiCrew));
            Detail8.setText(Html.fromHtml(vehiPass));
            Detail9.setText(Html.fromHtml(vehiCargo));
            Detail10.setText(Html.fromHtml(vehiConsum));

        }
        if (cata.equals("Starships")){
            String shipName = "<b>Vehicle Name: </b>" + swapitem.name;
            String shipClass = "<b>Classification: </b>" + swapitem.ShipClass;
            String shipModel = "<b>Model: </b>" + swapitem.ShipModel;
            String shipManu = "<b>Manufacturer: </b>" + swapitem.ShipManu;
            String shipCost = "<b>Cost: </b>" + swapitem.ShipCost + " Credits"; //remember units, credits
            String shipLength = "<b>Vehicle Length: </b>" + swapitem.ShipLength+ " meters"; //metric, remember units and conversions
            String shipAtmos = "<b>Atmospheric Speed: </b>" + swapitem.ShipAtmospherespeed;
            String shipHyper = "<b>Hyperspeed Rating: </b>" + swapitem.ShipHyperdrive;
            String shipMega = "<b>Megalights: </b>" + swapitem.ShipMGLT;
            String shipCrew = "<b>Crew Total: </b>" + swapitem.ShipCrew;
            String shipPass = "<b>Passenger Total: </b>" + swapitem.ShipPass;
            String shipCargo = "<b>Cargo Amount: </b>" + swapitem.ShipCargo +" kg"; // get units, allow unit conversion
            String shipConsum = "<b>Consumables: </b>" + swapitem.ShipConsum;





            //how the heck is this null
            Name.setText(Html.fromHtml(shipName));
            Detail1.setText(Html.fromHtml(shipClass));
            Detail2.setText(Html.fromHtml(shipModel));
            Detail3.setText(Html.fromHtml(shipManu));
            Detail4.setText(Html.fromHtml(shipCost));
            Detail5.setText(Html.fromHtml(shipLength));
            Detail6.setText(Html.fromHtml(shipAtmos));
            Detail7.setText(Html.fromHtml(shipHyper));
            Detail8.setText(Html.fromHtml(shipMega));
            Detail9.setText(Html.fromHtml(shipCrew));
            Detail10.setText(Html.fromHtml(shipPass));
            Detail11.setText(Html.fromHtml(shipCargo));
            Detail12.setText(Html.fromHtml(shipConsum));

        }

    }
}