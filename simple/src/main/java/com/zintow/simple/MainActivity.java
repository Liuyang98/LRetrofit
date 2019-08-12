package com.zintow.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zintow.lretrofit.LRetrofit;
import com.zintow.lretrofit.entity.CallInfo;
import com.zintow.simple.api.ApiService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private LRetrofit lRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        lRetrofit = new LRetrofit.Builder()
                .baseUrl("http://192.168.1.111:8080")
                .build();

        findViewById(R.id.btn_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                CallInfo callInfo = lRetrofit.create(ApiService.class).search(1, "pic", "经典");
                Log.e(TAG, "onClick: " + callInfo);
                break;
        }
    }
}
