package com.ousy.testondraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private TestImage mTestImage;
    private TestViewImage mTestViewImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTestImage = findViewById(R.id.test);
        mTestViewImage = findViewById(R.id.testViewImage);

        mTestImage.setImageResource(R.drawable.doctor_report_a_head_bg);

        mTestViewImage.setTvMessage();
        Log.e("ousyxx", "settext");
    }
}
