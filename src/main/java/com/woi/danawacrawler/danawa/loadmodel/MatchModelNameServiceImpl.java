package com.woi.danawacrawler.danawa.loadmodel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


@Component
public class MatchModelNameServiceImpl implements MatchModelNameService {
    private final String filePath;


    public MatchModelNameServiceImpl(@Value("${target.file.samsung.path}") String filePath) {
        this.filePath = filePath;
    }

    public List<MatchModelName> getMatchModelName() {
        // csv 파일을 읽어서 크롤링할 상품 목록을 리턴하는 메서드
        List<MatchModelName> allModels = new ArrayList<>();

        try {
            // read csv
            Reader reader = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader().
                    parse(reader);

            for (CSVRecord record : records) {
                String modelName = record.get("모델명");
                String productName = record.get("기기명");
                allModels.add(new MatchModelName(productName.trim(),modelName.trim()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allModels;
    }
}
