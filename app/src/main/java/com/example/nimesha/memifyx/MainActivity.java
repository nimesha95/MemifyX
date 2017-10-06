package com.example.nimesha.memifyx;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.R.attr.password;
import static com.example.nimesha.memifyx.R.id.signoutbtn;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "signin";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText pass = (EditText) findViewById(R.id.password);

        Button signinBtn = (Button) findViewById(R.id.signinbtn);
        Button signupredir = (Button) findViewById(R.id.signupredir);
        Button signoutBtn = (Button) findViewById(R.id.signoutbtn);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //goes to second activity if user is already logged in
                    Intent intent = new Intent(MainActivity.this, Landing.class);
                    startActivity(intent);

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        signinBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        signin(username.getText().toString(),pass.getText().toString());
                    }
                }
        );

        signupredir.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Signup.class);
                        startActivity(intent);
                    }
                }
        );

        signoutBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Log.d(TAG, "User signed out");
                    }
                }
        );
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

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    public void signin(String email, String password){
        if(email.length()==0 || password.length()==0){
            Log.w(TAG, "signInWithEmail:failure No inputs");
            Toast.makeText(MainActivity.this, "Enter valid email/password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(MainActivity.this, Landing.class);
                            startActivity(intent);

                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, user.getEmail());


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {

                        }
                    }
                });
    }
}
