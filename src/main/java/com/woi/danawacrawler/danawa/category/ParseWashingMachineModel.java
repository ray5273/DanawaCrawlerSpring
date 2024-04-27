package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

@Component
public class ParseWashingMachineModel implements ParseModel {
    // TV는 모델명이 반드시 마지막 split에 있음.
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        String[] split = productString.split(" ");
        modelNameAndProductName.setModelName(split[split.length - 1]);
        modelNameAndProductName.setProductName(productString);
        return modelNameAndProductName;
    }
}
