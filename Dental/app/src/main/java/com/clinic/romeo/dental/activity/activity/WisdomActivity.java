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

import fragment.WisdomFragment;

public class WisdomActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_wisdom);
        initInstances();
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, WisdomFragment.newInstance())
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
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_artboard);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = GetUserCurrent;
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
