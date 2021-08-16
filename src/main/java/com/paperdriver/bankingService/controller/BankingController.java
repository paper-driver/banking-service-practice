package com.paperdriver.bankingService.controller;

import com.paperdriver.bankingService.model.BankAccount;
import com.paperdriver.bankingService.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
            logger.info("get item");
            return ResponseEntity.status(HttpStatus.OK).body(result.get());
        } catch(InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
