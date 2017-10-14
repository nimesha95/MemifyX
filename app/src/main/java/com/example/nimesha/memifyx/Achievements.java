package com.example.nimesha.memifyx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.nimesha.memifyx.R.id.username;
import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class Achievements extends AppCompatActivity {

    String username;
    int swipes;
    TextView achieve_txt;
    private DatabaseReference mUserDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        if (CheckNetwork.isInternetAvailable(Achievements.this)) //returns true if internet available
        {
            Log.d("internet", "Net available");
        } else {
            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(Achievements.this);

            dialogBuilder
                    .withTitle("No Internet")                                  //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#11000000")                              //def
                    .withMessage("Please Connect to the Internet")                     //.withMessage(null)  no Msg
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)
                    .withButton1Text("Ok")                                      //def gone
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Achievements.this, decision_point.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

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
