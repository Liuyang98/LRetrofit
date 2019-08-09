package com.zintow.lretrofit.entity;

import java.util.List;

public class RequestEntity {
    private List<String> paramList;
    private String url;
    private String type;

    public RequestEntity(List<String> paramList, String url, String type) {
        this.paramList = paramList;
        this.url = url;
        this.type = type;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "paramList=" + paramList.toString() +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
