package com.example.ticketing.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServices {
    private int customerCount;
    private int retrieveTickets;
    private List<Thread> customerThreads = new ArrayList<>();
    private TicketPool ticketPool;

    public CustomerServices(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public void customerStart() {
        for (int i = 1; i <= customerCount; i++) {
            Thread thread = new Thread(new Customer(ticketPool, i + 1, retrieveTickets));
            customerThreads.add(thread);
            thread.start();
        }
    }

    public void customerStop() {
        for (Thread thread : customerThreads) {
            thread.interrupt();
        }
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public void setRetrieveTickets(int retrieveTickets) {
        this.retrieveTickets = retrieveTickets;
    }
}
