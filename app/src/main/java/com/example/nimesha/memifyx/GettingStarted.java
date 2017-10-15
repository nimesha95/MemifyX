package com.example.nimesha.memifyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static com.example.nimesha.memifyx.Signup.FB_DATABASE_PATH_user;

public class GettingStarted extends AppCompatActivity {

    public static String TAG = "smilies";
    SharedPreferences prefs;
    int swipes;
    int count;
    boolean islistInit = false;
    TextView tv;
    List<Question> questionList = new ArrayList<Question>();
    TextView textViewQuestionText;
    Button SubmitBtn;
    LinearLayout linlaHeaderProgress;
    ScrollView scrollViewQuestionText;
    SmileRating smileRating;
    CheckBox NotEnglishCheckBox;
    Question theQuestion;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_getting_started);

        SharedPreferences prefs = getSharedPreferences("memify", MODE_PRIVATE);
        username = prefs.getString("username", "User not found");

        smileRating = (SmileRating) findViewById(R.id.smile_rating);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        scrollViewQuestionText = (ScrollView) findViewById(R.id.scrollViewQuestionText);
        textViewQuestionText = (TextView) findViewById(R.id.textViewQuestionText);
        SubmitBtn = (Button) findViewById(R.id.button4);
        NotEnglishCheckBox = (CheckBox) findViewById(R.id.checkBox);
        //setQuestion();
        setProgressBarIndeterminateVisibility(true);
        scrollViewQuestionText.setOnTouchListener(new OnSwipeTouchListener(GettingStarted.this) {
            public void onSwipeRight() {
                Log.d(TAG, "Right");
                //SubmitBtn.setClickable(true);
                //SubmitBtn.setEnabled(true);
                Toast.makeText(GettingStarted.this, "please swipe left to skip this question", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Log.d(TAG, "Left");

                setQuestion();
                smileRating.setSelectedSmile(BaseRating.NONE, false);

            }

        });


        // sequence example
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);

        sequence.setConfig(config);

        sequence.addSequenceItem(textViewQuestionText,
                "Your task is to rate the toxicity of the following comments", "OK");

        sequence.addSequenceItem(textViewQuestionText,
                "This is where the comments are displayed", "NEXT");

        sequence.addSequenceItem(smileRating,
                "Select the Emoji that suites best for the above text", "GOT IT");

/*
        sequence.addSequenceItem(NotEnglishCheckBox,
                "Can't read the text?", "OK");
*/
        sequence.addSequenceItem(SubmitBtn,
                "After rating click this button!", "OK");

        sequence.addSequenceItem(SubmitBtn,
                "Take a look around! :D", "COOL");

        sequence.start();


        NotEnglishCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    smileRating.setSelectedSmile(BaseRating.NONE, false);
                    smileRating.setEnabled(false);
                    smileRating.setVisibility(View.INVISIBLE);
                    SubmitBtn.setClickable(true);
                    SubmitBtn.setEnabled(true);
                } else {
                    smileRating.setEnabled(true);
                    smileRating.setVisibility(View.VISIBLE);

                    if (smileRating.getRating() > 0) {
                        SubmitBtn.setClickable(true);
                        SubmitBtn.setEnabled(true);
                    } else {
                        SubmitBtn.setClickable(false);
                        SubmitBtn.setEnabled(false);
                    }
                }
            }
        });


        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                SubmitBtn.setClickable(true);
                SubmitBtn.setEnabled(true);
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        break;
                }
            }
        });

        SubmitBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(GettingStarted.this, GettingStarted2.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        setQuestion();
    }

    private String checkBoxStatus() {
        if (NotEnglishCheckBox.isChecked()) {
            Log.d("checkBoxStatus", "No");
            return "No";
        } else {
            Log.d("checkBoxStatus", "Yes");
            return "Yes";
        }

    }

    private String Toxicity() {
        if (smileRating.getRating() == 1 || smileRating.getRating() == 2) {
            Log.d("Toxicity", "Very");
            return "Very";
        } else if (smileRating.getRating() == 4 || smileRating.getRating() == 5) {
            Log.d("Toxicity", "NotAtAll");
            return "NotAtAll";
        } else if (smileRating.getRating() == 3) {
            Log.d("Toxicity", "Somewhat");
            return "Somewhat";
        } else return null;
    }

    void setQuestion() {
        if (questionList.isEmpty()) {


            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();

            //SubmitBtn.setClickable(false);
            //SubmitBtn.setEnabled(false);
            //Toast.makeText(TextRating.this, "left", Toast.LENGTH_SHORT).show();

        } else {
            for (Question qu : questionList) {
                Log.d("stuff", qu.getQuestion());
            }

            tv.setText("$wipes: " + swipes);
            NotEnglishCheckBox.setChecked(false);
            smileRating.setSelected(false);
            theQuestion = questionList.get(0);
            questionList.remove(0);
            scrollViewQuestionText.setVisibility(View.VISIBLE);
            textViewQuestionText.setText(theQuestion.getQuestion());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        tv = new TextView(this);
        tv.setText("Fetching...");
        tv.setTextColor(Color.WHITE);
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(20);
        menu.add(0, 0, 1, "swipes").setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        InputStream inputStream = null;
        String result = "";

        @Override
        protected void onPreExecute() {
            // SHOW THE SPINNER WHILE LOADING FEEDS
            scrollViewQuestionText.setVisibility(View.GONE);
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String newUrl = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_v2_x2000_zhs25/training_questions";
            String oldUrl = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_x2000_zhs25/next10_unanswered_questions";
            String url = newUrl;

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url);

                HttpResponse response = null;
                try {
                    response = httpclient.execute(httpget);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpEntity httpEntity = response.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {

            }
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            //parse JSON data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);
                    JSONObject questionObject = new JSONObject(jObject.getString("question"));

                    String question = questionObject.getString("revision_text");
                    String questionID = questionObject.getString("revision_id");
                    Log.d("hippo", questionID + " --> " + question);
                    questionList.add(new Question(questionID, question));

                    islistInit = true;


                }
                theQuestion = questionList.get(0);
                questionList.remove(0);
                textViewQuestionText.setText(theQuestion.getQuestion());// End Loop
                linlaHeaderProgress.setVisibility(View.GONE);
                scrollViewQuestionText.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }


    }

}
