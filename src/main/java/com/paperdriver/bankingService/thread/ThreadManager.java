package com.paperdriver.bankingService.thread;

import com.paperdriver.bankingService.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadFactory;

public class ThreadManager implements ThreadFactory {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private int threadId;
    private String name;

    public ThreadManager(String name) {
        threadId = 1;
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + threadId);
        logger.info("created new thread with id : " + threadId +
                " and name : " + t.getName());
        threadId++;
        return t;
    }
}

