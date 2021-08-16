package com.paperdriver.bankingService.controller;

import com.paperdriver.bankingService.model.BankAccount;
import com.paperdriver.bankingService.model.PaymentRequest;
import com.paperdriver.bankingService.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/banking")
public class BankingController {

    private static final Logger logger = LoggerFactory.getLogger(BankingController.class);

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getAccount(@RequestParam(value = "email") String email) throws Exception, ExecutionException {
        Future<BankAccount> result = transactionService.getBankAccount(email);
        try{
            logger.info("[/get] get account");
            return ResponseEntity.status(HttpStatus.OK).body(result.get());
        } catch(InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity sendMoney(@Valid @RequestBody PaymentRequest body) throws Exception, ExecutionException {
        Future<Boolean> result = transactionService.sendMoney(body);
        try{
            logger.info("[/send] send money");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result.get());
        } catch(InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
