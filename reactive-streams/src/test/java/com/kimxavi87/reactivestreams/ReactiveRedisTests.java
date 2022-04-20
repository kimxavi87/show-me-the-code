package com.kimxavi87.reactivestreams;

import com.github.fppt.jedismock.RedisServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ReactiveRedisTests {

    static ReactiveRedisTemplate<String, Employee> reactiveRedisTemplate;
    static RedisServer redisServer;
    static LettuceConnectionFactory redisConnectionFactory;

    @BeforeAll
    static void setUp() throws IOException {

        redisServer = RedisServer.newRedisServer();
        redisServer.start();

        System.out.println("Mock Redis Server start...");

        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisServer.getHost(), redisServer.getBindPort());
        factory.afterPropertiesSet();

        System.out.println("RedisConnectionFactory is Created");

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Employee> valueSerializer =
                new Jackson2JsonRedisSerializer<>(Employee.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Employee> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Employee> context =
                builder.value(valueSerializer).build();
        reactiveRedisTemplate = new ReactiveRedisTemplate<>(factory, context);
        redisConnectionFactory = factory;
        System.out.println("reactiveRedisTemplate is Created");
    }

    @AfterAll
    static void tearDown() {
        redisServer.stop();
        redisServer = null;

        redisConnectionFactory.destroy();
        redisConnectionFactory = null;
    }

    @Test
    void checkFieldsAreNotNull() {
        assertThat(reactiveRedisTemplate).isNotNull();
        assertThat(redisServer).isNotNull();
    }

    @Test
    void givenEmployeeData_whenPutValueToRedis_thenSuccess() {
        String keyPrefix = "employee:";
        Employee employee = new Employee("park-ji-sung", 45);
        reactiveRedisTemplate.opsForValue().set(keyPrefix + employee.getName(), employee).block();
        Employee gottenEmployee = reactiveRedisTemplate.opsForValue().get(keyPrefix + employee.getName()).block();
        assertThat(gottenEmployee).isEqualTo(employee);
    }

    @Test
    public void whenNotFoundValue_thenCantGetNext() {
        Employee hello = reactiveRedisTemplate.opsForValue()
                .get("hello")
                .doOnNext(employee -> log.info("doOnNext : {}", employee))
                .doOnSuccess(employee -> log.info("doOnSuccess : {}", employee))
                .map(employee -> {
                    log.info("map : {}", employee);
                    return employee;
                })
                .block();
        assertThat(hello).isNull();
    }

    static class Employee {
        private String name;
        private int age;

        public Employee() {
        }

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return age == employee.age &&
                    Objects.equals(name, employee.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }
}
