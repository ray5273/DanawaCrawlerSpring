package com.woi.danawacrawler.danawa.category;

import org.springframework.stereotype.Service;

@Service
public interface ParseModel {

    public ModelNameAndProductName parseModelNameAndProductName(String productString);
}

