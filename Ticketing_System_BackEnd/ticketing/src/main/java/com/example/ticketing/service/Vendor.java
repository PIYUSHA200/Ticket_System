package com.example.ticketing.service;

import java.util.concurrent.atomic.AtomicInteger;

public class Vendor implements Runnable {
    private static final AtomicInteger vendorIdGenerator = new AtomicInteger(1);
    private int vendorId;
    private TicketPool ticketPool;
    private int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int vendorId, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorId = vendorIdGenerator.getAndIncrement();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                ticketPool.addTickets(vendorId, ticketReleaseRate);
            } catch (InterruptedException e) {
                System.out.println("Vendor " + vendorId + " interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }
}
