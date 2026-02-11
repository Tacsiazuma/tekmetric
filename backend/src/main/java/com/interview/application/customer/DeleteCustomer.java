package com.interview.application.customer;

import com.interview.application.CustomerRepository;
import com.interview.application.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteCustomer {

    private final CustomerRepository repository;

    public DeleteCustomer(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(UUID id) {
        if (!repository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Customer not found: " + id);
        }
        repository.deleteById(id);
    }
}
