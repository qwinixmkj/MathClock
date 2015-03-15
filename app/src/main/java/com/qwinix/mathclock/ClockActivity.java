package com.qwinix.mathclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Date;


public class ClockActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "BroadcastTest";
    private Intent intent;
    private TextView mTimeView, mAlarmTime;
    private RelativeLayout mContainer;
    private int fontColor, backgroundColor;
    private LinearLayout mAlarmContainer;
    private RadioButton mBinary, mHex, mDecimal, mRoman, mOctal;
    private int mode =1;
    SharedPreferences mpref;
    private Button mCancel;
    private OneShotAlarm mOneAlarm;
    private int tmp=1;
    private boolean isSecEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mpref = getSharedPreferences("mathclock",MODE_PRIVATE);
        mTimeView = (TextView) findViewById(R.id.timeview);
        mAlarmTime = (TextView) findViewById(R.id.alarmview);
     //   mCancel = (Button) findViewById(R.id.cancelalarm);
        mAlarmContainer = (LinearLayout) findViewById(R.id.alarmcontainer);
        mContainer = (RelativeLayout) findViewById(R.id.clockcontainer);
        mBinary = (RadioButton) findViewById(R.id.binary);
        mHex = (RadioButton) findViewById(R.id.hex);
      //  mDecimal = (RadioButton) findViewById(R.id.decimal);
        mRoman = (RadioButton) findViewById(R.id.roman);
        mOctal = (RadioButton) findViewById(R.id.octal);

        intent = new Intent(this, TimeService.class);
        mContainer.setOnClickListener(this);
        if (savedInstanceState != null) {
            mode = savedInstanceState.getInt("mode");
        }
/*        mCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Settings.getAppContext(), RepeatingAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(Settings.getAppContext(),
                        0, intent, 0);
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);

                Intent intent1 = new Intent(Settings.getAppContext(), OneShotAlarm.class);
                PendingIntent sender1 = PendingIntent.getBroadcast(Settings.getAppContext(),
                        0, intent1, 0);
                am.cancel(sender1);

                Toast.makeText(ClockActivity.this, "Alarm has been cancelled",
                        Toast.LENGTH_LONG).show();
                mpref.edit().putString("alarmtime","").commit();
            }
        });*/

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
          //  new ColorPickerDialog(this, this, 0xFF000000, 1 ).show();
            Intent intent = new Intent(ClockActivity.this,Settings.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    private BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            alarmReceived();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        if (mpref.contains("clockmode")) {
            mode = mpref.getInt("clockmode", 0);


        }
        if (mpref.contains("alarmtime")) {
            if (mpref.getString("alarmtime","").length() > 0) {
                mAlarmContainer.setVisibility(View.VISIBLE);
                mAlarmTime.setText(mpref.getString("alarmtime",""));
            }
            else
                mAlarmContainer.setVisibility(View.GONE);


        }
        if (mpref.contains("fontcolor")) {
            fontColor = mpref.getInt("fontcolor",0xFF000000);


            mTimeView.setTextColor(fontColor);
            mAlarmTime.setTextColor(fontColor);
           /* mBinary.setTextColor(fontColor);
            mHex.setTextColor(fontColor);
        //    mDecimal.setTextColor(fontColor);
            mRoman.setTextColor(fontColor);
            mOctal.setTextColor(fontColor);*/
        }
        if (mpref.contains("background")) {
            backgroundColor = mpref.getInt("background",0xFF000000);
            mContainer.setBackgroundColor(backgroundColor);


        }
        if (mpref.contains("hassecondfield")) {
            isSecEnabled = mpref.getBoolean("hassecondfield",true);
        }

        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(TimeService.BROADCAST_ACTION));
        registerReceiver(broadcastReceiver1, new IntentFilter(OneShotAlarm.BROADCAST_ACTION_SINGLE));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiver1);
        stopService(intent);
    }

    private void updateUI(Intent intent) {
       /* String time = intent.getStringExtra("time");
        Log.d(TAG, time);*/
        Date now = new Date();
        String nowTime;
        switch (mode){
            case 1:
                if (isSecEnabled) {
                    nowTime = String.format("%tk:%tM:%tS %Tp", now, now, now,now);
                }else
                nowTime = String.format("%tk:%tM %Tp", now, now, now);
                mTimeView.setText(nowTime);
                mTimeView.setTextSize(40);
               /* mOctal.setChecked(false);
                mHex.setChecked(false);
                mRoman.setChecked(false);
                mBinary.setChecked(false);*/
                break;
            case 4:
                int h = Integer.parseInt(String.format("%tk", now));
                int m = Integer.parseInt(String.format("%tM", now));
                int s = Integer.parseInt(String.format("%tS", now));
                String amOrPM = String.format("%Tp", now);
                String Romanh = getRomanNumeral(h);
                String Romanm = getRomanNumeral(m);
                String Romans = getRomanNumeral(s);
                if (isSecEnabled) {
                    nowTime = (Romanh + ":" + Romanm + ":" + Romans+" "+amOrPM);
                }else
                nowTime = (Romanh + ":" + Romanm + " "+amOrPM);
                mTimeView.setText(nowTime);
                mTimeView.setTextSize(34);
                break;
            case 5:
                int h3 = Integer.parseInt(String.format("%tk", now));
                int m3 = Integer.parseInt(String.format("%tM", now));
                int s3 = Integer.parseInt(String.format("%tS", now));
                String amOrPM1 = String.format("%Tp", now);
                String hexNumHr = Integer.toHexString( 0x10000 | h3).substring(1).toUpperCase();
                String hexNumMn = Integer.toHexString( 0x10000 | m3).substring(1).toUpperCase();
                String hexNumSc = Integer.toHexString( 0x10000 | s3).substring(1).toUpperCase();
                if (isSecEnabled) {
                    nowTime = (hexNumHr + ":" + hexNumMn + ":" + hexNumSc+" "+amOrPM1);
                }else
                    nowTime = (hexNumHr + ":" + hexNumMn + " "+amOrPM1);
                mTimeView.setText(nowTime);
                mTimeView.setTextSize(34);
                break;
            case 2:
                int h4 = Integer.parseInt(String.format("%tk", now));
                int m4 = Integer.parseInt(String.format("%tM", now));
                int s4 = Integer.parseInt(String.format("%tS", now));
                String amOrPM2 = String.format("%Tp", now);
                String binNumHr = Integer.toBinaryString(h4);
                String binNumMn = Integer.toBinaryString(m4);
                String binNumSc = Integer.toBinaryString(s4);
                if (isSecEnabled) {
                    nowTime = (binNumHr + ":" + binNumMn + ":" + binNumSc+" "+amOrPM2);
                }else

                nowTime = (binNumHr + ":" + binNumMn + " "+amOrPM2);
                mTimeView.setText(nowTime);
                mTimeView.setTextSize(34);
                break;
            case 3:
                int h5 = Integer.parseInt(String.format("%tk", now));
                int m5 = Integer.parseInt(String.format("%tM", now));
                int s5 = Integer.parseInt(String.format("%tS", now));
                String amOrPM3 = String.format("%Tp", now);
                String octNumHr = Integer.toOctalString(h5);
                String octNumMn = Integer.toOctalString(m5);
                String octNumSc = Integer.toOctalString(s5);
                if (isSecEnabled) {
                    nowTime = (octNumHr + ":" + octNumMn + ":" + octNumSc+ " "+amOrPM3);
                }else

                nowTime = (octNumHr + ":" + octNumMn + " "+amOrPM3);
                mTimeView.setText(nowTime);
                mTimeView.setTextSize(34);
                break;
        }


    }

    /*@Override
    public void colorChanged(int color, int type) {
        if (type == 1) {
            mContainer.setBackgroundColor(color);
        }else
        mTimeView.setTextColor(color);
    }*/

    public void romanClicked(View view) {
        mOctal.setChecked(false);
        mHex.setChecked(false);
       // mDecimal.setChecked(false);
        mBinary.setChecked(false);
        mode = 2;
    }

    public void hexClicked(View view) {
        mOctal.setChecked(false);
        mRoman.setChecked(false);
      //  mDecimal.setChecked(false);
        mBinary.setChecked(false);
        mode = 3;
    }

    public void decimalClicked(View view) {
        mOctal.setChecked(false);
        mHex.setChecked(false);
        mRoman.setChecked(false);
        mBinary.setChecked(false);
        mode = 1;
    }

    public void octalClicked(View view) {
        mRoman.setChecked(false);
        mHex.setChecked(false);
       // mDecimal.setChecked(false);
        mBinary.setChecked(false);
        mode = 5;
    }

    public void binaryClicked(View view) {
        mRoman.setChecked(false);
        mHex.setChecked(false);
       // mDecimal.setChecked(false);
        mRoman.setChecked(false);
        mode = 4;
    }

    public String getRomanNumeral(int num) {
        String RomanX = "";

        DecimalFormat numform = new DecimalFormat("00");

        char digit1 = numform.format(num).charAt(0);
        char digit2 = numform.format(num).charAt(1);


        if(digit1 == '5') {
            RomanX = "L";
        }
        else if(digit1 == '4') {
            RomanX = "IL";
        }
        else if(digit1 == '3') {
            RomanX = "XXX";
        }
        else if(digit1 == '2') {
            RomanX = "XX";
        }
        else if(digit1 == '1') {
            RomanX = "X";
        } else {
            //exit
        }

        if(digit2 == '9') {
            RomanX += "IX";
        }
        else if(digit2 == '8') {
            RomanX += "VIII";
        }
        else if(digit2 == '7') {
            RomanX += "VII";
        }
        else if(digit2 == '6') {
            RomanX += "VI";
        }
        else if(digit2 == '5') {
            RomanX += "V";
        }
        else if(digit2 == '4') {
            RomanX += "IV";
        }
        else if(digit2 == '3') {
            RomanX += "III";
        }
        else if(digit2 == '2') {
            RomanX += "II";
        }
        else if(digit2 == '1') {
            RomanX += "I";
        } else if(digit2 == '0') {
            RomanX += "";
        }

        return RomanX;

    }//end Roman Numerals

    @Override
    public void onClick(View v) {
        if (v.getId() == mContainer.getId()) {
            if (mode != 1) {
                tmp = mode;
                mode = 1;
                mpref.edit().putInt("clockmode",mode).commit();
            }
            else if (mode == 1) {
                mode = tmp;
                mpref.edit().putInt("clockmode",mode).commit();
               /* switch (mode){
                    case 2:
                        mRoman.setChecked(true);
                        break;
                    case 3:
                        mHex.setChecked(true);
                        break;
                    case 4:
                        mBinary.setChecked(true);
                        break;
                    case 5:
                        mOctal.setChecked(true);
                        break;
                }*/
            }
        }
    }


    public void alarmReceived() {
      //  if (i == 1) {
            mAlarmContainer.setVisibility(View.GONE);
      //  }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode",mode);
    }
}
