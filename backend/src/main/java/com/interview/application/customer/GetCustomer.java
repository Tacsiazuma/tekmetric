package com.interview.application.customer;

import com.interview.application.CustomerRepository;
import com.interview.application.EntityNotFoundException;
import com.interview.domain.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetCustomer {

    private final CustomerRepository repository;

    public GetCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Customer execute(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
    }
}
