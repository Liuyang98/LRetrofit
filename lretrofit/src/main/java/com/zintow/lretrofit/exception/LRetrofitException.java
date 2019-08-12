package com.zintow.lretrofit.exception;

public class LRetrofitException extends RuntimeException {

    private static final long serialVersionUID = -2912559384646531479L;

    public LRetrofitException(String detailMessage) {
        super(detailMessage);
    }

    public LRetrofitException(Throwable throwable) {
        super(throwable);
    }

    public LRetrofitException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}