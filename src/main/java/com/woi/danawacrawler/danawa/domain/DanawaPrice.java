package com.woi.danawacrawler.danawa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class DanawaPrice {

    @Id @GeneratedValue
    public Long Id;

    public String modelName;

    public LocalDateTime dateTime;

    public Integer price;

    public DanawaPrice() {
    }

}
