package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecastActivity extends AppCompatActivity {
    private final static String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        Spinner spinnerCities = (Spinner) findViewById(R.id.activityWeatherSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCities.setAdapter(adapter);

        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString().toLowerCase();
                new ForecastQuery().execute(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String min;
        private String max;
        private String curr;
        private Bitmap image;

        @Override
        protected String doInBackground(String... args) {
            try {
                String city = args[0];
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+ city + ",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query.
                conn.connect();
                InputStream is = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(is, null);

                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if(parser.getEventType() == XmlPullParser.START_TAG) {
                        String loopTag = parser.getName();
                        if (loopTag.equals("temperature")) {
                            min = parser.getAttributeValue(null, "min");
                            publishProgress(25);
                            max = parser.getAttributeValue(null, "max");
                            publishProgress(50);
                            curr = parser.getAttributeValue(null, "value");
                            publishProgress(75);

                        } else if (loopTag.equals("weather")) {
                            String icon = parser.getAttributeValue(null, "icon");
                            String fileName = icon + ".png";
                            if (fileExistance(fileName)) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(fileName);

                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                Log.i(TAG, "Found the file locally");
                                image = BitmapFactory.decodeStream(fis);
                            }
                            else {
                                String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                image = getImage(new URL(iconUrl));

                                FileOutputStream outputStream = openFileOutput( fileName, Context.MODE_PRIVATE);
                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);

                                Log.i(TAG, "Downloaded the file from the Internet");
                                outputStream.flush();
                                outputStream.close();
                            }
                            publishProgress(100);
                        }
                    }
                    parser.next();
                }
                is.close();
            } catch (XmlPullParserException | IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar pb = (ProgressBar) findViewById(R.id.activityWeatherForecastProgressBar);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ProgressBar pb = (ProgressBar) findViewById(R.id.activityWeatherForecastProgressBar);
            pb.setVisibility(View.INVISIBLE);

            TextView currTextView = (TextView) findViewById(R.id.activityWeatherForecastTextViewCurTemp);
            TextView minTextView = (TextView) findViewById(R.id.activityWeatherForecastTextViewMinTemp);
            TextView maxTextView = (TextView) findViewById(R.id.activityWeatherForecastTextViewMaxTemp);

            String currString = getString(R.string.cur_temp) + ": " + curr;
            String minString = getString(R.string.min_temp) + ": " + min;
            String maxString = getString(R.string.max_temp) + ": " + max;
            currTextView.setText(currString);
            minTextView.setText(minString);
            maxTextView.setText(maxString);

            ImageView imageView = (ImageView) findViewById(R.id.activityWeatherForecastImageView);
            imageView.setImageBitmap(image);
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage (URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}