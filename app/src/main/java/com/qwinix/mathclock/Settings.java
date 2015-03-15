package com.qwinix.mathclock;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;


public class Settings extends Activity implements View.OnClickListener, ColorPickerDialog.OnColorChangedListener {
    private static int GET_MP3 = 101;
    private Button mChangeBackground, mChangeFontColor, mAlarm, mCancelAlarm, mDateSelect,mTimeSelect,mRingtone;
    SharedPreferences mpref;
    private Switch mSecSwitch, mAmPm;
    boolean isEnabled = true;
    boolean isAM = true;
    private Spinner mClock;
    private CheckBox mRepeat;
    private  boolean isRepeat=false;
    private int day, hour, minute = 00;
    private int[] dayval= new int[]{1,2,3,4,5,6,7};
    private String dayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Settings.context = getApplicationContext();
        mpref = getSharedPreferences("mathclock",MODE_PRIVATE);
        mChangeBackground = (Button) findViewById(R.id.clockcolor);
        mChangeFontColor = (Button) findViewById(R.id.fontcolor);
        mAlarm = (Button) findViewById(R.id.setalarm);
        mSecSwitch = (Switch) findViewById(R.id.togglebutton);
        mDateSelect = (Button) findViewById(R.id.datepick);
        mTimeSelect = (Button) findViewById(R.id.timepick);
        mCancelAlarm = (Button) findViewById(R.id.cancelalarm);
        mRingtone = (Button) findViewById(R.id.ring);
        mClock = (Spinner) findViewById(R.id.spinner1);
        //mMinute = (Spinner) findViewById(R.id.spinner3);
        mRepeat = (CheckBox) findViewById(R.id.repeatalarm);

        mRepeat.setChecked(false);

        mRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isRepeat = true;
                }
                else
                    isRepeat = false;
            }
        });

        mCancelAlarm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Settings.this, RepeatingAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(Settings.getAppContext(),
                        0, intent, 0);
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);

                Intent intent1 = new Intent(Settings.this, OneShotAlarm.class);
                PendingIntent sender1 = PendingIntent.getBroadcast(Settings.getAppContext(),
                        0, intent1, 0);
                am.cancel(sender1);

                Toast.makeText(Settings.this, "Alarm has been cancelled",
                        Toast.LENGTH_LONG).show();
                mpref.edit().putString("alarmtime","").commit();
            }
        });

        ArrayAdapter<String> clockAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.clock_mode));
        mClock.setAdapter(clockAdapter);
        if (mpref.contains("clockmode")) {
            int pos = mpref.getInt("clockmode", 0);
            if (pos >1) {
                mClock.setSelection(pos-1);
            }

        }
        mClock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //adapterView.getAdapter().
                if (i != 0) {

                    mpref.edit().putInt("clockmode",i+1).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       /* ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.week_days));
        mDay.setAdapter(dayAdapter);
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.hours));
        mHour.setAdapter(hourAdapter);
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.minutes));
        mMinute.setAdapter(minuteAdapter);

        mDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //adapterView.getAdapter().
                if (i != 0) {

                    dayName = (String)adapterView.getItemAtPosition(i);
                    day = dayval[i-1];
                    Log.d("mathew","rrrr "+i+" "+dayName+" "+day);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //adapterView.getAdapter().
                if (i != 0) {
                    hour = Integer.parseInt(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //adapterView.getAdapter().
                if (i != 0) {
                    minute = Integer.parseInt(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        mChangeFontColor.setOnClickListener(this);
        mChangeBackground.setOnClickListener(this);
        mRingtone.setOnClickListener(this);
       // mDateSelect.setOnClickListener(this);
      //  mTimeSelect.setOnClickListener(this);
        mAlarm.setOnClickListener(this);
        if (mpref.contains("hassecondfield")) {
            isEnabled = mpref.getBoolean("hassecondfield",true);
            mSecSwitch.setChecked(isEnabled);
        }
        mSecSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // if (b) {
                mpref.edit().putBoolean("hassecondfield",b).commit();
                // }

                //  Log.d("mathew","gggg "+mMeridian);
            }
        });
       /* mAmPm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isAM = true;
                }
                else
                    isAM = false;
            }
        });*/


    }

    private static Context context;

    public static Context getAppContext(){
        return Settings.context;
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
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clockcolor:
                new ColorPickerDialog(this, this, 0xFF000000, 1 ).show();
                overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
                break;
            case R.id.fontcolor:
                new ColorPickerDialog(this, this, 0xFF000000, 2 ).show();
                overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
                break;
            case R.id.ring:
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("audio/*");
                startActivityForResult(i, GET_MP3);
                overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
                break;
            case R.id.setalarm:
                String time;
                GlobalVariables.getInstance();
                if (isRepeat) {

                    Intent intent = new Intent(Settings.this, RepeatingAlarm.class);
                    PendingIntent sender = PendingIntent.getBroadcast(Settings.this,
                            0, intent, 0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    // Set the alarm's trigger time to 8:30 a.m.


                        calendar.set(Calendar.HOUR_OF_DAY, hour);

                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 00);

                    if (GlobalVariables.hour >=12) {
                        time = "Repeat alarm set at " +(GlobalVariables.hour-12)+":"+GlobalVariables.minute+" PM";
                    }else
                        time = "Repeat alarm set at " +GlobalVariables.hour+":"+GlobalVariables.minute+" AM";

                    mpref.edit().putString("alarmtime",time).commit();
                   /* long firstTime = SystemClock.elapsedRealtime();
                    firstTime += 15*1000;*/
                    // Schedule the alarm!
                    AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

                    am.setRepeating(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);

                   /* AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                    am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            firstTime, 15*1000, sender);*/
                }
                else{
                    Intent intent = new Intent(Settings.this, OneShotAlarm.class);
                    PendingIntent sender = PendingIntent.getBroadcast(Settings.this,
                            0, intent, 0);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.DAY_OF_MONTH, GlobalVariables.day);
                    calendar.set(Calendar.HOUR_OF_DAY, GlobalVariables.hour);
                    calendar.set(Calendar.MINUTE, GlobalVariables.minute);
                    calendar.set(Calendar.SECOND, 00);
                    //  calendar.add(Calendar.SECOND, 30);

                    // Schedule the alarm!
                    if (GlobalVariables.hour >=12) {
                        time = "Alarm set on "+GlobalVariables.dayOfWeek+", "+(GlobalVariables.hour-12)+":"+GlobalVariables.minute+" PM";
                    }else
                        time = "Alarm set on "+GlobalVariables.dayOfWeek+", "+GlobalVariables.hour+":"+GlobalVariables.minute+" AM";
                    mpref.edit().putString("alarmtime",time).commit();
                    AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                    }
                    else
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                }
                Toast.makeText(Settings.this, "Alarm has been set",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_MP3) {
                Uri selectedmp3 = data.getData();
                mpref.edit().putString("uri",selectedmp3.toString()).commit();
            }
        }
    }

    @Override
    public void colorChanged(int color, int type) {
        if (type == 1) {
            mpref.edit().putInt("background",color).commit();
        }else if (type == 2) {
            mpref.edit().putInt("fontcolor",color).commit();
        }
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
