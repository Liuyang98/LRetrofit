package com.zintow.lretrofit;

import com.zintow.lretrofit.proxy.RequestProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class LRetrofit {
    private static final String TAG = "LRetrofit";

    public <T> T create(Class<T> cls) {
        // 我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new RequestProxy(cls);
        return (T) Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[]{cls}, handler);
    }
}
