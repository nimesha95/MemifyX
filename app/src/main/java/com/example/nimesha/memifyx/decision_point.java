package com.example.nimesha.memifyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.nimesha.memifyx.MainActivity.MY_PREFS_NAME;
import static com.example.nimesha.memifyx.R.id.username;

public class decision_point extends AppCompatActivity {

    Button MemesBtn;
    Button TextRatingBtn;
    Button signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_point);

        MemesBtn = (Button) findViewById(R.id.MemesBtn);
        TextRatingBtn = (Button) findViewById(R.id.TxtRatingBtn);
        signoutBtn = (Button) findViewById(R.id.button2);
/*
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Log.d("username",prefs.getString("username",null));
*/
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

        signoutBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(decision_point.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
