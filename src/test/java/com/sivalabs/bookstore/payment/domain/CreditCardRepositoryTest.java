package com.sivalabs.bookstore.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:tc:postgresql:15-alpine:///dbname"})
class CreditCardRepositoryTest {
    private CreditCardRepository creditCardRepository;

    @Autowired private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.creditCardRepository = new CreditCardRepository(jdbcTemplate);
        jdbcTemplate.execute("delete from credit_cards");
        jdbcTemplate.execute(
                "insert into credit_cards(customer_name, card_number, cvv, expiry_month, expiry_year) values ('Siva', '1111222233334444', '123', 2, 2030)");
        jdbcTemplate.execute(
                "insert into credit_cards(customer_name, card_number, cvv, expiry_month, expiry_year) values ('Ramu', '1234123412341234', '123', 10, 2030)");
    }

    @Test
    void shouldGetCreditCardByCardNumber() {
        Optional<CreditCard> optionalCreditCard =
                creditCardRepository.findByCardNumber("1111222233334444");
        assertThat(optionalCreditCard).isNotEmpty();
        assertThat(optionalCreditCard.get().cardNumber()).isEqualTo("1111222233334444");
        assertThat(optionalCreditCard.get().cvv()).isEqualTo("123");
        assertThat(optionalCreditCard.get().expiryMonth()).isEqualTo(2);
        assertThat(optionalCreditCard.get().expiryYear()).isEqualTo(2030);
    }

    @Test
    void shouldReturnEmptyWhenCardNumberNotFound() {
        Optional<CreditCard> optionalCreditCard =
                creditCardRepository.findByCardNumber("1111111111111");
        assertThat(optionalCreditCard).isEmpty();
    }
}
