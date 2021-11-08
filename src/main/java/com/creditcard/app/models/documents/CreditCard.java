package com.creditcard.app.models.documents;

import com.creditcard.app.models.dto.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@Builder
@Document("CreditCard")
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Id
    private String id;
    private String cardNumber;
    private Customer customer;
    private Double limitCredit;
    private LocalDate expiration;
    private LocalDateTime createAt;
}
