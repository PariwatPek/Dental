package com.clinic.romeo.dental.activity.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.clinic.romeo.dental.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    /********************
     * Variable Zone
     ********************/
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    /********************
     * Function Zone
     ********************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null ){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void initInstances() {
      /*  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = GetUserCurrent;
    }
    /********************
     * Listener Zone
     ********************/
    final FirebaseAuth.AuthStateListener GetUserCurrent = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // user login
            } else {
                // user don't login
            }
        }
    };
}
