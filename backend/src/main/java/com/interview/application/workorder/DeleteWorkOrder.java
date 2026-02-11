package com.interview.application.workorder;

import com.interview.application.WorkOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteWorkOrder {

    private final WorkOrderRepository repository;

    public DeleteWorkOrder(WorkOrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(UUID id) {
        repository.findById(id).ifPresent(wo -> repository.deleteById(id));
    }
}
