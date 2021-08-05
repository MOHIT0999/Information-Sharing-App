package com.example.demo2;


import android.app.Application;

import com.parse.Parse;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("SMMe8SrAeH0AlYhNhfcp5u0mPflcAAwLpRQUqnQn")
                // if defined
                .clientKey("WqjESaJDgCctnpAC1saStRWEtX10pseObXcjXpry")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
