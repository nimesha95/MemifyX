package com.example.nimesha.memifyx;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.nimesha.memifyx.R.id.username;
import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class Achievements extends AppCompatActivity {

    private DatabaseReference mUserDatabaseRef;
    String username;
    int swipes;
    TextView achieve_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        achieve_txt = (TextView) findViewById(R.id.achieve_txt);

        mUserDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH_user);

        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        username = prefs.getString("username", "User not found");


        mUserDatabaseRef.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                swipes = dataSnapshot.child("swipes").getValue(Integer.class);
                achieve_txt.setText("You have Submitted : " + swipes);
                //Log.d("swipes", "" + swipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
