package com.kimxavi87.spring.es;

import com.kimxavi87.spring.es.repository.EsTestRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EsRepositoryTests {

    @Autowired
    EsTestRepository esTestRepository;

    @Disabled("ES 구축 후에 테스트")
    @Test
    public void test() {
        TestLog testLog = new TestLog();
        testLog.setData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        testLog.setIp("233.233.233.233");
        testLog.setTitle("title");
        esTestRepository.save(testLog);
    }
}
