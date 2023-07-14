package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle extras = getIntent().getExtras();
        String message = extras.getString("message");
        Long id = extras.getLong("id");

        MessageFragment fragment = new MessageFragment(null);
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putString("message", message);
        fragment.setArguments(args);
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        ft.add(R.id.activityChatWindowFrameLayout, fragment);

        ft.commit();
    }
}