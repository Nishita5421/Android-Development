package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.signin.
        GoogleSignIn;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.facebook.FacebookSdk.sdkInitialize;


public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private String TAG="MainActivity";
    private Button signInButton;
    private int RC_SIGN_IN=12;
    private TextView textView;
    private EditText emailId,password;
    private Button btnsigup;
    private CallbackManager callbackManager;
    private Button loginButton;
    private AccessTokenTracker accessToken;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String TAG1="FacebookAuthentication";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();


        textView=findViewById(R.id.textView1);
        emailId=findViewById(R.id.editText3);
        password=findViewById(R.id.editText1);
        btnsigup=findViewById(R.id.button);

        loginButton=findViewById(R.id.login_button);
        callbackManager=CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG1,"onSuccess"+loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {


                Log.d(TAG1,"onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG1,"onError");
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList( "user_friends","public_profile"));
            }
        });
        accessToken=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mAuth.signOut();
                }
            }

        };


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mFirebaseuse=mAuth.getCurrentUser();
                if (mFirebaseuse != null ) {
                    Toast.makeText(MainActivity.this, "You are logged in.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Please Login.", Toast.LENGTH_SHORT).show();
                }
            }
        };


        btnsigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                if(email.isEmpty())
                {emailId.setError("Please enter email Id");
                emailId.requestFocus();
            }
                else if(pass.isEmpty())
                {password.setError("Please enter Password");
                    password.requestFocus();
            }
            else if(email.isEmpty() && pass.isEmpty())
                {Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
            }
                else if(!(email.isEmpty() && pass.isEmpty()))
                {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {Toast.makeText(MainActivity.this, "Login Error, Please Login here", Toast.LENGTH_SHORT).show();}
                            else{
                                Intent intr=new Intent(MainActivity.this,Main2Activity.class);
                                startActivity(intr);
                            }
                        }
                    });
                }
            else {Toast.makeText(MainActivity.this,"Error Occured!",Toast.LENGTH_SHORT).show();}

            }
        });



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(log);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
            mGoogleSignInClient=GoogleSignIn.getClient(this,gso);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });}

    private void handleFacebookToken(AccessToken accessToken) {
        Log.d(TAG1,"handleFacebookToken"+accessToken);

        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {Log.d(TAG1,"sign in with credential:successful");
                FirebaseUser user =mAuth.getCurrentUser();
                updateUI(user);}

                else{Log.d(TAG1,"sign in with credential :failure");
                Toast.makeText(MainActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signIn()
        {

            Intent signInIntent=mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent,RC_SIGN_IN);
        }
 @Override
 public void onActivityResult(int requestCode,int resultCode,Intent data)
 {
     callbackManager.onActivityResult(requestCode, resultCode, data);
     super.onActivityResult(requestCode,resultCode,data);
     if(requestCode==RC_SIGN_IN)
     {
         Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
         handleSignInResult(task);
     }
 }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this,"Signed In Successfully",Toast.LENGTH_SHORT);
            // Signed in successfully, show authenticated UI.

            assert account != null;
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(MainActivity.this,"Sign In Failed",Toast.LENGTH_SHORT);
            firebaseAuthWithGoogle(null);

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, " Login Error", Toast.LENGTH_SHORT);
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT);
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });

    }
        private void updateUI(FirebaseUser fuser)
        {
            Intent start= new Intent(MainActivity.this,Splash.class);
            startActivity(start);
            GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getApplicationContext());



        }

    @Override
    protected void onStart()
    {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

    }

}

