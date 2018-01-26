package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.clinic.romeo.dental.R;
import com.clinic.romeo.dental.activity.activity.EditInfoActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



@SuppressWarnings("unused")
public class ProfileFragment extends Fragment {

    /********************
     * Variable Zone
     ********************/
    Button editProfile,logoutbtn;
    String userID;
    ListView mListView;
    TextView Tvemail,Tvname,Tvphone,Tvpatient;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    FirebaseDatabase mFirebaseDatabase;
    /********************
     * Function Zone
     ********************/
    public ProfileFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        editProfile = (Button) rootView.findViewById(R.id.button_edit_profile);
        logoutbtn = (Button) rootView.findViewById(R.id.logoutbtn);
        Tvemail = (TextView) rootView.findViewById(R.id.tvEmail);
        Tvname = (TextView) rootView.findViewById(R.id.tvName);
        Tvpatient =(TextView) rootView.findViewById(R.id.tvPatientid);
        Tvphone =(TextView) rootView.findViewById(R.id.tvPhonenum);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = GetUserCurrent;
        mDatabaseRef.child("users").child(userID).addValueEventListener(valueEventListener);
        logoutbtn.setOnClickListener(LogoutListener);
        editProfile.setOnClickListener(editprofileListener);
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
    final FirebaseAuth.AuthStateListener GetUserCurrent = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
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
            String email = dataSnapshot.child("Email").getValue(String.class);
            String name = dataSnapshot.child("Name").getValue(String.class);
            String patinetID = dataSnapshot.child("PatientID").getValue(String.class);
            String phoneNum = dataSnapshot.child("PhoneNum").getValue(String.class);

            Tvemail.setText("Email: "+email);
            Tvname.setText("Name: "+name);
            Tvpatient.setText("PatinetID: "+patinetID);
            Tvphone.setText("Phonenum: "+phoneNum);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
    View.OnClickListener LogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure logout ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LoginManager.getInstance().logOut();
                    mAuth.signOut();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.from_right,R.anim.to_left,
                                    R.anim.from_left,R.anim.to_right)
                            .replace(R.id.contentContainer,
                                    MainFragment.newInstance())
                            .commit();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    };
    View.OnClickListener editprofileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), EditInfoActivity.class);
            startActivity(intent);
        }
    };
    /********************
     * Inner Zone
     ********************/

}
