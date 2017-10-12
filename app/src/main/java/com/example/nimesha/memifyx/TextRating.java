package com.example.nimesha.memifyx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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

import static android.R.attr.id;
import static android.R.attr.name;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.example.nimesha.memifyx.R.id.checkBox;
import static com.example.nimesha.memifyx.R.id.username;


public class TextRating extends AppCompatActivity{
    boolean islistInit=false;
    List<Question> questionList = new ArrayList<Question>();
    public static String TAG = "smilies";
    TextView textViewQuestionText;
    Button SubmitBtn;
    LinearLayout linlaHeaderProgress;
    ScrollView scrollViewQuestionText;
    SmileRating smileRating;
    CheckBox NotEnglishCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_text_rating);
        smileRating = (SmileRating) findViewById(R.id.smile_rating);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        scrollViewQuestionText=(ScrollView) findViewById(R.id.scrollViewQuestionText);
        textViewQuestionText = (TextView) findViewById(R.id.textViewQuestionText);
        SubmitBtn = (Button) findViewById(R.id.button4);
        NotEnglishCheckBox = (CheckBox) findViewById (R.id.checkBox);
        setQuestion();
        setProgressBarIndeterminateVisibility(true);
        scrollViewQuestionText.setOnTouchListener(new OnSwipeTouchListener(TextRating.this) {
            public void onSwipeRight() {
                Log.d(TAG,"Right");
                //SubmitBtn.setClickable(true);
                //SubmitBtn.setEnabled(true);
                Toast.makeText(TextRating.this, "please swipe left to skip this question", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Log.d(TAG,"Left");

                setQuestion();
            }

        });



        NotEnglishCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    smileRating.setSelectedSmile(BaseRating.NONE,false);
                    smileRating.setEnabled(false);
                    smileRating.setVisibility(View.INVISIBLE);
                    SubmitBtn.setClickable(true);
                    SubmitBtn.setEnabled(true);
                }
                else {
                    smileRating.setEnabled(true);
                    smileRating.setVisibility(View.VISIBLE);

                    if (smileRating.getRating()>0){
                        SubmitBtn.setClickable(true);
                        SubmitBtn.setEnabled(true);}
                    else{
                        SubmitBtn.setClickable(false);
                        SubmitBtn.setEnabled(false);}
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
                        try {
                            if(NotEnglishCheckBox.isChecked()){
                                JSONObject readility = new JSONObject();
                                readility.put("enumAnswer", checkBoxStatus());

                                JSONObject ToxicityLevel = new JSONObject();
                                ToxicityLevel.put("enumAnswer", "");

                                JSONObject JsonObscene = new JSONObject();
                                JsonObscene.put("enumAnswer", "");

                                JSONObject JsonIdentityHate = new JSONObject();
                                JsonIdentityHate.put("enumAnswer", "");

                                JSONObject JsonInsult = new JSONObject();
                                JsonInsult.put("enumAnswer", "");

                                JSONObject JsonThreat = new JSONObject();
                                JsonThreat.put("enumAnswer", "");

                                JSONObject theAnswer = new JSONObject();

                                theAnswer.put("obscene",JsonObscene);
                                theAnswer.put("identityHate",JsonIdentityHate);
                                theAnswer.put("insult",JsonInsult);
                                theAnswer.put("threat", JsonThreat);

                                JSONObject finalAnswer = new JSONObject();
                                finalAnswer.put("answer",theAnswer);
                                Log.d("finalAnswer",finalAnswer.getString("answer"));
                            }
                            else {
                                JSONObject readility = new JSONObject();
                                readility.put("enumAnswer", checkBoxStatus());

                                JSONObject ToxicityLevel = new JSONObject();
                                ToxicityLevel.put("enumAnswer", Toxicity());

                                JSONObject theAnswer = new JSONObject();

                                theAnswer.put("readableAndInEnglish", readility);
                                theAnswer.put("toxic", ToxicityLevel);

                                Intent intent = new Intent(TextRating.this, typeButtonsActivity.class);
                                intent.putExtra("theAnswer", theAnswer.toString());
                                startActivity(intent);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(TextRating.this, "exception while building answer JSON", Toast.LENGTH_SHORT).show();

                        }


                    }
                }
        );
    }

    private String checkBoxStatus(){
        if(NotEnglishCheckBox.isChecked()){
            Log.d("checkBoxStatus","Yes");
            return "Yes";
        }
        else{
            Log.d("checkBoxStatus","No");
            return "No";
        }

    }

    private String Toxicity(){
        if(smileRating.getRating()==1 ||smileRating.getRating()==2){
            Log.d("checkBoxStatus","Very");
            return "Very";
        }
        else if(smileRating.getRating()==4 ||smileRating.getRating()==5){
            Log.d("checkBoxStatus","NotAtAll");
            return "NotAtAll";
        }

        else if (smileRating.getRating()==3){
            Log.d("checkBoxStatus","Somewhat");
            return "Somewhat";
        }
        else return null;
    }




    private class AsyncTaskRunner extends AsyncTask<String,Void, String> {

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
            String url = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_x2000_zhs25/next10_unanswered_questions";

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget= new HttpGet(url);

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
            //Log.d("testing123",result);


            //parse JSON data
            try {
                JSONArray jArray = new JSONArray(result);
                for(int i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);
                    JSONObject questionObject = new JSONObject(jObject.getString("question"));

                    String question = questionObject.getString("revision_text");
                    String questionID = questionObject.getString("revision_id");
                    Log.d("hippo",questionID+" --> "+question);
                    questionList.add(new Question(questionID,question));

                    islistInit=true;


                }
                Question theQuestion = questionList.get(0);
                questionList.remove(0);
                textViewQuestionText.setText(theQuestion.getQuestion());// End Loop
                linlaHeaderProgress.setVisibility(View.GONE);
                scrollViewQuestionText.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }



    }

    void setQuestion() {
        if (questionList.isEmpty()) {


            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();

            //SubmitBtn.setClickable(false);
            //SubmitBtn.setEnabled(false);
            //Toast.makeText(TextRating.this, "left", Toast.LENGTH_SHORT).show();

        }
        else {
            Question theQuestion = questionList.get(0);
            questionList.remove(0);
            textViewQuestionText.setText(theQuestion.getQuestion());
        }
    }

}
