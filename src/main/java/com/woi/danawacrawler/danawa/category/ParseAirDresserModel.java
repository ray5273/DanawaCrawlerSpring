package com.woi.danawacrawler.danawa.category;


import org.springframework.stereotype.Component;

@Component
public class ParseAirDresserModel implements ParseModel {


    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {

        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, null);

        String[] split = productString.split(" ");
        modelNameAndProductName.setModelName(split[split.length - 1]);
        modelNameAndProductName.setProductName(productString);
        return modelNameAndProductName;
    }
}
