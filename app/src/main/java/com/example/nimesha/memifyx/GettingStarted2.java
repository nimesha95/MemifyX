package com.example.nimesha.memifyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class GettingStarted2 extends AppCompatActivity {

    static int buttonStaus[] = new int[4];
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
    JSONObject finalAnswer;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started2);


        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        username = prefs.getString("username", "User not found");

        swipes = prefs.getInt("swipes", -99);

        ButtonSubmit = (ImageView) findViewById(R.id.btnsubmit);
        ButtonObscene = (ImageView) findViewById(R.id.btnobscene);
        ButtonIdentityHate = (ImageView) findViewById(R.id.btnidentityhate);
        ButtonInsult = (ImageView) findViewById(R.id.btninsult);
        ButtonThreat = (ImageView) findViewById(R.id.btnthreat);

        ButtonObscene.setTag(1);
        ButtonIdentityHate.setTag(1);
        ButtonInsult.setTag(1);
        ButtonThreat.setTag(1);

        // sequence example
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);

        sequence.setConfig(config);

        sequence.addSequenceItem(ButtonObscene,
                "Touch this to select the thereat level", "NEXT");

        sequence.addSequenceItem(ButtonObscene,
                "Touch it constantly to select the level of toxicity", "GOT IT");


        sequence.addSequenceItem(ButtonObscene,
                "Darker the color means higher the toxicity", "OK");

        sequence.addSequenceItem(ButtonSubmit,
                "Tap Here Submit the response\nYou're now ready!\nHave FUN! :D", "COOL");

        sequence.start();


        ButtonObscene.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                ButtonIdentityHate.setTag(1);
                ButtonInsult.setTag(1);
                ButtonThreat.setTag(1);

                if (ButtonObscene.getTag().equals(1)) {
                    ButtonObscene.setImageResource(R.drawable.btn_obscene_new);
                    ButtonObscene.setTag(3);
                }
//                else if(ButtonObscene.getTag().equals(2)){
//                    ButtonObscene.setImageResource(R.drawable.btn_obscene_new_1);
//                    ButtonObscene.setTag(3);
//                }
                else if (ButtonObscene.getTag().equals(3)) {
                    ButtonObscene.setImageResource(R.drawable.btn_obscene_new_2);
                    ButtonObscene.setTag(4);
                } else {
                    ButtonObscene.setImageResource(R.drawable.btn_obscene_new_3);
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
                    ButtonIdentityHate.setImageResource(R.drawable.btn_identityhate_new);
                    ButtonIdentityHate.setTag(3);
                }
//                else if(ButtonIdentityHate.getTag().equals(2)){
//                    ButtonIdentityHate.setImageResource(R.drawable.btn_identityhate_new_1);
//                    ButtonIdentityHate.setTag(3);
//                }
                else if (ButtonIdentityHate.getTag().equals(3)) {
                    ButtonIdentityHate.setImageResource(R.drawable.btn_identityhate_new_2);
                    ButtonIdentityHate.setTag(4);
                } else {
                    ButtonIdentityHate.setImageResource(R.drawable.btn_identityhate_new_4);
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
                    ButtonInsult.setImageResource(R.drawable.btn_insult_new);
                    ButtonInsult.setTag(3);
                }
//                else if(ButtonInsult.getTag().equals(2)){
//                    ButtonInsult.setImageResource(R.drawable.btn_insult_new_1);
//                    ButtonInsult.setTag(3);
//                }
                else if (ButtonInsult.getTag().equals(3)) {
                    ButtonInsult.setImageResource(R.drawable.btn_insult_new_2);
                    ButtonInsult.setTag(4);
                } else {
                    ButtonInsult.setImageResource(R.drawable.btn_insult_new_3);
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
                    ButtonThreat.setImageResource(R.drawable.btn_threat_new);
                    ButtonThreat.setTag(3);
                }
//                else if(ButtonThreat.getTag().equals(2)){
//                    ButtonThreat.setImageResource(R.drawable.btn_threat_new_1);
//                    ButtonThreat.setTag(3);
//                }
                else if (ButtonThreat.getTag().equals(3)) {
                    ButtonThreat.setImageResource(R.drawable.btn_threat_new_2);
                    ButtonThreat.setTag(4);
                } else {
                    ButtonThreat.setImageResource(R.drawable.btn_threat_new_3);
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
                swipes += 1;
                count += 1;
                Log.d("swipes:count", " " + swipes + " : " + count);

                Intent intentx = new Intent(GettingStarted2.this, decision_point.class);
                startActivity(intentx);

                try {
                    JSONObject JsonObscene = new JSONObject();
                    JsonObscene.put("enumAnswer", getObsceneLevel());

                    JSONObject JsonIdentityHate = new JSONObject();
                    JsonIdentityHate.put("enumAnswer", getIdentityHateLevel());

                    JSONObject JsonInsult = new JSONObject();
                    JsonInsult.put("enumAnswer", getInsultLevel());

                    JSONObject JsonThreat = new JSONObject();
                    JsonThreat.put("enumAnswer", getThreatLevel());

                    theAnswer.put("obscene", JsonObscene);
                    theAnswer.put("identityHate", JsonIdentityHate);
                    theAnswer.put("insult", JsonInsult);
                    theAnswer.put("threat", JsonThreat);

                    finalAnswer = new JSONObject();
                    finalAnswer.put("answer", theAnswer);
                    Log.d("finalAnswer", finalAnswer.getString("answer"));

                    PostAnotation postAnotation = new PostAnotation();
                    String newUrl = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_v2_x2000_zhs25/questions/" + questionId + "/answers/" + userName;
                    String oldUrl = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_x2000_zhs25/questions/" + questionId + "/answers/" + userName;
                    //postAnotation.setUrl(newUrl);
                    //postAnotation.execute();

                    //wp_v2_x2000_XXXXX

                    for (int i : buttonStaus) {
                        i = 0;
                    }
                    Toast.makeText(GettingStarted2.this, "Your response recorded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GettingStarted2.this, decision_point.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("finalAnswer", "error while creating JSON");

                }
                return false;
            }
        });


    }


    void ButtonClicked(ImageView b, int i) {
        buttonStaus[i] = (buttonStaus[i] + 1) % 3;

    }

    String getObsceneLevel() {
        return getLevel(0);
    }

    String getIdentityHateLevel() {
        return getLevel(1);
    }

    String getInsultLevel() {
        return getLevel(2);
    }

    String getThreatLevel() {
        return getLevel(3);
    }

    String getLevel(int i) {
        switch (buttonStaus[i]) {
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
                String postMessage = finalAnswer.toString(); //HERE_YOUR_POST_STRING.
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

                int responseCode = response.getStatusLine().getStatusCode();

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

                Log.d("AnswerSent", questionId + "-->" + finalAnswer.getString("answer"));
            } catch (Exception e) {
                Log.d("AnswerSent", "failed to sent");
            }

            response = result;
            Log.d("SendingReport", result);

        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        tv = new TextView(this);

        tv.setText("$wipes: " + swipes);
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(20);
        menu.add(0, 0, 1, "swipes").setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

}

