package com.kimxavi87.spring.es;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Setter
@Document(indexName = "test-index-#{@elasticsearchIndexPattern.getToday()}", createIndex = false)
public class TestLog {
    @Id
    private String id;

    @Field(type= FieldType.Text)
    private String title;

    @Field(type= FieldType.Ip)
    private String ip;

    @Field(type= FieldType.Keyword)
    private String data;
}
