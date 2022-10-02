package com.vocabulary.learning.app.response;

public class IndividualResponse<T extends Object> extends StatusResponse {

    private T value;

    public IndividualResponse() {

    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}