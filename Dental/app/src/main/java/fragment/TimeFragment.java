package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clinic.romeo.dental.R;
import com.clinic.romeo.dental.activity.activity.SecondActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;



@SuppressWarnings("unused")
public class TimeFragment extends Fragment implements View.OnClickListener{

    /********************
     * Variable Zone
     ********************/
    TextView dateView;
    final Calendar calendar = Calendar.getInstance();
    Button time9_00,time9_15,time9_30,time9_45,btntime;
    Button time10_00,time10_15,time10_30,time10_45;
    Button time11_00,time11_15,time11_30,time11_45;
    Button time14_00,time14_15,time14_30,time14_45;
    Button time15_00,time15_15,time15_30,time15_45;
    Button time16_00,time16_15,time16_30,time16_45;
    int date,month,year;
    String time;
    String userID;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    FirebaseDatabase mFirebaseDatabase;
    /*
    DatabaseReference mRootRef;
    FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();
    */
    /********************
     * Function Zone
     ********************/
    public TimeFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static TimeFragment newInstance(int strName,int strName2,int strName3) {
        TimeFragment fragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putInt("Date",strName);
        args.putInt("Month",strName2);
        args.putInt("Year",strName3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time, container, false);
        initInstances(rootView, savedInstanceState);
        date = getArguments().getInt("Date");
        month = getArguments().getInt("Month");
        year = getArguments().getInt("Year");
        Log.d("Date", String.valueOf(date));

        //Set textViewTop
        dateView.setText(String.valueOf(date));


        Log.d("Date2", String.valueOf(calendar.getTime()));

        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }


    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
/*
        mRootRef = FirebaseDatabase.getInstance().getReference();
*/
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = GetUserCurrent;


        dateView = rootView.findViewById(R.id.date);
        time9_00 = rootView.findViewById(R.id.btnTime9_00);
        time9_15 = rootView.findViewById(R.id.btnTime9_15);
        time9_30 = rootView.findViewById(R.id.btnTime9_30);
        time9_45 = rootView.findViewById(R.id.btnTime9_45);

        time10_00 = rootView.findViewById(R.id.btnTime10_00);
        time10_15 = rootView.findViewById(R.id.btnTime10_15);
        time10_30 = rootView.findViewById(R.id.btnTime10_30);
        time10_45 = rootView.findViewById(R.id.btnTime10_45);

        time11_00 = rootView.findViewById(R.id.btnTime11_00);
        time11_15 = rootView.findViewById(R.id.btnTime11_15);
        time11_30 = rootView.findViewById(R.id.btnTime11_30);
        time11_45 = rootView.findViewById(R.id.btnTime11_45);

        time14_00 = rootView.findViewById(R.id.btnTime14_00);
        time14_15 = rootView.findViewById(R.id.btnTime14_15);
        time14_30 = rootView.findViewById(R.id.btnTime14_30);
        time14_45 = rootView.findViewById(R.id.btnTime14_45);

        time15_00 = rootView.findViewById(R.id.btnTime15_00);
        time15_15 = rootView.findViewById(R.id.btnTime15_15);
        time15_30 = rootView.findViewById(R.id.btnTime15_30);
        time15_45 = rootView.findViewById(R.id.btnTime15_45);

        time16_00 = rootView.findViewById(R.id.btnTime16_00);
        time16_15 = rootView.findViewById(R.id.btnTime16_15);
        time16_30 = rootView.findViewById(R.id.btnTime16_30);
        time16_45 = rootView.findViewById(R.id.btnTime16_45);




        time9_00.setOnClickListener(this);
        time9_15.setOnClickListener(this);
        time9_30.setOnClickListener(this);
        time9_45.setOnClickListener(this);

        time10_00.setOnClickListener(this);
        time10_15.setOnClickListener(this);
        time10_30.setOnClickListener(this);
        time10_45.setOnClickListener(this);

        time11_00.setOnClickListener(this);
        time11_15.setOnClickListener(this);
        time11_30.setOnClickListener(this);
        time11_45.setOnClickListener(this);

        time14_00.setOnClickListener(this);
        time14_15.setOnClickListener(this);
        time14_30.setOnClickListener(this);
        time14_45.setOnClickListener(this);

        time15_00.setOnClickListener(this);
        time15_15.setOnClickListener(this);
        time15_30.setOnClickListener(this);
        time15_45.setOnClickListener(this);

        time16_00.setOnClickListener(this);
        time16_15.setOnClickListener(this);
        time16_30.setOnClickListener(this);
        time16_45.setOnClickListener(this);


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
    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }
    /********************
     * Listener Zone
     ********************/
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTime9_00 :
                calendar.set(year,month,date,9,00);
                DialogConfirm();
                break;
            case R.id.btnTime9_15 :
                calendar.set(year,month,date,9,15);
                DialogConfirm();
                break;
            case R.id.btnTime9_30 :
                calendar.set(year,month,date,9,30);
                DialogConfirm();
                break;
            case R.id.btnTime9_45 :
                calendar.set(year,month,date,9,45);
                DialogConfirm();
                break;
            case R.id.btnTime10_00 :
                calendar.set(year,month,date,10,00);
                DialogConfirm();
                break;
            case R.id.btnTime10_15 :
                calendar.set(year,month,date,10,15);
                DialogConfirm();
                break;
            case R.id.btnTime10_30 :
                calendar.set(year,month,date,10,30);
                DialogConfirm();
                break;
            case R.id.btnTime10_45 :
                calendar.set(year,month,date,10,45);
                DialogConfirm();
                break;

            case R.id.btnTime11_00 :
                calendar.set(year,month,date,11,00);
                DialogConfirm();
                break;
            case R.id.btnTime11_15 :
                calendar.set(year,month,date,11,15);
                DialogConfirm();
                break;
            case R.id.btnTime11_30 :
                calendar.set(year,month,date,11,30);
                DialogConfirm();
                break;
            case R.id.btnTime11_45 :
                calendar.set(year,month,date,11,45);
                DialogConfirm();
                break;
            case R.id.btnTime14_00 :
                calendar.set(year,month,date,14,00);
                DialogConfirm();
                break;
            case R.id.btnTime14_15 :
                calendar.set(year,month,date,14,15);
                DialogConfirm();
                break;
            case R.id.btnTime14_30 :
                calendar.set(year,month,date,14,30);
                DialogConfirm();
                break;
            case R.id.btnTime14_45 :
                calendar.set(year,month,date,14,45);
                DialogConfirm();
                break;

            case R.id.btnTime15_00 :
                calendar.set(year,month,date,15,00);
                DialogConfirm();
                break;
            case R.id.btnTime15_15 :
                calendar.set(year,month,date,15,15);
                DialogConfirm();
                break;
            case R.id.btnTime15_30 :
                calendar.set(year,month,date,15,30);
                DialogConfirm();
                break;
            case R.id.btnTime15_45 :
                calendar.set(year,month,date,15,45);
                DialogConfirm();
                break;
            case R.id.btnTime16_00 :
                calendar.set(year,month,date,16,00);
                DialogConfirm();
                break;
            case R.id.btnTime16_15 :
                calendar.set(year,month,date,16,15);
                DialogConfirm();
                break;
            case R.id.btnTime16_30 :
                calendar.set(year,month,date,16,30);
                DialogConfirm();
                break;
            case R.id.btnTime16_45 :
                calendar.set(year,month,date,16,45);
                DialogConfirm();
                break;
        }
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
                // user logout
            }
        }
    };
    /********************
     * Inner Zone
     ********************/

    private void upToFirebase() {

        //Time
        time = String.valueOf(calendar.getTime());
        Log.d("Date1", String.valueOf(time));

        DatabaseReference mChildDatabase = mDatabaseRef.child("Appointment").child(userID);
        mChildDatabase.child("Time").setValue(time);
/*
        //Up Data To Firebase here
        mRootRef.child("event").child(userID).child("Time").setValue(time);
*/
    }
    private  void  DialogConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure want to book this time");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upToFirebase();
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }

}
