package com.example.nimesha.memifyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class typeButtonsActivity extends AppCompatActivity {
    static int buttonStaus[]=new int [4];
    Button ButtonSubmit;
    Button ButtonObscene;
    Button ButtonIdentityHate;
    Button ButtonInsult;
    Button ButtonThreat;
    JSONObject theAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_buttons);
        Intent intent=getIntent();
        String theAnswerString=intent.getStringExtra("theAnswer");
        Log.d("theAnswer",theAnswerString);
        ButtonSubmit =(Button) findViewById(R.id.ButtonSubmit);
        ButtonObscene =(Button) findViewById(R.id.ButtonObscene);
        ButtonIdentityHate =(Button) findViewById(R.id.ButtonIdentityHate);
        ButtonInsult =(Button) findViewById(R.id.ButtonInsult);
        ButtonThreat =(Button) findViewById(R.id.ButtonThreat);



        try{
            theAnswer=new JSONObject(theAnswerString);}
        catch(Exception e){
            Log.d("theAnswer","Error converting JSON");

        }
        ButtonObscene.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        int colour []={14935269,14784150,14745600};
                        ButtonClicked(ButtonObscene,0,colour);


                    }
                }
        );

    }

    void ButtonClicked(Button b,int i,int[] colour ){
        buttonStaus[i]=(buttonStaus[i]+1)%3;
        b.setBackgroundColor(i);

    }



}
