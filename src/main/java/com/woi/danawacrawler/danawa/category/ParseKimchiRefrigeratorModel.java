package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ParseKimchiRefrigeratorModel implements ParseModel {


    // 냉장고는 아래 케이스는 해결하지 않음.
    // 삼성전자 비스포크 글램 RF85C90F1 + RQ49C9401 (화이트+핑크)
    // 삼성전자 비스포크 키친핏 글램 RR39A7605AP+ RZ32A7605AP+ RQ32C7612AP (색상선택형)

    // 아래 케이스들만 다룸
    // 삼성전자 코타 RT31CB5624C3 (화이트+차콜)
    // 삼성전자 비스포크 키친핏 코타+새틴 RF60C9012AP
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        Pattern pattern = Pattern.compile("(RQ\\w+)");
        java.util.regex.Matcher matcher = pattern.matcher(productString);

        if (matcher.find()) {
            String modelName = matcher.group(1); // 첫 번째 그룹은 모델명
            modelNameAndProductName.setModelName(modelName);
            modelNameAndProductName.setProductName(productString);
        }
        return modelNameAndProductName;
    }
}
