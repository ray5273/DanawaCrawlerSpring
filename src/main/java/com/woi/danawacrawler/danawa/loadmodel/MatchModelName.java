package com.woi.danawacrawler.danawa.loadmodel;

public class MatchModelName {

    private String productName;

    private String modelName;

    public MatchModelName(String productName, String modelName) {
        this.productName = productName;
        this.modelName = modelName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
