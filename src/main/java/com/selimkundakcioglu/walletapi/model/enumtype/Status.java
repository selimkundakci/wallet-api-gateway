package com.selimkundakcioglu.walletapi.model.enumtype;

public enum Status {
    DELETED(-1),
    PASSIVE(0),
    ACTIVE(1);

    private final Integer value;

    Status(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}