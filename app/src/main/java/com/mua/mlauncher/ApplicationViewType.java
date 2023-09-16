package com.mua.mlauncher;

public enum ApplicationViewType {

    TYPE_DEFAULT(0),

    TYPE_CONTENT(10),
    TYPE_HEADER(11),
    ;

    int id;

    ApplicationViewType(int id) {
        this.id = id;
    }
}