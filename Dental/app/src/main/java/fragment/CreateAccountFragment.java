package fragment;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class CreateAccountFragment extends Fragment {

    public CreateAccountFragment() {
        super();
    }

    /********************
     * Variable Zone
     ********************/
    Button registerbtn;
    String userID;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPasswordConfirm;
    ProgressBar progressbar;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;
    FirebaseDatabase mFirebaseDatabase;
    /********************
     * Function Zone
     ********************/
    public static CreateAccountFragment newInstance() {
        CreateAccountFragment fragment = new CreateAccountFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_create_account, container, false);
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
        FirebaseUser user = mAuth.getCurrentUser();
        mAuthListener = GetUserCurrent;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        progressbar =(ProgressBar) rootView.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);
        editTextEmail =(EditText) rootView.findViewById(R.id.edittext_email_regis);
        editTextPassword = (EditText) rootView.findViewById(R.id.edittext_password_regis);
        editTextPasswordConfirm = (EditText) rootView.findViewById(R.id.edittext_password_confirm);
        registerbtn =(Button) rootView.findViewById(R.id.button_register);
        registerbtn.setOnClickListener(Register);
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
    final View.OnClickListener Register = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressbar.setVisibility(View.VISIBLE);
            final String userEmail,userPass,userPassCon;
            userEmail = editTextEmail.getText().toString().trim();
            userPass = editTextPassword.getText().toString().trim();
            userPassCon = editTextPasswordConfirm.getText().toString().trim();

            if (!TextUtils.isEmpty(userEmail)&&!TextUtils.isEmpty(userPass)&&!TextUtils.isEmpty(userPassCon) && (userPass.equals(userPassCon))){
                mAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           progressbar.setVisibility(View.INVISIBLE);

                           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                           builder.setTitle("Register");
                           builder.setMessage("Register completed!");
                           builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   getFragmentManager().popBackStack();
                               }
                           });
                           builder.show();
                       }
                        else {
                           progressbar.setVisibility(View.INVISIBLE);
                           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                           builder.setTitle("Register");
                           builder.setMessage("Register failed Try Again");
                           builder.setPositiveButton("OK", null);
                           builder.show();
                       }
                    }
                });
            }
        }
    };
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
