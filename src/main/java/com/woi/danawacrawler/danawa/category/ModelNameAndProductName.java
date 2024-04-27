package com.woi.danawacrawler.danawa.category;

public class ModelNameAndProductName {

        private String modelName;

        private String productName;

        public ModelNameAndProductName(String modelName, String productName) {
            this.modelName = modelName;
            this.productName = productName;
        }

        public String getModelName() {
            return modelName;
        }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
            return productName;
        }

        @Override
        public String toString() {
            return "ModelNameAndProductName{" +
                    "modelName='" + modelName + '\'' +
                    ", productName='" + productName + '\'' +
                    '}';
        }

}
