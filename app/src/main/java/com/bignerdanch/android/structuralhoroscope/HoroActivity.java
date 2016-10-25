package com.bignerdanch.android.structuralhoroscope;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HoroActivity extends AppCompatActivity {

    Button butAcept;
    TextView birthDay;
    TextView infoZodiak, znakZodiak;
    int[][] zodiakYears = new int[12][11];

    String[] whatIsMean;

    private final int DATE_DIALOG_ID = 0;
    private int mDay, mMonth, mYear;
    String[] zodiakHoroscope = new String[]{"козерог", "водолей", "рыбы", "овен", "телец", "близнецы",
            "рак", "лев", "дева", "весы", "скорпион", "стрелец"};

    String[] zodiakChinaHoro = new String[]{"крыса", "бык", "тигр", "заяц", "дракон", "змея", "лошадь",
            "овца", "обезьяна", "петух", "собака", "свинья"};

    int[][] zodiakMean = new int[][]{
            {4, 3, 6, 1, 6, 3, 4, 5, 2, 6, 2, 7},// крыса
            {5, 7, 3, 6, 1, 6, 3, 4, 5, 2, 4, 2},// бык
            {2, 5, 4, 3, 6, 1, 6, 3, 4, 5, 7, 4},// тигр
            {7, 2, 5, 4, 3, 6, 1, 6, 3, 4, 5, 2},// заяц
            {2, 4, 2, 5, 4, 3, 6, 1, 6, 3, 4, 5},// дракон
            {5, 2, 4, 2, 5, 4, 3, 6, 1, 6, 7, 7},// змея
            {4, 5, 7, 7, 2, 5, 4, 3, 6, 1, 6, 3},// лошадь
            {3, 4, 5, 2, 4, 2, 5, 4, 3, 6, 1, 6},// овца
            {7, 3, 4, 7, 2, 4, 2, 5, 7, 3, 6, 1},// обезьяна
            {1, 7, 3, 4, 5, 2, 7, 2, 5, 4, 3, 6},// петух
            {7, 1, 6, 3, 7, 5, 2, 4, 2, 5, 4, 3},// собака
            {3, 6, 1, 6, 3, 4, 5, 7, 4, 7, 5, 4} // свинья
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horo);

        butAcept = (Button) findViewById(R.id.butacept);
        birthDay = (TextView) findViewById(R.id.birthdate);
        infoZodiak = (TextView) findViewById(R.id.textinfozodiak);
        znakZodiak = (TextView) findViewById(R.id.textznakzodiak);
        whatIsMean = getResources().getStringArray(R.array.means);

//add years to zodiakYears
        for (int i = 0; i < 12; i++) {
            int year = 1900 + i;
            for (int j = 0; j < 11; j++) {
                zodiakYears[i][j] = year;
                year += 12;
                System.out.print(zodiakYears[i][j] + " ");
            }
            System.out.println();
        }
//add current date to TextViwe
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        updateDisplay();

//add Date Picker Dialog
        butAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        if (isCurrentDate(mYear)) {
                            updateDisplay();
                            showInfo(mYear, mMonth, mDay);
                        }
                        else inccorectDate();
                    }
                }, mYear, mMonth, mDay);
        }
        return null;
    }

    public boolean isCurrentDate(int mYear) {
        if (mYear <= 2031 && mYear >= 1900) return true;

        return false;
    }

    public void showInfo(int mYear, int mMonth, int mDay){
        int IntchinaHoroscope = chinaHoro(mYear);
        int IntzodiakHoroscope = zodiakHoro(mDay, mMonth);
        infoZodiak.setText("Знак зодиака: " + zodiakHoroscope[IntzodiakHoroscope] + "\nЗнак по китайскому гороскопу: " +
        zodiakChinaHoro[IntchinaHoroscope]);
        znakZodiak.setText(whatIsMean[zodiakMean[IntchinaHoroscope][IntzodiakHoroscope] - 1]);
    }

    public void inccorectDate(){
        birthDay.setText("Введите дату");
        infoZodiak.setText("");
        znakZodiak.setText("");
    }

    public int chinaHoro(int year) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 11; j++) {

                if (year == zodiakYears[i][j]) return i;
            }
        }
        return -1;
    }

    public int zodiakHoro(int day, int month) {
        String StrDayMonth = String.format("%d.%02d", month + 1, day);
//                (month + 1) + "." + day;
        double DoubDayMonth = Double.parseDouble(StrDayMonth);
        System.out.println(DoubDayMonth);

        if (DoubDayMonth >= 3.21 && DoubDayMonth <= 4.19) return 3;
        else if (DoubDayMonth >= 4.20 && DoubDayMonth <= 5.20) return 4;
        else if (DoubDayMonth >= 5.21 && DoubDayMonth <= 6.21) return 5;
        else if (DoubDayMonth >= 6.22 && DoubDayMonth <= 7.22) return 6;
        else if (DoubDayMonth >= 7.23 && DoubDayMonth <= 8.22) return 7;
        else if (DoubDayMonth >= 8.23 && DoubDayMonth <= 9.22) return 8;
        else if (DoubDayMonth >= 9.23 && DoubDayMonth <= 10.22) return 9;
        else if (DoubDayMonth >= 10.23 && DoubDayMonth <= 11.22) return 10;
        else if (DoubDayMonth >= 11.23 && DoubDayMonth <= 12.21) return 11;
        else if ((DoubDayMonth >= 12.22 && DoubDayMonth <= 12.31) ||
                (DoubDayMonth >= 1.1 && DoubDayMonth <= 1.19)) return 0;
        else if (DoubDayMonth >= 1.20 && DoubDayMonth <= 2.18) return 1;
        else if (DoubDayMonth >= 2.19 && DoubDayMonth <= 3.20) return 2;

        return -1;
    }

    public void updateDisplay() {
        birthDay.setText(String.format("%02d.%02d.%04d", mDay, mMonth + 1, mYear));
    }
}
