package com.kimxavi87.spring.conf.hibernate;

import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

import java.time.Instant;

public class CreationTimestampLongTypeGeneration implements AnnotationValueGeneration<CreationTimestampLongType> {

    private ValueGenerator<Long> generator;

    @Override
    public void initialize(CreationTimestampLongType annotation, Class<?> propertyType) {
        generator = (session, owner) -> Instant.now().getEpochSecond();
    }

    @Override
    public GenerationTiming getGenerationTiming() {
        // INSERT : insert
        // ALWAYS : insert , update
        return GenerationTiming.INSERT;
    }

    @Override
    public ValueGenerator<?> getValueGenerator() {
        return generator;
    }

    @Override
    public boolean referenceColumnInSql() {
        return false;
    }

    @Override
    public String getDatabaseGeneratedReferencedColumnValue() {
        return null;
    }
}
