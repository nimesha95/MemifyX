package com.example.nimesha.memifyx;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class MemeSlider extends AppCompatActivity {
    public static String TAG = "MemeSlider";
    public List<ImageUpload> ImageArray;
    public int count;
    public int returnPoint;
    public int curIndex;
    SharedPreferences prefs;
    String username;
    TextView tv;
    int swipes;
    ImageView imgview1;
    TextView ImgName;
    TextView likecount;
    Button MemeUploadBtn;
    LikeButton likebtn;
    ImageUpload img;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog progressDialog;
    private DatabaseReference mUserDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_slider_new);

        if (CheckNetwork.isInternetAvailable(MemeSlider.this)) //returns true if internet available
        {
            Log.d("internet", "Net available");
        } else {
            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(MemeSlider.this);

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
                            Intent intent = new Intent(MemeSlider.this, decision_point.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        username = prefs.getString("username", "User not found");
        count = prefs.getInt("count", 0);
        Log.d("countBBY", "" + count);
        if (count < 0) {
            count = 0;
        } else if (count > prefs.getInt("imgArrLen", 0)) {
            count = prefs.getInt("imgArrLen", 0);
        }
        returnPoint = count;
        Log.d("countBBY", "return point " + prefs.getInt("imgArrLen", 0));
        mUserDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH_user);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Landing.FB_DATABASE_PATH);

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

        ImageArray = new ArrayList<ImageUpload>();

        imgview1 = (ImageView) findViewById(R.id.imageView2);
        ImgName = (TextView) findViewById(R.id.ImgName);
        likebtn = (LikeButton) findViewById(R.id.like_button);
        likecount = (TextView) findViewById(R.id.likecount);
        MemeUploadBtn = (Button) findViewById(R.id.MemeUploadBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    Log.d("somestuff", "" + snapshot.getKey());

                    img = snapshot.getValue(ImageUpload.class);
                    img.setKey(snapshot.getKey());
                    ImageArray.add(img);
                    Log.d("imglist", "" + img.getUrl());
                }
                Log.d("dumber", "curIndex->" + curIndex);
                Log.d("countBBY", "" + ImageArray.size());
                changeImg(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        MemeUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemeSlider.this, Landing.class);
                startActivity(intent);
            }
        });

        likebtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ImageUpload curImage = ImageArray.get(curIndex);
                int curlikes = curImage.getLike() + 1;
                ImageArray.get(curIndex).setLike(curlikes);
                mDatabaseRef.child(curImage.getKey()).child("like").setValue(curlikes);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                ImageUpload curImage = ImageArray.get(curIndex);
                int curlikes = curImage.getLike() - 1;
                ImageArray.get(curIndex).setLike(curlikes);
                mDatabaseRef.child(curImage.getKey()).child("like").setValue(curlikes);
            }
        });

        imgview1.setOnTouchListener(new OnSwipeTouchListener(MemeSlider.this) {
            public void onSwipeRight() {
                likebtn.setLiked(false);
                if (count == 0) {
                    Toast.makeText(MemeSlider.this, "End of the line!", Toast.LENGTH_SHORT).show();
                } else {
                    count = count - 1;
                    Log.d("dumber", "L->" + count);
                    changeImg(count);
                    DatabaseReference user = mUserDatabaseRef.child(username);

                    user.child("swipes").setValue(swipes);
                    if (swipes < 1) {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(MemeSlider.this);

                        dialogBuilder
                                .withTitle("Out of Swipes")                                  //.withTitle(null)  no title
                                .withTitleColor("#FFFFFF")                                  //def
                                .withDividerColor("#11000000")                              //def
                                .withMessage("Rate some text to earn more?")                     //.withMessage(null)  no Msg
                                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)
                                .withButton1Text("Yes! take me there!")                                      //def gone
                                .setButton1Click(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MemeSlider.this, TextRating.class);
                                        startActivity(intent);
                                    }
                                })

                                .show();
                    }
                }
            }

            public void onSwipeLeft() {
                if (count < ImageArray.size() - 1) {
                    likebtn.setLiked(false);
                    if (swipes > 0) {
                        count = count + 1;
                        Log.d("dumber", "R->" + count);
                        changeImg(count);
                        DatabaseReference user = mUserDatabaseRef.child(username);
                        if (count <= returnPoint) {
                        } else {
                            swipes -= 1;
                        }
                        user.child("swipes").setValue(swipes);
                        tv.setText("$wipes: " + swipes);
                        //Toast.makeText(MemeSlider.this, "left", Toast.LENGTH_SHORT).show();

                    } else {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(MemeSlider.this);

                        dialogBuilder
                                .withTitle("Out of Swipes")                                  //.withTitle(null)  no title
                                .withTitleColor("#FFFFFF")                                  //def
                                .withDividerColor("#11000000")                              //def
                                .withMessage("Rate some text to earn more?")                     //.withMessage(null)  no Msg
                                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)
                                .withButton1Text("Yes! take me there!")                                      //def gone
                                .setButton1Click(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MemeSlider.this, TextRating.class);
                                        startActivity(intent);
                                    }
                                })

                                .show();
                    }
                } else {
                    Toast.makeText(MemeSlider.this, "That's all the memes we have. Right now :D", Toast.LENGTH_SHORT).show();
                    count = ImageArray.size() - 1;
                }
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("count", count - 1);
        editor.putInt("imgArrLen", ImageArray.size());
        editor.commit();
    }

    public void changeImg(int count) {
        curIndex = count;
        likebtn.setEnabled(false);
        Picasso.with(this).load(ImageArray.get(count).getUrl()).fit().into(imgview1);
        ImgName.setText(ImageArray.get(count).getName());
        Log.d("likecountx", "" + ImageArray.get(count).getLike());
        likecount.setText("" + ImageArray.get(count).getLike());
        likebtn.setEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        tv = new TextView(this);

        tv.setText("$wipes: " + swipes);
        tv.setTextColor(Color.WHITE);
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(20);
        menu.add(0, 0, 1, "swipes").setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
}