package com.codeian.wrap;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputFilter;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Helper {

    Random random;

    protected String generateUniqueChar(int len){
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        random = new Random();
        String re = "";

        while (re.length() < len) {
            re += chars.charAt(random.nextInt(chars.length() - 1));
        }
        return re;
    }

    protected String generateDigit(int len){
        random = new Random();
        String re = "";

        while (re.length() < len){
            re += random.nextInt(10);
        }
        return re;

    }

    public String dateTimeString(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        formatter.setTimeZone(TimeZone.getTimeZone("Africa/Khartoum"));

        Date date = new Date();
        String formattedDate = formatter.format(date);

        return formattedDate;
    }

}
