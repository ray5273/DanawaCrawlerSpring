package com.woi.danawacrawler.danawa.crawlingItem;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


@Component
public class CrawlingItemReaderServiceImpl implements CrawlingItemReaderService {
    private final String filePath;


    public CrawlingItemReaderServiceImpl(@Value("${target.file.path}") String filePath) {
        this.filePath = filePath;
    }

    public List<CrawlingItem> readCrawlingItems() {
        // csv 파일을 읽어서 크롤링할 상품 목록을 리턴하는 메서드
        List<CrawlingItem> crawlingItems = new ArrayList<>();

        try {
            // read csv
            Reader reader = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader().
                    parse(reader);

            for (CSVRecord record : records) {
                String url = record.get("url");
                String category = record.get("category");
                Integer numPages = Integer.parseInt(record.get("numPages"));
                CrawlingItem crawlingItem = new CrawlingItem(category,url,numPages);
                crawlingItems.add(crawlingItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return crawlingItems;
    }
}
