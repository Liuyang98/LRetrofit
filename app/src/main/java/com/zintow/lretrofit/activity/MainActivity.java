package com.zintow.lretrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zintow.lretrofit.R;
import com.zintow.lretrofit.api.ApiService;
import com.zintow.lretrofit.LRetrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        ApiService apiService = new LRetrofit().create(ApiService.class);
//        apiService.test("admin", "123456");
//        apiService.save("1234");
        apiService.search(1,"pic","经典");
    }
}
