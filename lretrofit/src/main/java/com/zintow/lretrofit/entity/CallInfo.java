package com.zintow.lretrofit.entity;

import java.util.HashMap;
import java.util.List;

public class CallInfo {
    private HashMap<String, Object> callInfoMap;
    private String baseUrl;
    private String suffixUrl;
    private String type;

    private CallInfo(String baseUrl, RequestEntity entity, Object[] args) {
        callInfoMap = new HashMap<>();
        this.baseUrl = baseUrl;
        this.suffixUrl = entity.getUrl();
        this.type = entity.getType();
        List<String> paramList = entity.getParamList();
        for (int i = 0; i < paramList.size(); i++) {
            callInfoMap.put(paramList.get(i), args[i]);
        }
    }

    public static CallInfo newCall(String baseUrl, RequestEntity entity, Object[] args) {
        return new CallInfo(baseUrl, entity, args);
    }

    public HashMap<String, Object> getCallInfoMap() {
        return callInfoMap;
    }

    public String getUrl() {
        return baseUrl + suffixUrl;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CallInfo{" +
                "callInfoMap=" + callInfoMap +
                ", url='" + getUrl() + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
