package com.kimxavi87.spring.player.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class TestController {

    @GetMapping("/testtest")
    @ResponseBody
    public Map<String, Integer> test() {
        Map<String, Integer> map = new HashMap<>();
        Optional<String> s = Optional.of("abc");
        Stream.of(1,23, 4).collect(Collectors.toSet());

        return null;
    }
}
