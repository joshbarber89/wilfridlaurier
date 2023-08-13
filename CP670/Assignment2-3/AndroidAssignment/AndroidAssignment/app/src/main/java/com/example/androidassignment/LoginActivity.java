package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailAddressEditText = (EditText) findViewById(R.id.activityLoginEditTextLogin);
        SharedPreferences sharedPref = this.getSharedPreferences(
                "preferences", Context.MODE_PRIVATE);
        String emailAddress = sharedPref.getString("emailAddress", "");
        if (!emailAddress.equals("")) {
            emailAddressEditText.setText(emailAddress);
        }

        Button loginButton = (Button) findViewById(R.id.activityLoginButtonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("emailAddress", emailAddressEditText.getText().toString());
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
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