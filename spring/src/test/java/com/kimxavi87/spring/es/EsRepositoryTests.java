package com.kimxavi87.spring.es;

import com.kimxavi87.spring.es.repository.EsTestRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.IOException;

@Slf4j
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

    @Disabled("ES 구축 후에 테스트")
    @Test
    public void createIndex() {
        RestHighLevelClient client = createClient();

        CreateIndexRequest request = new CreateIndexRequest("test-index");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .build());

        try {
            client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("error", e);
        }

    }

    @Disabled("ES 구축 후에 테스트")
    @Test
    public void searchTest() throws IOException {
        // 850만 데이터 4분
        // shard=2 3m 45s
        // shard=3 3m 58s
        RestHighLevelClient client = createClient();

        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(5L));
        SearchRequest searchRequest = new SearchRequest("test-index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(10000);

        searchRequest.scroll(scroll);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = response.getScrollId();
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("first hits : " + hits.length + " ID: " + scrollId);

        while (hits.length > 0) {
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
            searchScrollRequest.scroll(scroll);
            response = client.scroll(searchScrollRequest, RequestOptions.DEFAULT);

            scrollId = response.getScrollId();
            hits = response.getHits().getHits();
            System.out.println("hits : " + hits.length);
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();

    }

    @Disabled("ES 구축 후에 테스트")
    @Test
    public void save() {
        // 850만개 25분
        // 4 request 로 바꾸니까 7분
        // 8 request 4분
        // 2 shard 3분
        // 3 shard 3m 23s
        // 1 shard 도 3m 상관 없는듯
    }

    private RestHighLevelClient createClient() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo("localhost:10200")
                .withBasicAuth("admin", "admin")
                .build();

        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
        return client;
    }
}
