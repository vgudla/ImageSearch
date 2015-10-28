package com.groupon.vgudla.imagesearch.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.groupon.vgudla.imagesearch.listener.InfiniteScrollListener;
import com.groupon.vgudla.imagesearch.R;
import com.groupon.vgudla.imagesearch.adapter.ImageAdapter;
import com.groupon.vgudla.imagesearch.model.Image;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    public static final String FULL_IMAGE_URL = "fullImageUrl";
    public static final int REQUEST_CODE = 200;
    public static final String COLOR = "imgcolor";
    public static final String IMAGE_SIZE = "imgsz";
    public static final String IMAGE_TYPE = "imgtype";
    public static final String SITE_SEARCH = "as_sitesearch";
    public static final String PAGE_INDEX = "start";
    public static final String GOOGLE_IMAGE_API = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
    public static final String AMPERSAND = "&";
    public static final String EQUALS = "=";
    private static final String LOG_TAG = "SearchActivity";

    private GridView gvImages;
    private ImageAdapter imageAdapter;
    private List<Image> imageList;
    private String query = "";
    private String color;
    private String imageSize;
    private String imageType;
    private String siteSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageList);
        gvImages.setAdapter(imageAdapter);
        gvImages.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadImages(page);
                return true;
            }
        });
    }

    private void setupViews() {
        gvImages = (GridView) findViewById(R.id.gvResults);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image image = imageList.get(position);
                Intent imageIntent = new Intent(SearchActivity.this, ImageActivity.class);
                imageIntent.putExtra(FULL_IMAGE_URL, image);
                startActivity(imageIntent);
            }
        });
    }

    private String getQueryString(int pageNumber) {
        StringBuffer queryString = new StringBuffer();
        if (pageNumber != 1) {
            queryString.append(AMPERSAND).append(PAGE_INDEX).append(EQUALS).append(pageNumber * 8);
        }
        if (color != null && !color.isEmpty()) {
            queryString.append(AMPERSAND).append(COLOR).append(EQUALS).append(color);
        }
        if (imageSize != null && !imageSize.isEmpty()) {
            queryString.append(AMPERSAND).append(IMAGE_SIZE).append(EQUALS).append(imageSize);
        }
        if (imageType != null && !imageType.isEmpty()) {
            queryString.append(AMPERSAND).append(IMAGE_TYPE).append(EQUALS).append(imageType);
        }
        if (siteSearch != null && !siteSearch.isEmpty()) {
            queryString.append(AMPERSAND).append(SITE_SEARCH).append(EQUALS).append(siteSearch);
        }
        return queryString.toString();
    }

    private void loadImages(int pageNumber) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG);
            return;
        }
        AsyncHttpClient httpClient = new AsyncHttpClient();
        final String apiQuery = GOOGLE_IMAGE_API + "&q=" + query + getQueryString(pageNumber);
        httpClient.get(apiQuery, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response == null) {
                        return;
                    }
                    JSONObject resonseObject = response.getJSONObject("responseData");
                    JSONArray responseArray = resonseObject.getJSONArray("results");
                    imageAdapter.addAll(Image.fromJSONArray(responseArray));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error while parsing json object: " + response, e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject response) {
                Log.e(LOG_TAG, "Error while retrieving images from: " + apiQuery, throwable);
            }
        });
    }

    //Check to see if network is available before making external service calls
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    // Handle action bar settings clicks here.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Handle action bar text search here.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                imageList.clear();
                loadImages(1);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = newText;
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickSettings(MenuItem item) {
        Intent settingsIntent = new Intent(SearchActivity.this, SettingsActivity.class);
        startActivityForResult(settingsIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            color = data.getExtras().getString(COLOR, "");
            imageSize = data.getExtras().getString(IMAGE_SIZE, "");
            imageType = data.getExtras().getString(IMAGE_TYPE, "");
            siteSearch = data.getExtras().getString(SITE_SEARCH, "");
        }
    }
}