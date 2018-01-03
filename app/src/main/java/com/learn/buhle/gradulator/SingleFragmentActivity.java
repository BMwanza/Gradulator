package com.learn.buhle.gradulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by BMwanza on 1/2/2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {


    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity); //Set the view to the layout file we defined

        /*
        Get the Fragment manager and use it to find the frame of the Fragment
         */
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_frame); //Set the Fragment up with the Frame

        /*
        If the Fragment is yet to be initialized the Fragment manager will begin the Transaction with this Fragment
         */
        if(fragment == null)
        {
            fragment = createFragment();
            manager.beginTransaction().add(R.id.fragment_frame, fragment).commit();
        }
    }
}
