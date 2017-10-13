package com.example.nimesha.memifyx;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.nimesha.memifyx.Landing.FB_DATABASE_PATH;

public class Signup extends AppCompatActivity {

    public static final String FB_DATABASE_PATH_user = "users";
    private static final String TAG = "signup";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText username = (EditText) findViewById(R.id.username1);
        final EditText pass = (EditText) findViewById(R.id.password1);

        Button signupBtn = (Button) findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();
        mUserDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH_user);

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

        final String newEmail = email + "@memify.com";
        mAuth.createUserWithEmailAndPassword(newEmail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Signup.this,user.getEmail()+" Registeration Succesfull!",Toast.LENGTH_SHORT).show();

                            //sets user info on firebase
                            mUserDatabaseRef.child(email).child("count").setValue(0);
                            mUserDatabaseRef.child(email).child("swipes").setValue(10);

                            SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", email);
                            editor.putInt("swipes", 10);     //at the signup user is given 10 swipes
                            editor.commit();

                            signin(newEmail, password);
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

                            Intent intent = new Intent(Signup.this, activity_terms.class);
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