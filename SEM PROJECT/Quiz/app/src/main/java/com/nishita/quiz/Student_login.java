package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import java.util.Arrays;

import static com.facebook.FacebookSdk.sdkInitialize;


public class Student_login extends AppCompatActivity {
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
    private int count=0;
    private String Type;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);

        mAuth = FirebaseAuth.getInstance();
        //String type= data;

        //textView=findViewById(R.id.textView1);
        emailId=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btnsigup=findViewById(R.id.login);

        loginButton=findViewById(R.id.login);
        callbackManager=CallbackManager.Factory.create();


        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Student_login.this, page2_choose.class);
            }
        });


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
                int count=1;
                LoginManager.getInstance().logInWithReadPermissions(Student_login.this, Arrays.asList( "user_friends","public_profile"));
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
                    Toast.makeText(Student_login.this, "You are logged in.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Student_login.this, Main2Activity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Student_login.this, "Please Login.", Toast.LENGTH_SHORT).show();
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
                {Toast.makeText(Student_login.this,"Fields are empty",Toast.LENGTH_SHORT).show();
            }
                else if(!(email.isEmpty() && pass.isEmpty()))
                {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(Student_login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {Toast.makeText(Student_login.this, "Login Error, Please Login here", Toast.LENGTH_SHORT).show();}
                            else{
                                Intent intr=new Intent(Student_login.this,Main2Activity.class);
                                startActivity(intr);
                            }
                        }
                    });
                }
            else {Toast.makeText(Student_login.this,"Error Occured!",Toast.LENGTH_SHORT).show();}

            }
        });




       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
            mGoogleSignInClient=GoogleSignIn.getClient(this,gso);
            /*signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //signIn();
                    Intent signInIntent=mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent,RC_SIGN_IN);
                }
            });*/


    }

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
                Toast.makeText(Student_login.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

 @Override
 public void onActivityResult(int requestCode,int resultCode,Intent data)
 {
     if(count==1){
     callbackManager.onActivityResult(requestCode, resultCode, data);}
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
            Toast.makeText(Student_login.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.

            assert account != null;
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(Student_login.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Student_login.this, " Login Error", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Student_login.this, "Failed", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });

    }
        private void updateUI(FirebaseUser fuser)
        {

            Intent start= new Intent(Student_login.this,Splash.class);
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

