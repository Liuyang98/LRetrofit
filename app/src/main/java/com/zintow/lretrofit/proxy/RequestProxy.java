package com.zintow.lretrofit.proxy;

import android.util.Log;

import com.zintow.lretrofit.anno.GET;
import com.zintow.lretrofit.anno.POST;
import com.zintow.lretrofit.anno.Param;
import com.zintow.lretrofit.entity.RequestEntity;

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
        initApiEntity(cls);
    }

    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(object, args);
        }
        doCall(object, method, args);
        return null;
    }

    private void doCall(Object object, Method method, Object[] args) {
        RequestEntity requestEntity = paramMap.get(method.getName());
        List<String> paramList = requestEntity.getParamList();
        HashMap<String, Object> hashMap = new HashMap<>(paramList.size());
        for (int i = 0; i < paramList.size(); i++) {
            hashMap.put(paramList.get(i), args[i]);
        }

        Log.e(TAG, "地址: " + requestEntity.getUrl() + "  请求类型: " + requestEntity.getType() + "  参数: " + hashMap.toString());
    }

    /**
     * 初始化，遍历方法，获取接口注解信息
     */
    private void initApiEntity(Class<T> cls) {
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

            if (!list.isEmpty()) {
                paramMap.put(method.getName(), new RequestEntity(list, value, type));
            }
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
}
