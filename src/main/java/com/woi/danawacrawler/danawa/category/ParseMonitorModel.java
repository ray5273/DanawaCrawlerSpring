package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParseMonitorModel implements ParseModel {

    // 노트북
    // 삼성전자 갤럭시북3 NT750XFG-KC51S
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {
        String lastWord = null;
        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        // 정규표현식 패턴 정의
        Pattern pattern = Pattern.compile("\\b[S|C|U|B|T|E|F]\\w{4,}\\b");
        Matcher matcher = pattern.matcher(productString);

        while (matcher.find()) {
            lastWord = matcher.group();
        }
        modelNameAndProductName.setModelName(lastWord);
        modelNameAndProductName.setProductName(productString);

        return modelNameAndProductName;
    }
}
