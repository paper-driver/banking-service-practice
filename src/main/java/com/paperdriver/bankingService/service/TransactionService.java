package com.paperdriver.bankingService.service;

import com.paperdriver.bankingService.model.BankAccount;
import com.paperdriver.bankingService.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

@Service
@Transactional
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ExecutorService executorService;

    public Future<BankAccount> getBankAccount(String email){
        logger.info("get account of " + email);
        return executorService.submit(() -> bankAccountRepository.findBankAccountByEmail(email));
    }

}
