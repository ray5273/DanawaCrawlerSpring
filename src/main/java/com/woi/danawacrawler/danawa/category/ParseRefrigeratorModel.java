package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

@Component
public class ParseRefrigeratorModel implements ParseModel {


    // 냉장고는 아래 케이스는 해결하지 않음.
    // 삼성전자 비스포크 글램 RF85C90F1 + RQ49C9401 (화이트+핑크)
    // 삼성전자 비스포크 키친핏 글램 RR39A7605AP+ RZ32A7605AP+ RQ32C7612AP (색상선택형)

    // 아래 케이스들만 다룸
    // 삼성전자 코타 RT31CB5624C3 (화이트+차콜)
    // 삼성전자 비스포크 키친핏 코타+새틴 RF60C9012AP
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        // 괄호 및 괄호 안의 내용 제거
        String[] removeBracket = productString.split("\\(");
        String productName = removeBracket[0];

        // 괄호 제거후 마지막 단어가 모델명
        String lastWord = productName.split(" ")[productName.split(" ").length - 1];

        modelNameAndProductName.setModelName(lastWord);
        modelNameAndProductName.setProductName(productString);
        return modelNameAndProductName;
    }
}
