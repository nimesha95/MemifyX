package com.example.nimesha.memifyx;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class button_grp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_grp);

        final ImageView imageView1 = (ImageView) findViewById(R.id.imageView10);
        final ImageView imageView2 = (ImageView) findViewById(R.id.imageView11);
        final ImageView imageView3 = (ImageView) findViewById(R.id.imageView12);
        final ImageView imageView4 = (ImageView) findViewById(R.id.imageView13);
        final ImageView imageView5 = (ImageView) findViewById(R.id.imageView14);

        imageView1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                imageView1.setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
                return false;
            }
        });

        imageView2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                imageView2.setColorFilter(0xFF0000FF, PorterDuff.Mode.MULTIPLY);
                return false;
            }
        });

        imageView3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                imageView3.setColorFilter(0xFF777777, PorterDuff.Mode.MULTIPLY);
                return false;
            }
        });

        imageView4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                imageView4.setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
                return false;
            }
        });

        imageView5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                imageView5.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
                return false;
            }
        });
    }
}
