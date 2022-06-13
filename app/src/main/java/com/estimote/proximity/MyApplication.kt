package com.estimote.proximity

import android.app.Application
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials

//
// Running into any issues? Drop us an email to: contact@estimote.com
//
//  Login
//  mattys.gervais1@uqac.ca
//  uqacisthebest2022@

class MyApplication : Application() {
    val cloudCredentials =  EstimoteCloudCredentials("mattys-gervais1-uqac-ca-s--033", "c71b4c5ef5517e9afe7db1f86eaf0b7c")
}
