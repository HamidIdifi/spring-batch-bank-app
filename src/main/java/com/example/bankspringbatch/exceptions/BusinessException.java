package com.example.bankspringbatch.exceptions;


public class BusinessException extends RuntimeException {


    private String key;
    private transient Object[] args;

    public BusinessException() {
        super();
    }

    public BusinessException(String defaultMessage, String key, Object[] args) {
        super(defaultMessage);
        this.key = key;
        this.args = args;
    }

    public BusinessException(String defaultMessage, Throwable cause, String key, Object[] args) {
        super(defaultMessage, cause);
        this.key = key;
        this.args = args;
    }

    public BusinessException(final Throwable cause, final String key, final Object[] args) {
        super(cause);
        this.key = key;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public Object[] getArgs() {
        return args;
    }

}
