package com.example.nimesha.memifyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.nimesha.memifyx.MainActivity.MY_PREFS_NAME;
import static com.example.nimesha.memifyx.R.id.username;
import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class decision_point extends AppCompatActivity {

    Button MemesBtn;
    Button TextRatingBtn;
    Button signoutBtn;
    Button MemeUploadBtn;
    Button GettingStartedBtn;
    Button Acheivements;

    private DatabaseReference mUserDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_point);

        MemesBtn = (Button) findViewById(R.id.MemesBtn);
        TextRatingBtn = (Button) findViewById(R.id.TxtRatingBtn);
        signoutBtn = (Button) findViewById(R.id.button2);
        MemeUploadBtn = (Button) findViewById(R.id.MemeUploadBtn);
        GettingStartedBtn = (Button) findViewById(R.id.GettingStartedBtn);
        Acheivements = (Button) findViewById(R.id.Acheivements);

        mUserDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH_user);


        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        String username = prefs.getString("username", "User not found");

        mUserDatabaseRef.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int swipes = dataSnapshot.child("swipes").getValue(Integer.class);

                SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("swipes", swipes);
                editor.commit();

                //Log.d("swipes",""+swipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        MemesBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(decision_point.this,MemeSlider.class);
                        startActivity(intent);
                    }
                }
        );

        TextRatingBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(decision_point.this,TextRating.class);
                        startActivity(intent);
                    }
                }
        );

        MemeUploadBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(decision_point.this, Landing.class);
                        startActivity(intent);
                    }
                }
        );

        signoutBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("username", "user signed out");
                        editor.commit();

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(decision_point.this, MainActivity.class);
                        startActivity(intent);

                    }
                }
        );
    }
}
