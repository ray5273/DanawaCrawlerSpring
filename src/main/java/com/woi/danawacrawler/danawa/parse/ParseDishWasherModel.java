package com.woi.danawacrawler.danawa.parse;


import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ParseDishWasherModel implements ParseModel {


    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        // 식기세척기는 DW로 모델명이 시작함
        // regex로 DW로 시작하는 모델명을 찾아서 모델명으로 설정

        Pattern pattern = Pattern.compile("(DW\\w+)");
        java.util.regex.Matcher matcher = pattern.matcher(productString);

        if (matcher.find()) {
            String modelName = matcher.group(1); // 첫 번째 그룹은 모델명
            modelNameAndProductName.setModelName(modelName);
            modelNameAndProductName.setProductName(productString);
        }
        return modelNameAndProductName;
    }
}
