package com.company.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    private static final Map<String, Circle> colorCircles = new HashMap<>();

    public Circle getCircle(String color) {
        Circle circle = colorCircles.get(color);

        if (circle == null) {
            Circle newCircle = new Circle(color);
            colorCircles.put(color, newCircle);
            return newCircle;
        } else {
            return circle;
        }
    }
}
