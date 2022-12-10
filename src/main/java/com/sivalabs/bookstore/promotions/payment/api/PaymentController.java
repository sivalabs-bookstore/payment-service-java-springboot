package com.sivalabs.bookstore.promotions.payment.api;

import com.sivalabs.bookstore.promotions.payment.domain.PaymentRequest;
import com.sivalabs.bookstore.promotions.payment.domain.PaymentResponse;
import com.sivalabs.bookstore.promotions.payment.domain.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/validate")
    public PaymentResponse validate(@Valid @RequestBody PaymentRequest paymentRequest) {
        return paymentService.validate(paymentRequest);
    }
}
