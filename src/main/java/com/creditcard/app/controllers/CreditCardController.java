package com.creditcard.app.controllers;

import com.creditcard.app.models.documents.CreditCard;
import com.creditcard.app.services.CreditCardService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * * My <b>controller</b>.
 * estructura para las rutas del proyecto
 *
 * @RestController annotation
 */
@Slf4j
@RestController
@RequestMapping("/creditcard")
public class CreditCardController {
  @Autowired
  CreditCardService service;

  @GetMapping("list")
  public Flux<CreditCard> findAll() {
    return service.findAll();
  }

  @GetMapping("/find/{id}")
  public Mono<CreditCard> findById(@PathVariable String id) {
    return service.findById(id);
  }

  @GetMapping("/findCreditCards/{id}")
  public Flux<CreditCard> findCreditCardByCustomer(@PathVariable String id) {
    return service.findCreditCardByCustomer(id);
  }

  @PostMapping("/create")
  public Mono<ResponseEntity<CreditCard>> create(@RequestBody CreditCard creditCard) {
    return service.findCustomerByNumber(creditCard.getCustomer().getDocumentNumber()).flatMap(c -> {
      creditCard.setCustomer(c);
      creditCard.setCreateAt(LocalDateTime.now());
      return service.create(creditCard);
    })
    .map(savedCustomer -> new ResponseEntity<>(savedCustomer, HttpStatus.CREATED))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<CreditCard>> update(@RequestBody CreditCard creditCard) {
    log.info("buscando tarjeta de credito");
    return service.findById(creditCard.getId())
      .flatMap(cc -> service.findCustomerById(creditCard.getCustomer().getId())
        .flatMap(customer -> {
          creditCard.setCustomer(customer);
          log.info("tarjeta de credito actualizada");
          return service.update(creditCard);
        }))
    .map(cc -> new ResponseEntity<>(cc, HttpStatus.CREATED))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<String>> delete(@PathVariable String id) {
    return service.delete(id)
     .filter(deleteCustomer -> deleteCustomer)
     .map(deleteCustomer -> new ResponseEntity<>("CreditCard Deleted", HttpStatus.ACCEPTED))
     .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
