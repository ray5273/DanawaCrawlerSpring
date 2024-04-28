package com.woi.danawacrawler.danawa.parse;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParseDesktopModel implements ParseModel {

    // Desktop 케이스
    // 삼성전자 데스크탑5 DM500TEZ-GAMING RTX3060 i7
    // 삼성전자 데스크탑5 DM500TEA-i9AL-Win11Home
    // 삼성전자 데스크탑5 DM500TEA-i9AL-RTX4070Ti
    // 삼성전자 데스크탑5 DM500TEA-i5 Desk
    // 삼성전자 데스크탑5 DM500TEA-AC71B 모니터 패키지
    // 삼성전자 데스크탑5 DM500TEA-A58A Win11Pro
    // 삼성전자 데스크탑5 DM500TEA-A58A RTX3050
    // 삼성전자 DM400TFZ-ZIPSB-11PRO
    // 삼성전자 다나와인증 DB400S7B i5-6 -> 케이스 제외
    
    // D영어-모델명-스펙 느낌으로 작성됨


    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        // 정규표현식 패턴 정의
        Pattern pattern = Pattern.compile("(\\w+-\\w+)(-\\w+)?");
        Matcher matcher = pattern.matcher(productString);

        if (matcher.find()) {
            String modelName = matcher.group(1); // 첫 번째 그룹은 모델명
            String spec = matcher.group(2); // 두 번째 그룹은 스펙
            if (spec != null) {
                modelName = modelName + spec;
            }

            modelNameAndProductName.setModelName(modelName);
            modelNameAndProductName.setProductName(productString);
        }

        return modelNameAndProductName;
    }
}
