package com.amanarora.notificationtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class Main2Activity : AppCompatActivity() {
    private val LOG_TAG = Main2Activity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        if (intent != null) {
            val data = intent.getStringExtra(TEST_DATA)
            Log.e(LOG_TAG, "Data $data")
            Toast.makeText(this, "Test: $data", Toast.LENGTH_LONG).show()
        }
    }
}
