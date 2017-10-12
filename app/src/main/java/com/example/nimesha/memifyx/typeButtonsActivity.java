package com.example.nimesha.memifyx;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.button;

public class typeButtonsActivity extends AppCompatActivity {
    static int buttonStaus[]=new int [4];
    String questionId;
    String userName;

    Button ButtonSubmit;
    Button ButtonObscene;
    Button ButtonIdentityHate;
    Button ButtonInsult;
    Button ButtonThreat;
    JSONObject theAnswer;
    JSONObject finalAnswer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_buttons);
        Intent intent=getIntent();
        String theAnswerString=intent.getStringExtra("theAnswer");
        questionId=intent.getStringExtra("questionId");
        userName="testUser";
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

        ButtonThreat.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //ButtonThreat.setBackgroundColor(14745600);
                        ButtonThreat.getBackground().setColorFilter(Color.RED,PorterDuff.Mode.MULTIPLY);
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

                            finalAnswer=new JSONObject();
                            finalAnswer.put("answer",theAnswer);
                            Log.d("finalAnswer",finalAnswer.getString("answer"));

                            PostAnotation postAnotation=new PostAnotation();
                            postAnotation.setUrl("https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_x2000_zhs25/questions/"+questionId+"/answers/"+userName);
                            postAnotation.execute();


                            for (int i: buttonStaus){
                                i=0;
                            }
                            onBackPressed();
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

    public class PostAnotation extends AsyncTask<Void, Void, String> {
        URL url;
        String response = null;


         public String setUrl(String url) {
            try {
                this.url = new URL(url);
                return "successfully set";


            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... voids) {

            try {

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(finalAnswer.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    Log.d("json", "" + in);
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                Log.d("AnswerSent", finalAnswer.getString("answer"));
            }
            catch(Exception e){
                Log.d("AnswerSent", "failed to sent");
            }
            response = result;
        }

    }

}
