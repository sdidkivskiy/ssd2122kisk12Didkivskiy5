package com.example.ATM_test.exceptions;

import java.util.Objects;

public class AtmException extends RuntimeException {
    private String message;

    public AtmException (String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtmException that = (AtmException) o;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "AtmException{" +
                "message='" + message + '\'' +
                '}';
    }
}