package com.example.nimesha.memifyx;

import android.content.Intent;
import android.graphics.Color;
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
                        //int colour []={14935269,14784150,14745600};
                        int colour []={Color.BLACK,Color.BLUE,Color.GREEN};
                        ButtonClicked(ButtonObscene,0,colour);


                    }
                }
        );
        ButtonIdentityHate.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //int colour []={14935269,14784150,14745600};
                        int colour []={Color.BLACK,Color.BLUE,Color.GREEN};
                        ButtonClicked(ButtonIdentityHate,1,colour);


                    }
                }
        );
        ButtonInsult.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //int colour []={14935269,14784150,14745600};
                        int colour []={Color.BLACK,Color.BLUE,Color.GREEN};
                        ButtonClicked(ButtonInsult,2,colour);


                    }
                }
        );
        ButtonThreat.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //int colour []={14935269,14784150,14745600};
                        int colour []={Color.BLACK,Color.BLUE,Color.GREEN};
                        ButtonClicked(ButtonThreat,3,colour);


                    }
                }
        );
        ButtonSubmit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            JSONObject JsonObscene = new JSONObject();
                            JsonObscene.put("enumAnswer", getObsceneLevel());

                            JSONObject JsonIdentityHate = new JSONObject();
                            JsonIdentityHate.put("enumAnswer", getIdentityHateLevel());

                            JSONObject JsonInsult = new JSONObject();
                            JsonInsult.put("enumAnswer", getInsultLevel());

                            JSONObject JsonThreat = new JSONObject();
                            JsonThreat.put("enumAnswer", getThreatLevel());

                            theAnswer.put("obscene",JsonObscene);
                            theAnswer.put("identityHate",JsonIdentityHate);
                            theAnswer.put("insult",JsonInsult);
                            theAnswer.put("threat", JsonThreat);

                            JSONObject finalAnswer = new JSONObject();
                            finalAnswer.put("answer",theAnswer);
                            Log.d("finalAnswer",finalAnswer.getString("answer"));

                        }
                        catch (Exception e){

                        }


                    }
                }
        );


    }

    void ButtonClicked(Button b,int i,int[] colour ){
        buttonStaus[i]=(buttonStaus[i]+1)%3;
        b.setBackgroundColor(colour[buttonStaus[i]]);

    }

    String getObsceneLevel(){
        return getLevel(0);
    }
    String getIdentityHateLevel(){
        return getLevel(1);
    }
    String getInsultLevel(){
        return getLevel(2);
    }
    String getThreatLevel(){
        return getLevel(3);
    }

    String getLevel(int i){
        switch (buttonStaus[i]){
            case 0:
                return "NotAtAll";

            case 1:
                return "Somewhat";

            case 2:
                return "Very";

            default:
                return "Error";
        }
    }


}
