package com.sivalabs.bookstore.payment.domain;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {
    private final CreditCardRepository creditCardRepository;

    public PaymentService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Transactional(readOnly = true)
    public PaymentResponse validate(PaymentRequest request) {
        Optional<CreditCard> creditCardOptional =
                creditCardRepository.findByCardNumber(request.cardNumber());
        if (creditCardOptional.isEmpty()) {
            return PaymentResponse.REJECTED;
        }
        CreditCard creditCard = creditCardOptional.get();
        if (creditCard.cvv().equals(request.cvv())
                && creditCard.expiryMonth() == request.expiryMonth()
                && creditCard.expiryYear() == request.expiryYear()) {
            return PaymentResponse.ACCEPTED;
        }
        return PaymentResponse.REJECTED;
    }
}
