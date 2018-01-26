package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.clinic.romeo.dental.R;
import com.clinic.romeo.dental.activity.activity.SecondActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



@SuppressWarnings("unused")
public class EditInfoFragment extends Fragment {

    /********************
     * Variable Zone
     ********************/
    String userID,userEmail;
    ProgressBar progressbar;
    EditText nameadd,emailadd,phoneadd,patientidadd;
    Button saveadd;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    FirebaseDatabase mFirebaseDatabase;

    /********************
     * Function Zone
     ********************/
    public EditInfoFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static EditInfoFragment newInstance() {
        EditInfoFragment fragment = new EditInfoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_edit_info, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        userEmail = user.getEmail();
        mAuthListener = GetUserCurrent;

        progressbar =(ProgressBar) rootView.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);
        nameadd = (EditText) rootView.findViewById(R.id.edittext_name_add);
        emailadd = (EditText) rootView.findViewById(R.id.edittext_email_add);
        phoneadd = (EditText) rootView.findViewById(R.id.edittext_Phone_add);
        patientidadd = (EditText) rootView.findViewById(R.id.edittext_PatientID_add);
        saveadd =(Button) rootView.findViewById(R.id.button_save_add);

        saveadd.setOnClickListener(saveButtonListener);
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
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // user login
            } else {
                // user logout
            }
        }
    };
    View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressbar.setVisibility(View.VISIBLE);
            String name,email , phoneNum , patientId ;
            name = nameadd.getText().toString().trim();
            email = emailadd.getText().toString().trim();
            phoneNum = phoneadd.getText().toString().trim();
            patientId = patientidadd.getText().toString().trim();

            if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(phoneNum)&&!TextUtils.isEmpty(patientId)){

                DatabaseReference mChildDatabase = mDatabaseRef.child("users").child(userID);
                mChildDatabase.child("PatientID").setValue(patientId);
                mChildDatabase.child("Name").setValue(name);
                mChildDatabase.child("Email").setValue(email);
                mChildDatabase.child("PhoneNum").setValue(phoneNum);
                progressbar.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                builder.setTitle("Finished");
                builder.setMessage("Save Information Success!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), SecondActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
            else {
                progressbar.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                builder.setTitle("Failed");
                builder.setMessage("Fill out all the fields");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        }
    };
    /********************
     * Inner Zone
     ********************/
}
