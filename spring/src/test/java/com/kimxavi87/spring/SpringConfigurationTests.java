package com.kimxavi87.spring;

import com.kimxavi87.spring.player.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringConfigurationTests {

    @Test
    public void proxyBeanMethods() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);

        TestConfiguration bean = applicationContext.getBean(TestConfiguration.class);
        // configuration 객체가 cglib에 의해 프록시 객체로 저장됨
        System.out.println(bean);

        // bean 메서드가 singleton을 보존하는 방향으로 가서 객체가 같음
        assertThat(bean.member()).isEqualTo(bean.member());
    }

    @Test
    public void proxyBeanMethodsFalse() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProxyBeanMethodsFalseConfiguration.class);

        ProxyBeanMethodsFalseConfiguration bean = applicationContext.getBean(ProxyBeanMethodsFalseConfiguration.class);
        // configuration bean 가져와도 객체 그 자체가 저장됨
        System.out.println(bean);

        // bean 메서드 자체가 그냥 메서드와 같다, 즉 다름
        assertThat(bean.member()).isNotEqualTo(bean.member());
    }

    @Configuration
    public static class TestConfiguration {
        @Bean
        public Member member() {
            return new Member("park");
        }
    }

    @Configuration(proxyBeanMethods = false)
    public static class ProxyBeanMethodsFalseConfiguration {
        @Bean
        public Member member() {
            return new Member("noproxy-park");
        }
    }
}
