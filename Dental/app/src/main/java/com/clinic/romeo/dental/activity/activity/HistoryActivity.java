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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import view.InfoCardView;

public class HistoryActivity extends AppCompatActivity implements InfoCardView.InfoClickListener {

    /********************
     * Variable Zone
     ********************/
    private InfoCardView icvAndroidVersion;
    private InfoCardView icvAndroidMarshmallow;
    Toolbar toolbar;

    String userID,userEmail;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    FirebaseDatabase mFirebaseDatabase;
    /********************
     * Function Zone
     ********************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        bindView();
        initInstances();
    }
    private void setupView() {
        icvAndroidVersion.setTitle("Title");
        icvAndroidVersion.setContent("no");

        icvAndroidMarshmallow.setTitle("Appointment");
        icvAndroidMarshmallow.setContent("no");
    }
    private void setupView(String s1,String s2) {
        icvAndroidVersion.setTitle("Title");
        icvAndroidVersion.setContent(s1);

        icvAndroidMarshmallow.setTitle("Appointment");
        icvAndroidMarshmallow.setContent(s2);
    }
    private void bindView() {
        icvAndroidVersion = (InfoCardView) findViewById(R.id.icv_android_version);
        icvAndroidMarshmallow = (InfoCardView) findViewById(R.id.icv_android_marshmallow);
    }
    @Override
    public void onTitleClick() {
        // Do something when title was clicked
    }

    @Override
    public void onContentClick() {
        // Do something when content was clicked
    }



    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_artboard);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = GetUserCurrent;

        DatabaseReference checkstate = mDatabaseRef.child("Appointment");
        checkstate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userID).exists()){
                    mDatabaseRef.child("Appointment").child(userID).addValueEventListener(valueEventListener);
                }
                else
                    setupView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null ){
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String titleinfo = dataSnapshot.child("Title").getValue(String.class);
            String time = dataSnapshot.child("Time").getValue(String.class);

            setupView(titleinfo,time);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
}
