package com.example.ticketing.controller;

import com.example.ticketing.userdata.Configuration;
import com.example.ticketing.userdata.CustomerData;
import com.example.ticketing.userdata.VendorData;
import com.example.ticketing.service.CustomerServices;
import com.example.ticketing.service.VendorServices;
import org.springframework.web.bind.annotation.*;
import com.example.ticketing.service.TicketPool;

@RestController
@RequestMapping("/ticketing")
public class TicketController {

    private final TicketPool ticketPool;
    private final VendorServices vendorServices;
    private final CustomerServices customerServices;

    public TicketController(TicketPool ticketPool, VendorServices vendorServices, CustomerServices customerServices) {
        this.ticketPool = ticketPool;
        this.vendorServices = vendorServices;
        this.customerServices = customerServices;
        this.ticketPool.setVendorServices(vendorServices);
        this.ticketPool.setCustomerServices(customerServices);
        this.vendorServices.setTicketPool(ticketPool);
    }

    @PostMapping("/config")
    public String configure(@RequestBody Configuration configuration) {
        ticketPool.configure(configuration.getTotalTickets(), configuration.getTicketReleaseRate(), configuration.getCustomerRetrievalRate(), configuration.getMaxTicketCapacity());
        vendorServices.setReleseRate(configuration.getTicketReleaseRate());
        customerServices.setRetrieveTickets(configuration.getCustomerRetrievalRate());
        return "Configuration successful";
    }

    @PostMapping("/start")
    public String start() {
        ticketPool.startSimulation();
        return "Starting ticketing service";
    }

    @PostMapping("/stop")
    public String stop() {
        ticketPool.stopSimulation();
        return "Stopping ticketing service";
    }

    @PostMapping("/customer-count")
    public String setCustomerCount(@RequestBody CustomerData customerData) {
        customerServices.setCustomerCount(customerData.getCustomerCount());
        return "Customer count set to " + customerData.getCustomerCount();
    }

    @PostMapping("/vendor-count")
    public String setVendorCount(@RequestBody VendorData vendorData) {
        vendorServices.setVendorCount(vendorData.getVendorCount());
        return "Vendor count set to " + vendorData.getVendorCount();
    }
}

