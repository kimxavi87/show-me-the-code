package com.kimxavi.example.service;

import com.kimxavi.example.Repository.BasketRepository;
import com.kimxavi.example.Repository.SoccerRepository;
import com.kimxavi.example.annotation.KimxaviInject;

public class SoccerService {

    @KimxaviInject
    public SoccerRepository soccerRepository;

    public BasketRepository basketRepository;
}
