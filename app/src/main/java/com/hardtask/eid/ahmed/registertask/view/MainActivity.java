package com.hardtask.eid.ahmed.registertask.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hardtask.eid.ahmed.registertask.R;
import com.hardtask.eid.ahmed.registertask.RegisterFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RegisterFragment fragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
        fragmentTransaction.replace(R.id.container_layout, fragment);
        fragmentTransaction.commit();
    }
}
