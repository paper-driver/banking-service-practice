package com.paperdriver.bankingService.service;

import com.paperdriver.bankingService.model.BankAccount;
import com.paperdriver.bankingService.model.PaymentRequest;
import com.paperdriver.bankingService.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ExecutorService executorService;

    @Transactional
    public Future<BankAccount> getBankAccount(String email){
        logger.info("get account of " + email);
        return executorService.submit(() -> retrieveAccount(email));
    }

    @Transactional
    public Future<Boolean> sendMoney(PaymentRequest paymentRequest){
        logger.info(paymentRequest.getFromEmail() + " send money to " + paymentRequest.getToEmail());
        return executorService.submit(() -> processPayment(paymentRequest));
    }

    public BankAccount retrieveAccount(String email){
        if(bankAccountRepository.existsBankAccountByEmail(email)){
            return bankAccountRepository.findBankAccountByEmail(email);
        }else{
            return null;
        }
    }

    public boolean processPayment(PaymentRequest paymentRequest){
        if(bankAccountRepository.existsBankAccountByEmail(paymentRequest.getFromEmail()) && bankAccountRepository.existsBankAccountByEmail(paymentRequest.getToEmail())) {
            BankAccount fromAccount = bankAccountRepository.findBankAccountByEmail(paymentRequest.getFromEmail());
            BankAccount toAccount = bankAccountRepository.findBankAccountByEmail(paymentRequest.getToEmail());
            if(fromAccount.getBalance() > paymentRequest.getAmount()){
                fromAccount.setBalance(fromAccount.getBalance() - paymentRequest.getAmount());
                toAccount.setBalance(toAccount.getBalance() + paymentRequest.getAmount());
                bankAccountRepository.saveAndFlush(fromAccount);
                bankAccountRepository.saveAndFlush(toAccount);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
