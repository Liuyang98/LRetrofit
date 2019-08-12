package com.zintow.simple.api;

import com.zintow.lretrofit.anno.GET;
import com.zintow.lretrofit.anno.POST;
import com.zintow.lretrofit.anno.Param;
import com.zintow.lretrofit.entity.CallInfo;
import com.zintow.lretrofit.entity.RequestEntity;

public interface ApiService {
    @GET("https://www.baidu.com")
    RequestEntity test(@Param("username") String username, @Param("password") String password);

    @POST("https://www.google.com")
    RequestEntity save(@Param("age") String age);

    @GET("https://www.yahoo.com")
    CallInfo search(@Param("page") int page, @Param("type") String type, @Param("search") String search);
}
