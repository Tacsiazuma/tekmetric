package com.interview.application.customer;

import com.interview.application.CustomerRepository;
import com.interview.domain.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListCustomers {

    private final CustomerRepository repository;

    public ListCustomers(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Customer> execute() {
        return repository.findAll();
    }
}
