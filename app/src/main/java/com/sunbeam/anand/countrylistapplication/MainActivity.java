package com.sunbeam.anand.countrylistapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private static final String JSON_URL = "https://restcountries.eu/rest/v2/all";
    List<Country> countryList;
    ProgressBar progressBar;
    private String TAG = MainActivity.class.getSimpleName();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        countryList = new ArrayList<>();
        progressBar =  findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        adapter = new MyAdapter(this, countryList);

        //adding the adapter to listview
        listView.setAdapter(adapter);
        new GetCountryList().execute();

    }

    private class GetCountryList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",
                    Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(JSON_URL);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {


                    // Getting JSON Array node
                    JSONArray obj = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < obj.length(); i++) {
                        //getting the json object of the particular index inside the array
                        String name = obj.getJSONObject(i).getString("name");
                        String capital = obj.getJSONObject(i).getString("capital");
                        String imageUrl = obj.getJSONObject(i).getString("flag");
                        String borders = "";
                        JSONArray jsonArray = obj.getJSONObject(i).getJSONArray("borders");
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < jsonArray.length(); j++){
                            sb.append(jsonArray.get(j).toString()).append(",");
                        }
                        if (sb.length()!=0) {
                           borders = sb.deleteCharAt(sb.length() -1).toString();
                        }
                        Country country = new Country(name, imageUrl, capital,borders);
                        countryList.add(country);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }
}
