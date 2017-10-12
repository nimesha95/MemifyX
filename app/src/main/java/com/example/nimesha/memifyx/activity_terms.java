package com.example.nimesha.memifyx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class activity_terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        final CheckBox TermsCheckBox = (CheckBox) findViewById (R.id.termscheckbox);
        final Button BtnAccept = (Button) findViewById(R.id.btnaccept);

        TermsCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    BtnAccept.setClickable(true);
                    BtnAccept.setEnabled(true);
                }
                else {
                    //case 2
                    BtnAccept.setClickable(false);
                    BtnAccept.setEnabled(false);
                }
            }
        });
    }



}
