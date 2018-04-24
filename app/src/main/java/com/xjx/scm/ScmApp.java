package com.xjx.scm;

import android.app.Application;

import timber.log.Timber;


public class ScmApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        AppModule.init(this);
    }
}
