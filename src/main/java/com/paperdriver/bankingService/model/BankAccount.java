package com.paperdriver.bankingService.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table( name = "bank_account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        })
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Long balance;

}
