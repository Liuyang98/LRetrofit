package com.zintow.lretrofit;

import com.zintow.lretrofit.anno.GET;
import com.zintow.lretrofit.anno.POST;
import com.zintow.lretrofit.anno.Param;
import com.zintow.lretrofit.entity.CallInfo;
import com.zintow.lretrofit.entity.RequestMethodInfo;
import com.zintow.lretrofit.exception.LRetrofitException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRetrofit {
    private final String baseUrl;
    private Map<String, RequestMethodInfo> serviceMethodCache;

    private LRetrofit(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T create(final Class<T> service) {
        eagerlyValidateMethods(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(proxy, args);
                }
                RequestMethodInfo requestEntity = serviceMethodCache.get(method.getName());
                if (requestEntity == null) {
                    throw new LRetrofitException(method.getName() + " Method illegal");
                }
                return CallInfo.newCall(baseUrl, requestEntity, args);
            }
        });
    }

    private void eagerlyValidateMethods(Class service) {
        if (serviceMethodCache == null) {
            serviceMethodCache = new HashMap<>();
            Method[] methods = service.getMethods();
            for (Method method : methods) {
                List<String> list = getMethodParameterNamesByAnnotation(method);
                String type = null;
                String value = null;

                Annotation annotation = method.getAnnotations()[0];
                if (annotation instanceof GET) {
                    value = ((GET) annotation).value();
                    type = "GET";
                } else if (annotation instanceof POST) {
                    value = ((POST) annotation).value();
                    type = "POST";
                }
                serviceMethodCache.put(method.getName(), new RequestMethodInfo(list, value, type));
            }
        }
        if (serviceMethodCache.isEmpty()) {
            throw new LRetrofitException(service.getName() + " No network request was registered");
        }
    }

    /**
     * 获取指定方法的参数名
     *
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表
     */
    private static List<String> getMethodParameterNamesByAnnotation(Method method) {
        List<String> list = new ArrayList<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations.length == 0) {

            return list;
        }
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof Param) {
                    Param param = (Param) annotation;
                    list.add(param.value());
                }
            }
        }
        return list;
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
