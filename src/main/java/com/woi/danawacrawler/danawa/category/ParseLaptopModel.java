package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParseLaptopModel implements ParseModel {

    // 노트북
    // 삼성전자 갤럭시북3 NT750XFG-KC51S
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        // 정규표현식 패턴 정의
        Pattern pattern = Pattern.compile("N(\\w+)(-\\w+)?");
        Matcher matcher = pattern.matcher(productString);

        if (matcher.find()) {
            String modelName = matcher.group(1); // 첫 번째 그룹은 모델명
            String modelSub = matcher.group(2); // 두 번째 그룹은 모델명 추가 스펙
            if (modelSub != null) {
                modelName = "N" + modelName + modelSub;
            }else{
                modelName = "N" + modelName;
            }

            modelNameAndProductName.setModelName(modelName);
            modelNameAndProductName.setProductName(productString);
        }

        return modelNameAndProductName;
    }
}
