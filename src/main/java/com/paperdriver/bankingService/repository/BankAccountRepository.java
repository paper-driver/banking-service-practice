package com.paperdriver.bankingService.repository;

import com.paperdriver.bankingService.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    BankAccount findBankAccountByEmail(String email);

    Boolean existsBankAccountByEmail(String email);


}
