package com.interview.application.customer;

import com.interview.application.CustomerRepository;
import com.interview.domain.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateCustomer {

    private final CustomerRepository repository;

    public CreateCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Customer execute(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        return repository.save(customer);
    }
}
