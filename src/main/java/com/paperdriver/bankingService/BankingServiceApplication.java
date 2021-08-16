package com.paperdriver.bankingService;

import com.paperdriver.bankingService.thread.ThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@SpringBootApplication
@EnableTransactionManagement
public class BankingServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(BankingServiceApplication.class);

//	@Bean
//	public ThreadFactory threadFactory() {
//		return new ThreadManager("bankingService");
//	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(20);
	}

	public static void main(String[] args) {
		SpringApplication.run(BankingServiceApplication.class, args);
	}

}
