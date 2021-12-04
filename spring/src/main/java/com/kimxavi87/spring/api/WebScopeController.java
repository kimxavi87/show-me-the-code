package com.kimxavi87.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Random;

@RequestMapping("/scope")
@RestController
public class WebScopeController {
    @Autowired
    private ScopeComponent scopeComponent;
    @Autowired
    private SessionScopeComponent sessionScopeComponent;

    @GetMapping
    public ResponseEntity<Integer> reqeust() {
        System.out.println("request : " + scopeComponent.getAge());
        // Class 확인해보면 CGLIB 에 의한 프록시 객체인 것 확인할 수 있음
        // Scope 제거하고 해보면? 그냥 해당 클래스로 나옴

        // @SessionScope 해도 잘 작동함
        System.out.println(scopeComponent.getClass());

        System.out.println("request : " + sessionScopeComponent.getAge());
        System.out.println(sessionScopeComponent.getClass());


        return ResponseEntity.ok(scopeComponent.getAge());
    }

    @SessionScope
    @Component
    public static class ScopeComponent {
        private int age;

        public ScopeComponent() {
            Random random = new Random();
            this.age = random.nextInt();
        }

        public int getAge() {
            return age;
        }
    }

    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Component
    public static class SessionScopeComponent {
        private int age;

        public SessionScopeComponent() {
            Random random = new Random();
            this.age = random.nextInt();
        }

        public int getAge() {
            return age;
        }
    }
}
