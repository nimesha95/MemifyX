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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    String questionId;
    String userName;

    TextView tv;

    SharedPreferences prefs;

    int swipes;
    int count;

    Button ButtonSubmit;
    Button ButtonObscene;
    Button ButtonIdentityHate;
    Button ButtonInsult;
    Button ButtonThreat;
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
                        DatabaseReference user=mUserDatabaseRef.child(username);
                        swipes+=1;
                        count+=1;
                        user.child("swipes").setValue(swipes);
                        user.child("count").setValue(count);
                        Log.d("swipes:count"," "+swipes+" : "+count);



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
                            String newUrl="https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_v2_x2000_zhs25/questions/"+questionId+"/answers/"+userName;
                            String oldUrl="https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_x2000_zhs25/questions/"+questionId+"/answers/"+userName;
                            postAnotation.setUrl(newUrl);
                            postAnotation.execute();

                            //wp_v2_x2000_XXXXX

                            for (int i: buttonStaus){
                                i=0;
                            }
                            Toast.makeText(typeButtonsActivity.this, "Your response recorded", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        catch (Exception e){
                            Log.d("finalAnswer","error while creating JSON");

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

            try {
                int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
                String postMessage=finalAnswer.toString(); //HERE_YOUR_POST_STRING.
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
                HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
                HttpClient client = new DefaultHttpClient(httpParams);

                HttpPost request = new HttpPost(url);
                request.setEntity(new ByteArrayEntity(
                        postMessage.toString().getBytes("UTF8")));
                HttpResponse response = client.execute(request);

//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(15000 /* milliseconds */);
//                conn.setConnectTimeout(15000 /* milliseconds */);
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(finalAnswer.toString());
//
//                writer.flush();
//                writer.close();
//                os.close();

                int responseCode=response.getStatusLine().getStatusCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    return "Successfully Sent";

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

                Log.d("AnswerSent", questionId+"-->"+finalAnswer.getString("answer"));
            }

            catch(Exception e){
                Log.d("AnswerSent", "failed to sent");
            }

            response = result;
            Log.d("SendingReport", result);

        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        tv = new TextView(this);

        tv.setText("$wipes: " +swipes);
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(20);
        menu.add(0, 0, 1, "swipes").setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

}
