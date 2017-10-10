package com.example.nimesha.memifyx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import static android.R.attr.id;
import static android.R.attr.name;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.example.nimesha.memifyx.R.id.username;


public class TextRating extends AppCompatActivity{

    public static String TAG = "smilies";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_rating);

        final TextView txt = (TextView) findViewById(R.id.textView3);
        final Button SubmitBtn = (Button) findViewById(R.id.button4);
        final CheckBox NotEnglishCheckBox = (CheckBox) findViewById (R.id.checkBox);

        txt.setOnTouchListener(new OnSwipeTouchListener(TextRating.this) {
            public void onSwipeRight() {
                Log.d(TAG,"Right");
                //SubmitBtn.setClickable(true);
                //SubmitBtn.setEnabled(true);
                Toast.makeText(TextRating.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Log.d(TAG,"Left");

                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();

                //SubmitBtn.setClickable(false);
                //SubmitBtn.setEnabled(false);
                Toast.makeText(TextRating.this, "left", Toast.LENGTH_SHORT).show();
            }

        });

        SubmitBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent inten = new Intent(TextRating.this,button_grp.class);
                        startActivity(inten);
                    }
                }
        );

        NotEnglishCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    SubmitBtn.setClickable(true);
                    SubmitBtn.setEnabled(true);
                }
                else {
                    //case 2
                    SubmitBtn.setClickable(false);
                    SubmitBtn.setEnabled(false);
                }
            }
        });

        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
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

    }

    private class AsyncTaskRunner extends AsyncTask<String,Void, String> {

        InputStream inputStream = null;
        String result = "";

        @Override
        protected String doInBackground(String... params) {
            String url = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_x2000_zhs25/next10_unanswered_questions";
/*
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget= new HttpGet(url);

            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(response.getStatusLine().getStatusCode()==200){
                String server_response = null;
                try {
                    server_response = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("api_response", server_response );
            } else {
                Log.d("api_response", "Failed to get server response" );
            }
            return "xyz";   //return some dummy value --- temporary
            */

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

                    String question_id = jObject.getString("question_id");
                    String question = jObject.getString("question");
                    Log.d("hippo",question_id+" --> "+question);
                } // End Loop
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }


        @Override
        protected void onPreExecute() {

        }
    }


}
