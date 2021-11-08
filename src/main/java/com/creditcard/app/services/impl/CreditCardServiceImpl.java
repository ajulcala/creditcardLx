package com.creditcard.app.services.impl;

import com.creditcard.app.models.dao.CreditCardDao;
import com.creditcard.app.models.documents.CreditCard;
import com.creditcard.app.models.dto.Customer;
import com.creditcard.app.services.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final WebClient webClient;
    private final WebClient webClientNumber;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    @Value("${config.base.gatewey}")
    private String url;

    @Value("${config.base.number}")
    private String urln;

    public CreditCardServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder().baseUrl(this.url).build();
        this.webClientNumber = WebClient.builder().baseUrl(this.urln).build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("customer");
    }

    @Autowired
    CreditCardDao dao;

    /**
     *
     * @param id
     * @return realiza una peticion
     */
    @Override
    public Mono<Customer> findCustomerById(String id) {
        log.info("buscando Customer por Id");
        return reactiveCircuitBreaker.run(webClient.get().uri(this.url,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Customer.class),
                throwable -> {return this.getDefaultCustomer();});
    }

    @Override
    public Mono<Customer> findCustomerByNumber(String number) {
        log.info("buscando Customer por documentNumber");
        return reactiveCircuitBreaker.run(webClientNumber.get().uri(this.urln,number).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Customer.class),
                throwable -> {return this.getDefaultCustomer();});
    }

    /**
     *
     * @return retorna un vacio
     */
    public Mono<Customer> getDefaultCustomer() {
        log.info("no encontro customer");
        Mono<Customer> customer = Mono.just(new Customer());
        return customer;
    }

    @Override
    public Mono<CreditCard> create(CreditCard t) {
        return dao.save(t);
    }

    @Override
    public Flux<CreditCard> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<CreditCard> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<CreditCard> update(CreditCard t) {
        return dao.save(t);
    }

    @Override
    public Mono<Boolean> delete(String t) {
        return dao.findById(t)
                .flatMap(credit -> dao.delete(credit).then(Mono.just(Boolean.TRUE)))
                .defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Flux<CreditCard> findCreditCardByCustomer(String id) {
        return dao.findByCustomerId(id);
    }
}
