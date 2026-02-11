package com.interview.application.customer;

import com.interview.application.CustomerRepository;
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
        repository.findById(id).ifPresent(c -> repository.deleteById(id));
    }
}
