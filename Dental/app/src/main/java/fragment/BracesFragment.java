package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clinic.romeo.dental.R;
import com.clinic.romeo.dental.activity.activity.BookingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class BracesFragment extends Fragment {

    /********************
     * Variable Zone
     ********************/
    String userID,useremail;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    FirebaseDatabase mFirebaseDatabase;

    public BracesFragment() {
        super();
    }

    public static BracesFragment newInstance() {
        BracesFragment fragment = new BracesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_braces_teeth, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(final View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        useremail = user.getEmail();
        mAuthListener = GetUserCurrent;
        // Get reference of widgets from XML layout
        Button btnBraces = (Button) rootView.findViewById(R.id.btn);
        final TextView tvBraces = (TextView) rootView.findViewById(R.id.tv1);

        btnBraces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure want to book this menu");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String braces = "BracesTeeth";
                        DatabaseReference mChildDatabase = mDatabaseRef.child("Appointment").child(userID);
                        mChildDatabase.child("Title").setValue(braces);
                        mChildDatabase.child("Email").setValue(useremail);
                        Intent intent = new Intent(getActivity(), BookingActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.show();


            }
        });

        // getActivity().getSupportFragmentManager().beginTransaction()
        //  .replace(R.id.contentContainer,)
        // .commit()

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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here

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
}
