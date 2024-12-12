package com.example.ticketing.service;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer implements Runnable {
    private static final AtomicInteger customerIdGenerator = new AtomicInteger(1);
    private final int customerId;
    private TicketPool ticketPool;
    private final int customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerId, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerId = customerIdGenerator.getAndIncrement();
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                int ticketsToPurchase = customerRetrievalRate;
                ticketPool.removeTickets(customerId, ticketsToPurchase);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer " + customerId + " interrupted.");
            }
        }
        System.out.println("Customer " + customerId + " stopped.");
    }
}
