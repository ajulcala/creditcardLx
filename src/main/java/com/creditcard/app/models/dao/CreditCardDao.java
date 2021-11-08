package com.creditcard.app.models.dao;

import com.creditcard.app.models.documents.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditCardDao  extends ReactiveMongoRepository <CreditCard, String> {
    Flux<CreditCard> findByCustomerId(String id);
}
