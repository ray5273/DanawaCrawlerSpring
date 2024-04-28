package com.woi.danawacrawler.danawa.parse;


import com.woi.danawacrawler.danawa.loadmodel.MatchModelName;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParseWatchModel implements ParseModel {
    @Override
    public ModelNameAndProductName parseModelNameAndProductName(String productString) {
        return null;
    }

    // 스마트폰은
    @Override
    public ModelNameAndProductName parseModelNameAndProductNameByList(String productString, List<MatchModelName> matchModelNames) {
        ModelNameAndProductName modelNameAndProductName = new ModelNameAndProductName(null, productString);

        for (MatchModelName matchModelName : matchModelNames) {
            if (productString.contains(matchModelName.getProductName())) {
                modelNameAndProductName.setModelName(matchModelName.getModelName());
                modelNameAndProductName.setProductName(productString);
                break;
            }
        }
        return modelNameAndProductName;
    }
}
