package com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData;

public class OpenFinDeskMessage {

    private String context;

    private String payload;

    public String getContext() {
        return context;
    }

    public String getPayload() {
        return payload;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public OpenFinDeskMessage() {
        this.context = "noContext";
        this.payload = "noPayload";
    }
}
