package com.interview.application.workorder;

import com.interview.application.EntityNotFoundException;
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
        if (!repository.findById(id).isPresent()) {
            throw new EntityNotFoundException("WorkOrder not found: " + id);
        }
        repository.deleteById(id);
    }
}
