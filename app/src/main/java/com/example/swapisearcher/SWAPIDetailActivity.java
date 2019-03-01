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

    private TextView CharName;
    private TextView CharHeight;
    private TextView CharMass;
    private TextView CharHair_color;
    private TextView CharSkin_color;
    private TextView CharEye_color;
    private TextView CharBirth_year;
    private TextView CharGender;
    private String units;
    private double divisor;

    private SWAPIUtils.SWAPIItem mswapiItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_item_detail);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String forecastUnits = sharedPreferences.getString(getString(R.string.pref_key), getString(R.string.pref_default));

        if (forecastUnits.equals("Centimeters")){
            units = "cm";
            divisor = 1;
        } else {
            units = "In";
            divisor = 2.5;
        }


        //textview findings
        CharName = (TextView) findViewById(R.id.tv_char_name);
        CharHeight = (TextView) findViewById(R.id.tv_char_height);
        CharMass =(TextView) findViewById(R.id.tv_char_mass);
        CharHair_color = (TextView) findViewById(R.id.tv_char_hair);
        CharSkin_color = (TextView) findViewById(R.id.tv_char_skin);
        CharEye_color = (TextView) findViewById(R.id.tv_char_eye);
        CharBirth_year = (TextView) findViewById(R.id.tv_char_birth);
        CharGender = (TextView) findViewById(R.id.tv_char_gender);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(SWAPIUtils.SWAPIItem.EXTRA_SWAPI_ITEM)) {
            mswapiItem = (SWAPIUtils.SWAPIItem) intent.getSerializableExtra(SWAPIUtils.SWAPIItem.EXTRA_SWAPI_ITEM);
            fillInLayoutText(mswapiItem);
        }

    }


//options menu

//on options item selected


//share character

    //fill in layout
    private void fillInLayoutText(SWAPIUtils.SWAPIItem swapitem) {
        String CharNaem = "<b>Character: </b>" + swapitem.name;
        String CharHeihgt = "<b>Height: </b>" + swapitem.height/divisor + units;
        String CharMsas = "<b>Mass: </b>" + swapitem.mass;
        String CharHair_coolr = "<b>Hair Color: </b>" + swapitem.hair_color;
        String CharSkin_coolr = "<b>Skin Color: </b>" + swapitem.skin_color;
        String CharEye_coolr = "<b>Eye Color: </b>" + swapitem.eye_color;
        String CharBirth_yaer = "<b>Birth Year: </b>" + swapitem.birth_year;
        String Genedr = "<b>Gender: </b>" + swapitem.gender;

        CharName.setText(Html.fromHtml(CharNaem));
        CharHeight.setText(Html.fromHtml(CharHeihgt));
        CharMass.setText(Html.fromHtml(CharMsas));
        CharHair_color.setText(Html.fromHtml(CharHair_coolr));
        CharSkin_color.setText(Html.fromHtml(CharSkin_coolr));
        CharEye_color.setText(Html.fromHtml(CharEye_coolr));
        CharBirth_year.setText(Html.fromHtml(CharBirth_yaer));
        CharGender.setText(Html.fromHtml(Genedr));
    }
}