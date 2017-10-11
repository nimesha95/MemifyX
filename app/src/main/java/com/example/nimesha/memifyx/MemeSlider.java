package com.example.nimesha.memifyx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static com.example.nimesha.memifyx.R.id.imageView;
import static com.example.nimesha.memifyx.R.id.scrollViewQuestionText;
import static com.example.nimesha.memifyx.R.id.tvImageName;

public class MemeSlider extends AppCompatActivity {
    ImageView imgview1;

    public static String TAG ="MemeSlider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_slider);
        imgview1 = (ImageView) findViewById(R.id.imageView2);

        imgview1.setOnTouchListener(new OnSwipeTouchListener(MemeSlider.this) {
            public void onSwipeRight() {
                Log.d(TAG,"Right");
                Toast.makeText(MemeSlider.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Log.d(TAG,"Left");
                changeImg();
                Toast.makeText(MemeSlider.this, "left", Toast.LENGTH_SHORT).show();

            }

        });
    }

    public void changeImg(){
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").fit().into(imgview1);
    }
}
