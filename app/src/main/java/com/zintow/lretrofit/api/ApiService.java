package com.zintow.lretrofit.api;

import com.zintow.lretrofit.anno.GET;
import com.zintow.lretrofit.anno.POST;
import com.zintow.lretrofit.anno.Param;

public interface ApiService {
    @GET("https://www.baidu.com")
    void test(@Param("username") String username, @Param("password") String password);

    @POST("https://www.google.com")
    void save(@Param("age") String age);


    @GET("https://www.yahoo.com")
    void search(@Param("page") int page, @Param("type") String type, @Param("search") String search);
}
