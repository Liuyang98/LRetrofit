package com.zintow.lretrofit.entity;

import java.util.HashMap;
import java.util.List;

public class CallInfo {
    private HashMap<String, Object> callInfoMap;
    private String url;
    private String type;

    private CallInfo(RequestEntity entity, Object[] args) {
        callInfoMap = new HashMap<>();
        this.url = entity.getUrl();
        this.type = entity.getType();
        List<String> paramList = entity.getParamList();
        for (int i = 0; i < paramList.size(); i++) {
            callInfoMap.put(paramList.get(i), args[i]);
        }
    }

    public static CallInfo newCall(RequestEntity entity, Object[] args) {
        return new CallInfo(entity, args);
    }

    public HashMap<String, Object> getCallInfoMap() {
        return callInfoMap;
    }

    public void setCallInfoMap(HashMap<String, Object> callInfoMap) {
        this.callInfoMap = callInfoMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CallInfo{" +
                "callInfoMap=" + callInfoMap +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
