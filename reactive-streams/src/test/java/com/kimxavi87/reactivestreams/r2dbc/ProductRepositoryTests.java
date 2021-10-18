package com.kimxavi87.reactivestreams.r2dbc;

import com.kimxavi87.reactivestreams.product.Product;
import com.kimxavi87.reactivestreams.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class ProductRepositoryTests {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private ProductRepository repository;

    @Test
    public void testR2dbc() {

        Product product = new Product("nike-shoes");
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(databaseClient, H2Dialect.INSTANCE);
        template.insert(Product.class)
                .using(product)
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }
}
