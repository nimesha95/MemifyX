package com.example.nimesha.memifyx;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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
                //SubmitBtn.setClickable(false);
                //SubmitBtn.setEnabled(false);
                Toast.makeText(TextRating.this, "left", Toast.LENGTH_SHORT).show();
            }

        });

        SubmitBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute();
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

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String url = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/wp_x2000_zhs25/to_answer_questions";
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
                Log.d("Server response", server_response );
            } else {
                Log.d("Server response", "Failed to get server response" );
            }
            return "xyz";
        }


        @Override
        protected void onPostExecute(String result) {

        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }


}
