package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParsePrinterModel implements ParseModel {

    // 노트북
    // 삼성전자 갤럭시북3 NT750XFG-KC51S
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        // 정규표현식 패턴 정의
        Pattern pattern = Pattern.compile("\\b[A-Z]+-[A-Z0-9]+\\b");
        Matcher matcher = pattern.matcher(productString);

        if (matcher.find()) {
            String modelName = matcher.group(); // 전체 일치 항목 가져오기

            modelNameAndProductName.setModelName(modelName);
            modelNameAndProductName.setProductName(productString);
        }

        return modelNameAndProductName;
    }
}
