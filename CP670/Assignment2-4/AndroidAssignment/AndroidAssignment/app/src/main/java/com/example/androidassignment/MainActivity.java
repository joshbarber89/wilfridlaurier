package com.example.androidassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonReference = (Button) findViewById(R.id.activityMainButtonButton);
        buttonReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button startChatButton = (Button) findViewById(R.id.activityMainStartChatButton);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "User clicked Start Chat");
                Intent intent = new Intent(getApplicationContext(),ChatWindow.class);
                startActivity(intent);
            }
        });

        Button startToolbar = (Button) findViewById(R.id.activityMainStartToolbar);
        startToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "User clicked Test Toolbar");
                Intent intent = new Intent(getApplicationContext(),TestToolbar.class);
                startActivity(intent);
            }
        });
        Button startWeather = (Button) findViewById(R.id.activityMainStartWeather);
        startWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "User clicked Weather");
                Intent intent = new Intent(getApplicationContext(),WeatherForecastActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Log.i(TAG, "Returned to MainActivity.onActivityResult");
            String messagePassed = data.getStringExtra("Response");
            if (messagePassed != null && !messagePassed.equals("")) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, messagePassed, duration);
                toast.show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume called");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy called");
    }
}