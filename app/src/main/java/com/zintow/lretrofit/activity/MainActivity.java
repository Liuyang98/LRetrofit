package com.zintow.lretrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zintow.lretrofit.R;
import com.zintow.lretrofit.api.ApiService;
import com.zintow.lretrofit.LRetrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        findViewById(R.id.btn_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
//                LRetrofit.create(ApiService.class).test("admin", "123456");
//                LRetrofit.create(ApiService.class).save("1234");
            LRetrofit.create(ApiService.class).search(1, "pic", "经典");
                break;
        }
    }
}
