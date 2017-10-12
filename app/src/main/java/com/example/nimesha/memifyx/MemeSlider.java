package com.example.nimesha.memifyx;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class MemeSlider extends AppCompatActivity {
    SharedPreferences prefs;

    private DatabaseReference mDatabaseRef;
    public List<ImageUpload> ImageArray;
    public int count = 1;
    public int back = 0;

    String username;
    TextView tv;

    int swipes;

    ImageView imgview1;
    TextView ImgName;
    private ProgressDialog progressDialog;
    LikeButton likebtn;

    public static String TAG = "MemeSlider";

    private DatabaseReference mUserDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_slider);


        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        username = prefs.getString("username", "User not found");

        mUserDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH_user);

        mUserDatabaseRef.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                swipes = dataSnapshot.child("swipes").getValue(Integer.class);
                Log.d("swipes", "" + swipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Landing.FB_DATABASE_PATH);

        ImageArray = new ArrayList<ImageUpload>();

        imgview1 = (ImageView) findViewById(R.id.imageView2);
        ImgName = (TextView) findViewById(R.id.ImgName);
        likebtn = (LikeButton) findViewById(R.id.like_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        likebtn.setEnabled(false);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    Log.d("somestuff", "" + snapshot.getKey());

                    ImageUpload img = snapshot.getValue(ImageUpload.class);

                    Log.d("blaster", "" + img.getLike());
                    ImageArray.add(img);

                    changeImg(0);
                    Log.d("imglist", "" + img.getUrl());
                    mDatabaseRef.child("-Kvn3dDykKlLZ0wBC1v-").child("like").setValue(5);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        imgview1.setOnTouchListener(new OnSwipeTouchListener(MemeSlider.this) {
            public void onSwipeRight() {
                if (swipes>0) {
                    Log.d(TAG, "Right");
                    changeImg(back - 1);
                    DatabaseReference user = mUserDatabaseRef.child(username);
                    swipes -= 1;
                    user.child("swipes").setValue(swipes);
                    tv.setText("$wipes: " + swipes);
                    //Toast.makeText(MemeSlider.this, "right", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MemeSlider.this, "not enough $wipes ,go earn some", Toast.LENGTH_SHORT).show();
                }
            }

            public void onSwipeLeft() {
                if (swipes>0) {
                    Log.d(TAG, "Left");
                    changeImg(count);
                    DatabaseReference user = mUserDatabaseRef.child(username);
                    swipes -= 1;
                    user.child("swipes").setValue(swipes);
                    tv.setText("$wipes: " + swipes);

                    //Toast.makeText(MemeSlider.this, "left", Toast.LENGTH_SHORT).show();
                    back = count;
                    count++;
                }
                else {
                    Toast.makeText(MemeSlider.this, "not enough $wipes, go earn some", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void changeImg(int count) {
        likebtn.setLiked(false);
        likebtn.setEnabled(false);
        Picasso.with(this).load(ImageArray.get(count).getUrl()).fit().into(imgview1);
        ImgName.setText(ImageArray.get(count).getName());
        likebtn.setEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        tv = new TextView(this);

        tv.setText("$wipes: " + swipes);
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(20);
        menu.add(0, 0, 1, "swipes").setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
}