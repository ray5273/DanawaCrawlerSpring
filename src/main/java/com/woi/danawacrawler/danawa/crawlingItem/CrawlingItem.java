package com.woi.danawacrawler.danawa.crawlingItem;

public class CrawlingItem {

    private String category;

    private String url;

    private Integer numPages;

    public CrawlingItem(String category, String url, Integer numPages) {
        this.category = category;
        this.url = url;
        this.numPages = numPages;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public Integer getNumPages() {
        return numPages;
    }

    @Override
    public String toString() {
        return "CrawlingItem{" +
                "category='" + category + '\'' +
                ", url='" + url + '\'' +
                ", numPages=" + numPages +
                '}';
    }
}
