package com.example.nimesha.memifyx;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import static com.example.nimesha.memifyx.R.id.image;

public class button_grp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_grp);

        final ImageView imageView1 = (ImageView) findViewById(R.id.btnobscene);
        final ImageView imageView2 = (ImageView) findViewById(R.id.btninsult);
        final ImageView imageView3 = (ImageView) findViewById(R.id.btnidentityhate);
        final ImageView imageView4 = (ImageView) findViewById(R.id.btnthreat);

        imageView1.setTag(1);
        imageView2.setTag(1);
        imageView3.setTag(1);
        imageView4.setTag(1);

        imageView1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if (imageView1.getTag().equals(1)) {
                    imageView1.setImageResource(R.drawable.btn_obscene_new);
                    imageView1.setTag(2);
                } else if (imageView1.getTag().equals(2)) {
                    imageView1.setImageResource(R.drawable.btn_obscene_new_1);
                    imageView1.setTag(3);
                } else if (imageView1.getTag().equals(3)) {
                    imageView1.setImageResource(R.drawable.btn_obscene_new_2);
                    imageView1.setTag(4);
                } else {
                    imageView1.setImageResource(R.drawable.btn_obscene_new_3);
                    imageView1.setTag(1);
                }
                return false;
            }
        });

        imageView2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if (imageView2.getTag().equals(1)) {
                    imageView2.setImageResource(R.drawable.btn_insult_new);
                    imageView2.setTag(2);
                } else if (imageView2.getTag().equals(2)) {
                    imageView2.setImageResource(R.drawable.btn_insult_new_1);
                    imageView2.setTag(3);
                } else if (imageView2.getTag().equals(3)) {
                    imageView2.setImageResource(R.drawable.btn_insult_new_2);
                    imageView2.setTag(4);
                } else {
                    imageView2.setImageResource(R.drawable.btn_insult_new_3);
                    imageView2.setTag(1);
                }
                return false;
            }
        });

        imageView3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if (imageView3.getTag().equals(1)) {
                    imageView3.setImageResource(R.drawable.btn_identityhate_new);
                    imageView3.setTag(2);
                } else if (imageView3.getTag().equals(2)) {
                    imageView3.setImageResource(R.drawable.btn_identityhate_new_1);
                    imageView3.setTag(3);
                } else if (imageView3.getTag().equals(3)) {
                    imageView3.setImageResource(R.drawable.btn_identityhate_new_2);
                    imageView3.setTag(4);
                } else {
                    imageView3.setImageResource(R.drawable.btn_identityhate_new_4);
                    imageView3.setTag(1);
                }
                return false;
            }
        });

        imageView4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if (imageView4.getTag().equals(1)) {
                    imageView4.setImageResource(R.drawable.btn_threat_new);
                    imageView4.setTag(2);
                } else if (imageView4.getTag().equals(2)) {
                    imageView4.setImageResource(R.drawable.btn_threat_new_1);
                    imageView4.setTag(3);
                } else if (imageView4.getTag().equals(3)) {
                    imageView4.setImageResource(R.drawable.btn_threat_new_2);
                    imageView4.setTag(4);
                } else {
                    imageView4.setImageResource(R.drawable.btn_threat_new_3);
                    imageView4.setTag(1);
                }
                return false;
            }
        });

    }

}
