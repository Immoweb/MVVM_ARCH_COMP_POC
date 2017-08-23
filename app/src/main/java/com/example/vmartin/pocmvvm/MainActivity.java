package com.example.vmartin.pocmvvm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vmartin.pocmvvm.ui.common.NavigationController;


public class MainActivity extends AppCompatActivity {

    NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        navigationController = new NavigationController(this);

        if (savedInstanceState == null) {
            navigationController.navigateToList();
        }
    }

}
