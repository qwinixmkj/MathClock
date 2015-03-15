package com.qwinix.mathclock;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
     //   Date date = new Date(selectedYear, selectedMonth, selectedDay-1);
        final Calendar c = Calendar.getInstance();
        c.set(year,month,day);

        Date date = c.getTime();
        String dayOfWeek = simpledateformat.format(date);
        GlobalVariables.getInstance();
        GlobalVariables.dayOfWeek = dayOfWeek;
        GlobalVariables.day = day;
    }
}