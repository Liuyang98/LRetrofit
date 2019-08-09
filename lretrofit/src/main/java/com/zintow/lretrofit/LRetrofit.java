package com.zintow.lretrofit;

import com.zintow.lretrofit.proxy.RequestProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class LRetrofit {

    public static  <T> T create(Class<T> cls) {
        InvocationHandler handler = new RequestProxy(cls);
        return (T) Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[]{cls}, handler);
    }

    public static class Builder {


        public Builder baseUrl(String baseUrl) {
            return this;
        }

        public LRetrofit build() {
            return new LRetrofit();
        }
    }
}
