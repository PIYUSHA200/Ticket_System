package com.example.ticketing.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorServices {
    private int vendorCount;
    private int releseRate;
    private List<Thread> vendorThreads = new ArrayList<>();
    private TicketPool ticketPool;

    public VendorServices() {
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public void vendorStart(int vendorCount) {
        for (int i = 1; i <= vendorCount; i++) {
            Thread thread = new Thread(new Vendor(ticketPool, i + 1, releseRate));
            vendorThreads.add(thread);
            thread.start();
        }
    }

    public void vendorStop() {
        for (Thread thread : vendorThreads) {
            thread.interrupt();
        }
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public void setReleseRate(int releseRate) {
        this.releseRate = releseRate;
    }
}

