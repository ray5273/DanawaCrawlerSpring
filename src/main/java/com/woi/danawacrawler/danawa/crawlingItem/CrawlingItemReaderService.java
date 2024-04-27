package com.woi.danawacrawler.danawa.crawlingItem;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrawlingItemReaderService {

    public List<CrawlingItem> readCrawlingItems();
}
