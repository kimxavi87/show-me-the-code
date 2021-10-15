package com.kimxavi87.spring.player.entity;

import lombok.Builder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Contest {
    @Id @GeneratedValue
    private long id;

    private String name;
    private int days;
    private String region;

    @Builder
    public Contest(String name, int days) {
        this.name = name;
        this.days = days;
    }
}
