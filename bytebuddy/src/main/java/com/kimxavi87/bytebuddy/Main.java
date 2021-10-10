package com.kimxavi87.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Main {
    public static void main(String[] args) {
        try {
            new ByteBuddy()
                    .redefine(Moja.class)
                    .method(named("pollOut")).intercept(FixedValue.value("Rabbit"))
                    .make().saveIn(new File("./build/classes/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Moja moja = new Moja();
//        System.out.println(moja.pollOut());
    }
}
