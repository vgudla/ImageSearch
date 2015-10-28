package com.groupon.vgudla.imagesearch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.groupon.vgudla.imagesearch.R;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private Spinner colorFilterSpinner;
    private Spinner imageTypeSpinner;
    private Spinner imageSizeSpinner;
    private EditText siteFilter;
    private String imageSize;
    private String imageType;
    private String colorFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupViews();
    }

    private void setupViews() {
        imageSizeSpinner = (Spinner) findViewById(R.id.spImageSize);
        List<String> imageSizes = Arrays.asList("small", "medium", "large", "xlarge");
        ArrayAdapter<String> imageSizeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, imageSizes);
        imageSizeSpinner.setAdapter(imageSizeAdapter);
        imageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSize = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                imageSize = null;
            }
        });

        colorFilterSpinner = (Spinner) findViewById(R.id.spColorFilter);
        List<String> colors = Arrays.asList("black", "blue", "brown", "gray", "green", "orange",
                                            "pink", "purple", "red", "teal", "white", "yellow");
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, colors);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorFilterSpinner.setAdapter(colorAdapter);
        colorFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorFilter = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        imageTypeSpinner = (Spinner) findViewById(R.id.spImageType);
        List<String> imageTypes = Arrays.asList("face", "photo", "clipart", "lineart");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, imageTypes);
        imageTypeSpinner.setAdapter(typeAdapter);
        imageTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        siteFilter = (EditText) findViewById(R.id.etSiteFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSettingsSave(View view) {
        Intent resultData = new Intent();
        resultData.putExtra(SearchActivity.COLOR, colorFilter);
        resultData.putExtra(SearchActivity.IMAGE_SIZE, imageSize);
        resultData.putExtra(SearchActivity.IMAGE_TYPE, imageType);
        resultData.putExtra(SearchActivity.SITE_SEARCH, siteFilter.getText().toString());
        setResult(RESULT_OK, resultData);
        this.finish();
    }

    public void onSettingsCancel(View view) {
        this.finish();
    }
}
