package com.zintow.lretrofit;

import com.zintow.lretrofit.proxy.RequestProxy;

import java.lang.reflect.Proxy;

public class LRetrofit {
    private String baseUrl;

    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new RequestProxy(service));
    }

    public LRetrofit(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static class Builder {
        private String httpUrl;


        public Builder baseUrl(String httpUrl) {
            this.httpUrl = httpUrl;
            return this;
        }

        public LRetrofit build() {
            return new LRetrofit(httpUrl);
        }
    }
}
