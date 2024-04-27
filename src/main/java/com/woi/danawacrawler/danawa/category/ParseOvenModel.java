package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParseOvenModel implements ParseModel {

    // 오븐
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {
        String lastWord = null;
        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        // 정규표현식 패턴 정의
        Pattern pattern = Pattern.compile("\\b[A-Z][A-Z0-9]*\\b");
        //        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(productString);

        while (matcher.find()) {
            lastWord = matcher.group();
        }
        modelNameAndProductName.setModelName(lastWord);
        modelNameAndProductName.setProductName(productString);

        return modelNameAndProductName;
    }
}
