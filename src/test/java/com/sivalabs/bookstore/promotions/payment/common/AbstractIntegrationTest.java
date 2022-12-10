package com.sivalabs.bookstore.promotions.payment.common;

import com.sivalabs.bookstore.promotions.payment.domain.CreditCard;
import com.sivalabs.bookstore.promotions.payment.domain.CreditCardRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    protected static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        Startables.deepStart(postgres).join();
    }

    @Autowired private CreditCardRepository creditCardRepository;

    @LocalServerPort private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        creditCardRepository.deleteAllInBatch();
        creditCardRepository.save(new CreditCard(null, "Siva", "1111222233334444", "123", 2, 2030));
        creditCardRepository.save(new CreditCard(null, "John", "1234123412341234", "123", 3, 2030));
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
