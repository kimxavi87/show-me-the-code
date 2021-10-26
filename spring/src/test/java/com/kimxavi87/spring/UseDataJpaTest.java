package com.kimxavi87.spring;

import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

// JUnit 5에 Spring 지원을 활성화하도록 지시
@ExtendWith(SpringExtension.class)
// Spring Data 저장소와 같은 JPA 관련 구성 요소를 초기화하는 데 필요한 구성 요소를 포함하는 "슬라이스"만 포함
// @DataJpaTest 달린 모든 테스트 클래스 내의 모든 테스트 메서드 간에 공유 됨
// 테스트 간에 데이터베이스 상태가 그대로 유지되고 테스트가 서로 독립적으로 유지
@DataJpaTest
public class UseDataJpaTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(memberRepository).isNotNull();
    }
}
