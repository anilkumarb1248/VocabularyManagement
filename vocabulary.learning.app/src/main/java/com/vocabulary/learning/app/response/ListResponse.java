package com.vocabulary.learning.app.response;

import java.util.List;

public class ListResponse<T extends Object> extends StatusResponse {

    private List<T> values;

    public ListResponse() {
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
