package com.woi.danawacrawler.danawa;

import com.woi.danawacrawler.DanawaCrawlerApplication;
import com.woi.danawacrawler.danawa.category.*;
import com.woi.danawacrawler.danawa.crawlingItem.CrawlingItem;
import com.woi.danawacrawler.danawa.domain.DanawaProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;


public class PageCrawler {
    // 페이지에 접속해서 상품 정보를 크롤링하는 클래스
    private CrawlingItem crawlingItem;
    private ChromeDriver driver;
    private EntityManager em;


    private static final Logger logger = LoggerFactory.getLogger(DanawaCrawlerApplication.class);


    public PageCrawler(CrawlingItem crawlingItem, ChromeDriver driver, EntityManager em) {
        this.crawlingItem = crawlingItem;
        this.driver = driver;
        this.em = em;
    }

    @Override
    public String toString() {
        return "PageCrawler{" +
                "crawlingItem=" + crawlingItem +
                ", driver=" + driver +
                '}';
    }

    public void clickSamsungCheckbox() {
        try {
            WebElement samsungCheckbox = driver.findElement(By.id("searchMakerRep702"));
            samsungCheckbox.click();
        }catch (NoSuchElementException e) {
            return;
        }
    }

    public boolean canClickNextPage(Integer currentPage) {
        if (currentPage != 1 && currentPage % 10 == 1) {
            try {
                driver.findElement(By.xpath("//a[@class=\"edge_nav nav_next\"]")).click();
            }catch (NoSuchElementException e) {
                return false;
            }
        }
        else if (currentPage != 1 && currentPage % 10 != 0) {
            Integer nextPage = currentPage % 10;
            String xpath = String.format("//a[@class=\"num \"][%d]", nextPage);
            try {
                driver.findElement(By.xpath(xpath)).click();
            }catch (NoSuchElementException e) {
                return false;
            }
        }
        return true;
    }

    public ParseModel selectParsingModel() {
        ParseModel parseModel = null;
        if (crawlingItem.getCategory().contains("TV")) {
            parseModel = new ParseTVModel();
            logger.info("TV category");
        } else if (crawlingItem.getCategory().contains("에어컨")) {
            parseModel = new ParseAirConditionerModel();
            logger.info("Air conditioner category");
        } else if (crawlingItem.getCategory().contains("냉장고")) {
            parseModel = new ParseRefrigeratorModel();
            logger.info("WashingMachine category");
        } else if (crawlingItem.getCategory().contains("에어드레서")) {
            parseModel = new ParseAirDresserModel();
            logger.info("Airdressor category");
        } else if (crawlingItem.getCategory().contains("데스크탑")) {
            parseModel = new ParseDesktopModel();
            logger.info("Desktop category");
        } else if (crawlingItem.getCategory().contains("식기세척기")) {
            parseModel = new ParseDishWasherModel();
            logger.info("Dishwasher category");
        } else if (crawlingItem.getCategory().contains("건조기")) {
            parseModel = new ParseDryerModel();
            logger.info("Dryer category");
        } else if (crawlingItem.getCategory().contains("랩탑")) {
            parseModel = new ParseLaptopModel();
            logger.info("Laptop category");
        } else if (crawlingItem.getCategory().contains("모니터")) {
            parseModel = new ParseMonitorModel();
            logger.info("Monitor category");
        } else if (crawlingItem.getCategory().contains("오븐")) {
            parseModel = new ParseOvenModel();
            logger.info("Oven category");
        }else if (crawlingItem.getCategory().contains("프린터")) {
            parseModel = new ParsePrinterModel();
            logger.info("Printer category");
        } else if (crawlingItem.getCategory().contains("슈드레서")) {
            parseModel = new ParseShoesDresserModel();
            logger.info("ShoesDresser category");
        } else if (crawlingItem.getCategory().contains("청소기")) {
            parseModel = new ParseCleanerModel();
            logger.info("Cleaner category");
        } else if (crawlingItem.getCategory().contains("세탁기")) {
            parseModel = new ParseWashingMachineModel();
            logger.info("Washing machine category");
        } else {
            // 기본
            parseModel = new ParseDefaultModel();
        }
        return parseModel;
    }

    public void crawlSinglePage() throws InterruptedException {
        // 상품 목록을 가져옴
        WebElement productDiv = driver.findElement(By.xpath("//div[@class=\"main_prodlist main_prodlist_list\"]"));
        List<WebElement> products = productDiv.findElements(By.xpath("//ul[@class=\"product_list\"]/li"));
        for (WebElement product : products) {
            EntityTransaction tx = null;
            try {
                if (product.getAttribute("id") == null) {
                    continue;
                }
                // 광고 제품은 제외
                if (product.getAttribute("class").contains("prod_ad_item")) {
                    continue;
                }
                if (product.getAttribute("id").strip().startsWith("ad")) {
                    continue;
                }

                // 제품 이름 정보가 없는 경우 제외
                if (product.findElement(By.xpath("./div/div[2]/p/a")).getText().strip().isEmpty()) {
                    continue;
                }

                // TODO : 삼성전자 더 프리스타일 SP-LSBP3LAXKR + 블루스카이 5500 AX060B510ESD 이런 두개짜리 어떻게 처리할지 고민
                String productName = product.findElement(By.xpath("./div/div[2]/p/a")).getText();

                // 만약 중고라는 메세지가 있으면 무시
                if (productName.contains("중고")) {
                    continue;
                }

                // 모델명과 제품명을 파싱
                ParseModel parseModel = selectParsingModel();


                ModelNameAndProductName mp = parseModel.parseModelNameAndProductName(productName);

                List<WebElement> productPrices = product.findElements(By.xpath("./div/div[3]/ul/li"));
                for (WebElement productPrice : productPrices) {
                    // 마지막 부분에 있는 광고 제품은 제외

                    // display none인 element를 보이게 하기 (가격이 안보이는 경우가 있음)
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].style.display='block';", productPrice);

                    // 가격과 옵션 정보를 크롤링
                    // ./p[2]/a : 가격
                    // ./div/p : 옵션
                    String priceStr = productPrice.findElement(By.xpath("./p[2]/a")).getText(); // e.g. 1,000원
                    String optionStr = productPrice.findElement(By.xpath("./div/p")).getText();
                    Integer price;
                    try {
                        price = Integer.parseInt(priceStr.replaceAll("[^0-9]", ""));
                        // OptionStr에 등수가 적혀있으면 없애기
                        optionStr = optionStr.replaceAll("\\d+위", "");
                    } catch (NumberFormatException e) {
                        logger.error("error", e);
                        continue;
                    }

                    tx = em.getTransaction();
                    tx.begin();

                    DanawaProduct danawaProduct = new DanawaProduct();
                    danawaProduct.setProductName(mp.getProductName());
                    danawaProduct.setModelName(mp.getModelName());
                    danawaProduct.setPrice(price);
                    danawaProduct.setOption(optionStr);
                    danawaProduct.setCategory(crawlingItem.getCategory());
                    danawaProduct.setDateTime(java.time.LocalDateTime.now());

                    em.persist(danawaProduct);
                    logger.info("product name: {}, price: {}, option: {}", productName, price, optionStr);
                    tx.commit();
                }
            }catch (Exception e){
                logger.error("error", e);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
    }

    public void crawl() throws InterruptedException {
        driver.get(crawlingItem.getUrl());

        // Wait for the page to load completely
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

        // Select the samsung checkbox
        clickSamsungCheckbox();

        // TODO : 여러 페이지 처리 추가해야함.
        // 최대 페이지보다 input으로 받은 페이지가 더 크면 최대 페이지만큼만 처리
        // TODO : 여기좀 확인하기 https://github.com/ray5273/Danawa-Crawler/blob/feature/fork-action-test/danawa_crawler.py

        // 위에꺼 띄우는 시간이 너무 짧아서 문제가 생김
        Thread.sleep(5000);


        for (int currentPage = 1; currentPage <= crawlingItem.getNumPages(); currentPage++) {
            if (!canClickNextPage(currentPage)) {
                break;
            }
            Thread.sleep(8000);
            crawlSinglePage();
            Thread.sleep(8000);
        }



    }

}
