package com.paperdriver.bankingService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private String fromEmail;
    private String toEmail;
    private Long amount;

}
