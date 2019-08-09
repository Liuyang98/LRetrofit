package com.zintow.lretrofit.interfaces;

public interface RequestResultListenner {

    void onError(Throwable e);

    void onNext(Object o);
}
