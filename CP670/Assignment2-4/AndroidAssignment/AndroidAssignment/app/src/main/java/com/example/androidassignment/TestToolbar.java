package com.example.androidassignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignment.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    private final String TAG = "TestToolbar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getText(R.string.hello_message), Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        View view = findViewById(android.R.id.content).getRootView();

        if (id == R.id.action_one) {
            Log.i(TAG, "Action one fired");
            Snackbar.make(view, "You selected Item 1", Snackbar.LENGTH_LONG)
                    //.setAnchorView(R.id.fab)
                    .setAction("Action", null).show();
        } else if (id == R.id.action_two) {
            Log.i(TAG, "Action two fired");
            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            builder.setTitle(R.string.go_back);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (id == R.id.action_three) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View inflatorView = inflater.inflate(R.layout.alert_dialog, null);
            builder.setView(inflatorView)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText message = (EditText) inflatorView.findViewById(R.id.alert_dialog_message);
                            Snackbar.make(view, message.getText(), Snackbar.LENGTH_LONG)
                                    //.setAnchorView(R.id.fab)
                                    .setAction("Action", null).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .create()
                    .show();
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "Version 1.0, by Josh Barber", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}