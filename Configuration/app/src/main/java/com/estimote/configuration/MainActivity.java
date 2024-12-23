package com.estimote.configuration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.EstimoteCloud;
import com.estimote.sdk.connection.scanner.ConfigurableDevice;
import com.estimote.sdk.connection.scanner.ConfigurableDevicesScanner;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.ExclusionStrategy;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_SCAN_RESULT_ITEM_DEVICE = "com.estimote.configuration.SCAN_RESULT_ITEM_DEVICE";
    public static final Integer RSSI_THRESHOLD = -50;

    private ConfigurableDevicesScanner devicesScanner;

    private TextView devicesCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        devicesCountTextView = (TextView) findViewById(R.id.devices_count);

        devicesScanner = new ConfigurableDevicesScanner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            devicesScanner.scanForDevices(new ConfigurableDevicesScanner.ScannerCallback() {
                @Override
                public void onDevicesFound(List<ConfigurableDevicesScanner.ScanResultItem> list) {
                    devicesCountTextView.setText(getString(R.string.detected_devices) + ": " + String.valueOf(list.size()));

                    if (!list.isEmpty()) {
                        ConfigurableDevicesScanner.ScanResultItem item = list.get(0);
                        if (item.rssi > RSSI_THRESHOLD) {
                            devicesScanner.stopScanning();

                            Intent intent = new Intent(MainActivity.this, ConfigureBeaconActivity.class);
                            intent.putExtra(EXTRA_SCAN_RESULT_ITEM_DEVICE, item.device);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        devicesScanner.stopScanning();
    }
}
