package com.example.swapisearcher;



//normals
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
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

    private SWAPIUtils.SWAPIItem mswapiItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_item_detail);

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
        String CharNaem = "Character: " + swapitem.name;
        String CharHeihgt = "Height: " + swapitem.height + "cm";
        String CharMsas = "Mass: " + swapitem.mass;
        String CharHair_coolr = "Hair Color: " + swapitem.hair_color;
        String CharSkin_coolr = "Skin Color: " + swapitem.skin_color;
        String CharEye_coolr = "Eye Color: " + swapitem.eye_color;
        String CharBirth_yaer = "Birth Year: " + swapitem.birth_year;
        String Genedr = "Gender: " + swapitem.gender;

        CharName.setText(CharNaem);
        CharHeight.setText(CharHeihgt);
        CharMass.setText(CharMsas);
        CharHair_color.setText(CharHair_coolr);
        CharSkin_color.setText(CharSkin_coolr);
        CharEye_color.setText(CharEye_coolr);
        CharBirth_year.setText(CharBirth_yaer);
        CharGender.setText(Genedr);
    }
}