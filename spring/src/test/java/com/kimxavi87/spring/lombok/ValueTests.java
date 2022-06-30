package com.kimxavi87.spring.lombok;

import lombok.Value;
import org.junit.jupiter.api.Test;

public class ValueTests {

    @Test
    public void value() {
        Outback outback = new Outback("sinchon", "tooumba", 25000);
        String menu = outback.menu;
        System.out.println(menu);
        System.out.println(outback.getName());
    }

    @Value
    public static class Outback {
        String name, menu;
        int price;
    }
}
