package com.clinic.romeo.dental.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.clinic.romeo.dental.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fragment.TimeFragment;

public class TimeActivity extends AppCompatActivity {
    /********************
     * Variable Zone
     ********************/
    Integer selectedDate2, selectedDate3, selectedDate4;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    /********************
     * Function Zone
     ********************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initInstances();
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, TimeFragment.newInstance(selectedDate2,
                            selectedDate3,selectedDate4))
                    .commit();
        }
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initInstances() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = GetUserCurrent;
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_artboard);

        Bundle bundle = getIntent().getExtras();
         selectedDate2 = bundle.getInt("selectedDate2");
         selectedDate3 = bundle.getInt("selectedDate3");
         selectedDate4 = bundle.getInt("selectedDate4");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more_info,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.actionProfile){
            Intent intent = new Intent(this, ProfileActitvity.class);
            startActivity(intent);
        }
        if (item.getItemId()== R.id.actionAboutus){
            Intent intent = new Intent(this, AboutusActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()== R.id.actionLogout){
            LoginManager.getInstance().logOut();
            mAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return true;
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
