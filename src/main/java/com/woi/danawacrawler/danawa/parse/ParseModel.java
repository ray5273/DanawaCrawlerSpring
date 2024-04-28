package com.woi.danawacrawler.danawa.parse;

import com.woi.danawacrawler.danawa.loadmodel.MatchModelName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParseModel {

    public ModelNameAndProductName parseModelNameAndProductName(String productString);
    default public ModelNameAndProductName parseModelNameAndProductNameByList(String productString, List<MatchModelName> matchModelNames){
        return null;
    }
}

