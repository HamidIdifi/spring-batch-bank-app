package com.example.bankspringbatch.exceptions;

public class ElementAlreadyExistException extends BusinessException{
    public ElementAlreadyExistException() {
    }

    public ElementAlreadyExistException(String defaultMessage, String key, Object[] args) {
        super(defaultMessage, key, args);
    }

    public ElementAlreadyExistException(String defaultMessage, Throwable cause, String key, Object[] args) {
        super(defaultMessage, cause, key, args);
    }

    public ElementAlreadyExistException(Throwable cause, String key, Object[] args) {
        super(cause, key, args);
    }
}
