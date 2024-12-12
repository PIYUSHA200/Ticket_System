package com.example.ticketing.service;

import org.springframework.stereotype.Service;

@Service
public class TicketPool {
    private int ticketCollection = 0;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int availableTickets;
    private int vendorCount;

    private VendorServices vendorServices;
    private CustomerServices customerServices;

    public TicketPool() {
    }

    public void setVendorServices(VendorServices vendorServices) {
        this.vendorServices = vendorServices;
    }

    public void setCustomerServices(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    public void configure(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized void addTickets(int availableTickets, int ticketReleaseRate) {
        while (ticketCollection >= maxTicketCapacity) {
            try {
                System.out.println("Vendor waiting, capacity is full!");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor interrupted while waiting.");
            }
        }

        int spaceLeft = maxTicketCapacity - ticketCollection;
        if (spaceLeft > 0) {
            int ticketsToAdd = Math.min(ticketReleaseRate, spaceLeft);
            ticketCollection += ticketsToAdd;
            System.out.println("Added " + ticketsToAdd + " tickets. Total tickets: " + ticketCollection);
            notifyAll();
        }
    }

    public synchronized void removeTickets(int customerId, int ticketsToRetrieve) {
        while (ticketCollection < ticketsToRetrieve) {
            try {
                System.out.println("Customer " + customerId + " waiting, not enough tickets available!");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer " + customerId + " interrupted while waiting.");
            }
        }

        ticketCollection -= ticketsToRetrieve;
        System.out.println("Customer " + customerId + " retrieved " + ticketsToRetrieve + " tickets. Remaining tickets: " + ticketCollection);
        notifyAll();
    }

    public void startSimulation() {
        vendorServices.vendorStart(vendorServices.getVendorCount());
        customerServices.customerStart();
    }

    public void stopSimulation() {
        vendorServices.vendorStop();
        customerServices.customerStop();
    }
}
