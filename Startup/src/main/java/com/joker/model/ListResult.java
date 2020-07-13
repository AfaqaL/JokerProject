package com.joker.model;

import java.util.List;

public class ListResult<T> {

    private List<T> list;

    private int version;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
