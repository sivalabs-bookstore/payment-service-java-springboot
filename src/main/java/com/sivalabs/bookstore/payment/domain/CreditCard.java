package com.sivalabs.bookstore.payment.domain;

public record CreditCard(
        Long id,
        String customerName,
        String cardNumber,
        String cvv,
        int expiryMonth,
        int expiryYear) {}
