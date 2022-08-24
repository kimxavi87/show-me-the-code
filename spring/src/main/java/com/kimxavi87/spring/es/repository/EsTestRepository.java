package com.kimxavi87.spring.es.repository;

import com.kimxavi87.spring.es.TestLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsTestRepository extends ElasticsearchRepository<TestLog, String> {
}

