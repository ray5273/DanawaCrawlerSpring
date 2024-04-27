package com.woi.danawacrawler;


import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = "com.woi.danawacrawler",
        basePackageClasses = AutoAppConfig.class
)
public class AutoAppConfig {

    @PostConstruct
    public void setup(){
        WebDriverManager.chromedriver().setup();
    }

    @Bean
    public ChromeDriver chromeDriver(){
        return new ChromeDriver();
    }
}
