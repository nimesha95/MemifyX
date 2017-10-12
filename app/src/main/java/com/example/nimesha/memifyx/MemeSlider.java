package com.example.nimesha.memifyx;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MemeSlider extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    public List<ImageUpload> ImageArray;
    public int count = 1;
    public int back = 0;

    ImageView imgview1;
    TextView ImgName;
    private ProgressDialog progressDialog;

    public static String TAG ="MemeSlider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_slider);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Landing.FB_DATABASE_PATH);
        ImageArray = new ArrayList<ImageUpload>();

        imgview1 = (ImageView) findViewById(R.id.imageView2);
        ImgName = (TextView) findViewById(R.id.ImgName);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading list image....");
        progressDialog.show();

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
                Log.d(TAG,"Right");
                changeImg(back - 1);
                //Toast.makeText(MemeSlider.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Log.d(TAG,"Left");
                changeImg(count);
                //Toast.makeText(MemeSlider.this, "left", Toast.LENGTH_SHORT).show();
                back = count;
                count++;
            }

        });
    }

    public void changeImg(int count) {
        Picasso.with(this).load(ImageArray.get(count).getUrl()).fit().into(imgview1);
        ImgName.setText(ImageArray.get(count).getName());
    }
}
