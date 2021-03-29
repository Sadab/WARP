package com.codeian.wrap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Network {
    Helper helper = new Helper();
    public static final String deviceId =  "deviceId";
    public static final String interval =  "interval";
    public static final String target =  "target";
    String id,delay,amount;

    public void getHttpResponse() throws IOException {

        String rand_num = helper.generateDigit(3);
        String install_ID = helper.generateUniqueChar(22);
        String fcm_token = helper.generateUniqueChar(134);
        String rand_key = helper.generateUniqueChar(43);

        String url = "https://api.cloudflareclient.com/v0a"+rand_num+"/reg";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Host", "api.cloudflareclient.com")
                .header("Connection", "Keep-Alive")
                .header("Accept-Encoding", "gzip")
                .header("User-Agent", "okhttp/3.12.1")
                .build();

//        Response response = client.newCall(request).execute();
//        Log.e(TAG, response.body().string());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                Log.e("TAG", mMessage);
            }
        });
    }

    public void postRequest(String id) throws IOException {

        String rand_num = helper.generateDigit(3);
        String install_ID = helper.generateUniqueChar(22);
        String fcm_token = helper.generateUniqueChar(134);
        String rand_key = helper.generateUniqueChar(43)+"=";

        String url = "https://api.cloudflareclient.com/v0a"+rand_num+"/reg";

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("key", rand_key);
            postdata.put("install_id", install_ID);
            postdata.put("fcm_token", install_ID+":APA91b"+fcm_token);
            postdata.put("referrer", id);
            postdata.put("warp_enabled", false);
            postdata.put("tos", helper.dateTimeString());
            postdata.put("type", "Android");
            postdata.put("locale", "es_ES");
        } catch(JSONException e){
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

//        System.out.println(url);
//
//        byte ptext[] = postdata.toString().getBytes("ISO-8859-1");
//        String value = new String(ptext, "UTF-8");
//        System.out.println(value);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Host", "api.cloudflareclient.com")
                .header("Connection", "Keep-Alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("User-Agent", "okhttp/3.12.1")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int mMessage = response.code();
                Log.e("TAG", String.valueOf(mMessage));

//                String jsonData = response.body().string();
//                try {
//                    JSONObject Jobject = new JSONObject(jsonData);
//                    System.out.print(Jobject.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }
}
