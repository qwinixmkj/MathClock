package com.qwinix.mathclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class SplashActivity extends Activity {

    private boolean isConnectedNetwork;
    private AlertDialog dialog;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        setContentView(R.layout.activity_splash);
        new CheckTime1().execute();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CheckTime1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {




            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
			/*Intent intent2 = new Intent(SplashScreen.this, JournalLogActivity.class);
			startActivity(intent2);
			finish();*/



                Intent intent2 = new Intent(SplashActivity.this, ClockActivity.class);
                startActivity(intent2);
                finish();

            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);

        }

    }

}