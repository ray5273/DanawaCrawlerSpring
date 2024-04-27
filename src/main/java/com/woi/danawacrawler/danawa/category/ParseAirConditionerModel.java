package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

@Component
public class ParseAirConditionerModel implements ParseModel {
    // TV는 모델명이 반드시 마지막 split에 있음.
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        String[] split = productString.split(" ");


        String lastWord = split[split.length - 1];

        if (lastWord.contains("거치형") || lastWord.contains("매립형")) {
            lastWord = split[split.length - 2];
        }

        modelNameAndProductName.setModelName(lastWord);
        modelNameAndProductName.setProductName(productString);
        return modelNameAndProductName;
    }
}
