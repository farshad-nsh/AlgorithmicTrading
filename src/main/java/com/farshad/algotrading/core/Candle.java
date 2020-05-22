package com.farshad.algotrading.core;

/**
 * @author farshad noravesh
 * @since version 1.0.0
 */
public class Candle {
    private String name;

    public Candle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
