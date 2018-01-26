package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clinic.romeo.dental.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;



public class MainFragment extends Fragment {

    public MainFragment() {
        super();
    }

    /********************
     * Variable Zone
     ********************/
    EditText emailUser;
    EditText passUser;
    Button loginbtn;
    Button createaccountbtn;
    LoginButton facebooklogin;
    ProgressBar progressbar;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;

    CallbackManager mCallbackManager;
    AccessToken accessToken;

    /********************
     * Function Zone
     ********************/
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        emailUser = (EditText) rootView.findViewById(R.id.edittext_email_login);
        passUser = (EditText) rootView.findViewById(R.id.edittext_password_login);
        loginbtn = (Button) rootView.findViewById(R.id.button_login);
        facebooklogin = (LoginButton) rootView.findViewById(R.id.button_facebooklogin);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = GetUserCurrent;

        createaccountbtn = (Button) rootView.findViewById(R.id.button_CreateAccount);
        createaccountbtn.setOnClickListener(CreateAccountListener);
        loginbtn.setOnClickListener(loginListener);
        initializeFacebookLogin();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
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
    final View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressbar.setVisibility(View.VISIBLE);
            final String userEmail, userPass;
            userEmail = emailUser.getText().toString().trim();
            userPass = passUser.getText().toString().trim();
            if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)) {
                mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressbar.setVisibility(View.INVISIBLE);
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.from_right, R.anim.to_left,
                                            R.anim.from_left, R.anim.to_right)
                                    .replace(R.id.contentContainer,
                                            AddInfoFragment.newInstance())
                                    .commit();
                        } else {
                            progressbar.setVisibility(View.INVISIBLE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                            builder.setTitle("Login");
                            builder.setMessage("Login Failed! username or password wrong");
                            builder.setPositiveButton("Try again", null);
                            builder.show();
                        }
                    }
                });
            }
        }
    };
    final View.OnClickListener CreateAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.from_right, R.anim.to_left,
                            R.anim.from_left, R.anim.to_right)
                    .replace(R.id.contentContainer,
                            CreateAccountFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    };
    final FirebaseAuth.AuthStateListener GetUserCurrent = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.from_right,R.anim.to_left,
                                R.anim.from_left,R.anim.to_right)
                        .replace(R.id.contentContainer,
                               AddInfoFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            } else {
                // user don't login

            }
        }
    };

    /********************
     * Innerclass Zone
     ********************/
    public void logoutfacebook(){
        facebooklogin.performClick();
    }

    private void initializeFacebookLogin() {

        facebooklogin.setReadPermissions("email");
        facebooklogin.setFragment(this);
        mCallbackManager = CallbackManager.Factory.create();
        facebooklogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity(),"Login result :"+ loginResult,Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(),"Cancel",Toast.LENGTH_SHORT).show();
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                // ...
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("facebooktoken", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.from_right, R.anim.to_left,
                                            R.anim.from_left, R.anim.to_right)
                                    .replace(R.id.contentContainer,
                                            AddInfoFragment.newInstance())
                                    .commit();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(),"UnSuccess",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}