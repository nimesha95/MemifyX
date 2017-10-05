package com.example.nimesha.memifyx;

import android.content.Intent;
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

public class Signup extends AppCompatActivity {

    private static final String TAG = "signup";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText username = (EditText) findViewById(R.id.username1);
        final EditText pass = (EditText) findViewById(R.id.password1);

        Button signupBtn = (Button) findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //goes to second activity if user is already logged in
                    //Intent intent = new Intent(MainActivity.this, Landing.class);
                    //startActivity(intent);

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        signupBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        signup(username.getText().toString(),pass.getText().toString());
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

    public void signup(final String email, final String password) {
        if (email.length() == 0 || password.length() == 0) {
            Log.w(TAG, "signInWithEmail:failure No inputs");
            Toast.makeText(Signup.this, "Enter valid email/password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Signup.this,user.getEmail()+" Registeration Succesfull!",Toast.LENGTH_SHORT).show();
                            signin(email,password);
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(Signup.this, "A user with same email exists",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signin(String email, String password){
        if(email.length()==0 || password.length()==0){
            Log.w(TAG, "signInWithEmail:failure No inputs");
            Toast.makeText(Signup.this, "Enter valid email/password",
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
                            Intent intent = new Intent(Signup.this, Landing.class);
                            startActivity(intent);

                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, user.getEmail());


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {

                        }
                    }
                });
    }
}
