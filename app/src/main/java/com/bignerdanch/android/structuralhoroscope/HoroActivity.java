package com.bignerdanch.android.structuralhoroscope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HoroActivity extends AppCompatActivity {

    Button butAcept;
    EditText birthDay;
    TextView infoZodiak, znakZodiak;
    int[][] zodiakYears = new int[12][11];
    Date dateOfBirthday;
    int[] dateParseInt;
    String[] whatIsMean;
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
        birthDay = (EditText) findViewById(R.id.birthdate);
        infoZodiak = (TextView) findViewById(R.id.textinfozodiak);
        znakZodiak = (TextView) findViewById(R.id.textznakzodiak);
        whatIsMean = getResources().getStringArray(R.array.means);

        for (int i = 0; i < 12; i++) {
            int year = 1900 + i;
            for (int j = 0; j < 11; j++) {
                zodiakYears[i][j] = year;
                year += 12;
                System.out.print(zodiakYears[i][j] + " ");
            }
            System.out.println();
        }

        butAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String birthday = birthDay.getText().toString();
                boolean isCurrentDate = isCurrentDate(birthday);

                if (isCurrentDate) {
                    infoZodiak.setText("Дата рождения:\n" + dateOfBirthday);
                    int chinaHoroPos = chinaHoro(dateParseInt[2]);
                    int zodiakHoroPos = zodiakHoro(dateParseInt[0], dateParseInt[1]);
                    infoZodiak.setText(infoZodiak.getText() + "\nЗнак зодиака: " +zodiakHoroscope[zodiakHoroPos] + "\nЗнак по китайскому гороскопу: " +
                            zodiakChinaHoro[chinaHoroPos]);
                    znakZodiak.setText(whatIsMean[zodiakMean[chinaHoroPos][zodiakHoroPos] - 1]);

                } else {
                    infoZodiak.setText("Введите верную дату");
                    znakZodiak.setText("");
                }
            }
        });

    }

    public boolean isCurrentDate(String date) {
        if (date.matches("\\d{2}[.]{1}\\d{2}[.]{1}\\d{4}")) {
            String[] dates = date.split("\\.");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

            try {
                dateOfBirthday = dateFormat.parse(date);
            } catch (ParseException e) {
                System.out.println("Exception date");
            }

            int day = Integer.parseInt(dates[0]);
            int months = Integer.parseInt(dates[1]);
            int year = Integer.parseInt(dates[2]);
            dateParseInt = new int[]{day, months, year};

//            System.out.println(day + " " + months + " " + year);

            if ((day > 0 && day <= 31) && (months > 0 && months <= 12) && (year >= 1900 && year <= 2031)) {
                return true;
            }
        }
        return false;
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
        String StrDayMonth = month + "." + day;
        double DoubDayMonth = Double.parseDouble(StrDayMonth);

        if (DoubDayMonth >= 3.21 && DoubDayMonth <= 4.19) return 3;
        else
        if (DoubDayMonth >= 4.20 && DoubDayMonth <= 5.20) return 4;
        else
        if (DoubDayMonth >= 5.21 && DoubDayMonth <= 6.21) return 5;
        else
        if (DoubDayMonth >= 6.22 && DoubDayMonth <= 7.22) return 6;
        else
        if (DoubDayMonth >= 7.23 && DoubDayMonth <= 8.22) return 7;
        else
        if (DoubDayMonth >= 8.23 && DoubDayMonth <= 9.22) return 8;
        else
        if (DoubDayMonth >= 9.23 && DoubDayMonth <= 10.22) return 9;
        else
        if (DoubDayMonth >= 10.23 && DoubDayMonth <= 11.22) return 10;
        else
        if (DoubDayMonth >= 11.23 && DoubDayMonth <= 12.21) return 11;
        else
        if ((DoubDayMonth >= 12.22 && DoubDayMonth <= 12.31) ||
                (DoubDayMonth >= 1.1 && DoubDayMonth <= 1.19)) return 0;
        else
        if (DoubDayMonth >= 1.20 && DoubDayMonth <= 2.18) return 1;
        else
        if (DoubDayMonth >= 2.19 && DoubDayMonth <= 3.20) return 2;

        return -1;
    }
}
