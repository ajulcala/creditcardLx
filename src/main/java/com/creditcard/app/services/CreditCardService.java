package com.creditcard.app.services;

import com.creditcard.app.models.documents.CreditCard;
import com.creditcard.app.models.dto.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {
    Mono<CreditCard> create(CreditCard t);
    Flux<CreditCard> findAll();
    Mono<CreditCard> findById(String id);
    Mono<CreditCard> update(CreditCard t);
    Mono<Boolean> delete(String t);
    Mono<Customer> findCustomerById(String id);
    Mono<Customer> findCustomerByNumber(String number);
    Flux<CreditCard> findCreditCardByCustomer(String id);
}
