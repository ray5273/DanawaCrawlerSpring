package com.woi.danawacrawler;

import com.woi.danawacrawler.danawa.PageCrawler;
import com.woi.danawacrawler.danawa.crawlingItem.CrawlingItem;
import com.woi.danawacrawler.danawa.crawlingItem.CrawlingItemReaderService;
import com.woi.danawacrawler.danawa.domain.DanawaProduct;
import com.woi.danawacrawler.danawa.loadmodel.MatchModelName;
import com.woi.danawacrawler.danawa.loadmodel.MatchModelNameService;
import com.woi.danawacrawler.danawa.parse.ModelNameAndProductName;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.persistence.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class DanawaCrawlerApplication {
	private static final Logger logger = LoggerFactory.getLogger(DanawaCrawlerApplication.class);

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(DanawaCrawlerApplication.class, args);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductCatalogPU");
		EntityManager em = emf.createEntityManager();


		CrawlingItemReaderService crawlingItemReaderService = context.getBean(CrawlingItemReaderService.class);
		List<CrawlingItem> crawlingItems = crawlingItemReaderService.readCrawlingItems();

		ChromeDriver driver = context.getBean(ChromeDriver.class);
		MatchModelNameService matchModelNameService = context.getBean(MatchModelNameService.class);
		List<MatchModelName> modelNameAndProductNames = matchModelNameService.getMatchModelName();
//		for (MatchModelName modelNameAndProductName : modelNameAndProductNames) {
//			logger.info("modelName: {}, productName: {}", modelNameAndProductName.getModelName(), modelNameAndProductName.getProductName());
//		}


		for (CrawlingItem crawlingItem : crawlingItems) {
			logger.info("category: {}, url: {}, numPages: {}", crawlingItem.getCategory(), crawlingItem.getUrl(), crawlingItem.getNumPages());

			PageCrawler pageCrawler = new PageCrawler(crawlingItem, driver, em,modelNameAndProductNames);
			pageCrawler.crawl();

		}
		em.close();
		emf.close();

		System.exit(0);
	}

}
