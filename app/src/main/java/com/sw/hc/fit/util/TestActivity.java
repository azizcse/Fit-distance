package com.sw.hc.fit.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.sw.hc.fit.activities.MainActivity;

public abstract class TestActivity extends AppCompatActivity {
    private GoogleApiClient client;
    private String LOG_TAG = "TestActivity";
    public void init() {
        client = new GoogleApiClient.Builder(this)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(@Nullable Bundle bundle) {
                                Log.d(LOG_TAG, "buildFitnessClient connected");
                                // Now you can make calls to the Fitness APIs
                                // the HomeFragment will call the "subscribeDailySteps()"
                            }
                            @Override
                            public void onConnectionSuspended(int i) {

                                Log.i(LOG_TAG, "buildFitnessClient connection suspended");
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.w(LOG_TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.w(LOG_TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                ).enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.e(LOG_TAG, "Google Play services failed. Cause: " + connectionResult.toString());
                    }
                })
                .build();
    }
}
