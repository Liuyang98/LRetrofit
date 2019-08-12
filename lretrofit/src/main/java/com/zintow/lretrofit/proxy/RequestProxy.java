package com.zintow.lretrofit.proxy;


import com.zintow.lretrofit.anno.GET;
import com.zintow.lretrofit.anno.POST;
import com.zintow.lretrofit.anno.Param;
import com.zintow.lretrofit.entity.CallInfo;
import com.zintow.lretrofit.entity.RequestEntity;
import com.zintow.lretrofit.exception.LRetrofitException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestProxy<T> implements InvocationHandler {
    private static final String TAG = "RequestProxy";
    private Map<String, RequestEntity> paramMap;

    public RequestProxy(Class<T> cls) {
        paramMap = new HashMap<>();
        Method[] methods = cls.getMethods();
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
            paramMap.put(method.getName(), new RequestEntity(list, value, type));
        }

        if (paramMap.isEmpty()) {
            throw new LRetrofitException(cls.getName() + " No network request was registered");
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(proxy, args);
        }
        return doCall(proxy, method, args);
    }

    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    private T doCall(Object proxy, Method method, Object[] args) {
        RequestEntity requestEntity = paramMap.get(method.getName());
        if (requestEntity == null) {
            throw new LRetrofitException(method.getName() + " Method illegal");
        }
        return (T) CallInfo.newCall(requestEntity, args);
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
}
