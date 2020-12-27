package com.example.common;

public enum Currency {
    DOLLER("DOLLER"), EURO("EURO"), RIAL("RIAL"), BITCOIN("BITCOIN");
    private String value;

    Currency(String value) {
        this.value=value;
    }
}
