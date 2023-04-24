package com.example.netdemo.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.netdemo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JavaBean loginService = retrofit.create(JavaBean.class);
        retrofit2.Call<Loginbean> ticket = loginService.getTicket("20011212", "111111");
        ticket.enqueue(new Callback<Loginbean>() {
            @Override
            public void onResponse(Call<Loginbean> call, Response<Loginbean> response) {
                Loginbean body = response.body();
                String s = body.toString();
                Log.d("TAG", "onResponse: "+body);

                Log.d("TAG", body.getData().getNickname());
                Log.d("TAG", body.getData().getPassword());

            }

            @Override
            public void onFailure(Call<Loginbean> call, Throwable t) {

            }
        });


    }
}