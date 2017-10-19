package com.example.nimesha.memifyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
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
import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class typeButtonsActivity extends AppCompatActivity {
    static int buttonStaus[]=new int [4];
    public String newUrl;
    String questionId;
    String userName;
    TextView tv;
    SharedPreferences prefs;
    int swipes;
    int count;
    ImageView ButtonSubmit;
    ImageView ButtonObscene;
    ImageView ButtonIdentityHate;
    ImageView ButtonInsult;
    ImageView ButtonThreat;
    JSONObject theAnswer;
    JSONObject finalAnswer ;
    String username;

    private DatabaseReference mUserDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_buttons);

        mUserDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH_user);

        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        username = prefs.getString("username", "User not found");

        mUserDatabaseRef.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                swipes = dataSnapshot.child("swipes").getValue(Integer.class);
                count=dataSnapshot.child("count").getValue(Integer.class);
                Log.d("swipes",""+swipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        swipes = prefs.getInt("swipes", -99);

        Intent intent=getIntent();
        String theAnswerString=intent.getStringExtra("theAnswer");
        questionId=intent.getStringExtra("questionId");
        userName="testUser";
        Log.d("theAnswer",theAnswerString);
        ButtonSubmit = (ImageView) findViewById(R.id.btnsubmit);
        ButtonObscene = (ImageView) findViewById(R.id.btnobscene);
        ButtonIdentityHate = (ImageView) findViewById(R.id.btnidentityhate);
        ButtonInsult = (ImageView) findViewById(R.id.btninsult);
        ButtonThreat = (ImageView) findViewById(R.id.btnthreat);

        ButtonObscene.setTag(1);
        ButtonIdentityHate.setTag(1);
        ButtonInsult.setTag(1);
        ButtonThreat.setTag(1);

        try{
            theAnswer=new JSONObject(theAnswerString);}
        catch(Exception e){
            Log.d("theAnswer","Error converting JSON");

        }


        ButtonObscene.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                ButtonIdentityHate.setTag(1);
                ButtonInsult.setTag(1);
                ButtonThreat.setTag(1);

                if (ButtonObscene.getTag().equals(1)) {
                    ButtonObscene.setImageResource(R.drawable.btn_obscene_1);
                    ButtonObscene.setTag(3);
                }
//                else if(ButtonObscene.getTag().equals(2)){
//                    ButtonObscene.setImageResource(R.drawable.btn_obscene_new_1);
//                    ButtonObscene.setTag(3);
//                }
                else if (ButtonObscene.getTag().equals(3)) {
                    ButtonObscene.setImageResource(R.drawable.btn_obscene_2);
                    ButtonObscene.setTag(4);
                } else {
                    ButtonObscene.setImageResource(R.drawable.btn_obscene_3);
                    ButtonObscene.setTag(1);
                }

                ButtonClicked(ButtonObscene, 0);
                return false;
            }
        });

        ButtonIdentityHate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                ButtonObscene.setTag(1);
                ButtonInsult.setTag(1);
                ButtonThreat.setTag(1);

                if (ButtonIdentityHate.getTag().equals(1)) {
                    ButtonIdentityHate.setImageResource(R.drawable.btn_identityhate_1);
                    ButtonIdentityHate.setTag(3);
                }

                else if (ButtonIdentityHate.getTag().equals(3)) {
                    ButtonIdentityHate.setImageResource(R.drawable.btn_identityhate_2);
                    ButtonIdentityHate.setTag(4);
                } else {
                    ButtonIdentityHate.setImageResource(R.drawable.btn_identityhate_3);
                    ButtonIdentityHate.setTag(1);
                }

                ButtonClicked(ButtonIdentityHate, 1);
                return false;
            }
        });

        ButtonInsult.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                ButtonObscene.setTag(1);
                ButtonIdentityHate.setTag(1);
                ButtonThreat.setTag(1);

                if (ButtonInsult.getTag().equals(1)) {
                    ButtonInsult.setImageResource(R.drawable.btn_insult_1);
                    ButtonInsult.setTag(3);
                }
//                else if(ButtonInsult.getTag().equals(2)){
//                    ButtonInsult.setImageResource(R.drawable.btn_insult_new_1);
//                    ButtonInsult.setTag(3);
//                }
                else if (ButtonInsult.getTag().equals(3)) {
                    ButtonInsult.setImageResource(R.drawable.btn_insult_2);
                    ButtonInsult.setTag(4);
                } else {
                    ButtonInsult.setImageResource(R.drawable.btn_insult_3);
                    ButtonInsult.setTag(1);
                }

                ButtonClicked(ButtonInsult, 2);
                return false;
            }
        });

        ButtonThreat.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if (ButtonThreat.getTag().equals(1)) {
                    ButtonThreat.setImageResource(R.drawable.btn_threat_1);
                    ButtonThreat.setTag(3);
                }
//                else if(ButtonThreat.getTag().equals(2)){
//                    ButtonThreat.setImageResource(R.drawable.btn_threat_new_1);
//                    ButtonThreat.setTag(3);
//                }
                else if (ButtonThreat.getTag().equals(3)) {
                    ButtonThreat.setImageResource(R.drawable.btn_threat_2);
                    ButtonThreat.setTag(4);
                } else {
                    ButtonThreat.setImageResource(R.drawable.btn_threat_3);
                    ButtonThreat.setTag(1);
                }

                ButtonClicked(ButtonThreat, 3);
                return false;
            }
        });

        ButtonSubmit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                DatabaseReference user = mUserDatabaseRef.child(username);
                swipes += 1;
                count += 1;
                user.child("swipes").setValue(swipes);
                user.child("count").setValue(count);
                Log.d("swipes:count", " " + swipes + " : " + count);


                try {
                    theAnswer.put("obscene", getObsceneLevel());
                    theAnswer.put("identityHate", getIdentityHateLevel());
                    theAnswer.put("insult", getInsultLevel());
                    theAnswer.put("threat", getThreatLevel());

                    finalAnswer = new JSONObject();
                    finalAnswer.put("answer", theAnswer.toString());
                    //finalAnswer.put("answer", theAnswer);
                    Log.d("finalAnswer", finalAnswer.getString("answer"));

                    PostAnotation postAnotation = new PostAnotation();
                    String newUrl = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_v1_x10k_zhs25/questions/" + questionId + "/answers/" + username;
                    String oldUrl = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_v1_x10k_zhs25/questions/" + questionId + "/answers/" + username;
                    Log.d("url", newUrl);
                    postAnotation.setUrl(newUrl);
                    postAnotation.execute();
                    //wp_v2_x2000_XXXXX
                    for (int i : buttonStaus) {
                        i = 0;
                    }
                    Toast.makeText(typeButtonsActivity.this, "Your response recorded", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } catch (Exception e) {
                    Log.d("finalAnswer", "error while creating JSON");
                }

                return false;
            }
        });
    }


    void ButtonClicked(ImageView b, int i) {
        buttonStaus[i]=(buttonStaus[i]+1)%3;
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

    public class PostAnotation extends AsyncTask<Void, Void, String> {
        String url;
        String response = null;


         public String setUrl(String url) {
            try {
                this.url = url;
                return "successfully set";


            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... voids) {
            Request requestX = null;

            try {
                requestX = Bridge
                        .post(url)
                        .body(finalAnswer)
                        .request();
            } catch (BridgeException e) {
                e.printStackTrace();
            }

            Response responseX = requestX.response();
            if (responseX.isSuccess()) {
                // Request returned HTTP status 200-300
                responseX.asString();
                Log.d("stuffhappens", responseX.asString());
            } else {
                // Request returned an HTTP error status
                Log.d("stuffhappens", responseX.asString());

            }
            return responseX.asString();
        }

        @Override
        protected void onPostExecute(String result) {
            try {

                Log.d("AnswerSent", questionId+"-->"+finalAnswer.toString());
            }

            catch(Exception e){
                Log.d("AnswerSent", "failed to sent");
            }

            Log.d("SendingReport", result);

        }

    }

}
