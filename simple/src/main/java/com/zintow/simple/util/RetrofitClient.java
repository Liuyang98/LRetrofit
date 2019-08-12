package com.zintow.simple.util;

import android.widget.Toast;

import com.zintow.lretrofit.LRetrofit;
import com.zintow.simple.api.ApiService;

import java.io.IOException;
import java.util.Observable;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrofitClient {


    private static LRetrofit retrofit;
    private static final String BASE_URL = "http://www.recheyouping.com/api/"; // 正式地址
    //    private static final String BASE_URL = "http://192.168.10.134:8799/api/";
//    private static final String BASE_URL = "http://192.168.1.124:8799/api/";
//    private static final String BASE_URL = "http://47.95.227.14:8799/api/";
    private OkHttpClient okHttpClient;

    private static LRetrofit getInstance() {
        if (retrofit != null) {
            return retrofit;
        } else {
            return new RetrofitClient().retrofit;
        }
    }

    private RetrofitClient() {
        initRetrofit();
    }

    private void initRetrofit() {
        try {
            retrofit = new LRetrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(getOkHttpClient())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            initHttpClient();
        }
        return okHttpClient;
    }

    private void initHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //DEBUG模式下 添加日志拦截器
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request mRequest = chain.request().newBuilder()
                        .header("App-Store", "google")
                        .build();
                return chain.proceed(mRequest);
            }
        });
        okHttpClient = builder.build();
    }

}
