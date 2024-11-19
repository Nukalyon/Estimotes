package com.estimote.configuration;

import android.app.Application;
import android.util.Log;

import com.estimote.sdk.EstimoteSDK;

public class ConfigurationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "<#App ID#>", "<#App Token#>");
        EstimoteSDK.enableDebugLogging(false);
    }
}
